import java.util.Arrays;
import java.util.Scanner;

/** 
 * poj1010 : <b>STAMP</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 回溯法，关键在剪枝，否则会TLE
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static int[] bestUsed = null;
    public static boolean isMore = false;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        String preLine = null;
        for (int i = 1; line != null && !line.equals("") && !line.equals("\n"); i++) {
            preLine = new String(line);
            line = scan.nextLine();
            if (i % 2 == 1) {
                String[] ds = preLine.split(" ");
                int[] denominations = new int[ds.length - 1];
                for (int j = 0; j < ds.length - 1; j++) {
                    denominations[j] = Integer.parseInt(ds[j]);
                }
                Arrays.sort(denominations);
                String[] ts = line.split(" ");
                for (int j = 0; j < ts.length - 1; j++) {
                    int total = Integer.parseInt(ts[j]);
                    cal(denominations, total, new int[denominations.length]);
                    System.out.print(total + " ");
                    int len = 0;
                    if (bestUsed == null) {
                        System.out.println("---- none");
                    } else {
                        for (int x : bestUsed) {
                            if(x > 0) {
                                len++;
                            }
                        }
                        if (isMore) {
                            System.out.println("(" + len + "): tie");
                        } else {
                            StringBuffer sb = new StringBuffer();
                            for (int k = 0; k < bestUsed.length; k++) {
                                for (int m = 0; m < bestUsed[k]; m++) {
                                    sb.append(" ");
                                    sb.append(denominations[k]);
                                }
                            }
                            System.out.println("(" + len + "):"
                                    + sb.toString() + "");
                        }
                    }
                    isMore = false;
                    bestUsed = null;
                }
            }
        }
        scan.close();
    }

    // 返回值的意义：bestUsed是否被替换
    public static boolean compare(int[] b, int[] denominations) {
        int atypes = 0, btypes = 0, acount = 0, bcount = 0, amax = 0, bmax = 0;
        for (int i = 0; i < bestUsed.length; i++) {
            if (bestUsed[i] > 0) {
                atypes++;
                acount += bestUsed[i];
                if(denominations[i] > amax) {
                    amax = denominations[i];
                }
            }
            if (b[i] > 0) {
                btypes++;
                bcount += b[i];
                if(denominations[i] > bmax) {
                    bmax = denominations[i];
                }
            }
        }
        if (atypes > btypes || atypes == btypes && acount < bcount || 
                atypes == btypes && acount == bcount && amax > bmax) {
            return false;
        } else if (atypes < btypes || atypes == btypes && acount > bcount || 
                atypes == btypes && acount == bcount && amax < bmax) {
            bestUsed = b.clone();
            return true;
        }
        isMore = true;
        return false;
    }

    public static boolean equals(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static void cal(int[] denominations, int total, int[] used) {
        int sum = 0;
        int useCount = 0;
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < used[i]; j++) {
                sum += denominations[i];
                useCount++;
            }
        }
        if(sum > total) {
            return;
        }
        if(sum + (4 - useCount) * denominations[denominations.length - 1] < total) {
            return;
        }
        if (sum == total) {
            if (bestUsed == null) {
                bestUsed = used.clone();
            } else if (!equals(used, bestUsed)) {
                boolean isReplaced = compare(used, denominations);
                if (isReplaced) {
                    isMore = false;
                }
            }
            return;
        }
        if (useCount >= 4) {
            return;
        }
        int repeats = 0;
        int old = 0;
        for (int i = 0; i < denominations.length; i++) {
            if(old == denominations[i]) {
                repeats++;
            } else {
                repeats = 0;
                old = denominations[i];
            }
            if(repeats < 5) {
                used[i]++;
                cal(denominations, total, used);
                used[i]--;
            }
        }
    }
}

