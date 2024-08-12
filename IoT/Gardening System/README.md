# IoT-Based Smart Urban Gardening System with Real-Time Visualization Dashboard

This project involves the development of an IoT-based system for managing and monitoring gardens in urban environments, complete with a real-time visualization dashboard. The system is designed to balance sustainability and technological advancements, addressing the challenges of urbanization while promoting eco-friendly practices.

## Introduction

The urbanization process has led to the need for innovative solutions to maintain green spaces within modern cities. This project proposes an IoT-based system that allows for the efficient management and monitoring of urban gardens, ensuring optimal growth conditions for plants by leveraging modern technology. The system includes a real-time visualization dashboard for easy monitoring and management.

## System Components

### Hardware Components
- **Soil NPK Sensor**: Monitors nitrogen, phosphorus, and potassium levels to optimize fertilizer use.
- **DHT11 Sensor**: Tracks humidity and temperature for historical data analysis.
- **Soil Moisture Sensor**: Prevents overwatering by monitoring soil moisture levels.
- **pH Meter Sensor**: Monitors soil pH to ensure optimal nutrient absorption.
- **ESP32 Microcontroller**: Handles data collection and communication with built-in Wi-Fi and Bluetooth capabilities.
- **Battery**: Powers the system, using lithium-ion batteries for their accessibility and cost-effectiveness.
- **LDR Sensor**: Measures sunlight intensity to optimize plant light exposure.
- **Water Pump**: Automates plant watering based on sensor data and system requirements.

### Software Components
- **Arduino IDE**: Used for programming the microcontroller to interact with hardware components.
- **Node-RED IoT Platform**: Manages and streamlines data from sensors in a visual interface.
- **InfluxDB**: Stores sensor data with timestamps for easy retrieval and analysis.
- **Grafana**: Provides a real-time visualization dashboard, turning sensor data into an accessible digital interface.

## System Architecture

The system is structured into several layers:

- **Edge Node**: Includes the sensors, microcontroller, and actuators, responsible for data gathering and initial processing.
- **Communication Node/Layer**: Utilizes the MQTT protocol for efficient data transmission between devices and the cloud.
- **Cloud Node/Layer**: Stores and processes data using InfluxDB and visualizes it through Grafana.

## Implementation

The system's implementation involves setting up the hardware components and programming them using Arduino IDE. Data is collected from the sensors and transmitted via MQTT to the cloud, where it is stored in InfluxDB. The data is then visualized in real-time on a Grafana dashboard, accessible through the web.

## Conclusion

This IoT-based smart gardening system integrates hardware and software to create a sustainable and efficient solution for managing urban gardens. The real-time visualization dashboard enhances user interaction, making it easier to monitor and maintain optimal conditions for plant growth in urban environments. The project demonstrates the potential of IoT technologies in contributing to sustainable urban living.