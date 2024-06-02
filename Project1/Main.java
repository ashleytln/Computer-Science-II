import java.io.*;
import java.util.*;
import java.lang.Math;

public class Main {
    public static boolean checkAvailability(Auditorium aud, int r, int seat, int total) {
        Node nextptr = aud.getFirst(), downptr = aud.getFirst();
        int i;
        // Traverse to desired row of linked list
        for (i = 0; i < r; i++)
            downptr = downptr.getDown();
        // Traverse to desired starting seat in row 
        nextptr = downptr;
        for (i = 0; i < seat; i++) {
            nextptr = nextptr.getNext();
        }
        // Check if enough seats available for user to reserve
        for (i = 0; i < total; i++) {
            // If seat is not empty and no adjacent seat available for user's selection, desired selection unavailable
            if (Character.compare(nextptr.getPayload().getTixType(), '.') != 0 && nextptr.getNext() == null)
                return false;
            // If current seat empty and seats available for user's selection, traverse to next seat
            if (nextptr.getNext() != null)
                nextptr = nextptr.getNext();
        }
        return true;
    }
   
    public static Seat findBestAvailable(Auditorium aud, int r, int c, int total) {
        Node nextptr = aud.getFirst(), downptr = nextptr.getDown(), startingptr = nextptr;
        double middleRow = (r + 1) / 2.0, middleSeat = (c + 1) / 2.0, dist = 0, smallestDist = Math.sqrt(Math.pow(10, 2) + Math.pow(26, 2));
      
        // Seat object that will store best available seat information
        Seat bestAvail = new Seat();
      
        // Start loops at 1 to utilize row/seat information more easily
        for (int i = 1; i <= r; i++) {
            // Tracks position of starting seat, number of possible available selections
            for (int j = 1; j <= c - total + 1; j++) {
                for (int k = 1; k <= total; k++) {
                    // Breaks loop if all seats in possible seat selection are not empty
                    if (Character.compare(nextptr.getPayload().getTixType(), '.') != 0)
                        break;
                    // Traverses to next node to check if next seat empty
                    else if (nextptr.getNext() != null)
                        nextptr = nextptr.getNext();
                    // Calculate distance between middle of seat selection and middle of auditorium if all seats are checked to be empty
                    if (k == total) {
                        if (total > 1)
                            dist = Math.sqrt(Math.pow(i - middleRow, 2) + Math.pow(((j + (j + total - 1)) / 2.0) - middleSeat, 2));
                        // Different formula since middle of 1 seat selection is itself
                        else if (total == 1)
                            dist = Math.sqrt(Math.pow(i - middleRow, 2) + Math.pow(j - middleSeat, 2));
                        // Checks if distance between center of possible selection and center of auditorium is smaller than currect smallest distance
                        if (dist < smallestDist) {
                            smallestDist = dist;
                            bestAvail.setRow(i);
                            bestAvail.setSeat((char)(j + 65 - 1));
                        }
                        // If tie for distance occurs
                        else if (dist == smallestDist) {
                            // Select row closest to middle of auditorium
                            if (Math.abs(i - middleRow) < Math.abs(bestAvail.getRow() - middleRow)) {
                            bestAvail.setRow(i);
                            bestAvail.setSeat((char)(j + 65 - 1));
                            }
                            // If tie for closest row, select row with smallest number
                            if (Math.abs(i - middleRow) == Math.abs(bestAvail.getRow() - middleRow)) {
                                if (i < bestAvail.getRow()) {
                                    bestAvail.setRow(i);
                                    bestAvail.setSeat((char)(j + 65 - 1));
                                }
                            }
                        }
                    }
                }
                // Traverses to starting seat of next possible selection
                if (startingptr.getNext() != null) {
                    nextptr = startingptr.getNext();
                    startingptr = nextptr;
                }
            }
            if (downptr != null) {
                nextptr = downptr;
                startingptr = nextptr;
                downptr = nextptr.getDown();
            }
        }
        return bestAvail;
    }
   
