package RMI;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Operations ops = (Operations) Naming.lookup("rmi://localhost/OperationsService");
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter two numbers:");
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.println("Addition: " + ops.add(a, b));
            System.out.println("Multiplication: " + ops.multiply(a, b));
            System.out.println("Subtraction: " + ops.subtract(a, b));
            System.out.println("Division: " + ops.divide(a, b));
            System.out.println("2's power of " + a + ": " + ops.power(a));
            System.out.println("Echo your name: " + ops.echo("Om"));
            System.out.println("Compare strings: " + ops.compareStrings("apple", "banana"));
            System.out.println("Count vowels in 'hello': " + ops.countVowels("hello"));
            System.out.println("Factorial of " + a + ": " + ops.factorial(a));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
