package Mutual_Exclusion_Token_Ring;

// Machine B - Token Ring Client
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TokenRingClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000); // Replace 'localhost' with Server IP if needed
        System.out.println("Connected to server!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);

        boolean hasToken = false;

        while (true) {
            // Wait to receive token
            String msg = in.readLine();
            if (msg != null && msg.equals("TOKEN")) {
                hasToken = true;
            }

            if (hasToken) {
                System.out.println("\nYou have the token.");
                System.out.print("Do you want to enter Critical Section? (yes/no): ");
                String answer = sc.nextLine();

                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("Entering Critical Section...");
                    try {
                        Thread.sleep(2000); // simulate work
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Exiting Critical Section...");
                }

                // Pass token back
                out.println("TOKEN");
                hasToken = false;
                System.out.println("Token passed to server.\n");
            }
        }
    }
}
