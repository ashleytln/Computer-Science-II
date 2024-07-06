public class Customer {
	// Members
	private String first;
	private String last;
	private String id;
	private double amount;
	
	// Constructors
	public Customer() {
	   this("", "", "", 0);
	}
	public Customer(String f, String l, String i, double a) {
	   first = f;
	   last = l;
	   id = i;
	   amount = a;
	}
	
	// Accessors
	public String getFirst() { return first; }
	public String getLast() { return last; }
	public String getID() { return id; }
	public double getAmountSpent() { return amount; }
	
	// Mutators
	public void setFirst(String f) { first = f; }
	public void setLast(String l) { last = l; }
	public void setID(String i) { id = i; }
	public void setAmountSpent(double a) { amount = amount + a; }
}
