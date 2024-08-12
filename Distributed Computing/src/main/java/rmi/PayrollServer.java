/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PayrollServer extends UnicastRemoteObject implements PayrollInterface {
    private static final String DB_URL = "jdbc:sqlite:database/payroll.db";
    private Map<String, Employee> employees = new HashMap<>();

    public PayrollServer() throws RemoteException {
        super();
        createTable(); // Create the employees table
    }

    // Method to create the employees table
    private void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                         "id VARCHAR(20) PRIMARY KEY," +
                         "first_name VARCHAR(50)," +
                         "last_name VARCHAR(50))";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    @Override
    public PayrollInfo getPayrollInfo(String id) throws RemoteException {
        if (employees.containsKey(id)) {
            Employee employee = employees.get(id);
            // Calculate payroll information for the employee
            PayrollCalculator calculator = new PayrollCalculator();

            // Provide the salary and hours worked values directly
            double salary = 5000.0; // Assume a salary of 5000 for testing purposes
            double hoursWorked = 160.0; // Assume 160 hours worked for testing purposes

            double grossPay = calculator.calculateGrossPay(salary, hoursWorked);
            double taxRate = 0.2; // Assuming 20% tax rate
            double insuranceRate = 0.08; // Assuming 8% insurance rate
            double deductions = calculator.calculateDeduction(grossPay, taxRate, insuranceRate);
            double netPay = calculator.calculateNetPay(grossPay, deductions);

            // Create and return a PayrollInfo object
            return new PayrollInfo(grossPay, deductions, netPay);
        } else {
            throw new RemoteException("Employee with ID " + id + " does not exist.");
        }
    }

    @Override
    public void registerEmployee(String firstName, String lastName, String id) throws RemoteException {
        // Check if the employee ID already exists
        if (employees.containsKey(id)) {
            throw new RemoteException("Employee with ID " + id + " already exists. Please choose a different ID.");
        }

        // Create a new Employee object and store it in the Map
        Employee newEmployee = new Employee(firstName, lastName, id);
        employees.put(id, newEmployee);
        System.out.println("Employee with ID " + id + " registered successfully.");

        // Store the employee details in the database
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "INSERT INTO employees (id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        serializeEmployees(); // Serialize the employees map
    }

    @Override
    public String getEmployeeDetails(String id) throws RemoteException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                return "Employee Details: " + firstName + " " + lastName + " (ID: " + id + ")";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return "Employee with ID " + id + " does not exist.";
    }

    @Override
    public void updateEmployeeDetails(String id, String firstName, String lastName) throws RemoteException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String query = "UPDATE employees SET first_name = ?, last_name = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RemoteException("Employee with ID " + id + " does not exist.");
            }
            System.out.println("Employee details updated successfully for ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public static void main(String[] args) {
        try {
            PayrollServer server = new PayrollServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("PayrollServer", server);
            System.out.println("Payroll Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void serializeEmployees() {
        try {
            FileOutputStream fileOut = new FileOutputStream("employees_Serialized.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(employees);
            out.close();
            fileOut.close();
            System.out.println("Employee data serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserializeEmployees() {
        try {
            FileInputStream fileIn = new FileInputStream("employees_Serialized.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            employees = (Map<String, Employee>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Employee data deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}