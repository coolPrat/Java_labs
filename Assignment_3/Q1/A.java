/*
 * A.java
 *
 * Version: 1: A.java, 9/13/2015 21:45:09
 *
 * Revisions: 1.0 initial version
 *
 */

public class A {
    /**
     * This program is for understanding Inheritance in Java.
     * each of the string expression below is explained with
     * the comment above.
     *
     * @author      Pratik Kulkarni
     */

    // This is a local variable initialized to 1 when class loads.
    int aInt = 1;

    /* This is a constructor for class A.
     * This is invoked when we try to create new object of A or any class that
     * is child class of A. This will set aInt as 11.
     */
    A() {
        aInt = 11;
    }


    //This function increments aInt of class A and returns the incremented value
    public int intPlusPlus()	{
        return ++aInt;
    }

    /*
    * This is an overridden method from class Object.
    * This method prints class name and aInt of class A
    */
    public String toString()	{
        return this.getClass().getName() + ": " + aInt;
    }

    // The main program.
    public static void main(String args[]) {
        /* Creating new object of A. Constructor will be called here and
         * aInt will be set to 11
         */
        A aA = new A();

        // Incrementing aInt to 12
        aA.intPlusPlus();

        /* As we have overridden the toString method of Object class this line
         * will print class-name : value of aInt
         * i.e. A : 12
         */
        System.out.println(aA);
    }
}
