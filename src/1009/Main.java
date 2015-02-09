import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/** 
 * poj1009 : <b>Maya Calendar</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 连续相同的像素点转换之后一定相同
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public static int[][] matrix = new int[][] { { -1, 0 }, { -1, -1 },
            { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 } };

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.next();
        int w = 0;
        List<Integer> data = new ArrayList<Integer>();
        while (!"0".equals(line)) {
            if ("".equals(line)) {
                line = scan.nextLine();
                continue;
            }
            if ("0 0".equals(line)) {
                edgeDetection(data, w);
                data.clear();
                System.out.println("0 0");
            } else {
                String[] strs = line.split(" ");
                if (strs.length == 1) {
                    w = Integer.parseInt(strs[0]);
                    System.out.println(w);
                } else {
                    data.add(Integer.parseInt(strs[0]));
                    data.add(Integer.parseInt(strs[1]));
                }
            }
            line = scan.nextLine();
        }
        System.out.println("0");
        scan.close();
    }

    public static Integer[] getDetections(int[][] data, int w, int h) {
        List<Integer> detections = new ArrayList<Integer>();
        detections.add(0);
        if (h > 1) {
            detections.add(w);
        }
        int total = 0;
        for (int i = 0; i < data.length; i++) {
            total += data[i][1];
            if (!detections.contains(total - 1)) {
                detections.add(total - 1);
            }
            if (total - w - 1 > 0 && !detections.contains(total - w - 1)) {
                detections.add(total - w - 1);
            }
            if (total + w - 1 < w * h && !detections.contains(total + w - 1)) {
                detections.add(total + w - 1);
            }
            if (i != data.length - 1) {
                if (!detections.contains(total)) {
                    detections.add(total);
                }
                if (total - w > 0 && !detections.contains(total - w)) {
                    detections.add(total - w);
                }
                if (total + w < w * h && !detections.contains(total + w)) {
                    detections.add(total + w);
                }
            }
        }
        Object[] os = detections.toArray();
        Arrays.sort(os, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return (Integer) o1 - (Integer) o2;
            }
        });
        Integer[] is = new Integer[os.length];
        for (int i = 0; i < os.length; i++) {
            is[i] = (Integer) os[i];
        }
        return is;
    }

    public static void edgeDetection(List<Integer> data, int w) {
        int h = getHeight(data, w);
        int[][] array = dual(data, w, h);
        List<Integer> sameLength = getSameLength(w, h, array);
        List<Integer> result = new ArrayList<Integer>();
        int temp = -1;
        int pos = 0;
        for (int i = 0; i < sameLength.size(); i++) {
            int[] xy = posToXY(pos, w);
            int edge = calEdge(array, w, h, xy[0], xy[1]);
            if (temp != edge) {
                temp = edge;
                result.add(edge);
                result.add(sameLength.get(i));
            } else {
                result.set(result.size() - 1, result.get(result.size() - 1)
                        + sameLength.get(i));
            }
            pos += sameLength.get(i);
        }
        int[][] resultArray = dual(result, w, h);
        for (int i = 0; i < resultArray.length; i++) {
            System.out.print(resultArray[i][0] + " ");
            System.out.println(resultArray[i][1]);
        }
    }

    public static int getRowCount(int[][] data, int w, int x, int y) {
        int pos = y * w + x;
        int current = 0;
        for (int i = 0; i < data.length; i++) {
            current += data[i][1];
            if (current > pos) {
                return data[i][1];
            }
        }
        throw new RuntimeException("无效取数！");
    }

    public static int[][] dual(List<Integer> data, int w, int h) {
        int[][] array = new int[data.size() / 2][2];
        for (int i = 0; i < data.size(); i++) {
            if (i % 2 == 0) {
                array[i / 2][0] = data.get(i);
            } else {
                array[i / 2][1] = data.get(i);
            }
        }
        return array;
    }

    public static int[] posToXY(int pos, int w) {
        int[] xy = new int[2];
        xy[0] = pos % w;
        xy[1] = pos / w;
        return xy;
    }

    private static List<Integer> getSameLength(int w, int h, int[][] array) {
        List<Integer> results = new ArrayList<Integer>();
        Integer[] detections = getDetections(array, w, h);
        for (int i = 0; i < detections.length; i++) {
            if (i == 0) {
                results.add(1);
            } else {
                if (detections[i] - detections[i - 1] != 1) {
                    results.add(detections[i] - detections[i - 1] - 1);
                }
                results.add(1);
            }
        }
        return results;
    }

    public static int getHeight(List<Integer> data, int w) {
        int count = 0;
        for (int i = 1; i < data.size(); i += 2) {
            count += data.get(i);
        }
        return count / w;
    }

    public static int getValue(int[][] data, int w, int x, int y) {
        int pos = y * w + x;
        return getValue(data, pos);
    }

    public static int getValue(int[][] data, int pos) {
        int current = 0;
        for (int i = 0; i < data.length; i++) {
            current += data[i][1];
            if (current > pos) {
                return data[i][0];
            }
        }
        throw new RuntimeException("无效取数！");
    }

    public static boolean validate(int w, int h, int x, int y) {
        if (x >= 0 && x < w && y >= 0 && y < h) {
            return true;
        }
        return false;
    }

    public static int calEdge(int[][] data, int w, int h, int x, int y) {
        int self = getValue(data, w, x, y);
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (!validate(w, h, x + matrix[i][0], y + matrix[i][1])) {
                continue;
            }
            int other = getValue(data, w, x + matrix[i][0], y + matrix[i][1]);
            int abs = Math.abs(self - other);
            if (abs > max) {
                max = abs;
            }
        }
        return max;
    }
}

