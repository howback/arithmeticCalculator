import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your expression: ");
        String expression = scanner.nextLine();
        System.out.println("Your expression = " + expression);
        new Verifier().verification(expression);
    }
}
