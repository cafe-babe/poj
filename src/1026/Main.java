import java.util.Arrays;
import java.util.Scanner;

/** 
 * poj1026 : <b>Cipher</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        while (n != 0) {
            int[] secret = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                secret[i] = scan.nextInt();
            }
            scan.nextLine();
            String line = scan.nextLine();
            int k = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            while (line.indexOf(" ") >= 0) {
                k = Integer.parseInt(line.substring(0, line.indexOf(" ")));
                line = line.substring(line.indexOf(" ") + 1);
                char[] message = new char[n + 1];
                for (int i = 1; i <= n; i++) {
                    if (i > line.length()) {
                        message[i] = ' ';
                    } else {
                        message[i] = line.charAt(i - 1);
                    }
                }
                
                System.out.println(new String(calChange(message, secret, n, k), 1, n));
                line = scan.nextLine();
            }
            System.out.println();
            n = scan.nextInt();
        }
        scan.close();
    }

    public static boolean contains(int[] array, int value) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] == 0) {
                break;
            }
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static char[] calChange(char[] message, int[] secret, int n, int k) {
        int[] mod = new int[n + 1];
        int[] pos = new int[n + 2];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(pos, 0);
            pos[1] = secret[i];
            for (int j = 2; j <= n + 1; j++) {
                if (contains(pos, secret[pos[j - 1]])) {
                    mod[i] = j - 1;
                    break;
                }
                pos[j] = secret[pos[j - 1]];
            }
        }
        char[] result = new char[n + 1];
        for(int i = 1; i <= n; i++) {
            int moveTimes = k % mod[i];
            int index = i;
            while(moveTimes-- > 0) {
                index = secret[index];
            }
            result[index] = message[i];
        }
        return result;
    }
}

