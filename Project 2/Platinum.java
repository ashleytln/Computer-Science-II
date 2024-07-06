public class Platinum extends Customer {
	// Members
	private int bonus;
	
	// Constructors
	public Platinum() {
	   bonus = 0;
	}
	
   public Platinum(String f, String l, String i, double a, int b) {
	   super(f, l, i, a);
	   bonus = b;
	}
	
	// Accessors
	public int getBonus() { return bonus; }
	
	// Mutators
	public void setBonus(int b) { bonus = b; }
}
