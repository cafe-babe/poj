import java.util.Scanner;

/** 
 * poj1022 : <b>Packing Unit 4D Cubes</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 发挥想象，4D思维
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int cases = scan.nextInt();
        for (int i = 0; i < cases; i++) {
            int n = scan.nextInt();
            int[][] matrix = new int[n + 1][9];
            for (int j = 1; j <= n; j++) {
                for (int k = 0; k < 9; k++) {
                    matrix[j][k] = scan.nextInt();
                }
            }
            if (check(matrix)) {
                int[] max = new int[4];
                int[] min = new int[4];
                cal(matrix, max, min, new int[4], 1, new boolean[n + 1], 0, 0);
                int sum = 1;
                for (int a = 0; a < 4; a++) {
                    sum *= max[a] - min[a] + 1;
                }
                System.out.println(sum);
            } else {
                System.out.println("Inconsistent");
            }
        }
        scan.close();
    }

    public static void cal(int[][] matrix, int[] max, int[] min, int[] current,
            int index, boolean[] arrived, int changeIndex, int change) {
        if(index == 0) {
            return;
        }
        if (arrived[index]) {
            return;
        }
        arrived[index] = true;
        for(int i = 1; i <= 8; i++) {
            current[changeIndex] += change;
            if(current[changeIndex] > max[changeIndex]) {
                max[changeIndex] = current[changeIndex];
            }
            if(current[changeIndex] < min[changeIndex]) {
                min[changeIndex] = current[changeIndex];
            }
            cal(matrix, max, min, current, get(matrix, matrix[index][i]), arrived, (i - 1) / 2, i % 2 == 1 ? 1 : -1);
            current[changeIndex] -= change;
        }
    }

    public static boolean check(int[][] matrix) {
        if (checkSymmetry(matrix)) {
            boolean[] arrived = new boolean[matrix.length];
            isArrived(matrix, 1, arrived);
            boolean allArrived = true;
            for (int i = 1; i < arrived.length; i++) {
                if (!arrived[i]) {
                    allArrived = false;
                    break;
                }
            }
            if (allArrived) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSymmetry(int[][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j <= 8; j++) {
                if (matrix[i][j] != 0
                        && matrix[get(matrix, matrix[i][j])][j % 2 == 1 ? j + 1 : j - 1] != matrix[i][0]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void isArrived(int[][] matrix, int index, boolean[] arrived) {
        if (index == 0 || arrived[index]) {
            return;
        }
        arrived[index] = true;
        for (int j = 1; j <= 8; j++) {
            isArrived(matrix, get(matrix, matrix[index][j]), arrived);
        }
    }
    
    public static int get(int[][] matrix, int cube) {
        for(int i = 1; i < matrix.length; i++) {
            if(cube == matrix[i][0]) {
                return i;
            }
        }
        return 0;
    }
}

