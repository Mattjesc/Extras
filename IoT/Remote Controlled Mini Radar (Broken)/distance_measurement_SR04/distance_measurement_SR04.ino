/*
 * This program is property of SME Dehradun. for any query related to this program, 
 * contact us at www.smedehradn.com
 * if your want any soluiton related for any IoT Customized Boards and Sensor, 
 * then contact to www.nuttyengineer.com 
 */
// Replace/ Fill-in information from your Blynk Template here

#define BLYNK_TEMPLATE_ID "Write your Template ID"
#define BLYNK_DEVICE_NAME "Write your Device ID" 

#define BLYNK_FIRMWARE_VERSION "0.1.0"
#define BLYNK_PRINT Serial
#include "BlynkEdgent.h"

#define echoPin 32
#define trigPin 33

long duration;
int distance; 

void ultrasonic()
{
    digitalWrite(trigPin, LOW);
    delayMicroseconds(2);
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);
    duration = pulseIn(echoPin, HIGH);
    distance = duration * 0.034 / 2; //formula to calculate the distance for ultrasonic sensor
    Serial.print("Distance: ");
    Serial.println(distance);
    Blynk.virtualWrite(V0, distance);
    delay(500);
}
void setup()
{
  Serial.begin(9600);
  pinMode(34, INPUT);
  pinMode(trigPin, OUTPUT); 
  pinMode(echoPin, INPUT); 
  BlynkEdgent.begin();
  delay(2000); 
}

void loop() 
{
  BlynkEdgent.run();
  ultrasonic();
}
