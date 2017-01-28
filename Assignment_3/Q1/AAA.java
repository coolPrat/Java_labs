
/*
 * AAA.java
 *
 * Version: 1: AAA.java, 9/13/2015 21:45:09
 *
 * Revisions: 1.0 initial version
 *
 */
public class AAA extends AA {

    /**
     * This program is for understanding Inheritance in Java.
     * each of the string expression below is explained with
     * the comment above.
     *
     * @author      Pratik Kulkarni
     */

    // AAA's local variable aInt.
    int aInt = 1;

    // AAA's constructor. This will set aInt to 11 while creating new object
    // of AAA.
    AAA() {
        aInt = 11;
    }

    /* Overridden method from AA.
     * This will increment AAA's aInt and return the incremented value.
     */
    public int intPlusPlus()	{
        return ++aInt;
    }

    // The main program
    public static void main(String args[]) {
        // Creating new object of AAA
        AAA aAAA = new AAA();

        // Casting AAA's object to AA
        AA   aAA = (AA)aAAA;

        // casting AA's object to A
        A     aA = (A)aAA;

        // incrementing aInt of AAA. Here aInt will become 12
        aAAA.intPlusPlus();

        /* incrementing aInt of AAA. Here aInt will become 13.
         * This line behaves same as above as the method intPlusPlus was
         * overriden by AAA
         */
        aAA.intPlusPlus();

        /* incrementing aInt of AAA. Here aInt will become 14.
         * This line behaves same as above as the method intPlusPlus was
         * overridden by AA
         * and that was again overridden by class AAA.
         */
        aA.intPlusPlus();

        /* This line will print:
         * AAA: 11
         * This is because AAA did not override toString method from AA.
         * Hence AAA will have AA's copy of toString method, which prints class-name
         * and AA's aInt.
         * As intPlusPlus was overridden AA's aInt is still 11 and its not incremented
         */
        System.out.println("aA:        "  + aA);

        // Below lines behave in same way as line above.
        System.out.println("aAA:       " + aAA);
        System.out.println("aAAA:      " + aAAA);

        /* Here we are trying access AAA's aInt. As it was incremented by
         * overridden method intPlusPlus we get aAAA.aInt as 14
         */
        System.out.println("aAAA:.aInt " + aAAA.aInt);
    }
}
