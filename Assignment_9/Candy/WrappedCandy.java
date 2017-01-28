/*
 * WrappedCandy.java
 *
 * Version: 1.0: WrappedCandy.java,v 1.0 10/25/2015 08:45:03
 *
 * Revisions: 1.0 initial version
 */

/**
 * This class represents wrapped-candy
 */
public class WrappedCandy {
    Candy candy;
    WrappingPaper wrappingPaper;

    public WrappedCandy(Candy candy, WrappingPaper wrappingPaper) {
        this.candy = candy;
        this.wrappingPaper = wrappingPaper;
    }
}
