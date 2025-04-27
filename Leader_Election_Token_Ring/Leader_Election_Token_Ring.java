package Leader_Election_Token_Ring;

//        •	All processes are arranged in a logical ring (circular structure).
//        •	Each process knows only its successor in the ring.
//        •	A special message called a token is passed from one process to the next.
//        •	The election is initiated by any process when it detects the leader is missing or failed.
//        •	The token is used to collect IDs of all active processes.
//        •	The process with the highest ID becomes the leader.

import java.util.*;

class Process {
    int id;
    boolean isActive;

    public Process(int id) {
        this.id = id;
        this.isActive = true;
    }
}

public class Leader_Election_Token_Ring {
    private List<Process> processes;
    private int tokenPosition;

    public Leader_Election_Token_Ring(int[] ids) {
        processes = new ArrayList<>();
        for (int id : ids) {
            processes.add(new Process(id));
        }
        tokenPosition = 0; // start from 0th position
    }

    public void startElection(int initiatorIndex) {
        System.out.println("\nElection started by Process " + processes.get(initiatorIndex).id);
        int currentIndex = initiatorIndex;
        int highestId = processes.get(currentIndex).id;

        int startIndex = currentIndex;
        currentIndex = (currentIndex + 1) % processes.size();

        // Pass token in the ring
        while (currentIndex != startIndex) {
            Process current = processes.get(currentIndex);
            if (current.isActive) {
                System.out.println("Token passed from Process " + processes.get((currentIndex - 1 + processes.size()) % processes.size()).id +
                        " to Process " + current.id);
                if (current.id > highestId) {
                    highestId = current.id;
                }
            }
            currentIndex = (currentIndex + 1) % processes.size();
        }

        System.out.println("Token passed from Process " + processes.get((currentIndex - 1 + processes.size()) % processes.size()).id +
                " to Process " + processes.get(startIndex).id);
        System.out.println("Election complete. Leader is Process " + highestId);

        // Inform all processes of the new leader
        informLeader(highestId, startIndex);
    }

    private void informLeader(int leaderId, int startIndex) {
        int currentIndex = (startIndex + 1) % processes.size();
        System.out.println("\nInforming all processes about the new leader...");
        while (currentIndex != startIndex) {
            System.out.println("Leader message passed to Process " + processes.get(currentIndex).id);
            currentIndex = (currentIndex + 1) % processes.size();
        }
        System.out.println("All processes informed. Process " + leaderId + " is the new leader.\n");
    }
    public static void main(String args[]){
        // Example process IDs
        int[] ids = {3, 7, 2, 9, 5};

        Leader_Election_Token_Ring ring = new Leader_Election_Token_Ring(ids);

        // Start election from process at index 2 (ID = 2)
        ring.startElection(2);
    }
}
