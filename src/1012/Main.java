import java.util.Scanner;

/** 
 * poj1012 : <b>Joseph</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    private static int[] results = new int[15];
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        while(!"0".equals(line)) {
            int k = Integer.parseInt(line);
            if(results[k] == 0) {
                results[k] = getMinM(k);
            }
            System.out.println(results[k]);
            line = scan.nextLine();
        }
        scan.close();
    }
    
    public static int getMinM(int k) {
        int minM = k + 1;
        while(true) {
            if(minM % (k + 1) == 0) {
                if(validate(k, minM, 2 * k, 1)) {
                    return minM;
                }
            }
            if(minM % (k + 1) == 1) {
                if(validate(k, minM, 2 * k, 1)) {
                    return minM;
                }
                minM += k - 2;
            }
            minM ++;
        }
    }
    
    public static boolean validate(int k, int m, int n, int c) {
        if(n == k) {
            return true;
        }
        int out = (m - 2 + c) % n + 1;
        if(out <= k) {
            return false;
        }
        return validate(k, m, n - 1, out);
    }
}

