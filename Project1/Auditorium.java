import java.io.*;
import java.util.Scanner;

public class Auditorium {
    private static Node first = null;
    private static int numRow = 0;
    private static int numCol = 0;
   
    public Node getFirst() {
        return first;
    }
    public int getNumRow() {
        return numRow;
    }
    public int getNumCol() {
      return numCol;
    }
    
    // Helper method used to read file and create Auditorium
    private static void readFile(Scanner scnr) {
        String line = "";
        Scanner fileReader;
        File file;
        Seat tempSeat;
        Node nextptr = null, downptr = null, tempNode;
        
        // Prompt for file input
        System.out.print("Enter filename: ");
        file = new File(scnr.nextLine());
        // File input validation
        do {
            try {
                fileReader = new Scanner(file);
                break;
            }
            catch (FileNotFoundException f) {
                System.out.println("File cannot be opened.");
            }
            System.out.println("Enter filename: ");
            file = new File(scnr.nextLine());
        } while (true);
        // While file is not empty, read file
        while (fileReader.hasNext()) {
            line = fileReader.nextLine();
            if (line.isEmpty())
                break;
            // Increment current row number by 1
            numRow += 1;
            // For each character in line, create Seat with row, column, and ticket type and store in Node
            for (int i = 0; i < line.length(); i++) {
                tempSeat = new Seat(numRow, (char)(i + 65), line.charAt(i));
                // If Auditorium is currently empty, store as first Node of Auditorium
                if (first == null) {
                    first = new Node(tempSeat);
                    nextptr = first;
                    downptr = first;
                }
                // If Auditorium not empty and current seat is first in row
                else if (i == 0) {
                    tempNode = new Node(tempSeat);
                    // Point first node in row above down to current node
                    downptr.setDown(tempNode);
                    // Point downptr to current node
                    downptr = tempNode;
                    // Point current node down to null
                    downptr.setDown(null);
                    // Set nextptr to downptr to create rest of row
                    nextptr = downptr;
                }
                // If Auditorium not empty and current seat not first in row
                else {
                    // Traverse to end of current row's linked list
                    while (nextptr.getNext() != null)
                        nextptr = nextptr.getNext();
                    tempNode = new Node(tempSeat);
                    // Point previous node in row to current node
                    nextptr.setNext(tempNode);
                    // Point nextptr to current node to create rest of row
                    nextptr = tempNode;
                    // Point current node to null
                    nextptr.setNext(null);
                }
            }
        }
        numCol = line.length();
         
        fileReader.close();
    }
   
    public Auditorium(Scanner input) {
        readFile(input);
    }
   
    // Used to display auditorium when user wants to reserve seats
    public String toString() {
        String auditorium = "  ";
        int i;
        Node nextptr = first, downptr = nextptr.getDown();
      
        // Store column header
        for (i = 0; i < numCol; i++)
            auditorium += (char)(i + 65);
        auditorium += "\n";
        // Traverse through each row of Auditorium
        for (i = 0; i < numRow; i++) {
            // Store row header value
            auditorium += "" + (i + 1) + " ";
            // Display taken seats in row with "#", empty seats with "."
            for (int j = 0; j < numCol; j++) {
                if (Character.isLetter(nextptr.getPayload().getTixType()))
                    auditorium += "#";
                else if (!(Character.isLetter(nextptr.getPayload().getTixType())))
                    auditorium += ".";
                // Traverse to next seat if exists
                if (nextptr.getNext() != null)
                    nextptr = nextptr.getNext();
            }
            auditorium += "\n";
            // If Auditorium has more rows, traverse down to next row
            if (downptr != null) {
                nextptr = downptr;
                downptr = nextptr.getDown();
            }
        }
        return auditorium;
    }
}
