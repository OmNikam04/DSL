package RMI;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // start RMI registry at port 1099
            OperationsImpl obj = new OperationsImpl(); // No error now
            Naming.rebind("OperationsService", obj);
            System.out.println("Server is ready...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
