import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1003 : <b>Hangover</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 简单公式
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static int getCount(int currentCount, double currentValue, double value) {
        double cValue = currentValue + 1.0 / (currentCount + 1);
        if(cValue >= value) {
            return currentCount;
        }
        return getCount(currentCount + 1, cValue, value);
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        List<String> strList = new ArrayList<String>();
        while(!"0.00".equals(line)) {
            strList.add(line);
            line = scan.nextLine();
        }
        for(int i = 0; i < strList.size(); i++) {
            String str = strList.get(i);
            int count = getCount(1, 0, Double.parseDouble(str));
            System.out.println(count + " card(s)");
        }
        scan.close();
    }
}

