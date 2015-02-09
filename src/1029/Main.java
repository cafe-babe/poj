import java.util.Scanner;

/** 
 * poj1029 : <b>False coin</b> <br/>
 * <p>
 * 暴力模拟<br/>
 * 几点线索：<br/>
 * 1， 如果两边相等，则两边全是真的<br/>
 * 2，如果是大于，那么左边的可能是重了，右边的可能是轻了，但如果原本已经被认定为可能是轻了的在左边，那他肯定是真的<br/>
 * 3，小于同上<br/>
 * 4，如果是大于或小于，那么没有使用那的那些，全部都是真的<br/>
 * 5，如果是大于或小于，而且只有一个是可能重了或轻了，那他就是假的<br/>
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    // 0 无任何信息 1确定是真的 2确定是假的 3确定是重了 4确定是轻了 5可能大了 6 可能小了
    public static int[] state;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        state = new int[n + 1];
        int k = scan.nextInt();
        int mayFalseCount = 0;
        int falseIndex = 0;
        for (int i = 0; i < k; i++) {
            int pi = scan.nextInt();
            int[] left = new int[pi];
            int[] right = new int[pi];
            for (int j = 0; j < pi; j++) {
                left[j] = scan.nextInt();
            }
            for (int j = 0; j < pi; j++) {
                right[j] = scan.nextInt();
            }
            scan.nextLine();
            char result = scan.nextLine().charAt(0);
            if (result == '=') {
                for (int a = 0; a < left.length; a++) {
                    state[left[a]] = 1;
                    state[right[a]] = 1;
                }
            }
            int used[] = new int[pi * 2];
            int m = 0;
            if (result == '>') {
                for (int a = 0; a < left.length; a++) {
                    if (state[left[a]] == 6) {
                        state[left[a]] = 1;
                    } else if (state[left[a]] == 0) {
                        state[left[a]] = 5;
                    }
                    if (state[right[a]] == 5) {
                        state[right[a]] = 1;
                    } else if (state[right[a]] == 0) {
                        state[right[a]] = 6;
                    }
                    used[m++] = left[a];
                    used[m++] = right[a];
                }
            }
            if (result == '<') {
                for (int a = 0; a < left.length; a++) {
                    if (state[left[a]] == 5) {
                        state[left[a]] = 1;
                    } else if (state[left[a]] == 0) {
                        state[left[a]] = 6;
                    }
                    if (state[right[a]] == 6) {
                        state[right[a]] = 1;
                    } else if (state[right[a]] == 0) {
                        state[right[a]] = 5;
                    }
                    used[m++] = left[a];
                    used[m++] = right[a];
                }
            }
            if (result != '=') {
                for (int a = 1; a < state.length; a++) {
                    boolean isUsed = false;
                    for (int b = 0; b < used.length; b++) {
                        if (a == used[b]) {
                            isUsed = true;
                            break;
                        }
                    }
                    if (!isUsed) {
                        state[a] = 1;
                    }
                }
            }
            mayFalseCount = 0;
            falseIndex = 0;
            if (result != '=') {
                for (int a = 0; a < left.length; a++) {
                    if (state[left[a]] == 5 || state[left[a]] == 6) {
                        mayFalseCount++;
                        falseIndex = left[a];
                    }
                    if (state[right[a]] == 5 || state[right[a]] == 6) {
                        mayFalseCount++;
                        falseIndex = right[a];
                    }
                }
                if (mayFalseCount == 1) {
                    state[falseIndex] = 3;
                    break;
                }
            }
        }
        int mustFalseIndex = 0;
        mayFalseCount = 0;
        for (int i = 1; i < state.length; i++) {
            if (state[i] == 3) {
                mustFalseIndex = i;
                break;
            }
            if (state[i] == 5 || state[i] == 6 || state[i] == 0) {
                mayFalseCount++;
                falseIndex = i;
            }
        }

        if (mustFalseIndex != 0) {
            System.out.println(mustFalseIndex);
        } else {
            if (mayFalseCount == 1) {
                System.out.println(falseIndex);
            } else {
                System.out.println(0);
            }
        }
        scan.close();
    }
}
