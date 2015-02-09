import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1021 : <b>2D-Nim</b> <br/>
 * @author Ervin.zhang
 */
public class Main {

    public static int[][] move = new int[][] { { 0, -1 }, { 1, 0 }, { 0, 1 },
            { -1, 0 } };

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int cases = scan.nextInt();
        for (int i = 0; i < cases; i++) {
            int W = scan.nextInt();
            int H = scan.nextInt();
            int n = scan.nextInt();
            int[][] left = new int[n][2];
            int[][] right = new int[n][2];
            for (int j = 0; j < n; j++) {
                left[j][0] = scan.nextInt();
                left[j][1] = scan.nextInt();
            }
            for (int j = 0; j < n; j++) {
                right[j][0] = scan.nextInt();
                right[j][1] = scan.nextInt();
            }
            if(cal(left, right, W, H)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        scan.close();
    }

    public static boolean cal(int[][] left, int[][] right, int W, int H) {
        boolean[] leftUsed = new boolean[left.length];
        boolean[] rightUsed = new boolean[left.length];
        List<Cluster> leftClusters = new ArrayList<Cluster>();
        List<Cluster> rightClusters = new ArrayList<Cluster>();
        for (int i = 0; i < left.length; i++) {
            if (!leftUsed[i]) {
                Cluster cluster = new Cluster();
                findCluster(cluster, left, i, leftUsed, W, H);
                leftClusters.add(cluster);
            }
            if (!rightUsed[i]) {
                Cluster cluster = new Cluster();
                findCluster(cluster, right, i, rightUsed, W, H);
                rightClusters.add(cluster);
            }
        }
        if(leftClusters.size() != rightClusters.size()) {
            return false;
        }
//      Object[] leftArray = leftClusters.toArray();
//      Object[] rightArray = leftClusters.toArray();
//      Arrays.sort(leftArray);
//      Arrays.sort(rightArray);
        boolean[] used = new boolean[rightClusters.size()];
        int usedCount = 0;
        left : for(int i = 0; i < leftClusters.size(); i++) {
            Cluster leftCluster = leftClusters.get(i);
            for(int j = 0; j < rightClusters.size(); j++) {
                Cluster rightCluster = rightClusters.get(j);
                if(!used[j]) {
                    if(leftCluster.compare(rightCluster)) {
                        used[j] = true;
                        usedCount++;
                        break;
                    }
                    if(usedCount == leftClusters.size()) {
                        break left;
                    }
                }
            }
        }
        if(usedCount == leftClusters.size()) {
            return true;
        } else {
            return false;
        }
    }

    public static void findCluster(Cluster cluster, int[][] board, int i,
            boolean[] used, int W, int H) {
        used[i] = true;
        cluster.add(board[i][0], board[i][1]);
        for (int j = 0; j < move.length; j++) {
            int x = board[i][0] + move[j][0];
            int y = board[i][1] + move[j][1];
            int index = getIndex(board, x, y, W, H);
            if (index >= 0 && !used[index]) {
                findCluster(cluster, board, index, used, W, H);
            }
        }
    }

    public static int getIndex(int[][] board, int x, int y, int W, int H) {
        if (x >= 0 && x <= W && y >= 0 && y <= H) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][0] == x && board[i][1] == y) {
                    return i;
                }
            }
        }
        return -1;
    }
}

class Cluster implements Comparable<Cluster> {
    public List<Point> points = new ArrayList<Point>();

    public void add(int x, int y) {
        Point point = new Point();
        point.x = x;
        point.y = y;
        points.add(point);
    }

    public void reflect(int type) {
        if(type == 0) {
            for(Point p : points) {
                p.x = -p.x;
            }
        } else {
            for(Point p : points) {
                p.y = -p.y;
            }
        }
    }
    
    public void rotate() {
        for(Point p : points) {
            int temp = p.x;
            p.x = -p.y;
            p.y = temp;
        }
    }
    
    public static int[] reflectType = new int[]{0, 1, 0};
    
    public boolean compare(Cluster cluster) {
        if(points.size() != cluster.points.size()) {
            return false;
        }
        for(int i = 0; i < 4; i++) {
            if(i > 0) {
                rotate();
            }
            for(int j = -1; j < 3; j++) {
                if(j >= 0) {
                    reflect(reflectType[j]);
                }
                if(equals(cluster)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cluster other = (Cluster) obj;
        Object[] thisArray = points.toArray(); 
        Object[] otherArray = other.points.toArray(); 
        Arrays.sort(thisArray);
        Arrays.sort(otherArray);
        int xOffset = ((Point)thisArray[0]).x - ((Point)otherArray[0]).x;
        int yOffset = ((Point)thisArray[0]).y - ((Point)otherArray[0]).y;
        for(int i = 1; i < points.size(); i++) {
            if(((Point)thisArray[i]).x - ((Point)otherArray[i]).x != xOffset || ((Point)thisArray[i]).y - ((Point)otherArray[i]).y != yOffset) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Cluster o) {
        return points.size() - o.points.size();
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < points.size(); i++) {
            if(i != 0) {
                sb.append(" , ");
            }
            sb.append(points.get(i).x + ":" + points.get(i).y);
        }
        return sb.toString();
    }
}

class Point implements Comparable<Point>{
    public int x;
    public int y;
    public int compareTo(Point o) {
        if(x == o.x) 
            return y - o.y;
        return x - o.x;
    }
}