    public static void reserveSeats(Auditorium aud, int r, int seat, int a, int c, int s) {
        Node nextptr = aud.getFirst(), downptr = aud.getFirst();
        int i;
      
        // Traverse to desired row of linked list
        for (i = 0; i < r; i++)
            downptr = downptr.getDown();
        nextptr = downptr;
        // Traverse to desired starting seat in row 
        for (i = 0; i < seat; i++)
            nextptr = nextptr.getNext();
      
        // Reserves adult seats until all tickets reserved
        while (a > 0) {
            nextptr.getPayload().setTixType('A');
            a--;
            nextptr = nextptr.getNext();
        }
        // Reserves child seats until all tickets reserved
        while (c > 0) {
            nextptr.getPayload().setTixType('C');
            c--;
            nextptr = nextptr.getNext();
        }
        // Reserves senior seats until all tickets reserved
        while (s > 0) {
            nextptr.getPayload().setTixType('S');
            s--;
            nextptr = nextptr.getNext();
        }
    }
   
    public static void displayReport(Auditorium aud, int r, int c) throws IOException {
        // Opens output file, creates new if does not exist
        File file = new File("A1.txt");
        if (!file.exists())
            file.createNewFile();
        FileOutputStream report = new FileOutputStream(file, false);
        PrintWriter printer = new PrintWriter(report);
       
        int totalSeats = r * c, adultSold = 0, childSold = 0, seniorSold = 0;
        double totalSales;
        Node nextptr = aud.getFirst(), downptr = nextptr.getDown();
        // Loops through each element of linked lists, counts amount of tickets sold
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (Character.compare(nextptr.getPayload().getTixType(), 'A') == 0)
                    adultSold++;
                else if (Character.compare(nextptr.getPayload().getTixType(), 'C') == 0)
                    childSold++;
                else if (Character.compare(nextptr.getPayload().getTixType(), 'S') == 0)
                    seniorSold++;
                // Prints final reserved auditorium seats in one row to file
                printer.print(nextptr);
                if (nextptr.getNext() != null)
                    nextptr = nextptr.getNext();
            }
            // Prints new line to print next row
            if (i < (r - 1))
                printer.print("\n");
            if (downptr != null) {
                nextptr = downptr;
                downptr = nextptr.getDown();
            }
        }
        printer.close();
        totalSales = (adultSold * 10) + (childSold * 5) + (seniorSold * 7.5);
        // Prints report to console
        System.out.printf("Total Seats: %d\n", totalSeats);
        System.out.printf("Total Tickets: %d\n", adultSold + childSold + seniorSold);
        System.out.printf("Adult Tickets: %d\n", adultSold);
        System.out.printf("Child Tickets: %d\n", childSold);
        System.out.printf("Senior Tickets: %d\n", seniorSold);
        System.out.printf("Total Sales: $%.2f\n", totalSales);
    }
   
    public static void main(String[] args) throws IOException {
        int userChoice = 0, rowChoice = 0, aTix = 0, cTix = 0, sTix = 0;
        char seatLetter;
      
        Scanner input = new Scanner(System.in);
        // Create Auditorium object with valid input file
        Auditorium a = new Auditorium(input);
      
        // Display menu while user input indicates to keep reserving seats
        do {
            System.out.println("1. Reserve Seats");
            System.out.println("2. Exit");
         
            // User input validation
            do {
                try {
                    System.out.print("Enter menu choice: ");
                    userChoice = input.nextInt();
                    while ((userChoice != 1) && (userChoice != 2)) {
                        System.out.print("Invalid choice\nEnter menu choice: ");
                        userChoice = input.nextInt();
                    }
                break;
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid choice");
                    input.next();
                    continue;
                }
            } while (true);
         
            if (userChoice == 1) {
                System.out.println(a);
                // User input validation
                do {
                    try {
                        System.out.print("Enter row choice: ");
                        rowChoice = input.nextInt();
                        while ((rowChoice < 1) || (rowChoice > a.getNumRow())) {
                            System.out.print("Invalid choice\nEnter row choice: ");
                            rowChoice = input.nextInt();
                        }
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        input.next();
                        continue;
                    }
                } while (true);
            
                do {
                    try {
                        System.out.print("Enter starting seat letter: ");
                        seatLetter = input.next().charAt(0);
                        seatLetter = Character.toUpperCase(seatLetter);
                        while ((int)(seatLetter) - 65 > a.getNumCol()) {
                            System.out.print("Invalid choice\nEnter starting seat letter: ");
                            seatLetter = input.next().charAt(0);
                            seatLetter = Character.toUpperCase(seatLetter);
                        }
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        input.next();
                        continue;
                    }
                } while (true);
            
                do {
                    try {
                        System.out.print("Number of adult tickets: ");
                        aTix = input.nextInt();
                        while (aTix < 0) {
                            System.out.print("Invalid choice\nNumber of adult tickets: ");
                            aTix = input.nextInt();
                        }
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        input.next();
                        continue;
                    }
                } while (true);
            
                do {
                    try {
                        System.out.print("Number of child tickets: ");
                        cTix = input.nextInt();
                        while (cTix < 0) {
                            System.out.print("Invalid choice\nNumber of child tickets: ");
                            cTix = input.nextInt();
                        }
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        input.next();
                        continue;
                    }
                } while (true);
            
                do {
                    try {
                        System.out.print("Number of senior tickets: ");
                        sTix = input.nextInt();
                        while (sTix < 0) {
                            System.out.print("Invalid choice\nNumber of senior tickets: ");
                            sTix = input.nextInt();
                        }
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        input.next();
                        continue;
                    }
                } while (true);
            
                if (checkAvailability(a, rowChoice - 1, (int)(seatLetter) - 65, aTix + cTix + sTix))
                    reserveSeats(a, rowChoice - 1, (int)(seatLetter) - 65, aTix, cTix, sTix);
                else {
                    Seat bestAvail = findBestAvailable(a, a.getNumRow(), a.getNumCol(), aTix + cTix + sTix);
                    if (Character.compare(bestAvail.getSeat(), '\0') == 0)
                        System.out.println("no seats available");
                    else {
                        System.out.printf("Best available:\n%d%c", bestAvail.getRow(), bestAvail.getSeat());
                        // Displays range to console if reserving more than 1 seat
                        if ((aTix + cTix + sTix) > 1)
                            System.out.printf(" - %d%c", bestAvail.getRow(), (char)(bestAvail.getSeat() + aTix + cTix + sTix - 1));
                        // Only reserves seats if user indicates yes
                        char reserveYN;
                        do {
                            try {
                                System.out.print("\nReserve seats? (Y/N): ");
                                reserveYN = input.next().charAt(0);
                                reserveYN = Character.toUpperCase(reserveYN);
                                while (Character.compare(reserveYN, 'Y') != 0 && Character.compare(reserveYN, 'N') != 0) {
                                System.out.print("Invalid choice\nReserve seats? (Y/N): ");
                                reserveYN = input.next().charAt(0);
                                reserveYN = Character.toUpperCase(reserveYN);
                                }
                                break;
                            }
                            catch (InputMismatchException e) {
                                System.out.println("Invalid choice");
                                input.next();
                                continue;
                            }
                        } while (true);
                     
                        if (Character.compare(Character.toUpperCase(reserveYN), 'Y') == 0)
                            reserveSeats(a, bestAvail.getRow() - 1, (int)(bestAvail.getSeat()) - 65, aTix, cTix, sTix);
                    }
                }
            }
            else
                displayReport(a, a.getNumRow(), a.getNumCol());
            
        } while (userChoice == 1);
    }
}
