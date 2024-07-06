import java.io.*;
import java.util.Scanner;

public class Main {
   /* Add to Existing Title in BST
   *     Parameters: BinTree<DVD> (pass in BST to invoke recursive BST search method), DVD, int (quantity to add)
   *     Returns: void
   *     Method called if title exists in BST.
   *     Uses recursive BST search method to retrieve memory address of DVD object to modify its data using int parameter.
   */
   public static void addToExisting(BinTree<DVD> b, DVD d, int quantity) {
      // Search returns DVD object to be added to
      DVD temp = b.search(d);
      // Checks if DVD exists in BST as a precaution, adds quantity to number of available DVDs
      if (temp != null)
         temp.setAvailable(temp.getAvailable() + quantity);
   }
   
   /* Remove from Existing Title in BST
   *     Parameters: BinTree<DVD> (pass in BST to invoke recursive BST search and delete method), DVD, int (quantity to remove)
   *     Returns: void
   *     Method called if title exists in BST.
   *     Uses recursive BST search method to retrieve memory address of DVD object to modify its data using int parameter.
   *     Removes title from inventory (Node holding DVD object from BST) if both available and rented member variables are 0.
   */
   public static void removeFromExisting(BinTree<DVD> b, DVD d, int quantity) {
      // Search returns DVD object to be removed from
      DVD temp = b.search(d);
      // If there are available DVDS, subtract specified quantity from current availability
      if (temp.getAvailable() > 0)
         temp.setAvailable(temp.getAvailable() - quantity);
      // If no more available DVDs and no DVDs are being rented, remove DVD from tree
      if ((temp.getAvailable() == 0) && (temp.getRented() == 0))
         b.delete(temp);
   }
   
   /* Increment Available or Rented
   *     Parameters: BinTree<DVD> (pass in BST to invoke recursive BST search method), DVD, int (quantity to add)
   *     Returns: void
   *     Function called if title exists in BST.
   *     Uses recursive BST search method to retrieve memory address of DVD object to modify its data using int parameter.
   *     Either 1 or -1 is passed in as argument for int parameter.
   */
   public static void incrementAvailRented(BinTree<DVD> b, DVD d, int quantity) {
      // Search returns DVD object to be modified
      DVD temp = b.search(d);
      // If quantity == 1, adds to available, subtracts from rented
      // If quantity == -1, subtracts from available, adds to rented
      temp.setAvailable(temp.getAvailable() + quantity);
      temp.setRented(temp.getRented() - quantity);
   }
   
   /* Read Inventory File and Build Binary Search Tree
   *     Parameters: String (filename)
   *     Returns: BinTree<DVD> (since tree instantiated within method)
   *     Reads inventory file and builds BST.
   *     Throws FileNotFoundException since data input (file) should be valid.
   */
   public static BinTree<DVD> buildBST(String filename) throws FileNotFoundException {
      String line, title;
      int available, rented;
      // Open file for input
      File file = new File(filename);
      Scanner input = new Scanner(file);
      // Create new BST object
      BinTree<DVD> b = new BinTree<DVD>();
      // Parse for DVD data for each line of file
      while (input.hasNext()) {
         line = input.nextLine();
         if (line.isEmpty())
            break;
         title = line.substring(line.indexOf('"') + 1, line.lastIndexOf('"'));
         line = line.substring(line.indexOf(',') + 1);
         available = Integer.parseInt(line.substring(0, line.indexOf(',')));
         line = line.substring(line.indexOf(',') + 1);
         rented = Integer.parseInt(line);
         DVD d = new DVD(title, available, rented);
         // If first file line, DVD is stored in root of BST
         b.insert(d);
      }
      // Close file input stream
      input.close();
      return b;
   }
   
   /* Process Transaction Log File
   *     Parameters: String (filename), BinTree<DVD> (pass in BST to invoke recursive BST search and insert methods)
   *     Returns: void
   *     Reads transaction log file and modifies DVDs inside BST based on input.
   *     Throws FileNotFoundException since data input (file) should be valid.
   */
   public static void processTransactionLog(String filename, BinTree<DVD> b) throws FileNotFoundException {
      String line, transaction, title;
      int quantity = 0;
      // Open file for input
      File file = new File(filename);
      Scanner input = new Scanner(file);
      // Parse each file input line for transaction, title, and quantity to add/remove if exists
      while (input.hasNext()) {
         line = input.nextLine();
         // If (last) line is empty, stop reading file
         if (line.isEmpty())
            break;
         transaction = line.substring(0, line.indexOf(' '));
         line = line.substring(line.indexOf('"') + 1);
         title = line.substring(0, line.indexOf('"'));
         if (line.indexOf(',') != -1) {
            line = line.substring(line.indexOf(',') + 1);
            quantity = Integer.parseInt(line);
         }
         // Create DVD object to use to search for title in BST
         DVD d = new DVD(title, quantity, 0);
         // If adding DVDs 
         if (transaction.compareTo("add") == 0) {
            // If title does not exist in BST, call recursive BST insert method to add new title
            if (b.search(d) == null)
               b.insert(d);
            // If title does exist in BST, add quantity to its number of available DVDs 
            else
               addToExisting(b, d, quantity);
         }
         // If removing DVDs
         else if (transaction.compareTo("remove") == 0)
            removeFromExisting(b, d, quantity);
         // If renting DVD, call method to increment available by -1 (rented by 1)
         else if (transaction.compareTo("rent") == 0)
            incrementAvailRented(b, d, -1);
         // If returning DVD, call method to increment available by 1 (rented by -1)
         else if (transaction.compareTo("return") == 0)
            incrementAvailRented(b, d, 1);
      }
      // Close file input stream
      input.close();
   }
   
   /* Display Report
   *     Parameters: Node<DVD> (first argument passed through is BST's root)
   *     Returns: void
   *     Uses inorder traversal (depth-first) to print DVD title, number of available DVDs, and number of rented DVDs
   *     in alphabetical order using Node toString() method.
   */
   public static void displayReport(Node<DVD> node) {
      // If node does not exist, print nothing
      if (node == null)
         return;
      // Recursively traverse left subtree
      displayReport(node.left);
      // Processes root node
      System.out.println(node.toString());
      // Recursively traverse right subtree
      displayReport(node.right);
   }
   
	public static void main(String[] args) throws FileNotFoundException {
		String invFile, transFile;
		// Create Scanner for console input, prompt for inventory and transaction log filenames
		Scanner input = new Scanner(System.in);
		System.out.println("Enter inventory filename:");
		invFile = input.next();
		System.out.println("Enter transaction log filename:");
		transFile = input.next();
		input.close();
		System.out.println();
		// Create BST using inventory file input
		BinTree<DVD> b = buildBST(invFile);
      // Modify BST using transaction log file input
		processTransactionLog(transFile, b);
		// Display BST to console
		displayReport(b.root);
	}
}
