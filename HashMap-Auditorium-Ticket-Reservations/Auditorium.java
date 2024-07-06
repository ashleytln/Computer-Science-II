import java.io.*;
import java.util.Scanner;

public class Auditorium {
   private Node first = null;
   private int numRow = 0;
   private int numCol = 0;
   
   // Constructor, takes filename as parameter, so one Auditorium object created for each of three files
   public Auditorium(String filename) {
      String line = "";
      Scanner fileReader;
      File file = new File(filename);
      Seat tempSeat;
      Node nextptr = null, downptr = null, tempNode;
      
      // Handles FileNotFoundException as a precaution
      do {
         try {
            fileReader = new Scanner(file);
            break;
         }
         catch (FileNotFoundException f) {
            System.out.println("File cannot be opened.");
            return; // Auditorium not created if faulty programming
         }
      } while (true);
      
      // While file not empty, creating Auditorium object row by row
      while (fileReader.hasNext()) {
         line = fileReader.nextLine();
         // If line empty (if last line is empty), stop reading file
         if (line.isEmpty())
            break;
         // Increment number of rows for each nonempty line of file read
         this.numRow++;
         for (int i = 0; i < line.length(); i++) {
            tempSeat = new Seat(numRow, (char)(i + 65), line.charAt(i));
            // If Auditorium object empty, set file input to first auditorium seat
            if (first == null) {
               first = new Node(tempSeat);
               nextptr = first;
               downptr = first;
            }
            // If Auditorium's first seat exists, set file input to first seat in new row 
            else if (i == 0) {
               tempNode = new Node(tempSeat);
               downptr.setDown(tempNode);
               downptr = tempNode;
               downptr.setDown(null);
               nextptr = downptr;
            }
            // If neither first seat nor first column, traverse to last filled in seat and add file input seat to end
            else {
               while (nextptr.getNext() != null)
                  nextptr = nextptr.getNext();
               tempNode = new Node(tempSeat);
               nextptr.setNext(tempNode);
               nextptr = tempNode;
               nextptr.setNext(null);
            }
         }
      }
      // To prevent occurrence of reinitialization, set number of columns after all the traversal
      numCol = line.length();
      // Close file input stream
      fileReader.close();
   }
   
   // Accessors
   public Node getFirst() {
      return first;
   }
   public int getNumRow() {
      return numRow;
   }
   public int getNumCol() {
      return numCol;
   }
   
   @Override
   // Used to display auditorium when user wants to reserve seats
   public String toString() {
      String auditorium = "  ";
      int i;
      Node nextptr = first, downptr = nextptr.getDown();
      // Store column header
      for (i = 0; i < numCol; i++)
         auditorium += (char)(i + 65);
      auditorium += "\n";
      for (i = 0; i < numRow; i++) {
         // Store row side-column
         auditorium += "" + (i + 1) + " ";
         for (int j = 0; j < numCol; j++) {
            // If tickets previously reserved with ticket type, mark occupied
            if (Character.isLetter(nextptr.getPayload().getTixType()))
               auditorium += "#";
            // If tickets are available for reservation, mark empty
            else if (!(Character.isLetter(nextptr.getPayload().getTixType())))
               auditorium += ".";
            // Traverse to next seat in auditorium
            if (nextptr.getNext() != null)
               nextptr = nextptr.getNext();
         }
         auditorium += "\n";
         // Traverse to next row in auditorium
         if (downptr != null) {
            nextptr = downptr;
            downptr = nextptr.getDown();
         }
      }
      // Return fully constructed Auditorium
      return auditorium;
   }
}
