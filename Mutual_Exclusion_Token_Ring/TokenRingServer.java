package Mutual_Exclusion_Token_Ring;

// Machine A - Token Ring Server
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TokenRingServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);

        boolean hasToken = true; // Initially, server starts with token

        while (true) {
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

                // Pass token
                out.println("TOKEN");
                hasToken = false;
                System.out.println("Token passed to client.\n");
            }

            // Wait to receive token back
            String msg = in.readLine();
            if (msg != null && msg.equals("TOKEN")) {
                hasToken = true;
            }
        }
    }
}
