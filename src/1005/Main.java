import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 
 * 1005 : <b>I Think I Need a Houseboat</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 简单的数学公式
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static double PI = 3.141592653;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        int row = Integer.parseInt(line);
        List<Integer> results = new ArrayList<Integer>();
        for(int i = 0; i < row; i++) {
            line = scan.nextLine();
            String[] strs = line.split(" ");
            results.add(getYears(Double.parseDouble(strs[0]), Double.parseDouble(strs[1])));
        }
        for(int i = 0; i < results.size(); i++) {
            System.out.println("Property " + (i + 1) + ": This property will begin eroding in year " + results.get(i) + ".");
        }
        System.out.println("END OF OUTPUT.");
        scan.close();
    }
    
    public static int getYears(double x, double y) {
        double s = PI * (x * x + y * y) / 2;
        int year = (int) (s / 50) + 1;
        return year;
    }
}

