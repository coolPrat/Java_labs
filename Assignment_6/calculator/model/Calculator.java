/*
 * Calculator.java
 *
 * Version: 1: Calculator.java,v 1 10/3/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *
 * @author: Pratik kulkarni
 */

package calculator.model;

import calculator.controller.CalculatorController;
import calculator.controller.Utils;
import java.util.Stack;

public class Calculator {
    /**
     * It is the model class for calculator. It holds three stacks which represent
     * the state of the calculator application.
     * It performs all the computation operations and returns the result or
     * error message.
     */

    private Stack<String> OPERATORS = new Stack<>(); // Stack to store
    // operators
    private Stack<String> OPERANDS = new Stack<>(); // Stack to store
    // operands
    private Stack<String> EXPRESSIONSTACK = new Stack<>();
    // Stack to store input string while processing it

    CalculatorController controller ;

    /**
     * Constructor for Calculator class.
     * @param controller    object of CalculatorController
     */
    public Calculator(CalculatorController controller) {
        this.controller = controller;
    }

    /**
     * This method will process an operator based on its precedence. It checks
     * the precedence of last pushed operator with the input. If the input has
     * lower precedence then it will evaluate the operator on the stack and then
     * it will push the input.
     *
     * @param       input    Single char input which is the input operator.
     */
    public void processOperator(char input) {
        double result;
        if (!OPERATORS.empty() && OPERANDS.size() >= 2) {
            char currentOperator = OPERATORS.peek().charAt(0);
            if (precedenceOf(input) <= precedenceOf(currentOperator)) {
                result = calculate(OPERANDS.pop(), OPERATORS.pop().charAt(0),
                        OPERANDS.pop());
                OPERANDS.push(String.valueOf(result));
                processOperator(input);
            } else {
                OPERATORS.push(String.valueOf(input));
            }
        } else if((!OPERATORS.empty() && OPERANDS.size() < 2)) {
            controller.printError("Invalid input... " +
                    "Please check your expression");
        } else {
            OPERATORS.push(String.valueOf(input));
        }
    }

    /**
     * This method will process the bracket. As an expression inside a bracket
     * has highest precedence as compared to other operations, we evaluate the
     * brackets here. For evaluating whole expression we assume it to be inside
     * a bracket. i.e. ( expression )
     */
    public void processBracket() {
        String str;
        while(EXPRESSIONSTACK.size() > 0) {
            str = EXPRESSIONSTACK.pop();
            if(Utils.isDigit(str)) {
                OPERANDS.push(str);
            } else if(Utils.isOperator(str)) {
                processOperator(str.charAt(0));
            } else if(Utils.isOpenParenthesis(str)) {
                break;
            }
        }
        EXPRESSIONSTACK.push(evaluateExpression());
    }

    /**
     * This method will return precedence value of an operator.
     *
     * @param       operator    Single char input which is the input operator.
     *
     * @return                  Integer between 1-5 as precedence value of the
     *                          operator
     */
    public int precedenceOf(char operator) {
        switch (operator) {
            case '^':
                return 6;
            case '*':
                return 5;
            case '/':
                return 4;
            case '%':
                return 3;
            case '-':
                return 2;
            case '+':
                return 1;
            default: return -1;
        }
    }

    /**
     * This method will perform arithmetic operation on given operands.
     *
     * @param       operator    Single char input which is the input operator.
     * @param       operand1    String first operand.
     * @param       operand2    String second operand.
     *
     * @return                  Result of evaluation of expression.
     */
    public double calculate(String operand1, char operator, String operand2) {
        double firstOperand = Double.parseDouble(operand1);
        double secondOperand = Double.parseDouble(operand2);
        switch (operator) {
            case '+':
                return (firstOperand + secondOperand);
            case '-':
                return (firstOperand - secondOperand);
            case '*':
                return (firstOperand * secondOperand);
            case '/': {
                if (secondOperand == 0) {
                    controller.printError("Can not divide by zero");
                }
                return (firstOperand / secondOperand);
            }
            case '%':
                return (firstOperand % secondOperand);
            case '^':
                return Math.pow(firstOperand, secondOperand);
            default:
                return 0;
        }
    }

    /**
     * This method will evaluate all the operators on operator stack.
     *
     * @return       Result of expression formed by operand and operator stacks.
     */
    public String evaluateExpression() {
        while ((!OPERATORS.empty()) && (OPERANDS.size() >= 2)) {
            String op1 = OPERANDS.pop();
            String op2 = OPERANDS.pop();
            String operator = OPERATORS.pop();
            OPERANDS.push(String.valueOf(calculate(op1, operator.charAt(0),
                    op2)));
        }
        if ((!OPERATORS.empty() && OPERANDS.size() < 2) ||
                (OPERANDS.empty() && OPERATORS.empty())) {
            controller.printError("Invalid input..." +
                    "Please enter a valid expression");
        }
        return (OPERANDS.peek());
    }

    /**
     * This method pushes the specified string on the EXPRESSION STACK.
     * @param str    String to push on the stack.
     */
    public void pushToStack(String str) {
        EXPRESSIONSTACK.push(str);
    }

    /**
     * The result is stored on EXPRESSION stack. This method wwill return the
     * top element of ths stack which is the result of the computation.
     *
     * @return    The result of the computation in String format.
     */
    public String getResult() {
        String result;
        if (EXPRESSIONSTACK.size() == 1) {
           result = this.EXPRESSIONSTACK.pop();
        } else {
            result = "Something went wrong!";
        }
        return result;
    }
}
