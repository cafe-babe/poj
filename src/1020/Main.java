import java.util.Scanner;

/** 
 * poj1020 : <b>Anniversary Cake</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int cases = scan.nextInt();
        for(int i = 0; i < cases; i++) {
            int bigSide = scan.nextInt();
            int requestCount = scan.nextInt();
            int[] requests = new int[11];
            int areaSum = 0;
            for(int j = 0; j < requestCount; j++) {
                int index = scan.nextInt();
                requests[index] ++;
                areaSum += index * index;
            }
            if(areaSum != bigSide * bigSide) {
                System.out.println("HUTUTU!");
            } else {
                if(dfs(bigSide, requests, new int[bigSide + 1], requestCount,0)) {
                    System.out.println("KHOOOOB!");
                } else {
                    System.out.println("HUTUTU!");
                }
            }
        }
        scan.close();
    }
    
    public static boolean dfs(int bigSide, int[] requests, int[] colUsed, int n, int a) {
        if(a == n) {
            return true;
        }
        int min = 50;
        int minIndex = 0;
        for(int i = 1; i < colUsed.length; i++) {
            if(colUsed[i] == 0) {
                min = 0; 
                minIndex = i;
                break;
            } else if(colUsed[i] < min){
                min = colUsed[i];
                minIndex = i;
            }
        }
        for(int size = 10; size > 0; size--) {
            if(requests[size] == 0) {
                continue;
            }
            if(bigSide - colUsed[minIndex] >= size && bigSide - minIndex + 1 >= size) {
                boolean flag = true;
                for(int i = minIndex; i <= minIndex + size - 1; i++) {
                    if(colUsed[minIndex] < colUsed[i]) {
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    for(int i = minIndex; i <= minIndex + size - 1; i++) {
                        colUsed[i] += size;
                    }
                    requests[size]--;
                    if(dfs(bigSide, requests, colUsed, n,a + 1)) {
                        return true;
                    }
                    for(int i = minIndex; i <= minIndex + size - 1; i++) {
                        colUsed[i] -= size;
                    }
                    requests[size]++;
                }
            }
        }
        return false;
    }
}

