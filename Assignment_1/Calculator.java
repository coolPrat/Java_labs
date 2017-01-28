/*
 * Calculator.java
 *
 * Version: 1: Calculator.java,v 1 2015/08/31 22:15:00
 *
 * Revisions: 1
 */

import java.util.Stack;

/**
 * The class Calculator takes an expression as input and evaluates it. It will
 * make decisions based on precedence of operators in the expression.
 *
 * @author      Pratik kulkarni
 */

public class Calculator {
    private static Stack<String> OPERANDS = new Stack<>(); // stack for operands
    private static Stack<String> OPERATORS = new Stack<>();// stack for operator

    /**
     * The main method. It takes an expression (String) as input and prints
     * the result or an error message if the expression in incorrect.
     *
     * @param       args    String array to accept expression string as command
     *                      line argument.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid input... Please check your expression");
            System.exit(0);
        }
        for (String str: args) {
            if(isDigit(str)) {
                OPERANDS.push(str);
            } else if(isOperator(str)) {
                processOperator(str.charAt(0));
            }else {
                System.out.println("Invalid input... " +
                        "Please check your expression");
                System.exit(0);
            }
        }
        evaluateExpression();
    }

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
        return (input.equals("+") || input.equals("-") || input.equals("*") ||
                input.equals("/") || input.equals("%"));
    }

    /**
     * This method will process an operator based on its precedence. It checks
     * the precedence of last pushed operator with the input. If the input has
     * lower precedence then it will evaluate the operator on the stack and then
     * it will push the input.
     *
     * @param       input    Single char input which is the input operator.
     */
    public static void processOperator(char input) {
        int result;
        if (!OPERATORS.empty()) {
            char currentOperator = OPERATORS.peek().charAt(0);
            if (precedenceOf(input) < precedenceOf(currentOperator)) {
                result = calculate(OPERANDS.pop(), OPERATORS.pop().charAt(0),
                        OPERANDS.pop());
                OPERANDS.push(String.valueOf(result));
                processOperator(input);
            } else {
                OPERATORS.push(String.valueOf(input));
            }
        } else {
            OPERATORS.push(String.valueOf(input));
        }
    }

    /**
     * This method will return precedence value of an operator.
     *
     * @param       operator    Single char input which is the input operator.
     *
     * @return                  Integer between 1-5 as precedence value of the
     *                          operator
     */
    public static int precedenceOf(char operator) {
        switch (operator) {
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
    public static int calculate(String operand1, char operator, String operand2) {
        int firstOperand = Integer.parseInt(operand1);
        int secondOperand = Integer.parseInt(operand2);
        switch (operator) {
            case '+':
                return (firstOperand + secondOperand);
            case '-':
                return (secondOperand - firstOperand);
            case '*':
                return (firstOperand * secondOperand);
            case '/':
                return (secondOperand / firstOperand);
            case '%':
                return (secondOperand % firstOperand);
            default:
                return 0;
        }
    }

    /**
     * This method will evaluate all the operators on operator stack.
     */
    public static void evaluateExpression() {
        while ((!OPERATORS.empty()) || (OPERANDS.size() > 1)) {
            try {
                String op1 = OPERANDS.pop();
                String op2 = OPERANDS.pop();
                String operator = OPERATORS.pop();
                OPERANDS.push(String.valueOf(calculate(op1, operator.charAt(0),
                        op2)));
            } catch (Exception e) {
                System.out.println("Invalid input");
                System.exit(0);
            }
        }
        System.out.println(OPERANDS.pop());
    }
}
