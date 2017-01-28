/*
 * Utils.java
 *
 * Version: 1: Utils.java,v 1 10/3/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 * @author: Pratik kulkarni
 *
 */


package calculator.controller;

public class Utils {
    /***
     * The class Utils provides a methods to check if the input is a digit,
     * operator, opening parenthesis or a closing parenthesis.
     *
     * The methods are used by all levels (model, view, controller) hence they
     * are marked static.
     *
     */


    /**
     * This method checks if the string input is numeric.
     *
     * @param       input    String input that needs to be checked.
     *
     * @return               boolean true if the input is numeric else false.
     */
    public static boolean isDigit(String input) {
        return input.matches("\\d+");
    }

    /**
     * This method checks if the string input is an operator.
     *
     * @param       input    String input that needs to be checked.
     *
     * @return               boolean true if the input is operator else false.
     */
    public static boolean isOperator(String input) {
        return (input.equals("^") || input.equals("+") || input.equals("-") ||
                input.equals("*") || input.equals("/") || input.equals("%"));
    }

    /**
     * This method checks if the string input is an opening parenthesis "("
     *
     * @param       input    String input that needs to be checked.
     *
     * @return               boolean true if the input is opening parenthesis
     *                       else false.
     */
    public static boolean isOpenParenthesis(String input) {
        return (input.equals("("));
    }

    /**
     * This method checks if the string input is an closing Parenthesis ")".
     *
     * @param       input    String input that needs to be checked.
     *
     * @return               boolean true if the input is closing Parenthesis
     *                       else false.
     */
    public static boolean isCloseParenthesis(String input) {
        return (input.equals(")"));
    }
}
