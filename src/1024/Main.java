import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1024 : <b>Tester Program</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static int W, H;
    public static char[] path;
    public static int[][] walls;
    public static int[][] moves = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 },
            { -1, 0 } };
    public static List<char[]> paths = new ArrayList<char[]>();
    public static Point dp;
    public static boolean isCorrect = true;
    public static boolean isArrived = false;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int cases = scan.nextInt();
        while (cases-- > 0) {
            W = scan.nextInt();
            H = scan.nextInt();
            scan.nextLine();
            path = scan.nextLine().toCharArray();
            int wallCount = scan.nextInt();
            walls = new int[wallCount][4];
            for (int i = 0; i < wallCount; i++) {
                for (int j = 0; j < 4; j++) {
                    walls[i][j] = scan.nextInt();
                }
            }
            dp = go();
            if (dp != null && validate()) {
                System.out.println("CORRECT");
            } else {
                System.out.println("INCORRECT");
            }
        }
        scan.close();
    }

    public static Point go() {
        Point point = new Point(0, 0);
        boolean[][] arrived = new boolean[W][H];
        arrived[point.x][point.y] = true;
        for (int i = 0; i < path.length; i++) {
            int move = getMove(path[i]);
            point = move(move, point);
            if (point == null || arrived[point.x][point.y]) {
                return null;
            }
            arrived[point.x][point.y] = true;
        }
        return point;
    }

    public static int getMove(char c) {
        return c == 'U' ? 0 : c == 'R' ? 1 : c == 'D' ? 2 : 3;
    }

    public static Point move(int move, Point srcPoint) {
        Point destPoint = new Point(0, 0);
        destPoint.x = srcPoint.x + moves[move][0];
        destPoint.y = srcPoint.y + moves[move][1];
        if (destPoint.x < 0 || destPoint.y < 0 || destPoint.x > W - 1
                || destPoint.y > H - 1) {
            return null;
        }
        for (int i = 0; i < walls.length; i++) {
            if (srcPoint.x == walls[i][0] && srcPoint.y == walls[i][1]
                    && destPoint.x == walls[i][2] && destPoint.y == walls[i][3]) {
                return null;
            }
        }
        return destPoint;
    }

    public static void cal(int step, int x, int y, int[] steps) {
        if (x == dp.x && y == dp.y) {
            if (step < path.length) {
                isCorrect = false;
                return;
            }
        }
        if (step == path.length) {
            for(int i = 0; i < steps.length; i++) {
                System.out.print(steps[i] + " ");
            }
            System.out.println();
            if (x == dp.x && y == dp.y) {
                if (isArrived) {
                    isCorrect = false;
                } else {
                    isArrived = true;
                }
            }
            return;
        }
        for (int i = 0; i < 4; i++) {
            Point p = new Point(x, y);
            Point mp = move(i, p);
            if (mp == null) {
                continue;
            }
            int temp = steps[step];
            steps[step] = i;
            cal(step + 1, mp.x, mp.y, steps);
            if(!isCorrect) {
                break;
            }
            steps[step] = temp;
        }
    }

    public static boolean validate() {
        cal(0, 0, 0, new int[path.length]);
        if (isCorrect) {
            for (int i = 0; i < walls.length; i++) {
                isCorrect = true;
                int[] temp = walls[i];
                walls[i] = new int[] { -1, -1, -1, -1 };
                cal(0, 0, 0, new int[path.length]);
                if (!isCorrect) {
                    return false;
                }
                walls[i] = temp;
            }
            return true;
        }
        return false;
    }
}

class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Wall {
    public Point first;
    public Point second;
}

