# Sustainable Urban Garden Monitor IoT Application README (Matthew Jesua Chandra _TP063123)

## Hardware Requirements
- ESP32 microcontroller
- Soil NPK Sensor
- DHT11 Temperature and Humidity Sensor
- Soil Moisture Sensor
- pH Meter Sensor
- LDR Sensor
- Water Pump
- Battery

## Hardware Setup
1. Connect the Soil NPK Sensor to the ESP32:
   - VCC to 3.3V
   - GND to GND
   - TX to GPIO27
   - RX to GPIO26
2. Connect the DHT11 Sensor to the ESP32:
   - VCC to 3.3V
   - GND to GND
   - DATA to GPIO4
3. Connect the Soil Moisture Sensor to the ESP32:
   - VCC to 3.3V
   - GND to GND
   - AOUT to GPIO2
4. Connect the pH Meter Sensor to the ESP32:
   - VCC to 3.3V
   - GND to GND
   - AOUT to GPIO32
5. Connect the LDR Sensor to the ESP32:
   - VCC to 3.3V
   - GND to GND
   - AOUT to GPIO15
6. Connect the Water Pump to the ESP32:
   - VCC to 5V
   - GND to GND
   - IN to GPIO5
7. Power the ESP32 using a battery connected to the designated power pins.

## Software Requirements
- Arduino IDE
- Required Libraries:
  - PubSubClient
  - DHT
  - Wire
  - SoftwareSerial

## Software Setup
1. Install the Arduino IDE on your computer.
2. Open the Arduino IDE and create a new sketch.
3. Copy the provided code for the Sustainable Urban Garden Monitor into the sketch.
4. Install the required libraries:
   - Go to Sketch -> Include Library -> Manage Libraries.
   - Search for each library (PubSubClient, DHT, Wire, SoftwareSerial) and install the latest version.
5. Update the sketch with your WiFi credentials:
   - Replace `"EOSC-27-5"` with your WiFi SSID.
   - Replace `"Style@882713"` with your WiFi password.
6. Verify and flash the sketch to the ESP32:
   - Connect the ESP32 to your computer using a USB cable.
   - Select the appropriate board and port in the Arduino IDE.
   - Click on the "Upload" button to flash the sketch to the ESP32.

## IoT Platform Setup
1. Install Node.js on your computer.
2. Install Node-RED by running the following command in the terminal:
   ```
   npm install -g --unsafe-perm node-red
   ```
3. Launch Node-RED by running the command:
   ```
   node-red
   ```
4. Open a web browser and navigate to `http://localhost:1880` to access the Node-RED editor.
5. Import the provided Node-RED flow by copying the JSON code and pasting it into the Node-RED editor.
6. Configure the MQTT nodes in the flow:
   - Double-click on each MQTT input node.
   - Set the broker IP address to `192.168.100.44` and the port to `1883`.
   - Update the topic for each sensor node to match the topics in the Arduino sketch.
7. Deploy the Node-RED flow by clicking the "Deploy" button.

## Database Setup
1. Install InfluxDB on your computer.
2. Open a terminal and start the InfluxDB service.
3. Open the InfluxDB CLI by running the command:
   ```
   influx
   ```
4. Create a new database named "diota_assignment" by running the following command in the InfluxDB CLI:
   ```
   CREATE DATABASE diota_assignment
   ```
5. Configure the InfluxDB output nodes in the Node-RED flow:
   - Double-click on each InfluxDB output node.
   - Set the hostname to `127.0.0.1` and the port to `8086`.
   - Set the database name to `diota_assignment`.

## Visualization Setup
1. Install Grafana on your computer.
2. Start the Grafana server and open a web browser.
3. Navigate to `http://localhost:3000` to access the Grafana interface.
4. Log in with the default credentials (admin/admin).
5. Add InfluxDB as a data source in Grafana:
   - Click on the gear icon in the sidebar and select "Data Sources".
   - Click on "Add data source" and select "InfluxDB".
   - Set the URL to `http://localhost:8086` and the database name to `diota_assignment`.
   - Save the data source configuration.
6. Import the provided Grafana dashboard:
   - Click on the "+" icon in the sidebar and select "Import".
   - Paste the JSON code of the provided Grafana dashboard and click "Load".
   - Select the InfluxDB data source and click "Import".
7. Customize the dashboard panels as needed to display the desired sensor data.

## Running the Application
1. Power on the ESP32 by connecting the battery.
2. Ensure the Mosquitto MQTT broker is running:
   - Open a terminal and navigate to the Mosquitto installation directory.
   - Run the command `mosquitto -v` to start the broker in verbose mode.
3. Open the Node-RED flow in a web browser at `http://localhost:1880`.
4. Access the Grafana dashboard at `http://localhost:3000` to monitor the sensor data and garden status.

Note: Make sure all the components are properly connected and the required software dependencies are installed before running the application. Refer to the documentation for troubleshooting and further guidance.