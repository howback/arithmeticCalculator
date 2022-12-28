import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier {

    public void verification(String expression) {
        Pattern pattern = Pattern.compile("^((\\d*\\.?\\d*)([+-/*()]))*(\\d*\\.?\\d*)$");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.matches()) {
            String transformedString = transformToSimplifiedNotation(expression);
            checkBrackets(expression);
            if (!transformedString.equals(expression)) {
                System.out.println("Simplified expression = " + transformedString);
            }
            System.out.println("Answer = " + new Calculator().calculate(transformedString));
            System.out.println("Amount of numbers in expression = " + new Calculator().amountOfNumbers(expression));
        } else
            System.err.println("Input Error. Valid characters: 0-9 and the following characters: + - / * ( ) .");
    }

    public void throwsAnError(String error) {
        if (error.equals("sings")) {
            System.err.println("Input Error arithmetic sings");
            System.exit(1);
        }
        if (error.equals("brackets")) {
            System.err.println("incorrect placement of arithmetic brackets!");
            System.exit(1);
        }
        if (error.equals("start")) {
            System.err.println("Input Error, the expression must start with a digit, a minus sign, or an opening arithmetic bracket");
            System.exit(1);
        }
        if (error.equals("byZero")) {
            System.err.println("divide by zero is not allowed!");
            System.exit(1);
        }
    }

    private void checkBrackets(String expression) {
        int open = 0, close = 0;
        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);

            if (i == 0) {
                if (token == '*' || token == '/') {
                    throwsAnError("start");
                }
            }

            if (token == '*' || token == '/') {
                if (expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '/') {
                    throwsAnError("sings");
                } else if (expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-') {
                    throwsAnError("sings");
                }
            } else if (token == '+' || token == '-') {
                if (expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '-') {
                    throwsAnError("sings");
                }
            } else if (token == '(') {
                open += 1;
            } else if (token == ')') {
                close += 1;
                if (close > open) {
                    throwsAnError("brackets");
                }
                if (open != close) {
                    throwsAnError("brackets");
                }
            }
        }
    }

    private String transformToSimplifiedNotation(String expression) {
        String transformedExpression = "";
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            list.add(expression.charAt(i));
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == '-') {
                if (i == 0) list.add(0, '0');
                else if (list.get(i - 1) == '*' || list.get(i - 1) == '/') {
                    list.add(i, '0');
                    list.add(i, '(');
                    int closingBracketPosition = 3 + i;
                    while (Calculator.getPriority(list.get(closingBracketPosition)) == 0) {
                        closingBracketPosition = closingBracketPosition + 1;
                        if (closingBracketPosition == list.size()) list.add(')');
                    }
                    if (closingBracketPosition != list.size() - 1) list.add(closingBracketPosition, ')');
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            transformedExpression += list.get(i);
        }
        return transformedExpression;
    }
}
