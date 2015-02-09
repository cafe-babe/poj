import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1018 : <b>Communication System</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        for (int index = 0; index < t; index++) {
            int n = scan.nextInt();
            List<Info> data = new ArrayList<Info>();
            int[] maxB = new int[n];
            for (int j = 0; j < n; j++) {
                int producters = scan.nextInt();
                for (int k = 0; k < producters; k++) {
                    Info info = new Info();
                    info.B = scan.nextInt();
                    info.P = scan.nextInt();
                    info.id = j + 1;
                    if(info.B > maxB[j]) {
                        maxB[j] = info.B;
                    }
                    data.add(info);
                }
            }
            Object[] array = data.toArray();
            Arrays.sort(array);
            Info[] dev = new Info[array.length];
            for(int i = 0; i < array.length; i++) {
                dev[i] = (Info) array[i];
            }
            
            double maxBP = 0;
            for(int i = 0; i < dev.length - n + 1; i++) {
                boolean[] visited = new boolean[dev.length];
                double tp = dev[i].P;
                visited[dev[i].id - 1] = true;
                int count = 1;
                boolean flag = false;
                for(int j = i + 1; j < dev.length; j++) {
                    if(visited[dev[j].id - 1]) {
                        continue;
                    }
                    if(dev[j].B > maxB[dev[j].id - 1]) {
                        flag = true;
                        break;
                    }
                    count++;
                    visited[dev[j].id - 1] = true;
                    tp += dev[j].P;
                }
                if(count < n || flag) {
                    break;
                }
                double bp = dev[i].B / tp;
                maxBP = bp > maxBP ? bp : maxBP;
            }
            System.out.println(new DecimalFormat("0.000").format(maxBP));
        }
        scan.close();
    }
}

class Info implements Comparable<Info> {
    public int B;
    public int P;
    public int id;
    public int compareTo(Info o) {
        return B == o.B ? P == o.P ? id - o.id : P - o.P : B - o.B;
    }
}

class Main2 {
    public static double max = 0;
    public static int[] used;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scan.nextInt();
            int[][][] data = new int[n][][];
            used = new int[n];
            max = 0;
            for (int j = 0; j < n; j++) {
                int producters = scan.nextInt();
                data[j] = new int[producters][2];
                for (int k = 0; k < producters; k++) {
                    data[j][k][0] = scan.nextInt();
                    data[j][k][1] = scan.nextInt();
                }
            }
            cal(data, 0);
            System.out.println(new DecimalFormat("0.000").format(max));
        }
        scan.close();
    }

    public static void cal(int[][][] data, int level) {
        double bp = 0, b = 999999999, p = 0;
        for (int i = 0; i < used.length; i++) {
            if (data[i][used[i]][0] < b) {
                b = data[i][used[i]][0];
            }
            p += data[i][used[i]][1];
        }
        bp = b / p;
        if (bp > max) {
            max = bp;
        }
        if (level >= data.length) {
            return;
        }
        for (int i = 0; i < data[level].length; i++) {
            used[level] = i;
            cal(data, level + 1);
        }
    }
}

