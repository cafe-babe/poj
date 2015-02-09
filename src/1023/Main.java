import java.util.Scanner;

/** 
 * poj1023 : <b>The Fun Number System</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int cases = scan.nextInt();
        for (int i = 0; i < cases; i++) {
            int k = scan.nextInt();
            scan.nextLine();
            char[] pn = scan.nextLine().trim().toCharArray();
            long n = scan.nextLong();
            while(k > 0) {
                k--;
                if((n & 1) == 1) {
                    if(pn[k] == 'n') {
                        n += 2;
                    }
                    pn[k] = '1';
                } else {
                    pn[k] = '0';
                }
                n >>= 1;
            }
            if(n == 0) {
                System.out.println(pn);
            } else {
                System.out.println("Impossible");
            }
        }
        scan.close();
    }

    //DFS，不能处理64位的情况，而且效率不高
    @Deprecated
    public static boolean cal(long pn, long n, MyLong c, int k, int index) {
        if (index == -1) {
            long positive = pn & c.value;
            long negative = ~pn & c.value;
            if (positive - negative == n) {
                return true;
            } else {
                return false;
            }
        }
        long pMax = 0;
        long nMax = 0;
        for(int i = k - 1; i > index; i--) {
            pMax |= (1 << i) & (pn & c.value);
            nMax |= (1 << i) & (~pn & c.value);
        }
        for(int i = index; i >= 0; i--) {
            pMax |= (1 << i) & pn;
            nMax |= (1 << i) & ~pn;
        }
        if(pMax < n || -nMax > n) {
            return false;
        }
        c.value &= ~(1 << index);
        if (cal(pn, n, c, k, index - 1)) {
            return true;
        }
        c.value |= (1 << index);
        if (cal(pn, n, c, k, index - 1)) {
            return true;
        }
        return false;
    }
}

class MyLong {
    public long value = 0;
}

