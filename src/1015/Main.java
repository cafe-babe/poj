import java.util.Arrays;
import java.util.Scanner;

/** 
 * poj1015 : <b>Jury Compromise</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 动态规划
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static int[] v = null;
    public static int[] s = null;
    public static int[][] path = null;
    public static int[][] dp = null;
    public static int index = 1;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        while (!"0 0".equals(line.trim())) {
            String[] strs = line.split("( )+");
            int n = Integer.parseInt(strs[0]);
            int m = Integer.parseInt(strs[1]);
            v = new int[n + 1];
            s = new int[n + 1];
            for (int i = 0; i < n; i++) {
                line = scan.nextLine().trim();
                strs = line.split("( )+");
                int a = Integer.parseInt(strs[0]);
                int b = Integer.parseInt(strs[1]);
                v[i + 1] = a - b;
                s[i + 1] = a + b;
            }
            dp(m, n);
            System.out.println();
            line = scan.nextLine();
            line = scan.nextLine();
        }
        scan.close();
    }

    public static boolean isUsed(int j, int k, int i) {
        while (j > 0 && path[j][k] != i) {
            k -= v[path[j][k]];
            j--;
        }
        return j == 0 ? false : true;
    }

    public static void dp(int m, int n) {
        int fix = 20 * m;
        dp = new int[m + 1][2 * fix + 1];
        path = new int[m + 1][2 * fix + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < 2 * fix + 1; j++) {
                dp[i][j] = -1;
                path[i][j] = 0;
            }
        }
        dp[0][fix] = 0;
        // dp[j][k]
        for (int j = 1; j <= m; j++) {
            for (int k = 0; k < 2 * fix + 1; k++) {
                if (dp[j - 1][k] >= 0) {
                    for (int i = 1; i <= n; i++) {
                        if (dp[j][k + v[i]] < dp[j - 1][k] + s[i]) {
                            if (!isUsed(j - 1, k, i)) {
                                dp[j][k + v[i]] = dp[j - 1][k] + s[i];
                                path[j][k + v[i]] = i;
                            }
                        }
                    }
                }
            }
        }
        int k;
        for (k = 0; k <= fix; k++) {
            if (dp[m][fix + k] >= 0 || dp[m][fix - k] >= 0) {
                break;
            }
        }
        int div = dp[m][fix + k] > dp[m][fix - k] ? fix + k : fix - k;
        int j = m;
        int[] cands = new int[m];
        k = div;
        for(int i = 0; i < m; i++) {
            cands[i] = path[j][k];
            k -= v[path[j][k]];
            j--;
        }
        Arrays.sort(cands);
        System.out.println("Jury #" + index);
        System.out.println("Best jury has value " + ((dp[m][div] + div - fix) / 2)
                + " for prosecution and value " + ((dp[m][div] - div + fix) / 2) + " for defence: ");
        for (int i = 0; i < cands.length; i++) {
            System.out.print(" " + cands[i]);
        }
        System.out.println();
        index++;
    }
}

