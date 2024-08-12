#ifdef ESP8266
  #include <ESP8266WiFi.h>     // ESP8266 WiFi library
#else
  #include <WiFi.h>            // ESP32 WiFi library
#endif
#include <SoftwareSerial.h>
#include <Wire.h>
#include <PubSubClient.h>
#include "DHT.h"             // DHT sensor library

#define LDR_PIN 15
#define SOIL_MOISTURE_PIN 2 
#define DHT_PIN 4
#define DHT_TYPE DHT22

#define WIFI_SSID "EOSC-27-5"
#define WIFI_PASSWORD "Style@882713"
#define MQTT_BROKER "192.168.100.44"

#define HUMIDITY_FEED "sensor/DHT22/humidity"
#define TEMP_CELSIUS_FEED "sensor/DHT22/temperature_celsius"
#define TEMP_FAHRENHEIT_FEED "sensor/DHT22/temperature_fahrenheit"
#define SOIL_MOISTURE_FEED "sensor/soil_moisture"
#define LDR_FEED "sensor/ldr"
#define PH_FEED "sensor/ph"
#define NPK_NITROGEN_FEED "sensor/NPK/nitrogen"
#define NPK_PHOSPHORUS_FEED "sensor/NPK/phosphorous"
#define NPK_POTASSIUM_FEED "sensor/NPK/potassium"

// pH sensor variables
const int PH_SENSOR_PIN = A0;
float phSensorValue;
float phVoltage = 0;

// NPK sensor and RS485 pins
SoftwareSerial npkSerial(27, 26);  // RX, TX
int RS485_DE = 32;
int RS485_RE = 33;

// DHT sensor instance
DHT dhtSensor(DHT_PIN, DHT_TYPE);

WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

void connectToWiFi() {
  delay(10);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(WIFI_SSID);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("Connected to WiFi");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void reconnectMQTT() {
  while (!mqttClient.connected()) {
    Serial.print("Connecting to MQTT broker...");
    
    if (mqttClient.connect("ESP8266Client")) {
      Serial.println("Connected");
    } else {
      Serial.print("Failed, rc=");
      Serial.print(mqttClient.state());
      Serial.println(" Retrying in 5 seconds");
      delay(5000);
    }
  }
}

void setup() {
  Serial.begin(9600);
  Serial.println("Initializing...");

  dhtSensor.begin();
  pinMode(PH_SENSOR_PIN, INPUT);
  delay(1000);

  // NPK sensor setup
  npkSerial.begin(4800);
  pinMode(RS485_DE, OUTPUT);
  pinMode(RS485_RE, OUTPUT);
  digitalWrite(RS485_DE, LOW);
  digitalWrite(RS485_RE, LOW);

  connectToWiFi();
  mqttClient.setServer(MQTT_BROKER, 1883);
}

void readSensorsAndPublish() {
  // DHT sensor readings
  float humidity = dhtSensor.readHumidity();
  float tempCelsius = dhtSensor.readTemperature();
  float tempFahrenheit = dhtSensor.readTemperature(true);

  if (isnan(humidity) || isnan(tempCelsius) || isnan(tempFahrenheit)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }

  float heatIndexCelsius = dhtSensor.computeHeatIndex(tempCelsius, humidity, false);

  Serial.print("\nHumidity: ");
  Serial.print(humidity);
  Serial.print("%  Temperature: ");
  Serial.print(tempCelsius);
  Serial.print("°C  Heat Index: ");
  Serial.print(heatIndexCelsius);
  Serial.println("°C");

  mqttClient.publish(TEMP_CELSIUS_FEED, String(tempCelsius).c_str(), true);
  mqttClient.publish(TEMP_FAHRENHEIT_FEED, String(tempFahrenheit).c_str(), true);
  mqttClient.publish(HUMIDITY_FEED, String(humidity).c_str(), true);

  // LDR sensor reading
  int ldrValue = analogRead(LDR_PIN);
  Serial.print("LDR Value: ");
  Serial.println(ldrValue);  
  mqttClient.publish(LDR_FEED, String(ldrValue).c_str(), true);

  // Soil moisture sensor reading
  int soilMoistureValue = analogRead(SOIL_MOISTURE_PIN);
  Serial.print("Soil Moisture: ");
  Serial.println(soilMoistureValue);
  mqttClient.publish(SOIL_MOISTURE_FEED, String(soilMoistureValue).c_str(), true);

  // pH sensor reading
  phSensorValue = analogRead(PH_SENSOR_PIN);
  Serial.print("pH Value: ");
  phVoltage = phSensorValue * (3.3 / 4095.0);
  float phValue = 3.3 * phVoltage;
  Serial.println(phValue);
  mqttClient.publish(PH_FEED, String(phValue).c_str(), true);

  // NPK sensor readings
  byte npkQuery[]{ 0x01, 0x03, 0x00, 0x00, 0x00, 0x07, 0x04, 0x08 };
  byte npkData[19];
  digitalWrite(RS485_DE, HIGH);
  digitalWrite(RS485_RE, HIGH);

  npkSerial.write(npkQuery, sizeof(npkQuery));

  digitalWrite(RS485_DE, LOW);
  digitalWrite(RS485_RE, LOW);

  if (npkSerial.available() >= sizeof(npkData)) {
    npkSerial.readBytes(npkData, sizeof(npkData));

    unsigned int soilHumidity = (npkData[3] << 8) | npkData[4];
    unsigned int soilTemperature = (npkData[5] << 8) | npkData[6];
    unsigned int soilConductivity = (npkData[7] << 8) | npkData[8];
    unsigned int soilPH = (npkData[9] << 8) | npkData[10];
    unsigned int nitrogen = (npkData[11] << 8) | npkData[12];
    unsigned int phosphorus = (npkData[13] << 8) | npkData[14];
    unsigned int potassium = (npkData[15] << 8) | npkData[16];

    Serial.print("Soil Humidity: ");
    Serial.println((float)soilHumidity / 10.0);
    Serial.print("Soil Temperature: ");
    Serial.println((float)soilTemperature / 10.0);
    Serial.print("Soil Conductivity: ");
    Serial.println(soilConductivity);
    Serial.print("Soil pH: ");
    Serial.println((float)soilPH / 10.0);
    Serial.print("Nitrogen: ");
    Serial.println(nitrogen);
    Serial.print("Phosphorus: ");
    Serial.println(phosphorus);
    Serial.print("Potassium: ");
    Serial.println(potassium);

    mqttClient.publish(NPK_NITROGEN_FEED, String(nitrogen).c_str(), true);
    mqttClient.publish(NPK_PHOSPHORUS_FEED, String(phosphorus).c_str(), true);
    mqttClient.publish(NPK_POTASSIUM_FEED, String(potassium).c_str(), true);
  }
}

void loop() {
  if (!mqttClient.connected()) {
    reconnectMQTT();
  }
  mqttClient.loop();

  delay(5000);

  readSensorsAndPublish();
}