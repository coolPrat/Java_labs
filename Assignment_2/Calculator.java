/*
 * Calculator.java
 *
 * Version: 2.4: Calculator.java,v 2.4 9/7/2015 16:34:23
 *
 * Revisions: 1.0 initial version
 *            2.1 adding power operator
 *            2.2 processing parenthesis
 *            2.3 removed try-catch
 *            2.4 removed static methods
 */

import java.util.Stack;

public class Calculator {
    /**
     * The class Calculator takes an expression as input and evaluates it.
     * It will make decisions based on precedence of operators in the expression.
     *
     * @author      Pratik kulkarni
     */

    private Stack<String> OPERATORS = new Stack<>(); // Stack to store
                                                            // operators
    private Stack<String> OPERANDS = new Stack<>(); // Stack to store
                                                           // operands
    private Stack<String> EXPRESSIONSTACK = new Stack<>();
    // Stack to store input string while processing it

    /**
     * This method checks if the string input is numeric.
     *
     * @param       input    String input that needs to be checked.
     *
     * @return               boolean true if the input is numeric else false.
     */
    public boolean isDigit(String input) {
        return input.matches("\\d+");
    }

    /**
     * This method checks if the string input is an operator.
     *
     * @param       input    String input that needs to be checked.
     *
     * @return               boolean true if the input is operator else false.
     */
    public boolean isOperator(String input) {
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
    public boolean isOpenParenthesis(String input) {
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
    public boolean isCloseParenthesis(String input) {
        return (input.equals(")"));
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
            System.out.println("Invalid input... " +
                    "Please check your expression");
            System.exit(0);
        } else {
            OPERATORS.push(String.valueOf(input));
        }
    }

    /**
     * This method takes a String array as input and processes it. It will add
     * elements in EXPRESSIONSTACK which holds the input. In case the input is
     * closing bracket it will evaluate the bracket, which is recently closed.
     *
     * @param input  String array which holds the input expression.
     */
    public void processInput(String[] input) {
        for(String str : input) {
            if(isDigit(str) || isOperator(str) || isOpenParenthesis(str)) {
                EXPRESSIONSTACK.push(str);
            } else if(isCloseParenthesis(str)) {
                processBracket();
            } else {
                System.out.println("Invalid input... " +
                        "Please check your expression");
                System.exit(0);
            }
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
            if(isDigit(str)) {
                OPERANDS.push(str);
            } else if(isOperator(str)) {
                processOperator(str.charAt(0));
            } else if(isOpenParenthesis(str)) {
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
            case '/':
                return (firstOperand / secondOperand);
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
            System.out.println("Invalid input..." +
                    "Please enter a valid expression");
            System.exit(0);
        }
        return (OPERANDS.peek());
    }
    /**
     * The main program. It takes an expression as input and prints the result or
     * error message.
     *
     * @param     args    String array to store command line argument.
     */
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Invalid input... Please enter valid expression");
            System.exit(0);
        }
        Calculator calculator = new Calculator();
        calculator.processInput(args);

        //process expression assuming the expression to be in format
        // ( expression )
        calculator.processBracket();
        System.out.println(calculator.EXPRESSIONSTACK.pop());
    }
}
