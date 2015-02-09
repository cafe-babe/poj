import java.util.Scanner;

/** 
 * poj1017 : <b>Packets</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        while(!"0 0 0 0 0 0".equals(line)) {
            String[] strs = line.split(" ");
            int[] data = new int[7];
            for(int i = 0; i < 6; i++) {
                data[i + 1] = Integer.parseInt(strs[i]);
            }
            cal2(data);
            line = scan.nextLine();
        }
        scan.close();
    }
    public static void cal(int[] data) {
        int count = 0;
        count += data[6];
        count += data[5];
        data[1] = data[1] > data[5] * 11 ? data[1] - data[5] * 11 : 0;
        count += data[4];
        int left41 = (data[2] > data[4] * 5 ? 0 : data[4] * 5 - data[2]) * 4;
        data[2] = data[2] > data[4] * 5 ? data[2] - data[4] * 5 : 0;
        data[1] = data[1] > left41 ? data[1] - left41 : 0;
        count += (data[3] + 3) / 4;
        int left32 = (data[3] % 4 == 0 ? 0 : (3 - data[3] % 4) * 2 + 1);
        int left31 = (left32 > data[2] ? left32 - data[2] : 0) * 4 + (data[3] % 4 == 0 ? 0 : 8 -  data[3] % 4);
        data[2] = data[2] > left32 ? data[2] - left32 : 0;
        data[1] = data[1] > left31 ? data[1] - left31 : 0;
        count += (data[2] + 8) / 9;
        int left21 = data[2] % 9 == 0 ? 0 : (9 - data[2] % 9) * 4;
        data[1] = data[1] > left21 ? data[1] - left21 : 0;
        count += (data[1] + 35) / 36;
        System.out.println(count);
    }
    
    //网上帅算法
    public static void cal2(int[] data) {
        int count = data[6] + data[5] + data[4] + (data[3] + 3) / 4;
        int y2 = 5 * data[4] + (data[3] % 4 == 0 ? 0 : (3 - data[3] % 4) * 2 + 1);
        if(y2 < data[2]) {
            count += (data[2] - y2 + 8) / 9; 
        }
        int y1 = 36 * count - 36 * data[6] - 25 * data[5] - 16 * data[4] - 9 * data[3] - 4 * data[2];
        if(y1 < data[1]) {
            count += (data[1] - y1 + 35) / 36;
        }
        System.out.println(count);
    }
}

