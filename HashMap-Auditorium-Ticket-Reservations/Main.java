import java.io.*;
import java.util.*;		// Scanner, HashMap, ArrayList

public class Main {
   // Reads userdb.dat file for usernames and passwords, stores in HashMap and returns HashMap to main method.
   // Returns HashMap object storing usernames & passwords from file since dynamically created inside method.
   public static HashMap<String, ArrayList<String>> createHashMap() {
      // Create new HashMap object
      HashMap <String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
      Scanner fileReader;
      String line;
      // In case file does not exist, returns null and program will end if returned value is null
      do {
         try {
            fileReader = new Scanner(new File("userdb.dat"));
            break;
         }
         catch (FileNotFoundException f) {
            System.out.println("File could not be opened.");
            return null;
         }
      } while (true);
      // For each nonempty fileline, store each username (key) & password (into ArrayList value) into HashMap
      while (fileReader.hasNext()) {
         line = fileReader.nextLine();
         // If (last) line is empty, stop reading file/do not store in HashMap
         if (line.isEmpty())
            break;
         // Create new ArrayList for each username entry to hold individual password (and possibly orders)
         ArrayList<String> list = new ArrayList<String>();
         String username = line.substring(0, line.indexOf(' '));
         String password = line.substring(line.indexOf(' ') + 1);
         // Add password to ArrayList
         list.add(password);
         // Assign ArrayList to username (key) in HashMap object
         map.put(username, list);
      }
      // Close input stream
      fileReader.close();
      // Return filled in HashMap object
      return map;
   }
   // Displays to console a formatted String of each auditorium's individual statistics (open seats, reserved seats,
   //    adult/child/senior sales, total sales) as well as the auditoriums' statistics combined together (summed).
   public static void printReport(Auditorium a1, Auditorium a2, Auditorium a3) {
      // To reduce unnecessary memory allocation, the following calculations will be used for:
      // Open seats = numRow * numCol - (a + c + s), Reserved seats = a + c + s, Individual auditorium sales = 10 * a + 5 * c + 7.5 * s
      // aTotal, cTotal, and sTotal will be incremented by the current auditorium's value of a, c, s, respectively with each traversal
      int a = 0, c = 0, s = 0, aTotal =  0, cTotal = 0, sTotal = 0;
      double allTotal = 0;
      Node nextptr = a1.getFirst(), downptr = nextptr.getDown();
      // Traverses through Auditorium 1, counts number of adult, child, senior seats reserved
      for (int i = 0; i < a1.getNumRow(); i++) {
         for (int j = 0; j < a1.getNumCol(); j++) {
            if (Character.compare(nextptr.getPayload().getTixType(), 'A') == 0)
               a++;
            else if (Character.compare(nextptr.getPayload().getTixType(), 'C') == 0)
               c++;
            else if (Character.compare(nextptr.getPayload().getTixType(), 'S') == 0)
               s++;
            if (nextptr.getNext() != null)
               nextptr = nextptr.getNext();
         }
         if (downptr != null) {
            nextptr = downptr;
            downptr = nextptr.getDown();
         }
      }
      aTotal += a;
      cTotal += c;
      sTotal += s;
      allTotal += 10 * a + 5 * c + 7.5 * s;
      System.out.printf("Auditorium 1\t%d\t%d\t%d\t%d\t%d\t$%.2f\n", a1.getNumRow() * a1.getNumCol() - (a + c + s),
                           a + c + s, a, c, s, 10 * a + 5 * c + 7.5 * s);
      nextptr = a2.getFirst();
      downptr = nextptr.getDown();
      a = c = s = 0;
      // Traverses through Auditorium 2, counts number of adult, child, senior seats reserved
      for (int i = 0; i < a2.getNumRow(); i++) {
         for (int j = 0; j < a2.getNumCol(); j++) {
            if (Character.compare(nextptr.getPayload().getTixType(), 'A') == 0)
               a++;
            else if (Character.compare(nextptr.getPayload().getTixType(), 'C') == 0)
               c++;
            else if (Character.compare(nextptr.getPayload().getTixType(), 'S') == 0)
               s++;
            if (nextptr.getNext() != null)
               nextptr = nextptr.getNext();
         }
         if (downptr != null) {
            nextptr = downptr;
            downptr = nextptr.getDown();
         }
      }
      aTotal += a;
      cTotal += c;
      sTotal += s;
      allTotal += 10 * a + 5 * c + 7.5 * s;
      System.out.printf("Auditorium 2\t%d\t%d\t%d\t%d\t%d\t$%.2f\n", a2.getNumRow() * a2.getNumCol() - (a + c + s),
                           a + c + s, a, c, s, 10 * a + 5 * c + 7.5 * s);
      
      nextptr = a3.getFirst();
      downptr = nextptr.getDown();
      a = c = s = 0;
      // Traverses through Auditorium 3, counts number of adult, child, senior seats reserved
      for (int i = 0; i < a3.getNumRow(); i++) {
         for (int j = 0; j < a3.getNumCol(); j++) {
            if (Character.compare(nextptr.getPayload().getTixType(), 'A') == 0)
               a++;
            else if (Character.compare(nextptr.getPayload().getTixType(), 'C') == 0)
               c++;
            else if (Character.compare(nextptr.getPayload().getTixType(), 'S') == 0)
               s++;
            if (nextptr.getNext() != null)
               nextptr = nextptr.getNext();
         }
         if (downptr != null) {
            nextptr = downptr;
            downptr = nextptr.getDown();
         }
      }
      aTotal += a;
      cTotal += c;
      sTotal += s;
      allTotal += 10 * a + 5 * c + 7.5 * s;
      System.out.printf("Auditorium 3\t%d\t%d\t%d\t%d\t%d\t$%.2f\n", a3.getNumRow() * a3.getNumCol() - (a + c + s),
                           a + c + s, a, c, s, 10 * a + 5 * c + 7.5 * s);
      // Sum of open seats, reserved seats, adult seats, child seats, senior seats, and total sales accumulated from all 3 auditoriums
      System.out.printf("Total\t%d\t%d\t%d\t%d\t%d\t$%.2f\n", (a1.getNumRow() * a1.getNumCol()) + (a2.getNumRow() * a2.getNumCol()) +
                           (a3.getNumRow() * a3.getNumCol()) - (aTotal + cTotal + sTotal), aTotal + cTotal + sTotal, aTotal, cTotal, sTotal, allTotal);
   }
   // Store auditorium in output file (called 3 times, for each Auditorium) before program terminates
   // Parameters are one Auditorium object and its corresponding output filename since creating multiple output stream objects
   //    to read all 3 auditorium files may cause issues with the stream, so it seemed better to call function 3 times rather than once
   public static void storeAuditorium(Auditorium a, String filename) {
      File file = new File(filename);
      FileOutputStream os;
      PrintWriter printer;
      // If IOException/FileNotFoundException occurs, program will end (through returning to main method & also returning in main (exit))
      do {
         try {
            if (!file.exists())
               file.createNewFile();
            os = new FileOutputStream(file, false);
            printer = new PrintWriter(os);
            break;
         }
         catch (IOException e) {
            System.out.println("File could not be opened.");
            return;
         }
      } while (true);
      // Traverse through auditorium and print each seat (with ticket type) to output file
      Node nextptr = a.getFirst(), downptr = nextptr.getDown();
      for (int i = 0; i < a.getNumRow(); i++) {
         for (int j = 0; j < a.getNumCol(); j++) {
            printer.print(nextptr);
            if (nextptr.getNext() != null)
               nextptr = nextptr.getNext();
         }
         // Once pointer reaches end of row, moves on to printing next row (if not the last)
         if (i < (a.getNumRow() - 1))
            printer.print("\n");
         if (downptr != null) {
            nextptr = downptr;
            downptr = nextptr.getDown();
         }
      }
      // Close output stream
      printer.close();
   }
   // Uses console input/output to prompt user throuugh reserving seats for their given choice of auditorium.
   // Parameters are Scanner for console input, Auditorium to pass in to checkAvailability (verify Auditorium seats) and/or reserveSeats (modify seat
   //    ticket type from empty to chosen type), int for Auditorium number to pass in to addOrder (user's audChoice from main, used to write order),
   //    ArrayList<String> holding user's orders from HashMap to pass into addOrder where String holding order will be added to the end
   public static void startReserveProcess(Scanner s, Auditorium aud, int num, ArrayList<String> list) {
      int rowChoice, aTix, cTix, sTix;
      char seatLetter;
      // Input data validation for row choice, seat letter, and number of each ticket type
      do {
         try {
            System.out.print("Enter row choice: ");
            rowChoice = s.nextInt();
            // If input not within bounds of auditorium number of rows
            while ((rowChoice < 1) || (rowChoice > aud.getNumRow())) {
               System.out.print("Invalid choice\nEnter row choice: ");
               rowChoice = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Enter starting seat letter: ");
            seatLetter = s.next().charAt(0);
            seatLetter = Character.toUpperCase(seatLetter);
            // If input not within bounds of auditorium number of columns
            while (!Character.isLetter(seatLetter) || (int)(seatLetter) - 65 > aud.getNumCol()) {
               System.out.print("Invalid choice\nEnter starting seat letter: ");
               seatLetter = s.next().charAt(0);
               seatLetter = Character.toUpperCase(seatLetter);
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Number of adult tickets: ");
            aTix = s.nextInt();
            // If input is integer but negative, invalid
            while (aTix < 0) {
               System.out.print("Invalid choice\nNumber of adult tickets: ");
               aTix = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Number of child tickets: ");
            cTix = s.nextInt();
            // If input is integer but negative, invalid
            while (cTix < 0) {
               System.out.print("Invalid choice\nNumber of child tickets: ");
               cTix = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Number of senior tickets: ");
            sTix = s.nextInt();
            // If input is integer but negative, invalid
            while (sTix < 0) {
               System.out.print("Invalid choice\nNumber of senior tickets: ");
               sTix = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      // Data all validated, call checkAvailability to check if all seats in selection are empty to reserve, then reserve seats & add to user's list of orders
      // rowChoice - 1 & (int)(seatLetter) - 65 is for feasibility of traversal within the Auditorium for checkAvailability & reserveSeats
      if (checkAvailability(aud, rowChoice - 1, (int)(seatLetter) - 65, aTix + cTix + sTix)) {
         reserveSeats(aud, rowChoice - 1, (int)(seatLetter) - 65, aTix, cTix, sTix);
         addOrder(num, rowChoice, seatLetter, aTix, cTix, sTix, list);
      }
      // If seat selection unavailable, call findBestAvailable and find best available seats in auditorium if possible for reservation
      else {
         // Find best available seats using findBestAvailable with desired auditorium and number of tickets
         Seat bestAvail = findBestAvailable(aud, aud.getNumRow(), aud.getNumCol(), aTix + cTix + sTix);
         // If Seat is null, seats are unavailable
         if (Character.compare(bestAvail.getSeat(), '\0') == 0)
            System.out.println("no seats available");
         else {
            // If best seat available, display starting seat of selection
            System.out.printf("Best available:\n%d%c", bestAvail.getRow(), bestAvail.getSeat());
            // Displays seat selection range to console if reserving more than 1 seat
            // Ending seat letter calculated by adding total tickets - 1 to best available seat letter and converting to char
            if ((aTix + cTix + sTix) > 1)
               System.out.printf(" - %d%c\n", bestAvail.getRow(), (char)(bestAvail.getSeat() + aTix + cTix + sTix - 1));
            char reserveYN;
            // Prompt user to choose whether to reserve seats or not, validates user input
            do {
               try {
                  System.out.print("Reserve seats? (Y/N): ");
                  reserveYN = s.next().charAt(0);
                  reserveYN = Character.toUpperCase(reserveYN);
                  while (Character.compare(reserveYN, 'Y') != 0 && Character.compare(reserveYN, 'N') != 0) {
                     throw new InputMismatchException();
                  }
                  break;
               }
               catch (InputMismatchException e) {
                  System.out.println("Invalid choice");
                  s.next();
                  continue;
               }
            } while (true);
            // Only reserve seats and add to order if user decides to reserve
            if (Character.compare(Character.toUpperCase(reserveYN), 'Y') == 0) {
               reserveSeats(aud, bestAvail.getRow() - 1, (int)(bestAvail.getSeat()) - 65, aTix, cTix, sTix);
               addOrder(num, bestAvail.getRow(), bestAvail.getSeat(), aTix, cTix, sTix, list);
            }
         }
      }
   }
   // Adds order to end of ArrayList<String> which holds user's (password and) past orders if applicable
   // Parameters are int for Auditorium number to label which Auditorium order pertains to, int for row where order's seats are, char for starting
   //    seat of order's seat selection, int for each ticket type count for order, ArrayList<String> holding user's orders in HashMap
   public static void addOrder(int num, int r, char seat, int a, int c, int s, ArrayList<String> list) {
      // Construct order with which Auditorium it belongs to
      String order = "Auditorium " + num + ", ";
      // For each seat in order selection, add seat(s) to string separated by commas if multiple
      for (int i = 0; i < (a + c + s); i++) {
         order += r + Character.toString(seat + i);
         if (i != a + c + s - 1)
            order += ",";
      }
      // Labels order with number of adult, child, senior tickets
      order += "\n" + a + " adult, " + c + " child, " + s + " senior";
      // Adds to end of ArrayList holding orders
      list.add(order);
   }
   // Checks if all seats in user's selection are available to reserve
   // Parameters are Auditorium to traverse and verify that all seats are empty, int to traverse to desired auditorium row & seat and to use as
   //    an upper bound to check if all seats available in user's selection
   public static boolean checkAvailability(Auditorium aud, int r, int seat, int total) {
      Node nextptr = aud.getFirst(), downptr = aud.getFirst();
      int i;
      // Traverse to desired row of linked list
      for (i = 0; i < r; i++) {
         downptr = downptr.getDown();
      }
      // Traverse to desired starting seat in row 
      nextptr = downptr;
      for (i = 0; i < seat; i++) {
         nextptr = nextptr.getNext();
      }
      // At user's desired starting seat, check if all seats are empty within seat selection range (given amount of total tickets)
      for (i = 0; i < total; i++) {
         // If seat is not empty, return false (seats not available)
         if (Character.compare(nextptr.getPayload().getTixType(), '.') != 0)
            return false;
         // Otherwise, traverse to next seat in seat selection
         if (nextptr.getNext() != null)
            nextptr = nextptr.getNext();
         // If at end of row and availability not fulfilled, return false (seats not available)
         else if (nextptr.getNext() == null && i != total - 1)
            return false;
         // If at end of row and availability fulfilled, break from loop and return true (seats available)
         else
            break;
      }
      return true;
   }
   // Find best available seats in entire auditorium by comparing for shortest distance from middle of auditorium and smallest row closest to auditorium
   // Parameters are Auditorium to traverse and verify all seats in potential selection are empty, ints holding auditorium number of rows/columns, int holding number of total tickets
   public static Seat findBestAvailable(Auditorium aud, int r, int c, int total) {
      // startingptr holds the starting seat of each potential seat selection since nextptr and downptr need to keep value and change for traversal
      Node nextptr = aud.getFirst(), downptr = nextptr.getDown(), startingptr = nextptr;
      // Calculate middle row & middle seat for center of Auditorium, set smallestDist to the maximum an auditorium could have to start for comparison
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
         // Traverse to next row if exists in Auditorium
         if (downptr != null) {
            nextptr = downptr;
            startingptr = nextptr;
            downptr = nextptr.getDown();
         }
      }
      // Return starting seat of best available selection (null if could not be found)
      return bestAvail;
   }
   // Modifies Auditorium by changing all seats in selection to its new ticket type
   // Parameters are Auditorium to traverse and modify based on seat selection, ints holding seat selection row, starting seat, ticket type information
   // Since modifying parameter variables inside method will have no effect on actual argument variables, not creating new variables to save memory
   public static void reserveSeats(Auditorium aud, int r, int seat, int a, int c, int s) {
      Node nextptr = aud.getFirst(), downptr = aud.getFirst();
      int i; 
      // Traverse to desired row of linked list
      for (i = 0; i < r; i++) {
         downptr = downptr.getDown();
      }
      nextptr = downptr;
      // Traverse to desired starting seat in row 
      for (i = 0; i < seat; i++) {
         nextptr = nextptr.getNext();
      }
      
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
   // Helper method to edit order when adding tickets when updating
   // Uses String to locate index of order in ArrayList
   private static void editAddOrder(int num, int r, char seat, int a, int c, int s, ArrayList<String> list, String order) {
      String edit = "Auditorium " + num + ", ";
      String seats = "";
      // Form string with seats in seat selection
      for (int i = 0; i < (a + c + s); i++) {
         seats += r + Character.toString(seat + i);
         if (i != a + c + s - 1)
            seats += ",";
      }
      // Starts at index of where seats start in order
      for (int j = order.indexOf(' ') + 4; j < order.length(); j += 3) {
         // If edit row is numerically before current seat's, add before, then add the rest of the seats ordered
         if (r < Character.getNumericValue(order.charAt(j))) {
            edit += seats;
            edit += ",";
            edit += order.substring(j, order.indexOf('\n'));
            break;
         }
         // If edit row is numerically after than order's current seats, add current seat, reiterate for loop to add edit seats
         else if (r > Character.getNumericValue(order.charAt(j))) {
            edit += order.substring(j, j + 2);
            edit += ",";
         }
         // If edit row is same as current seat's
         else if (r == Character.getNumericValue(order.charAt(j))) {
            // If edit seat is alphabetically before current seat's, add before, then add the rest of the seats
            if (seat < order.charAt(j + 1)) {
               edit += seats;
               edit += ",";
               edit += order.substring(j, order.indexOf('\n'));
               break;
            }
            // If edit seat is alphabetically after current seat's, add current seat, reiterate loop to add edit seats
            else if (seat > order.charAt(j + 1)) {
               edit += order.substring(j, j + 2);
               edit += ",";
            }
         }
         // If current seat is last seat in order
         if (order.charAt(j + 2) != ',') {
            edit += seats;
            break;
         }
      }
      // Calculate new total of ticket types based on new seat selection and old seat selection counts
      int aTotal = a + Character.getNumericValue(order.charAt(order.indexOf("adult") - 2));
      int cTotal = c + Character.getNumericValue(order.charAt(order.indexOf("child") - 2));
      int sTotal = s + Character.getNumericValue(order.charAt(order.indexOf("senior") - 2));
      edit += "\n" + aTotal + " adult, " + cTotal + " child, " + sTotal + " senior";
      // Modify current order in ArrayList by accessing index of old order and reassigning to edited order
      list.set(list.indexOf(order), edit);
   }
   // Prompts user through reservation process, adding tickets to preexisting order, tickets can be anywhere in Auditorium from given order
   // Returns boolean which determines whether user taken back to update menu (false) or main menu (true)
   public static boolean addTickets(Scanner s, Auditorium aud, int num, ArrayList<String> list, String order) {
      int rowChoice, aTix, cTix, sTix;
      char seatLetter;
      // Data validation for row, starting seat letter, number of ticket types
      do {
         try {
            System.out.print("Enter row choice: ");
            rowChoice = s.nextInt();
            while ((rowChoice < 1) || (rowChoice > aud.getNumRow())) {
               System.out.print("Invalid choice\nEnter row choice: ");
               rowChoice = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Enter starting seat letter: ");
            seatLetter = s.next().charAt(0);
            seatLetter = Character.toUpperCase(seatLetter);
            while (!Character.isLetter(seatLetter) || (int)(seatLetter) - 65 > aud.getNumCol()) {
               System.out.print("Invalid choice\nEnter starting seat letter: ");
               seatLetter = s.next().charAt(0);
               seatLetter = Character.toUpperCase(seatLetter);
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Number of adult tickets: ");
            aTix = s.nextInt();
            while (aTix < 0) {
               System.out.print("Invalid choice\nNumber of adult tickets: ");
               aTix = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Number of child tickets: ");
            cTix = s.nextInt();
            while (cTix < 0) {
               System.out.print("Invalid choice\nNumber of child tickets: ");
               cTix = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Number of senior tickets: ");
            sTix = s.nextInt();
            while (sTix < 0) {
               System.out.print("Invalid choice\nNumber of senior tickets: ");
               sTix = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      // Check if seats are available in user's selection, reserve and edit order if possible
      // Returns true to main (go back to main menu)
      if (checkAvailability(aud, rowChoice - 1, (int)(seatLetter) - 65, aTix + cTix + sTix)) {
         reserveSeats(aud, rowChoice - 1, (int)(seatLetter) - 65, aTix, cTix, sTix);
         editAddOrder(num, rowChoice, seatLetter, aTix, cTix, sTix, list, order);
         return true;
      }
      // If seats unavailable, do not find best available and return false (go back to update menu)
      else {
         System.out.println("Seats unavailable");
         return false;
      }
   }
   // Helper method to edit order when deleting a ticket
   // Uses String to locate index of order in ArrayList
   private static void editDeleteOrder(int num, int r, char seat, char type, ArrayList<String> list, String order) {
      // String holds beginning part of order with auditorium number
      String edit = order.substring(0, order.indexOf(',') + 2);
      // If seat is 1st seat of order, add substring without seat in it
      if (order.indexOf(Integer.toString(r) + seat) == order.indexOf(',') + 2)
         edit += order.substring(order.indexOf(Integer.toString(r) + seat) + 3, order.indexOf('\n'));
      else {
         edit += order.substring(order.indexOf(',') + 2, order.indexOf(Integer.toString(r) + seat) - 1);
         // If removed seat is not last seat of order, add seats that come after removed seat in order
         if (order.indexOf(Integer.toString(r) + seat) != order.indexOf('\n') - 2)
            edit += order.substring(order.indexOf(Integer.toString(r) + seat) + 3, order.indexOf('\n'));
      }
      // Stores value of original ticket type count from old order
      int a = Character.getNumericValue(order.charAt(order.indexOf("adult") - 2));
      int c = Character.getNumericValue(order.charAt(order.indexOf("child") - 2));
      int s = Character.getNumericValue(order.charAt(order.indexOf("senior") - 2));
      // Based on type of ticket removed, decrement value
      if (type == 'A')
         a--;
      else if (type == 'C')
         c--;
      else if (type == 'S')
         s--;
      edit += "\n" + a + " adult, " + c + " child, " + s + " senior";
      // Modify order at original order's index in ArrayList by assigning edited order
      list.set(list.indexOf(order), edit);
   }
   // Prompts user through deletion process (row & seat), deleting a ticket from preexisting order, ticket must be within order selection
   // Returns boolean which determines whether user taken back to update menu (false) or main menu (true)
   public static boolean deleteTicket(Scanner s, Auditorium aud, int num, ArrayList<String> list, String order) {
      int rowChoice, i;
      char seatLetter, tixType;
      Node nextptr = aud.getFirst(), downptr = aud.getFirst();
      // Validating user's input of row and seat letter
      do {
         try {
            System.out.print("Enter row choice: ");
            rowChoice = s.nextInt();
            while ((rowChoice < 1) || (rowChoice > aud.getNumRow())) {
               System.out.print("Invalid choice\nEnter row choice: ");
               rowChoice = s.nextInt();
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      do {
         try {
            System.out.print("Enter seat letter: ");
            seatLetter = s.next().charAt(0);
            seatLetter = Character.toUpperCase(seatLetter);
            while (!Character.isLetter(seatLetter) || (int)(seatLetter) - 65 > aud.getNumCol()) {
               System.out.print("Invalid choice\nEnter starting seat letter: ");
               seatLetter = s.next().charAt(0);
               seatLetter = Character.toUpperCase(seatLetter);
            }
            break;
         }
         catch (InputMismatchException e) {
            System.out.println("Invalid choice");
            s.next();
            continue;
         }
      } while (true);
      // Verify if seat is part of order and can be removed; if invalid, return false (go back to update menu)
      if (!order.contains(Integer.toString(rowChoice) + seatLetter)) {
         System.out.println("Seat is not part of order");
         return false;
      }
      // If seat valid, traverse to desired row of linked list
      for (i = 0; i < rowChoice - 1; i++) {
         downptr = downptr.getDown();
      }
      nextptr = downptr;
      // Traverse to desired seat in row 
      for (i = 0; i < (int)(seatLetter) - 65; i++) {
         nextptr = nextptr.getNext();
      }
      // Get type of ticket being removed, used to edit order
      tixType = nextptr.getPayload().getTixType();
      // If seat is the only one in order, remove from list of orders in ArrayList
      if (order.substring(order.indexOf(',') + 2, order.indexOf('\n')).length() == 2)
         list.remove(order);
      // Otherwise, modify order using type of ticket removed
      else
         editDeleteOrder(num, rowChoice, seatLetter, tixType, list, order);
      // Set removed seat in Auditorium back to empty
      nextptr.getPayload().setTixType('.');
      // Return true since removal successful (go back to main menu)
      return true;
   }
   // Cancels order by removing from ArrayList and setting all seats in order back to empty in Auditorium
   public static void cancelOrder(Auditorium aud, ArrayList<String> list, String order) {
      // Seat selection derived from order
      String seats = order.substring(order.indexOf(',') + 2, order.indexOf('\n'));
      do {
         Node nextptr = aud.getFirst(), downptr = aud.getFirst();
         int i;
         // Traverse to desired row of linked list
         for (i = 0; i < Character.getNumericValue(seats.charAt(0)) - 1; i++) {
            downptr = downptr.getDown();
         }
         nextptr = downptr;
         // Traverse to desired starting seat in row 
         for (i = 0; i < (int)(seats.charAt(1)) - 65; i++) {
            nextptr = nextptr.getNext();
         }
         // Set seat in selection to emtpy
         nextptr.getPayload().setTixType('.');
         // If seats left in selection, move on to next seat to be removed in selection
         if (seats.indexOf(',') != -1)
            seats = seats.substring(3);
         // If no more seats left in selection, break out of loop
         else if (seats.length() == 2)
            break;
      } while (true);
      // Remove order from ArrayList (automatically shifts left orders if needed)
      list.remove(order);
   }
   
   public static void main(String[] args) throws IOException {
      // Create HashMap object by reading userdb.dat file
      HashMap <String, ArrayList <String>> map = createHashMap();
      // As data validation, if map is not created from file, exit program.
      if (map == null)
         return;
      String username, password;
      int custChoice = 0, adChoice = 0;
      Scanner console = new Scanner(System.in);
      // Creating Auditorium objects using filenames
      Auditorium a1 = new Auditorium("A1.txt");
      Auditorium a2 = new Auditorium("A2.txt");
      Auditorium a3 = new Auditorium("A3.txt");
      // Login system loop, iterates when login information is invalid or user chooses to log out
      do {
         // Prompt user for username and password
         System.out.print("Enter username: ");
         username = console.next();
         System.out.print("Enter password: ");
         password = console.next();
         // Validating username exists in HashMap
         if (map.containsKey(username)) {
            // Implement 3 strike rule - if password entered wrong 3 times, reset and have user enter different input
            int strikes = 0;
            // Loop iterates maximum of 2 times; 3rd strike breaks loop
            while (password.compareTo(map.get(username).get(0)) != 0) {
               System.out.println("Invalid password");
               strikes++;
               if (strikes < 3) {
                  System.out.print("Enter password: ");
                  password = console.next();
               }
               else if (strikes == 3) {
                  System.out.println("Wrong password entered 3 times. System resetting...\n");
                  break;
               }   
            }
            // Display admin main menu while user logged in is admin
            while (username.compareTo("admin") == 0) {
               System.out.println("\nAdmin Main Menu\n1. Print Report\n2. Logout\n3. Exit\n");
               // Data validation for menu choice
               do {
                  try {
                     System.out.print("Enter menu choice: ");
                     adChoice = console.nextInt();
                     while (adChoice != 1 && adChoice != 2 && adChoice != 3) {
                        System.out.print("Invalid choice\nEnter menu choice: ");
                        adChoice = console.nextInt();
                     }
                     break;
                  }
                  catch (InputMismatchException e) {
                     System.out.println("Invalid choice");
                     console.next();
                     continue;
                  }
               } while (true);
               // If admin chooses 1, print report of each Auditorium
               if (adChoice == 1)
                  printReport(a1, a2, a3);
               // If choice is 2, break out of while loop, login system loop will reiterate
               else if (adChoice == 2) {
                  System.out.println();
                  break;
               }
               // If choice is 3, call function to store auditoriums in output files, program ends since login system loop does not reiterate
               else if (adChoice == 3) {
                  storeAuditorium(a1, "A1Final.txt");
                  storeAuditorium(a2, "A2Final.txt");
                  storeAuditorium(a3, "A3Final.txt");
                  return;
               }
            }
            // Display customer main menu while user logged in is not admin
            while (username.compareTo("admin") != 0) {
               System.out.println("\nCustomer Main Menu\n1. Reserve Seats\n2. View Orders\n3. Update Order\n4. Display Receipt\n5. Log Out\n");
               // Data validation for main menu choice
               do {
                  try {
                     System.out.print("Enter menu choice: ");
                     custChoice = console.nextInt();
                     while (custChoice != 1 && custChoice != 2 && custChoice != 3 && custChoice != 4 && custChoice != 5) {
                        System.out.print("Invalid choice\nEnter menu choice: ");
                        custChoice = console.nextInt();
                     }
                     break;
                  }
                  catch (InputMismatchException e) {
                     System.out.println("Invalid choice");
                     console.next();
                     continue;
                  }
               } while (true);
               // If customer chooses 1, have customer choose which Auditorium to reserve in (validating data input)
               if (custChoice == 1) {
                  int audNum;
                  Auditorium aud = null;
                  System.out.println("\n1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3\n");
                  do {
                     try {
                        System.out.print("Enter auditorium choice: ");
                        audNum = console.nextInt();
                        while (audNum != 1 && audNum != 2 && audNum != 3) {
                           System.out.print("Invalid choice\nEnter auditorium choice: ");
                           audNum = console.nextInt();
                        }
                        break;
                     }
                     catch (InputMismatchException e) {
                        System.out.println("Invalid choice");
                        console.next();
                        continue;
                     }
                  } while (true);
                  // Based on user input, store address of chosent Auditorium in aud variable 
                  if (audNum == 1) {
                     System.out.println(a1);
                     aud = a1;
                  }
                  else if (audNum == 2) {
                     System.out.println(a2);
                     aud = a2;
                  }
                  else if (audNum == 3) {
                     System.out.println(a3);
                     aud = a3;
                  }
                  // Reserve seats using startReserveProcess
                  startReserveProcess(console, aud, audNum, map.get(username));
               }
               // If choice is 2, display all orders contained in username's ArrayList value (starting at index 1)
               else if (custChoice == 2) {
                  // If arraylist is not bigger than 1, no orders
                  if (map.get(username).size() < 2)
                     System.out.println("No orders\n");
                  // Print all user's orders
                  else {
                     System.out.println();
                     for (int i = 1; i < map.get(username).size(); i++)
                        System.out.println(map.get(username).get(i));
                  }
               }
               // If choice is 3, display order submenu
               else if (custChoice == 3) {
                  String order;
                  // If arraylist is not bigger than 1, display no orders
                  if (map.get(username).size() < 2) {
                     System.out.println("No orders");
                     break;
                  }
                  else {
                     // Display all user's orders in menu type display
                     for (int i = 1; i < map.get(username).size(); i++)
                        System.out.println(i + ". " + map.get(username).get(i));
                     // Data validation for order choice
                     do {
                        try {
                           System.out.println("Enter order to update:");
                           // Store input in custChoice since value will be overridden later with 2nd menu choice (will not cause reiteration of login system loop)
                           custChoice = console.nextInt();
                           if (custChoice > map.get(username).size() - 1 || custChoice < 1)
                              throw new InputMismatchException();
                           break;
                        }
                        catch (InputMismatchException e) {
                           System.out.println("Invalid choice\n");
                           console.next();
                           continue;
                        }
                     } while (true);
                     // Since index of ArrayList is offset by 1 (password is 0th element), no need to alter index argument
                     order = map.get(username).get(custChoice);
                     System.out.println("\n1. Add tickets to order\n2. Delete tickets from order\n3. Cancel Order");
                     // Data validation for menu choice
                     do {
                        try {
                           System.out.print("Enter menu choice: ");
                           // Store input in custChoice since choices 1-3 will not cause program to enter else if blocks for choices 4 & 5
                           custChoice = console.nextInt();
                           if (custChoice != 1 && custChoice != 2 && custChoice != 3)
                              throw new InputMismatchException();
                           break;
                        }
                        catch (InputMismatchException e) {
                           System.out.println("Invalid choice");
                           console.next();
                           continue;
                        }
                        finally {
                           // Determine which Auditorium object to modify from chosen order
                           Auditorium aud;
                           if (Character.getNumericValue(order.charAt(order.indexOf(' ') + 1)) == 1)
                              aud = a1;
                           else if (Character.getNumericValue(order.charAt(order.indexOf(' ') + 1)) == 2)
                              aud = a2;
                           else
                              aud = a3;
                           // If user chooses 1, call addTickets, if return true break to main menu, if return false continue back to update menu
                           if (custChoice == 1) {
                              System.out.println(aud);
                              if (addTickets(console, aud, Character.getNumericValue(order.charAt(order.indexOf(' ') + 1)), map.get(username), order))
                                 break;
                           }
                           // If user chooses 2, call deleteTickets, if return true break to main menu, if return false continue back to update menu
                           else if (custChoice == 2) {
                              if (deleteTicket(console, aud, Character.getNumericValue(order.charAt(order.indexOf(' ') + 1)), map.get(username), order))
                                 break;
                           }
                           // If user chooses 3, call cancelOrder, break to main menu
                           else if (custChoice == 3) {
                              cancelOrder(aud, map.get(username), order);
                              break;
                           }
                        }
                     } while (true);
                  }
               }
               // If choice is 4, display each order & calculate order total
               else if (custChoice == 4) {
                  double orderTotal = 0;
                  System.out.println();
                  for (int i = 1; i < map.get(username).size(); i++) {
                     int adultCount = 0, childCount = 0, seniorCount = 0;
                     System.out.println(map.get(username).get(i));
                     // Calculate ticket type count from orders
                     adultCount += Character.getNumericValue(map.get(username).get(i).charAt(map.get(username).get(i).indexOf("adult") - 2));
                     childCount += Character.getNumericValue(map.get(username).get(i).charAt(map.get(username).get(i).indexOf("child") - 2));
                     seniorCount += Character.getNumericValue(map.get(username).get(i).charAt(map.get(username).get(i).indexOf("senior") - 2));
                     System.out.printf("Order Total: $%.2f\n\n", 10 * adultCount + 5 * childCount + 7.5 * seniorCount);
                     orderTotal += 10 * adultCount + 5 * childCount + 7.5 * seniorCount;
                  }
                  System.out.printf("Customer Total: $%.2f\n", orderTotal);
               }
               // If choice is 5, break out of while loop, login system loop will reiterate
               else if (custChoice == 5) {
                  break;
               }
            }
         }
         else
            System.out.println("The username entered does not exist in our system.\n");
      } while (!(map.containsKey(username)) || password.compareTo(map.get(username).get(0)) != 0 || custChoice == 5 || adChoice == 2);
      console.close();
   }
}
