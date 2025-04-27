package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    int add(int a, int b) throws RemoteException;
    int subtract(int a, int b) throws RemoteException;
    int multiply(int a, int b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
    int power(int base) throws RemoteException;
    double celsiusToFahrenheit(double celsius) throws RemoteException;
    double milesToKilometers(double miles) throws RemoteException;
    String echo(String name) throws RemoteException;
    String compareStrings(String s1, String s2) throws RemoteException;
    int countVowels(String word) throws RemoteException;
    long factorial(int n) throws RemoteException;
}
