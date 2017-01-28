
/*
 * AA.java
 *
 * Version: 1: AA.java, 9/13/2015 21:45:09
 *
 * Revisions: 1.0 initial version
 *            1.1 updated code for changing output
 *
 */
public class AA extends A {

    /**
     * This program is for understanding Inheritance in Java.
     * each of the string expression below is explained with
     * the comment above.
     *
     * @author      Pratik Kulkarni
     */


    // Local variable aInt of class AA
    int aInt = 1;

    // Constructor of class AA. Sets aInt as 11.
    AA() {
        aInt = 11;
    }

    /*
    * Overridden method from Class A
    * This will increment aInt of class AA and return incremented value
    */
    public int intPlusPlus()	{
       // adding super will now force incrementing A's aInt.
        return ++super.aInt;
    }

    /*
    * Overridden method from class A.
    * This will print class name and value aInt of that class
    */
    public String toString()      {
        return this.getClass().getName() + ": " + aInt;
    }

    // The main program.
    public static void main(String args[]) {
        // Creating new object of AA. aInt will be set to 11
        AA aAA = new AA();

        /* Using A's reference to point to AA's object.
         * As AA extends A this is legal.
         */
        A   aA = aAA;

        /* Calling intPlusPlus() on AA's object with AA's reference.
         * It will increment aInt to 12
         */
        aAA.intPlusPlus();

        /* Calling intPlusPlus() on AA's object with A's reference.
         * Although the reference is of type A, as the method was overridden by AA
         * So it will still call AA's intPlusPlus() and increment aInt to 13
         */
        aA.intPlusPlus();

        /* This line will class toString() of AA as A's toString  was overridden by AA.
         * This line will print:
         * AA: 13
         */
        System.out.println(aA);

        /* This line will class toString() of AA.
         * This line will print:
         * AA: 13
         */
        System.out.println(aAA);

        /* Here, as we are trying to access A's aInt variable it will print 11.
         * This is because AA's object will have variables from class A and those are
         * different from variables of class AA. Even though they have same name they are distinct.
         * Ourtput of following line is:
         * aA: 11
         */
        System.out.println("aA: " + aA.aInt);
    }
}
