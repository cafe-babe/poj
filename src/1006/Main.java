import java.util.Scanner;

/** 
 * poj1006 : <b>Biorhythms</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 直接转换，注意边界的情况
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    
    //23 28 33
    public static long get(long p, long e, long i, long d) {
        long days = d + 1; 
        while(true) {
            long a = days - p;
            long b = days - e; 
            long c = days - i; 
            if(a % 23 == 0 && b % 28 == 0 && c % 33 == 0) {
                break;
            }
            if(a % 23 == 0) {
                if(b % 28 == 0) {
                    days += 644;
                } else {
                    days += 23;
                }
                continue;
            }
            days ++;
        }
        return days - d;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int row = 0;
        while(true) {
            row++;
            String line = scan.nextLine();
            if("-1 -1 -1 -1".equals(line)) {
                return;
            }
            String[] strs = line.split(" ");
            long p = Integer.parseInt(strs[0]);
            long e = Integer.parseInt(strs[1]);
            long i = Integer.parseInt(strs[2]);
            long d = Integer.parseInt(strs[3]);
            System.out.println("Case " + row + ": the next triple peak occurs in " + get(p, e, i, d) +" days.");
        }
    }
}

