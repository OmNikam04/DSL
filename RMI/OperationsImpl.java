package RMI;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class OperationsImpl extends UnicastRemoteObject implements Operations {

    protected OperationsImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) throws RemoteException {
        return a - b;
    }

    @Override
    public int multiply(int a, int b) throws RemoteException {
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) throw new ArithmeticException("Division by zero!");
        return a / b;
    }

    @Override
    public int power(int base) throws RemoteException {
        return (int) Math.pow(2, base);
    }

    @Override
    public double celsiusToFahrenheit(double celsius) throws RemoteException {
        return (celsius * 9/5) + 32;
    }

    @Override
    public double milesToKilometers(double miles) throws RemoteException {
        return miles * 1.60934;
    }

    @Override
    public String echo(String name) throws RemoteException {
        return "Hello, " + name;
    }

    @Override
    public String compareStrings(String s1, String s2) throws RemoteException {
        return s1.compareTo(s2) >= 0 ? s1 : s2;
    }

    @Override
    public int countVowels(String word) throws RemoteException {
        int count = 0;
        for (char c : word.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public long factorial(int n) throws RemoteException {
        long fact = 1;
        for (int i = 2; i <= n; i++) fact *= i;
        return fact;
    }
}
