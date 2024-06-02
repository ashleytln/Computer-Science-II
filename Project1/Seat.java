public class Seat {
    private int row;
    private char seat;
    private char tixType;
    
    // Constructors
    public Seat() {
        this(0, '\0', '\0');
    }
    public Seat(int r, char s, char t) {
        row = r;
        seat = s;
        tixType = t;
    }
   
    // Mutators
    public void setRow(int r) {
        row = r;   
    }
    public void setSeat(char s) {
        seat = s;
    }
    public void setTixType(char t) {
        tixType = t;
    }
   
    // Accessors
    public int getRow() {
        return row;
    }
    public char getSeat() {
        return seat;
    }
    public char getTixType() {
        return tixType;
    }
    
    // Override default toString by returning value returned by default toString using tixType
    public String toString() {
        return Character.toString(tixType);
    }
}
