import java.util.Scanner;

/** 
 * poj1013 : <b>Counterfeit Dollar</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 暴力模拟
 * 1， 如果两边相等，则两边全是真的<br/>
 * 2，如果是大于，那么左边的可能是重了，右边的可能是轻了，
 *    但如果原本已经被认定为可能是轻了的在左边，那他肯定是真的<br/>
 * 3，小于同上
 * 4，如果是大于或小于，那么没有使用那的那些，全部都是真的<br/>
 * 5，如果是大于或小于，而且只有一个是可能重了或轻了，那他就是假的<br/>
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    //0 无任何信息    1确定是真的  2确定是假的  3确定是重了  4确定是轻了  5可能大了 6 可能小了
    public static int[] states = null;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine().trim();
        int row = Integer.parseInt(line);
        String[][] strs = new String[3][3];
        for(int i = 1; i <= row * 3; i++) {
            line = scan.nextLine();
            String[] ss = line.split(" ");
            strs[(i - 1) % 3] = ss;
            if(i % 3 == 0) {
                states = new int[12];
                resolve(strs);
            }
        }
        scan.close();
    }
    
    public static void resolve(String[][] data) {
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i][0].length(); j++) {
                char c1 = data[i][0].charAt(j);
                char c2 = data[i][1].charAt(j);
                if(data[i][2].equals("even")) {
                    states[c1 - 'A'] = 1;
                    states[c2 - 'A'] = 1;
                } else if(data[i][2].equals("up")) {
                    if(states[c1 - 'A'] == 1 || states[c1 - 'A'] == 6) {
                        states[c1 - 'A'] = 1;
                    } else {
                        states[c1 - 'A'] = 5;
                    }
                    if(states[c2 - 'A'] == 1 ||states[c2 - 'A'] == 5) {
                        states[c2 - 'A'] = 1;
                    } else {
                        states[c2 - 'A'] = 6;
                    }
                } else {
                    if(states[c1 - 'A'] == 1 || states[c1 - 'A'] == 5) {
                        states[c1 - 'A'] = 1;
                    } else {
                        states[c1 - 'A'] = 6;
                    }
                    if(states[c2 - 'A'] == 1 ||states[c2 - 'A'] == 6) {
                        states[c2 - 'A'] = 1;
                    } else {
                        states[c2 - 'A'] = 5;
                    }
                }
            }
            if(!data[i][2].equals("even")) {
                for(int k = 0; k < states.length; k++) {
                    if(data[i][0].indexOf((char)('A' + k)) == -1 && data[i][1].indexOf((char)('A' + k)) == -1) {
                        states[k] = 1;
                    }
                }
            }
            if(validate()) {
                return;
            }
        }
    }
    
    public static boolean validate() {
        int inCount = 0;
        int index = -1;
        for(int i = 0; i < states.length; i++) {
            if(states[i] == 5 || states[i] == 6) {
                index = i;
                inCount ++;
            }
        }
        if(inCount == 1) {
            if(states[index] == 5) {
                System.out.println((char)('A' + index) + " is the counterfeit coin and it is heavy. ");
            } else {
                System.out.println((char)('A' + index) + " is the counterfeit coin and it is light. ");
            }
            return true;
        }
        return false;
    }
}   

