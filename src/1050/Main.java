import java.util.Scanner;

/** 
 * poj1050 : <b>To the Max</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 将二维矩阵列求和，转换成一维数组，对一维数组求最大子序列和
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    
    private static int n;
    
    /** 
     * 求一维数组的最大子序列和（动态规划）
     */
    private static int maxSubSeq(int[] seq) {
        int max = Integer.MIN_VALUE;
        // s 代表以元素i结尾的最大子序列和
        int s = seq[0];
        for (int i = 1; i < seq.length; i++) {
            if (s > 0) {
                s = s + seq[i];
            } else {
                s = seq[i];
            }
            if (s > max) {
                max = s;
            }
        }
        return max;
    }
    
    /** 
     * 求矩阵中第i行到第j行的最大子矩阵和
     */
    private static int maxSubRect(int[][] array, int i, int j) {
        int[] colSum = new int[n];
        for (int a = 0; a < n; a++) {
            for (int k = i; k <= j; k++) {
                colSum[a] += array[k][a];
            }
        }
        return maxSubSeq(colSum);
    }
    
    /** 
     * 求最大子矩阵和
     */
    private static int maxSubRect(int[][] array) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int maxSub = maxSubRect(array, i, j);
                if (maxSub > max) {
                    max = maxSub;
                }
            }
        }
        return max;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = Integer.parseInt(scan.nextLine());
        int[][] array = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                array[i][j] = scan.nextInt();
            }
        }
        System.out.println(maxSubRect(array));
        scan.close();
    }
}

