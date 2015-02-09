import java.util.Scanner;

/** 
 * poj1014 : <b>Dividing</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static int[] inputs = new int[6];
    public static int[] b = new int[]{60, 30, 20, 15, 12, 10};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        int index = 1;
        while (!"0 0 0 0 0 0".equals(line.trim())) {
            String[] strs = line.split(" ");
            int totalValue = 0;
            for (int i = 0; i < 6; i++) {
                inputs[i] = Integer.parseInt(strs[i]) % b[i];
                totalValue += inputs[i] * (i + 1);
            }
            boolean b = false;
            if(totalValue % 2 == 0) {
                b = validate(totalValue / 2, new int[6]);
            }
            System.out.println("Collection #" + index + ":");
            if(b) {
                System.out.println("Can be divided.");
            } else {
                System.out.println("Can't be divided.");
            }
            System.out.println();
            index++;
            line = scan.nextLine();
        }
        scan.close();
    }

    public static boolean validate(int num, int[] used) {
        int sum = 0;
        for (int i = 0; i < used.length; i++) {
            sum += used[i] * (i + 1);
        }
        if (sum == num) {
            return true;
        }
        if (sum > num) {
            return false;
        }
        for (int i = used.length - 1; i >= 0; i--) {
            if (used[i] < inputs[i]) {
                used[i]++;
                if (validate(num, used)) {
                    return true;
                }
                used[i]--;
            }
        }
        return false;
    }
}

