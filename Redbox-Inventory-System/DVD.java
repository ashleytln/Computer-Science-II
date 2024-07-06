public class DVD implements Comparable<DVD>, Cloneable {
   // Member variables
   private String title;
   private int available;
   private int rented;
   
   // Overloaded constructor
   public DVD(String title, int available, int rented) {
      this.title = title;
      this.available = available;
      this.rented = rented;
   }
   
   // Mutators
   public void setTitle(String title) {
      this.title = title;
   }
   public void setAvailable(int available) {
      this.available = available;
   }
   public void setRented(int rented) {
      this.rented = rented;
   }
   
   // Accessors
   public String getTitle() {
      return title;
   }
   public int getAvailable() {
      return available;
   }
   public int getRented() {
      return rented;
   }
   
   @Override
   public DVD clone() throws CloneNotSupportedException {
      return new DVD(this.getTitle(), this.getAvailable(), this.getRented());
   }
   
   // Overridden DVD toString method that returns formatted string with DVD information
   @Override
   public String toString() {
      return String.format("%-50s%-5d%-5d", title, available, rented);
   }
   
   // Overridden DVD compareTo method comparing titles of DVDs
   @Override
   public int compareTo(DVD d) {
      if (title.compareTo(d.getTitle()) == 0)
         return 0;
      else if (title.compareTo(d.getTitle()) < 0)
         return -1;
      else
         return 1;
   }
}
