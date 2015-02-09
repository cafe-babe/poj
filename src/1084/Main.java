import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/** 
 * poj1184 : <b>聪明的打字员</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 广度优先遍历
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static int min = 999;
    public static boolean[] used = new boolean[7000000];

    public static boolean change(char[] value, int type) {
        char temp;
        int cursor = value[0] - '0';
        switch (type) {
        case 1:
            if (cursor == 1)
                return false;
            temp = value[cursor];
            value[cursor] = value[1];
            value[1] = temp;
            break;
        case 2:
            if (cursor == 6)
                return false;
            temp = value[cursor];
            value[cursor] = value[6];
            value[6] = temp;
            break;
        case 3:
            if (value[cursor] == '9')
                return false;
            value[cursor] += 1;
            break;
        case 4:
            if (value[cursor] == '0')
                return false;
            value[cursor] -= 1;
            break;
        case 5:
            if (cursor == 1) {
                return false;
            }
            value[0] -= 1;
            break;
        case 6:
            if (cursor == 6) {
                return false;
            }
            value[0] += 1;
            break;
        }
        return true;
    }

    public static char[] getChars(int x) {
        return String.valueOf(x).toCharArray();
    }

    public static void bfs(char[] cValue, char[] destValue) {
        if (equals(cValue, destValue)) {
            min = 0;
            return;
        }
        Queue<Step> q = new LinkedList<Step>();
        q.add(new Step(Integer.parseInt(new String(cValue)), 0));
        while (!q.isEmpty()) {
            Step currentStep = q.poll();
            char[] current = getChars(currentStep.value);
            int offset = Integer.parseInt(new String(current));
            if (used[offset]) {
                continue;
            }
            used[offset] = true;
            for (int i = 1; i <= 6; i++) {
                int cursor = current[0] - '0';
                char[] temp = new char[7];
                copy(current, temp);
                if (change(temp, i)) {
                    if (equals(temp, destValue)) {
                        min = currentStep.step + 1;
                        return;
                    } else {
                        q.add(new Step(Integer.parseInt(new String(temp)),
                                currentStep.step + 1, cursor, i));
                    }
                }
            }
        }
    }

    public static boolean contains(char[] str, char c) {
        for (int i = 1; i < str.length; i++) {
            if (c == str[i]) {
                return true;
            }
        }
        return false;
    }

    public static void copy(char[] src, char[] dest) {
        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i];
        }
    }

    public static boolean equals(char[] str1, char[] str2) {
        for (int i = 1; i < str1.length; i++) {
            char c1 = str1[i];
            char c2 = str2[i];
            if (c1 != c2) {
                return false;
            }
        }
        return true;
    }

    // 654789 854841 15 000159 000519 8
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        String[] strs = line.split(" ");
        String srcValue = "1" + strs[0];
        String destValue = "1" + strs[1];
        long s = System.currentTimeMillis();
        bfs(srcValue.toCharArray(), destValue.toCharArray());
        System.out.println(System.currentTimeMillis() - s);
        System.out.println(min);
        scan.close();
    }
}

class Step {
    public int value;
    public int step = 0;
    public int preCursor = 1;
    public int preAction = 0;

    public Step(int value, int step) {
        this.value = value;
        this.step = step;
    }

    public Step(int value, int step, int preCursor, int preAction) {
        this.value = value;
        this.step = step;
        this.preCursor = preCursor;
        this.preAction = preAction;
    }
}
