import java.util.Scanner;
import java.util.StringTokenizer;

/** 
 * poj1067 : <b>取石子游戏</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 必败态规律符合黄金分割关系
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        double sqrt5 = Math.sqrt(5);
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        while (line != null && !line.equals("")) {
            StringTokenizer st = new StringTokenizer(line);
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            }
            int j = (int) (a * (sqrt5 - 1) / 2);
            int aj = (int) (j * (1 + sqrt5) / 2);
            int aj1 = (int) ((j + 1) * (1 + sqrt5) / 2);
            if (a == aj && b == a + j || a == aj1 && b == a + j + 1) {
                System.out.println("0");
            } else {
                System.out.println("1");
            }
            line = scan.nextLine();
        }
        scan.close();
    }
}

