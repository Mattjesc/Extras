# Smart Home Automation Control System using LabView

![image](https://github.com/user-attachments/assets/f6f40fdd-b8a5-4ec3-bb31-66575d994c90)
![image](https://github.com/user-attachments/assets/91cc4403-9072-4790-ab30-c44321c96945)
![image](https://github.com/user-attachments/assets/744ff2c4-3ecd-4d2e-8c5b-e412d7949eab)
![image](https://github.com/user-attachments/assets/cfefaea9-8465-4420-be65-99b704266d54)
![image](https://github.com/user-attachments/assets/1b21f1d8-8de1-45fe-ace2-e3723c6ad3c4)
![image](https://github.com/user-attachments/assets/70175570-e347-4884-b09b-6d08ad94e5a7)


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

![image](https://github.com/user-attachments/assets/142ae0c4-dadf-40b5-b187-046aeb1e9805)
![image](https://github.com/user-attachments/assets/0badeafe-29a9-4d6c-a6b4-5d1d04bf0f7a)
![image](https://github.com/user-attachments/assets/8472ebd8-1766-45e7-9975-257cf2b59d87)
![image](https://github.com/user-attachments/assets/d4bda299-bc13-4102-87bf-be5a58536d08)
![image](https://github.com/user-attachments/assets/58f6f197-f772-4e08-89b9-c86659acae5d)
![image](https://github.com/user-attachments/assets/a11e133a-6b8b-43cf-9dd2-530a82585e91)
![image](https://github.com/user-attachments/assets/82c0ded8-f8c3-438b-a040-21c912dd62d4)


## System Design
### Floor Plan
The project includes a basic floor plan showcasing the layout of the smart home with strategically placed controls for each element.

![image](https://github.com/user-attachments/assets/765135b8-b01d-4c6d-af47-5431c204030c)


### Protocol Design
The system uses TCP connection initialized at `localhost` on port `6340`. A timeout of 30000 milliseconds is set to handle connection delays.


![image](https://github.com/user-attachments/assets/266e6758-96f3-4b5b-9091-2c81e9e9ffe9)
![image](https://github.com/user-attachments/assets/5bcab766-faad-4a38-9a1d-86e7ea7589b2)


## User Guide
1. **Login**: Start by entering valid credentials on the login screen.
2. **Server Controls**: After successful login, access the server-side interface to control various elements of the smart home.
3. **Client Side**: The client side will reflect the changes made on the server side, synchronizing actions in real-time.

## Additional Features
- **Global Variables**: Used for efficient data management across multiple VI files.
- **CSV-based Login**: User credentials are managed through a CSV file, acting as a simple database.

## Conclusion
This LabView-based smart home control system demonstrates the potential of automation in modern homes. By leveraging LabView's graphical programming capabilities, this project provides a functional and user-friendly prototype for smart home management.
