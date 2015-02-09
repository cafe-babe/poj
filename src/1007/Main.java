import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/** 
 * poj1007 : <b>DNA Sorting</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        String[] strs = line.split(" ");
        int row = Integer.parseInt(strs[1]);
        String[] data = new String[row];
        for(int i = 0; i < row; i++) {
            line = scan.nextLine();
            data[i] = line;
        }
        Arrays.sort(data, new Comparator<String>() {
            public int compare(String o1, String o2) {
                int i1 = getInversions(o1.toCharArray());
                int i2 = getInversions(o2.toCharArray());
                return i1 - i2;
            }
        });
        for(String str : data) {
            System.out.println(str);
        }
        scan.close();
    }
    
    public static int getInversions(char[] str) {
        int inversions = 0;
        for(int i = 0; i < str.length; i++) {
            for(int j = i + 1; j < str.length; j++) {
                if(str[i] > str[j]) {
                    inversions ++;
                }
            }
        }
        return inversions;
    }
}

