import java.util.Scanner;

public class PAC1Ex1 {

    public static void main(String[] args) {

        // Constant & var
        int number;

        // Prompt
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a positive number: ");

        // Get input
        number = scanner.nextInt(); //assigns the variable "number" the integer entered by keyboard

        // Data check & execution
        if(number < 0) {
            System.out.println("[ERROR]: The given number is not positive");
        } else {
            Fibonacci(number);
        }
    }

    private static void Fibonacci(int n) {

        // Constant & var
        int old;
        int actual = 0;
        int next = 1;

        // Iteration along requested number of items
        for (int i = 0; i < n; ++i) {
            System.out.print(actual + ", "); //prints the 'i' value of the fibonacci succession

            // Positions update
            old = actual;
            actual = next;
            next = old + actual;
        }
    }
}

