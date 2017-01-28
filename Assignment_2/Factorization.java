/*
* Factorization.java
*
* Version: 1, 9/1/2015 9:49:50 pm
*
* Revision: 1
*
*/

public class Factorization {
    /**
     * The class Factorization will print prime factors of the number entered.
     *
     * @author      Pratik kulkarni
     */

    /**
     * The main program. It will take the number a input and print its prime
     * factors in following pattern, number = factor1 * factor2 ...
     *
     * @param args  String array to take command line input.
     */
    public static void main(String[] args) {
        if (!(args.length == 1) || args[0] == " ") {
            System.out.println("Enter a valid integer");
            System.exit(0);
        }
        int number = Integer.valueOf(args[0]);
        String factors = number + " = " + factorise(number);
        System.out.println(factors);
    }

    /**
     * This function will find the prime factors of the number.
     *
     * @param    number    The integer of which needs to be factorized.
     * @return             It will return a string in pattern
     *                     factor1 * factor2 * factor3 ...
     */
    public static String factorise(int number) {
        String factors = "";
        boolean isPrime = true;
        for(int i = 2; i < number; i++) {
            if(number % i == 0) {
                isPrime = false;
                factors = factors + i + " * " + factorise(number/i);
                break;
            } else {
                continue;
            }
        }
        return (isPrime?factors + String.valueOf(number): factors);

    }
}
