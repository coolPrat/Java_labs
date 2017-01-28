/*
 * Calculator.java
 *
 * Version: 1: Calculator.java,v 1 10/3/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 * @author: Pratik kulkarni
 */



import java.util.Scanner;
import java.util.Vector;



public class CalculatorView {
    /**
     * This is the view class for Calculator. It takes a expression as a input
     * passes it to controller and prints the result returned by the controller.
     */
    private Scanner inputScanner = new Scanner(System.in);
    // Scanner to accept input from user.

    /**
     * This method will take the input, remove trailing white spaces and return it.
     *
     * @return    the input String entered by user.
     */
    private String takeInput() {
        System.out.println("Enter expression: ");
        String input = inputScanner.next().trim();
        if (input.length() == 0) {
            System.out.println("Invalid expression");
            return null;
        }
        return input;
    }

    /**
     * This method will create a vector of type string to hold the input
     * expression. It will break the expression and store operands and operators
     * separately.
     *
     * @param    input    input expression as a String
     * @return            vector of type String that holds the expression
     */
    private Vector<String> tokenizeInput(String input) {
        Vector<String> inputVector = new Vector<>();
        int indexOfLastOperator = -1;
        String digits = null;
        for(int index = 0; index < input.length(); index++) {
            String current = String.valueOf(input.charAt(index));
            if (!Utils.isDigit(current)) {
                digits = input.substring(indexOfLastOperator + 1, index);
                if (!(digits.length() == 0)) {
                    inputVector.add(digits);
                }
                inputVector.add(current);
                indexOfLastOperator = index;
            }
        }
        digits = input.substring(indexOfLastOperator + 1, input.length());
        if (!(digits.length() == 0)) {
            inputVector.add(digits);
        }
        return inputVector;
    }

    /**
     * It will print the string that is passed to it.
     * @param string    String to print.
     */
    public void print(String string) {
        System.out.println(string);
    }

    /**
     * The main program. This will ask user for input and will return the result.
     * @param args    command line arguments (ignored)
     */
    public static void main(String[] args) {
        CalculatorView calculator = new CalculatorView();
        String input = calculator.takeInput();
        Vector<String> inputVector = calculator.tokenizeInput(input);
        CalculatorController controller = new CalculatorController();
        String result = controller.processInput(inputVector);
        System.out.println(result);
    }
}
