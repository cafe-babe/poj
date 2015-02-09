import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1016 : <b>Number That Count</b> <br/>
 * @author Ervin.zhang
 */
public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        while (!"-1".equals(line)) {
            judge(line.trim());
            line = scan.nextLine();
        }
        scan.close();
    }

    public static String compress(String num) {
        int[] digits = new int[10];
        for (int i = 0; i < num.length(); i++) {
            digits[num.charAt(i) - '0']++;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] > 0) {
                sb.append(digits[i]);
                sb.append((char) (i + '0'));
            }
        }
        return sb.toString();
    }

    public static void judge(String num) {
        int step = 0;
        List<String> total = new ArrayList<String>();
        total.add(num);
        while (step < 15) {
            String compress = compress(total.get(total.size() - 1));
            if(compress.equals(total.get(total.size() - 1))) {
                if(step == 0) {
                    System.out.println(num + " is self-inventorying ");
                } else {
                    System.out.println(num + " is self-inventorying after " + step + " steps ");
                }
                break;
            }
            int index = total.indexOf(compress);
            if(index > 0) {
                System.out.println(num + " enters an inventory loop of length " + (total.size() - index));
                break;
            }
            total.add(compress);
            step++;
        }
        if(step == 15)
            System.out.println(num + " can not be classified after 15 iterations");
    }
}

