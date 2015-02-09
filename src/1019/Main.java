import java.util.Scanner;

/** 
 * poj1019 : <b>Number Sequence</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 打表
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static final int SIZE = 31269;
    public static long[] a = new long[SIZE];
    public static long[] s = new long[SIZE];

    public static void main(String[] args) {
        playTable();
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        int row = Integer.parseInt(line);
        for (int idx = 0; idx < row; idx++) {
            long i = Long.parseLong(scan.nextLine());
            System.out.println(cal(i));
        }
        scan.close();
    }
    public static void playTable() {
        a[1] = s[1] = 1;
        for (int i = 2; i < SIZE; i++) {
            a[i] = a[i - 1] + (long) Math.log10((double) i) + 1;
            s[i] = s[i - 1] + a[i];
        }
    }
    public static int cal(long n) {
        int i = 1, len = 0;
        for (; s[i] < n; i++);
        long pos = n - s[i - 1];
        for (i = 1; len < pos; i++)
            len += (long) Math.log10((double) i) + 1;
        //len - pos == 第i个数的第几位
        return (int) ((i - 1) / (long)Math.pow((double)10, len - pos) % 10);
    }
}

