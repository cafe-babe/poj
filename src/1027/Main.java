import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1027 : <b>The Same Game</b> <br/>
 * <p>
 * 解决思路：<br/>
 * 利用DFS找出所有簇，然后挑出最大的（也可以每找到一个簇就比较一次，
 *        直接找出最大的），然后模拟消除的过程
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static int[][] move = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 },
            { -1, 0 } };

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int games = scan.nextInt();
        scan.nextLine();
        scan.nextLine();
        for (int game = 1; game <= games; game++) {
            char[][] canvus = new char[11][16];
            for (int i = 10; i > 0; i--) {
                char[] line = scan.nextLine().trim().toCharArray();
                for (int j = 1; j <= 15; j++) {
                    canvus[i][j] = line[j - 1];
                }
            }
            System.out.println("Game " + game + ":\n");
            simulate(canvus);
            if (game < games)
                scan.nextLine();
        }
        scan.close();
    }

    public static void simulate(char[][] canvus) {
        Cluster maxCluster = findMaxCluster(canvus);
        int[] colColorCount = new int[16];
        int colCount = 15;
        char[] blankCol = new char[11];
        Arrays.fill(blankCol, ' ');
        Arrays.fill(colColorCount, 10);
        int move = 1;
        int totalScore = 0;
        int remaining = 150;
        while (maxCluster != null) {
            Object[] ps = maxCluster.points.toArray();
            Arrays.sort(ps);
            for (int i = 0; i < ps.length; i++) {
                Point p = (Point) ps[i];
                for (int k = p.y; k < colColorCount[p.x]; k++) {
                    canvus[k][p.x] = canvus[k + 1][p.x];
                }
                canvus[colColorCount[p.x]--][p.x] = ' ';
            }
            int score = (maxCluster.points.size() - 2)
                    * (maxCluster.points.size() - 2);
            totalScore += score;
            remaining -= maxCluster.points.size();
            System.out.println("Move " + move + " at (" + maxCluster.minPoint.y
                    + "," + maxCluster.minPoint.x + "): removed "
                    + maxCluster.points.size() + " balls of color "
                    + maxCluster.color + ", got " + score + " points. ");
            for (int i = 1; i <= colCount; i++) {
                if (colColorCount[i] == 0) {
                    int j;
                    for (j = i; j < colCount; j++) {
                        for(int k = 1; k <= 10; k++) {
                            canvus[k][j] = canvus[k][j + 1];
                        }
                        colColorCount[j] = colColorCount[j + 1];
                    }
                    for(int k = 1; k <= 10; k++) {
                        canvus[k][j] = ' ';
                    }
                    colCount--;
                    i--;
                }
            }
            maxCluster = findMaxCluster(canvus);
            move++;
        }
        totalScore += remaining == 0 ? 1000 : 0;
        System.out.println("Final score: " + totalScore + ", with " + remaining + " balls remaining. \n");
    }

    public static Cluster findMaxCluster(char[][] canvus) {
        List<Cluster> clusters = findAllClusters(canvus);
        if (clusters.size() == 0) {
            return null;
        }
        Cluster maxCluster = clusters.get(0);
        for (int i = 1; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            if (cluster.points.size() > maxCluster.points.size()) {
                maxCluster = cluster;
            } else if (cluster.points.size() == maxCluster.points.size()) {
                if (cluster.minPoint.x < maxCluster.minPoint.x) {
                    maxCluster = cluster;
                } else if (cluster.minPoint.x == maxCluster.minPoint.x) {
                    if (cluster.minPoint.y < maxCluster.minPoint.y) {
                        maxCluster = cluster;
                    }
                }
            }
        }
        return maxCluster;
    }

    public static List<Cluster> findAllClusters(char[][] canvus) {
        boolean[][] used = new boolean[11][16];
        List<Cluster> clusters = new ArrayList<Cluster>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 15; j++) {
                if (!used[i][j] && canvus[i][j] != ' ') {
                    Cluster cluster = new Cluster();
                    cluster.color = canvus[i][j];
                    DFS(canvus, cluster, used, new Point(j, i));
                    if (cluster.points.size() > 1) {
                        clusters.add(cluster);
                    }
                }
            }
        }
        return clusters;
    }

    public static void DFS(char[][] canvus, Cluster cluster, boolean used[][],
            Point p) {
        if (p.x < 1 || p.x > 15 || p.y < 1 || p.y > 10 || used[p.y][p.x]
                || canvus[p.y][p.x] != cluster.color) {
            return;
        }
        cluster.points.add(p);
        if (cluster.minPoint == null) {
            cluster.minPoint = p;
        } else {
            if (p.x < cluster.minPoint.x || p.x == cluster.minPoint.x
                    && p.y < cluster.minPoint.y) {
                cluster.minPoint = p;
            }
        }
        used[p.y][p.x] = true;
        for (int i = 0; i < 4; i++) {
            int x = p.x + move[i][0];
            int y = p.y + move[i][1];
            DFS(canvus, cluster, used, new Point(x, y));
        }
    }
}

class Cluster {
    List<Point> points = new ArrayList<Point>();
    Point minPoint;
    char color;
}

class Point implements Comparable<Point>{
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        return o.y - y;
    }
}

