public class Gold extends Customer {
	// Members
	private double discount;
	
	// Constructors
	public Gold() {
	   discount = 0.00;
	}
	public Gold(String f, String l, String i, double a, double d) {
	   super(f, l, i, a);
	   discount = d;
	}
	
	// Accessors
	public double getDiscount() { return discount; }
	
	// Mutators
	public void setDiscount(double d) { discount = d; }
}

