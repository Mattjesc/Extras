# Smart Home Control System using LabView

## Overview
This project is a prototype of a smart home control system developed using LabView. The system simulates the control of various home elements such as curtains, lights, doors, and a television. It employs a client-server architecture and uses TCP/IP for communication.

## Features
- **Login System**: Secure access with username and password, validated via a CSV file.
- **Client-Server Architecture**: Uses TCP protocol to handle requests and responses between the client and server.
- **Interactive Controls**:
  - **Curtains**: Control using sliders.
  - **Lights**: Toggle using switches.
  - **Doors**: Open/close with Boolean switches.
  - **Television**: Change channels and adjust volume using interface buttons and sliders.
- **Graphical User Interface**: Developed using LabView's NXG style palette for an intuitive and streamlined user experience.

## System Design
### Floor Plan
The project includes a basic floor plan showcasing the layout of the smart home with strategically placed controls for each element.

### Protocol Design
The system uses TCP connection initialized at `localhost` on port `6340`. A timeout of 30000 milliseconds is set to handle connection delays.

## User Guide
1. **Login**: Start by entering valid credentials on the login screen.
2. **Server Controls**: After successful login, access the server-side interface to control various elements of the smart home.
3. **Client Side**: The client side will reflect the changes made on the server side, synchronizing actions in real-time.

## Additional Features
- **Global Variables**: Used for efficient data management across multiple VI files.
- **CSV-based Login**: User credentials are managed through a CSV file, acting as a simple database.

## Conclusion
This LabView-based smart home control system demonstrates the potential of automation in modern homes. By leveraging LabView's graphical programming capabilities, this project provides a functional and user-friendly prototype for smart home management.