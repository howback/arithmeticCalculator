import java.util.Stack;

public class Calculator {
    public double calculate(String verifiedString) {
        String rpn = expressionToRPN(verifiedString);
        return rpnToAnswer(rpn);
    }

    public int amountOfNumbers(String expression) {
        int amount = 0;
        for (int i = 1; i < expression.length(); i++) {
            char token = expression.charAt(i);
            char past = expression.charAt(i - 1);
            if (getPriority(past) == 0 && getPriority(token) != 0) amount += 1;
        }
        if (getPriority(expression.charAt(expression.length() - 1)) == 0) amount += 1;
        return amount;
    }

    private String expressionToRPN(String expr) {
        String current = "";
        Stack<Character> stack = new Stack<>();
        int priority;
        for (int i = 0; i < expr.length(); i++) {
            priority = getPriority(expr.charAt(i));
            if (priority == 0) {
                current += expr.charAt(i);
            }
            if (priority == 1) {
                stack.push(expr.charAt(i));
            }
            if (priority > 1) {
                current += " ";
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) current += stack.pop();
                    else break;
                }
                stack.push(expr.charAt(i));
            }
            if (priority == -1) {
                current += " ";
                while (getPriority(stack.peek()) != 1) current += stack.pop();
                stack.pop();
            }
        }
        while (!stack.empty()) current += stack.pop();
        return current;
    }

    private double rpnToAnswer(String rpn) {
        String operand = "";
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') continue;
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) break;
                }
                stack.push(Double.parseDouble(operand));
                operand = "";
            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double last = stack.pop(), penultimate = stack.pop();
                if (rpn.charAt(i) == '+') stack.push(penultimate + last);
                if (rpn.charAt(i) == '-') stack.push(penultimate - last);
                if (rpn.charAt(i) == '*') stack.push(penultimate * last);
                if (rpn.charAt(i) == '/') {
                    if (last == 0) {
                        new Verifier().throwsAnError("byZero");
                    } else stack.push(penultimate / last);
                }
            }
        }
        return stack.pop();
    }

    //set priorities for characters (*,/)=3 (+,-)=2  '('=1  ')'=-1  (0-9)=0
    public static int getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if (token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else return 0;
    }
}
