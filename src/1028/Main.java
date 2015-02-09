import java.util.Scanner;
import java.util.Stack;

/** 
 * poj1028 : <b>Web Navigation</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        Stack<String> backStack = new Stack<String>();
        Stack<String> forwardStack = new Stack<String>();
        String currentUrl = "http://www.acm.org/";
        while (!"QUIT".equals(line)) {
            String[] command = line.split(" ");
            if (command.length > 1) {
                backStack.push(currentUrl);
                forwardStack.clear();
                currentUrl = command[1];
                System.out.println(currentUrl);
            }
            if ("BACK".equals(command[0])) {
                if (backStack.isEmpty()) {
                    System.out.println("Ignored");
                } else {
                    forwardStack.push(currentUrl);
                    currentUrl = backStack.pop();
                    System.out.println(currentUrl);
                }
            }
            if ("FORWARD".equals(command[0])) {
                if (forwardStack.isEmpty()) {
                    System.out.println("Ignored");
                } else {
                    backStack.push(currentUrl);
                    currentUrl = forwardStack.pop();
                    System.out.println(currentUrl);
                }
            }
            line = scan.nextLine();
        }
        scan.close();
    }
}

