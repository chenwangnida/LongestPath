package yodletriangle;

/**
 * Graphs cannot have duplicate vertices, so I created this class to make every
 * integer unique.
 *
 * @author Clark
 */
public class GraphInteger {

    private int value, id;

    /**
     * 
     * Constructor
     * @param a
     * @param b 
     */
    public GraphInteger(int a, int b) {
        value = a;
        id = b;
    }

    public int getID() {
        return id;
    }
    
   public int getValue() {
       return value;
   }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
