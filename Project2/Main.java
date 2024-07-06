import java.io.*; // File/console input/output
import java.util.Scanner;
import java.lang.Math; // Math.round, Math.PI

public class Main {
    // Read customer file and create arrays
    public static Customer[] createCustomerArray(String filename) throws FileNotFoundException {
        Customer[] arr;
        String line, id, first, last;
        double amount = 0.00, discount = 0.00;
        int bonus = 0, size = 0, i = 0;
      
        File file = new File(filename);
        Scanner f = new Scanner(file);
      
        // Find number of file lines (number of customers)
        while (f.hasNextLine()) {
            if (!f.nextLine().isEmpty())
                size++;
        }
        f.close();
      
        arr = new Customer[size];
      
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            line = s.nextLine();
            if (!line.isEmpty()) {
                // Extract data from file input line
                id = line.substring(0, line.indexOf(' '));
                line = line.substring(line.indexOf(' ') + 1);
                first = line.substring(0, line.indexOf(' '));
                line = line.substring(line.indexOf(' ') + 1);
                last = line.substring(0, line.indexOf(' '));
                line = line.substring(line.indexOf(' ') + 1);
                // Bounds of substring depend on if end of line
                if (line.indexOf(' ') != -1)
                    amount = Double.parseDouble(line.substring(0, line.indexOf(' ')));
                else
                    amount = Double.parseDouble(line.substring(0));
            
                // If line contains discount, create Gold Customer
                if (line.indexOf('%') != -1) {
                    line = line.substring(line.indexOf(' ') + 1);
                    discount = Double.parseDouble(line.substring(0, line.indexOf('%'))) / 100;
                    arr[i] = new Gold(first, last, id, amount, discount);
                }
                // If line contains bonus bucks, create Platinum Customer
                else if (line.indexOf(' ') != -1) {
                    line = line.substring(line.indexOf(' ') + 1);
                    bonus = Integer.parseInt(line.substring(0));
                    arr[i] = new Platinum(first, last, id, amount, bonus);;
                }
                // Otherwise, create regular Customer
                else
                    arr[i] = new Customer(first, last, id, amount);
                i++;
            }
        }
        s.close();
      
