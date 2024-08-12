package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PayrollInterface extends Remote {
    void registerEmployee(String firstName, String lastName, String id) throws RemoteException;
    String getEmployeeDetails(String id) throws RemoteException;
    void updateEmployeeDetails(String id, String firstName, String lastName) throws RemoteException;
    PayrollInfo getPayrollInfo(String id) throws RemoteException;
}