/*
 * X.java
 *
 * Version: 1: X.java, 9/13/2015 21:45:09
 *
 * Revisions: 1.0 initial version
 *
 */

class X {

    /**
     * This program is for understanding unary operator in Java.
     * each of the string expression below is explained with
     * the comment above.
     *
     * @author      Pratik Kulkarni
     */
    public static void main( String args[] ) {

        int n = 0;

        here:

        while ( true )  {

            while ( ( ( n != 3 ) || ( n != 5 ) ) && ( n < 4 ) )  {
            // iteration 1: n = 0, all conditions true
            // iteration 2: n = 2, all conditions true
                if ( ++n == 0 )
                // iteration 1: n = 1, condition false
                // iteration 2: n = 3, condition false
                    System.out.println("a/	n is " + n );
                else if ( n++ == 1 )    {
                // iteration 1: n = 1, condition true, n incremented to 2
                // "b/ n is 2" will be printed
                // iteration 2: n = 3, condition false, n incremented to 4
                    System.out.println("b/	n is " + n );
                } else if ( n++ == 2 )
                // iteration 1: not visited
                // iteration 2: n = 4, condition false, n incremented to 5
                    System.out.println("c/	n is " + n );
                else
                // iteration 1: not visited
                // iteration 2: will print "d/	n is 5"
                    System.out.println("d/	n is " + n );

                // This line is not part of any if-else statement hence it will always be printed
                System.out.println("	executing break here");

            // The inner while loop will break after iteration 2 with n = 5
            }

            /*
            * following lien checks if n is divisible by 2 if it is then it
            * checks if it is equal to 4
            * incase its not divisible by 2 then it checks if it is divisible by
            * 3. If it is not then it prints 3 else !3
            */

            System.out.println( n % 2 == 0 ? // false n % 2 will give 1
                    ( n == 4 ? "=" : "!" )
                    : ( n % 3 != 0 ? "3" : "!3" )); // n % 3 !=0 is true, hence 3 will be printed
            break here; // this will break the outer while loop.

            /* The output of above program will be:
             * b/ n is 2
             *  	executing break here
             * d/ n is 5
             * 	    executing break here
             * 3
             */
        }
    }
}
