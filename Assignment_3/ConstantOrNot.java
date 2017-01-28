/*
 * ConstantOrNot.java
 *
 * Version: 1: ConstantOrNot.java, 9/13/2015 21:45:09
 *
 * Revisions: 1.0 initial version
 *
 */

import java.util.Vector;	// what does this line do?
/*
 This line allows us to refer to Vector class in java.util package with out
 using package name every time.
*/

class ConstantOrNot {
    /**
     * This program is for understanding concept of final variable in java.
     * each of the string expression below is explained with
     * the comment above.
     *
     * @author      Pratik Kulkarni
     */

    private final int aInt = 1;
    private final String aString = "abc";
    private final Vector aVector = new Vector();

    public void doTheJob() {
        // aInt = 3; why would this fail?
        /* integer aInt is declared as final. Hence it can be initialized at-max once.
         * as it s initialized once as 1 we can not update aInt to hold 3.
         */

        // aString = aString + "abc"; why would this fail?
        /* This will fail because aString holds a literal "abc", as aString is
         * also declared as final the value of aString can't be changed once initialized.
         */

        aVector.add("abc");		// why does this work?
        /* Even though aVector is declared final aVector holds reference to a
         * Vector object which else-where in memory.
         * While adding "abc" to vector we do not change value of aVector but we
         * change the object that is referenced by aVector. Hence this line will
         * run.
         */
    }

    public static void main( String args[] ) {
        new ConstantOrNot().doTheJob();

    }
}
