public class Node {
   private Node next;
   private Node down;
   private Seat payload;
   
   // Constructors
   public Node() {
      next = null;
      down = null;
      payload = new Seat();
   }
   public Node(Seat s) {
      next = null;
      down = null;
      payload = s;
   }
   
   // Mutators
   public void setNext(Node n) {
      next = n;
   }
   public void setDown(Node n) {
      down = n;
   }
   
   // Accessors
   public Node getNext() {
      return next;  
   }
   public Node getDown() {
      return down;
   }
   public Seat getPayload() {
      return payload;
   }
   
   // Overridden toString
   @Override
   public String toString() {
      return payload.toString();   
   }
}
