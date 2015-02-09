import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** 
 * poj1002 : <b>487-3279</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 直接转换
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    
    public static Map<String, Integer> map = new HashMap<String, Integer>();
    
    public static String processTel(String key) {
        StringBuffer tel = new StringBuffer();
        for(int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(c == '-') {
                continue;
            } else if (c >= 'A' && c <= 'Y') {
                tel.append(convert(c));
            } else if(c >= '0' && c <= '9') {
                tel.append(c);
            }
        }
        return tel.toString();
    }
    
    public static char convert(char letter) {
        int num = 0;
        if(letter > 'Q') {
            num = (letter - 1 - 'A') / 3;
        } else {
            num = (letter - 'A') / 3;
        }
        return (char) (num + '2');
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        int row = Integer.parseInt(line);
        boolean hasDuplicates = false; 
        for(int i = 0; i < row; i++) {
            line = scan.nextLine();
            String key = processTel(line);
            if(map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
                hasDuplicates = true;
            } else {
                map.put(key, 1);
            }
        }
        scan.close();
        if(!hasDuplicates) {
            System.out.println("No duplicates.");
            return;
        }
        String[] strs = new String[map.size()];
        int i = 0;
        for(String key : map.keySet()) {
            strs[i] = key;
            i ++;
        }
        Arrays.sort(strs, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }
        });
        for(String str : strs) {
            Integer count = map.get(str);
            String tel = str.substring(0, 3) + '-' + str.substring(3);
            if(count != 1) {
                System.out.println(tel + " " + count);
            }
        }
    }
    
}

