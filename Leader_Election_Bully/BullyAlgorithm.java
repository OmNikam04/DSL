package Leader_Election_Bully;

//        How Does It Work?
//        1.	Each process has a unique ID.
//        2.	When a process notices the coordinator (leader) has failed, it starts an election.
//        3.	The process sends an ELECTION message to all processes with higher IDs.
//        4.	If no higher-ID process responds, it becomes the new coordinator and sends a COORDINATOR message to all.
//        5.	If any higher-ID process responds, that process takes over the election and repeats the steps.
//        6.	Eventually, the highest-ID process becomes the coordinator.

import java.util.*;

class Process {
    int id;
    boolean isAlive;
    boolean isCoordinator;

    public Process(int id) {
        this.id = id;
        this.isAlive = true;
        this.isCoordinator = false;
    }

    public void crash() {
        isAlive = false;
        isCoordinator = false;
    }

    public void activate() {
        isAlive = true;
    }
}

public class BullyAlgorithm {
    static List<Process> processes = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // Create processes with IDs 1 to n
        for (int i = 1; i <= n; i++) {
            processes.add(new Process(i));
        }

        // Assume initially the last process is coordinator
        processes.get(n - 1).isCoordinator = true;
        System.out.println("\nInitial Coordinator is Process P" + n);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Crash a process");
            System.out.println("2. Recover a process");
            System.out.println("3. Start Election");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    crashProcess();
                    break;
                case 2:
                    recoverProcess();
                    break;
                case 3:
                    initiateElection();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void crashProcess() {
        System.out.print("Enter process ID to crash: ");
        int id = sc.nextInt();
        Process p = getProcessById(id);
        if (p != null && p.isAlive) {
            p.crash();
            System.out.println("Process P" + id + " has crashed.");
        } else {
            System.out.println("Process P" + id + " is already crashed or does not exist.");
        }
    }

    static void recoverProcess() {
        System.out.print("Enter process ID to recover: ");
        int id = sc.nextInt();
        Process p = getProcessById(id);
        if (p != null && !p.isAlive) {
            p.activate();
            System.out.println("Process P" + id + " has recovered.");
        } else {
            System.out.println("Process P" + id + " is already alive or does not exist.");
        }
    }

    static void initiateElection() {
        System.out.print("Enter process ID to initiate election: ");
        int id = sc.nextInt();
        Process initiator = getProcessById(id);

        if (initiator == null || !initiator.isAlive) {
            System.out.println("Process P" + id + " cannot initiate election (not alive or doesn't exist).");
            return;
        }

        System.out.println("\nProcess P" + id + " initiates an election...");

        int highestId = id; // Assume initiator is the highest
        for (Process p : processes) {
            if (p.id > id && p.isAlive) {
                System.out.println("P" + id + " sends ELECTION to P" + p.id);
                System.out.println("P" + p.id + " responds with OK");
                if (p.id > highestId) {
                    highestId = p.id;
                }
            }
        }

        Process newCoordinator = getProcessById(highestId);
        if (newCoordinator != null) {
            for (Process p : processes) {
                p.isCoordinator = false; // Clear previous coordinator
            }
            newCoordinator.isCoordinator = true;
            System.out.println("\nP" + newCoordinator.id + " becomes the new COORDINATOR.");
            for (Process p : processes) {
                if (p.isAlive && p.id != newCoordinator.id) {
                    System.out.println("P" + newCoordinator.id + " sends COORDINATOR message to P" + p.id);
                }
            }
        }
    }

    static Process getProcessById(int id) {
        for (Process p : processes) {
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }
}
