# Distributed Computing on Payroll System

This project focuses on developing a distributed payroll system for BHEL Company, which currently faces inefficiencies with its manual payroll process. The proposed solution involves implementing a user-friendly, scalable payroll system using Java Remote Method Invocation (RMI) technology. This system will automate payroll calculations, employee management, and reporting, all within a secure, distributed computing environment.

## Key Features

- **Employee Registration and Management**: Employees can register their accounts with essential details such as first name, last name, and ID number. The system ensures uniqueness by checking for existing usernames.

- **Payroll Calculation**: The system automates the calculation of gross pay, deductions, and net pay based on predefined formulas. This reduces errors and streamlines the payroll process.

- **Centralized Database**: A centralized database stores and manages all employee information, ensuring data consistency and accessibility across the distributed system.

- **Reporting Tools**: The system includes comprehensive tools for generating payroll reports and employee earnings statements, providing HR and management with valuable insights.

- **Secure Communication**: All interactions between the client (employee or HR) and the payroll system are secured, ensuring data integrity and privacy.

## Technical Implementation

- **Java RMI Technology**: The system leverages Java RMI to enable remote method invocation, allowing different parts of the system to communicate seamlessly across a distributed environment.

- **Multithreading**: To ensure smooth operation and handle multiple requests simultaneously, the system uses multithreading. This allows tasks like payroll calculations and database updates to run concurrently.

- **Socket Programming**: The system employs socket programming to enable communication between different components, allowing multiple users to interact with the system in real-time across a local network.

- **JavaDB (JDBC)**: JavaDB is used as the database system to manage employee records and payroll data, ensuring reliable data storage and retrieval.

- **Object-Oriented Programming (OOP)**: The system is developed using OOP principles, ensuring modularity, scalability, and ease of maintenance.

## User Interaction

The system provides a simple Graphical User Interface (GUI) for employees and HR staff. The GUI allows users to:

- **Register**: Employees can create their profiles by entering their details.
- **Login**: Employees can log in to access their payroll information.
- **Update Details**: Employees can update their personal information through the self-service portal.
- **Calculate Payroll**: HR staff can calculate payroll for all employees and generate reports.

## Conclusion

The distributed payroll system is designed to replace the outdated manual processes with an efficient, automated solution. By leveraging RMI, multithreading, and secure communication, the system ensures that payroll operations are conducted smoothly, accurately, and securely in a distributed environment. The use of a centralized database and comprehensive reporting tools further enhances the system's capabilities, making it a robust solution for BHEL Company's payroll management needs.