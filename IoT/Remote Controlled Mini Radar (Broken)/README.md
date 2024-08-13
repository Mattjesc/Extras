# IoT Remote Controlled Mini Radar

Note: Code is broken as of now

![image](https://github.com/user-attachments/assets/cfe31dcb-a092-4695-8df0-ddf6901bd6d0)
![image](https://github.com/user-attachments/assets/d4b67498-1a74-4f97-ace7-6c24a97e31c3)


This project focuses on the development of a Remote Controlled Miniaturized Radar using IoT technology. The radar is designed to simulate how real-life radar systems work by detecting incoming objects and measuring environmental conditions such as temperature and humidity. The project leverages hobbyist electronics and IoT cloud services for data storage and visualization.

## Introduction

The project aims to create a small-scale, budget-friendly radar system that can monitor and detect objects, simulate real-life radar operations, and visualize the data on an IoT platform. The system uses various sensors and a microcontroller to gather data, which is then transmitted to the cloud for real-time monitoring and analysis.

## Physical Components

The physical components used in this project include:

- **ESP32 Microcontroller**: Provides Wi-Fi and Bluetooth capabilities, making it ideal for IoT projects.
- **HC-SR04 Ultrasonic Sensor**: Measures the distance of incoming objects using ultrasonic waves.
- **SG90 Micro Servo Motor**: Controls the rotation of the ultrasonic sensor to simulate radar movement.
- **DHT11 Sensor**: Monitors temperature and humidity levels.
- **Breadboard**: Provides a platform for connecting the components without soldering.
- **Male-to-Male Jumper Cables**: Used for wiring the components together.

## Software Components

- **Arduino IDE**: The development environment used for writing and uploading code to the ESP32.
- **Blynk IoT Cloud Service**: Used for storing and visualizing the radar data in real-time. The data can be accessed via a web interface or a mobile app.

## Physical Design

The radar system's physical design includes an architecture diagram and a detailed layout of how each component is connected and functions within the system. The design allows for easy assembly and modification, making it suitable for hobbyist and educational purposes.

### Pin Connections Summary:

- **DHT11 Sensor**:
  - VCC → 3.3V
  - GND → GND
  - DATA → GPIO 26

- **HC-SR04 Ultrasonic Sensor**:
  - VCC → 5V
  - GND → GND
  - Trig → GPIO 33
  - Echo → GPIO 32

- **Servo Motor**:
  - VCC → 5V
  - GND → GND
  - Signal → GPIO 25

## Logical Design

The logical design involves the source code that runs on the ESP32 microcontroller. The code handles:

- **Sensor Data Collection**: Continuously reads data from the ultrasonic sensor and DHT11 sensor.
- **Servo Motor Control**: Allows remote control of the radar's rotation via the Blynk app.
- **Data Transmission**: Sends the collected data to the Blynk IoT platform for visualization.
- **Environmental Monitoring**: Continuously monitors and reports temperature and humidity levels.

## Application Testing and Outcomes

The project was tested using the Blynk app, where the radar's operation was controlled remotely. The application interface provided real-time data visualization, including object distance, temperature, and humidity levels. The radar successfully detected objects and adjusted its rotation based on user input.

## Conclusion

This project demonstrates the potential of IoT technology in creating a functional and educational radar system. The miniaturized radar provides insights into how real-world radar systems operate, offering a practical application of IoT for data collection and visualization. The project highlights the importance of combining hardware and software to create effective and interactive systems.
