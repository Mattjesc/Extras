
package rmi;


import java.rmi.RemoteException;

public class ClientHandler implements Runnable {
    private PayrollInterface server;
    private String methodName;
    private Object[] arguments;

    public ClientHandler(PayrollInterface server, String methodName, Object[] arguments) {
        this.server = server;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public void run() {
        try {
            // Log the thread information and the method being executed
            System.out.println("Thread " + Thread.currentThread().getId() + " is handling " + methodName + " request");
            
            // Invoke the corresponding method on the server based on the method name and arguments
            switch (methodName) {
                case "registerEmployee":
                    server.registerEmployee((String) arguments[0], (String) arguments[1], (String) arguments[2]);
                    break;
                case "getEmployeeDetails":
                    server.getEmployeeDetails((String) arguments[0]);
                    break;
                case "updateEmployeeDetails":
                    server.updateEmployeeDetails((String) arguments[0], (String) arguments[1], (String) arguments[2]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid method name: " + methodName);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
