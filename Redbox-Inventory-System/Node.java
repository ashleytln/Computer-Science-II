public class Node<G extends Comparable<G>> implements Comparable<Node<G>> {
   // Member variables
   Node<G> left;
   Node<G> right;
   private G payload;
   
   // Default constructor
   public Node() {
      left = null;
      right = null;
      payload = null;
   }
   
   // Overloaded constructor
   public Node(G payload) {
      left = null;
      right = null;
      this.payload = payload;
   }

   // Mutator
   public void setPayload(G payload) {
      this.payload = payload;
   }
   
   // Accessor
   public G getPayload() {
      return payload;
   }
   
   // Overridden Node toString method that utilizes payload's toString method
   @Override
   public String toString() {
      return payload.toString();
   }
   
   // Overridden Node compareTo method that utilizes payload's compareTo method
   @Override
   public int compareTo(Node<G> n) {
      return this.payload.compareTo(n.payload);
   }
