/*
 * Box.java
 *
 * Version: 1.0: Box.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * This class represents a box.
 */
public class Box {
    private int boxId;
    private int numberOfCandies;
    private WrappedCandy[] wrappedCandies = new WrappedCandy[4];
    static int count;


    public void addCandy(WrappedCandy wrappedCandy) {
        wrappedCandies[numberOfCandies] = wrappedCandy;
        numberOfCandies++;
    }

    public int getSize() {
        return numberOfCandies;
    }

    public Box() {
        this.boxId = count++;
    }
}