        return arr;
    }
   
    public static Customer[] removeCustomer(Customer[] arr, Customer c) {
        Customer[] newArr = new Customer[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length && j < newArr.length; i++) {
            if (!c.equals(arr[i])) {
                newArr[j] = new Customer(arr[i].getFirst(), arr[i].getLast(), arr[i].getID(), arr[i].getAmountSpent());
                // Increment index of newArr if Customer copied over
                j++;
            }
        }
        return newArr;
    }
   
    // Add customer to preferred array
    public static Customer[] addCustomer(Customer[] arr, Customer c) {
        Customer[] newArr = null;
        newArr = new Customer[arr.length + 1]; // Create array one size bigger
         
        // Copy elements of existing preferred array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getAmountSpent() >= 200)
                newArr[i] = new Platinum(arr[i].getFirst(), arr[i].getLast(), arr[i].getID(), arr[i].getAmountSpent(), (int)(arr[i].getAmountSpent() - 200) / 5);
            else
                newArr[i] = new Gold(arr[i].getFirst(), arr[i].getLast(), arr[i].getID(), arr[i].getAmountSpent(), calculateDiscount(arr[i].getAmountSpent()));
        }
        if (c.getAmountSpent() >= 200)
            newArr[newArr.length - 1] = new Platinum(c.getFirst(), c.getLast(), c.getID(), c.getAmountSpent(), (int)(c.getAmountSpent() - 200) / 5);
        else
            newArr[newArr.length - 1] = new Gold(c.getFirst(), c.getLast(), c.getID(), c.getAmountSpent(), calculateDiscount(c.getAmountSpent()));
        return newArr;
    }
   
    public static double calculateCost(char size, String drink, double sqInchPrice, int quantity) {
        final double dS = 4, dM = 4.5, dL = 5.5;
        final double hS = 4.5, hM = 5.75, hL = 7;
        final int ozS = 12, ozM = 20, ozL = 32;
        final double sodaPrice = 0.20, teaPrice = 0.12, punchPrice = 0.15;
      
        double basePrice = 0, surfaceArea = 0;
      
        if (size == 'S') {
            surfaceArea = 2 * Math.PI * dS / 2 * hS;
            if (drink.equals("soda"))
                basePrice = sodaPrice * ozS;
            else if (drink.equals("tea"))
                basePrice = teaPrice * ozS;
            else if (drink.equals("punch"))
                basePrice = punchPrice * ozS;
        }
        else if (size == 'M') {
            surfaceArea = 2 * Math.PI * dM / 2 * hM;
            if (drink.equals("soda"))
                basePrice = sodaPrice * ozM;
            else if (drink.equals("tea"))
                basePrice = teaPrice * ozM;
            else if (drink.equals("punch"))
                basePrice = punchPrice * ozM;
        }
        else if (size == 'L') {
            surfaceArea = 2 * Math.PI * dL / 2 * hL;
            if (drink.equals("soda"))
                basePrice = sodaPrice * ozL;
            else if (drink.equals("tea"))
                basePrice = teaPrice * ozL;
            else if (drink.equals("punch"))
                basePrice = punchPrice * ozL;
        }
        return (Math.round(((basePrice + (surfaceArea * sqInchPrice)) * quantity) * 100.0) / 100.0);
    }
   
    public static double calculateDiscount(double amount) {
        double discount;
        if (amount >= 150)
            discount = 0.15;
        else if (amount >= 100)
            discount = 0.10;
        else
            discount = 0.05;
        return discount;
    }
   
    public static void printCustomerData(Customer[]  c, String filename) throws IOException/*, FileNotFoundException */ {
        // Create output file
        File file = new File(filename);
        if (!file.isFile())
            file.createNewFile();
        FileOutputStream data = new FileOutputStream(file, false);
        PrintWriter printer = new PrintWriter(data);
        // Print customer information
        for (int i = 0; i < c.length; i++) {
            printer.printf("%s %s %s %.2f", c[i].getID(), c[i].getFirst(), c[i].getLast(), c[i].getAmountSpent());
            // If customer is Gold, print discount
            if (c[i] instanceof Gold)
                printer.printf(" %d%%", (int)(((Gold)c[i]).getDiscount() * 100));
            // If customer is Platinum, print bonus
            else if (c[i] instanceof Platinum)
                printer.printf(" %d", ((Platinum)c[i]).getBonus());
            // Print newline to print next customer's information
            if (i != c.length - 1)
                printer.print("\n");
        }
        // Done printing, close printer
        printer.close();
    }
    public static void main(String[] args) throws IOException {
        String regFile, prefFile, ordFile, id = "", drink = "";
        char size = '\0';
        double amount = 0.00, sqInchPrice = 0.00;
        int quantity = 0;
        
        Scanner input = new Scanner(System.in), fileReader;
        Customer[] regular = null, preferred = null;
        
        // Prompt user input for customer and order file names
        System.out.println("Enter regular customer file name:");
        regFile = input.next();
        System.out.println("Enter preferred customer file name:");
        prefFile = input.next();
        System.out.println("Enter orders file name:");
        ordFile = input.next();
      
        // Create regular array from reading file
        regular = createCustomerArray(regFile);
      
        // Check if preferred file exists
        File file = new File(prefFile);
        try {
            fileReader = new Scanner(file);
        }
        // If preferred file does not exist, do nothing
        catch (FileNotFoundException f) {
            if (!file.exists())
                file.createNewFile();
        }
        // Only create preferred array if file exists
        if (file.length() != 0)
            preferred = createCustomerArray(prefFile);
      
        file = new File(ordFile);
        fileReader = new Scanner(file);
      
        while (fileReader.hasNextLine()) {
            int numFields = 0;
            // Throws exception if data invalid
            try {
                boolean valid = false;
                id = fileReader.next();
                for (int i = 0; i < regular.length; i++) {
                    // If order id belongs to regular customer, order is valid
                    if (id.equals(regular[i].getID()))
                        valid = true;
                    // If preferred array exists
                    else if (preferred != null) {
                        // If order id belongs to preferred customer, order is valid
                        if (i < preferred.length && id.equals(preferred[i].getID()))
                            valid = true;
                    }
                    // If reached end of array and order does not belong to a customer, throw exception
                    if (i == regular.length - 1 && !valid)
                        throw new Exception();
                }
                numFields++;
                size = fileReader.next().charAt(0);
                // If current character is not valid drink size, throw exception 
                if (Character.compare(size, 'S') != 0 && Character.compare(size, 'M') != 0 && Character.compare(size, 'L') != 0)
                    throw new Exception();
                numFields++;
                drink = fileReader.next();
                // If current word is not valid drink type, throw exception
                if (!drink.equals("soda") && !drink.equals("tea") && !drink.equals("punch"))
                    throw new Exception();
                numFields++;
                // Throws exception if next input is not double
                sqInchPrice = fileReader.nextDouble();
                numFields++;
                // Throws exception if next input is not integer
                quantity = fileReader.nextInt();
                numFields++;
                // If line does not contain five valid inputs, order not valid and throw exception
                if (numFields != 5)
                    throw new Exception();
                valid = true;
                if (valid) {
                    amount = calculateCost(size, drink, sqInchPrice, quantity);
                    // Check which array customer is in
                    for (int i = 0; i < regular.length; i++) {
                        // If customer is regular (ID matches)
                        if (id.equals(regular[i].getID())) {
                        regular[i].setAmountSpent(amount);
                     Customer temp = regular[i];
                     // Check for promotion, promote regular Customer to Platinum if spent more than 200
                     if (regular[i].getAmountSpent() >= 200) {
                        regular = removeCustomer(regular, temp);
                        // If no prior preferred customers exist, create new array with promoted Customer
                        if (preferred == null) {
                           preferred = new Customer[1];
                           preferred[0] = new Platinum(temp.getFirst(), temp.getLast(), temp.getID(), temp.getAmountSpent(), (int)(preferred[i].getAmountSpent() - 200) / 5);
                        }
                        else
                           preferred = addCustomer(preferred, temp);
                     }
                     // Promote regular Customer to Gold if spent 50-199.99 dollars
                     else if (regular[i].getAmountSpent() >= 50) {
                        regular = removeCustomer(regular, temp);
                        // If no prior preferred customers exist, create new array with promoted Customer
                        if (preferred == null) {
                           preferred = new Customer[1];
                           preferred[0] = new Gold(temp.getFirst(), temp.getLast(), temp.getID(), temp.getAmountSpent(), calculateDiscount(temp.getAmountSpent()));
                        }
                        else
                           preferred = addCustomer(preferred, temp);
                     }
                     break; // Break for loop if found regular customer
                  }
                  // If customer is preferred (ID matches)
                  else if (preferred != null && i < preferred.length && id.equals(preferred[i].getID())) {
                     // Check for promotion, promote Gold to Platinum
                     if (preferred[i] instanceof Gold) {
                        preferred[i].setAmountSpent(amount * (1 - ((Gold)preferred[i]).getDiscount()));
                        if (preferred[i].getAmountSpent() >= 200)
                           preferred[i] = new Platinum(preferred[i].getFirst(), preferred[i].getLast(), id, preferred[i].getAmountSpent(), (int)(preferred[i].getAmountSpent() - 200) / 5);
                     }
                     // If Platinum prior to processing order, update bonus bucks if necessary
                     else {
                        // If bonus bucks exceeds order cost, order is free, amount spent does not change
                        if (((Platinum)preferred[i]).getBonus() > amount)
                           ((Platinum)preferred[i]).setBonus(0);
                        // Otherwise update amount spent with bonus bucks applied
                        else {
                           preferred[i].setAmountSpent((-1) * ((Platinum)preferred[i]).getBonus());
                           // Updates bonus bucks after previously owned bonus bucks applied to order
                           ((Platinum)preferred[i]).setBonus(((int)(preferred[i].getAmountSpent() - 200) / 5) - ((Platinum)preferred[i]).getBonus());
                        }
                     }   
                  }
               }
            }
         }
         catch (Exception e) {
            if (fileReader.hasNextLine())
               fileReader.nextLine();
         }
      }
      fileReader.close();
      
      // Print array data to files
      if (regular != null)
         printCustomerData(regular, "customer.dat");
      if (preferred != null)
         printCustomerData(preferred, "preferred.dat");
	}
}
