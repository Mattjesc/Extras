/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PayrollClient {
    private PayrollInterface server;

    public PayrollClient() throws RemoteException {
 try {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        server = (PayrollInterface) registry.lookup("PayrollServer");
    } catch (NotBoundException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., display an error message or throw a custom exception
    }
    }

    public PayrollInfo getPayrollInfo(String id) throws RemoteException {
    return server.getPayrollInfo(id);
}
    
    public void registerEmployee(String firstName, String lastName, String id) throws RemoteException {
        server.registerEmployee(firstName, lastName, id);
    }

    public String getEmployeeDetails(String id) throws RemoteException {
        return server.getEmployeeDetails(id);
    }

    public void updateEmployeeDetails(String id, String firstName, String lastName) throws RemoteException {
        server.updateEmployeeDetails(id, firstName, lastName);
    }

    public static void main(String[] args) {
        try {
            PayrollClient client = new PayrollClient();
            // Create and display the GUI
            EmployeeRegistrationForm form = new EmployeeRegistrationForm(client);
            form.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}