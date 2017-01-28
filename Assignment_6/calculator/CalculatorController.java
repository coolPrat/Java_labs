/*
 * CalculatorController.java
 *
 * Version: 1: CalculatorController.java,v 1 10/3/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 * @author: Pratik kulkarni
 */

import java.util.Vector;


public class CalculatorController {
    /**
     * This is a controller class. It takes String vector from the view and
     * processes it with model. It has object of model and view so it can mediate
     * between them
     */

    private Calculator calculator = new Calculator(this); // Model
    private CalculatorView view = new CalculatorView();
    // object of view

    /**
     * This method takes a String array as input and processes it. It will add
     * elements in EXPRESSIONSTACK which holds the input. In case the input is
     * closing bracket it will evaluate the bracket, which is recently closed.
     *
     * @param inputVector  String array which holds the input expression.
     * @return             result of computation.
     */
    public String processInput(Vector<String> inputVector) {
        for(String str : inputVector) {
            if(Utils.isDigit(str) || Utils.isOperator(str) || Utils.isOpenParenthesis(str)) {
                calculator.pushToStack(str);
            } else if(Utils.isCloseParenthesis(str)) {
                calculator.processBracket();
            } else {
                printError("Invalid input... " +
                        "Please check your expression");
            }
        }
        calculator.processBracket();
        return calculator.getResult();
    }

    /**
     * This method will print the error message passed to it (via view) and the
     * terminate the program.
     *
     * @param error    error message that needs to printed
     */
    public void printError(String error) {
        view.print(error);
        System.exit(0);
    }
}
