import java.util.Scanner;
import java.util.StringTokenizer;

/** 
 * poj1001 : <b>Exponentiation</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 一：使用BigDecimal直接解决！！！<br/>
 * 二：将大数按位分割，利用多项式相乘原理拆分
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    
    public static int BITS = 4;
    public static int MAX_PEER = 10000;

    /**
     * 将大数拆分
     */
    public static String[] divide(String val) {
        int len = val.length();
        int size = len % BITS == 0 ? len / BITS : len / BITS + 1;
        String[] mag = new String[size];
        for (int i = 0; i < size - 1; i++) {
            mag[i] = val.substring(len - i * BITS - BITS, len - i * BITS);
        }
        mag[size - 1] = val.substring(0, len - (size - 1) * BITS);
        return mag;
    }

    /**
     * 将大数拆分，将位放在数组中
     */
    public static int[] getMagnitude(String val) {
        String[] magStrs = divide(val);
        int[] mag = new int[magStrs.length];
        for (int i = 0; i < magStrs.length; i++) {
            mag[i] = Integer.parseInt(magStrs[i]);
        }
        return mag;
    }

    /**
     * 大数相加
     */
    public static String add(String val1, String val2) {
        int[] mag1 = getMagnitude(val1);
        int[] mag2 = getMagnitude(val2);
        int[] resultMag = new int[Math.max(mag1.length, mag2.length) + 1];
        boolean overflow = false;
        for (int i = 0; i < resultMag.length; i++) {
            if (overflow) {
                resultMag[i]++;
                overflow = false;
            }
            if (i < mag1.length) {
                resultMag[i] += mag1[i];
            }
            if (i < mag2.length) {
                resultMag[i] += mag2[i];
            }
            if (resultMag[i] > MAX_PEER) {
                resultMag[i] -= MAX_PEER;
                overflow = true;
            }
        }
        return transMag(resultMag);
    }

    /**
     * 大数相乘
     */
    public static String multiply(String val1, String val2) {
        int index1 = val1.indexOf('.');
        int index2 = val2.indexOf('.');
        int digit1 = index1 == -1 ? 0 : val1.length() - index1 - 1;
        int digit2 = index2 == -1 ? 0 : val2.length() - index2 - 1;
        String intVal = multiplyInt(val1.replace(".", ""),
                val2.replace(".", ""));
        int digit = digit1 + digit2;
        if (digit == 0) {
            return intVal;
        }
        if (digit >= intVal.length()) {
            String str = "0.";
            for (int i = 0; i < digit - intVal.length(); i++) {
                str += "0";
            }
            return str + intVal;
        }
        return intVal.substring(0, intVal.length() - digit) + "."
                + intVal.substring(intVal.length() - digit);
    }

    public static String multiplyInt(String val1, String val2) {
        int[] mag1 = getMagnitude(val1);
        int[] mag2 = getMagnitude(val2);
        int[] resultMag = new int[Math.max(mag1.length, mag2.length) * 2];
        int[] overflow = new int[resultMag.length];
        for (int i = 0; i < mag1.length; i++) {
            for (int j = 0; j < mag2.length; j++) {
                int r = mag1[i] * mag2[j] + overflow[i + j] + resultMag[i + j];
                if (r > MAX_PEER) {
                    overflow[i + j + 1] += r / MAX_PEER;
                }
                resultMag[i + j] = r % MAX_PEER;
                overflow[i + j] = 0;
            }
        }
        for (int i = 0; i < overflow.length; i++) {
            resultMag[i] += overflow[i];
        }
        return transMag(resultMag);
    }

    public static String transMag(int[] mag) {
        StringBuffer sb = new StringBuffer();
        for (int i = mag.length - 1; i >= 0; i--) {
            if (sb.length() != 0) {
                sb.append(format(mag[i], BITS));
            } else {
                if (mag[i] != 0) {
                    sb.append(mag[i]);
                }
            }
        }
        return sb.toString();
    }

    public static String format(int val, int place) {
        StringBuffer sb = new StringBuffer();
        String result = String.valueOf(val);
        for (int i = 0; i < place - result.length(); i++) {
            sb.append("0");
        }
        return sb.append(result).toString();
    }

    public static String pow(String r, String n) {
        String val = r;
        for (int i = 1; i < Integer.parseInt(n); i++) {
            val = multiply(val, r);
        }
        return val;
    }

    /**
     * 将结果转换成符合要求的格式
     */
    public static String formatNumber(String val) {
        int endZeros = 0;
        int startZeros = 0;
        for (int i = 0; i < val.length() && val.charAt(i) == '0'; i++) {
            startZeros++;
        }
        if (startZeros == val.length()) {
            return "0";
        }
        if (val.contains(".")) {
            for (int i = val.length() - 1; i >= 0 && val.charAt(i) == '0'; i--) {
                endZeros++;
            }
        }

        val = val.substring(startZeros, val.length() - endZeros);
        if (val.equals("") || val.equals("0.") || val.equals(".")) {
            return "0";
        }
        if (val.startsWith(".")) {
            val = "0" + val;
        }
        if (val.endsWith(".")) {
            val = val.substring(0, val.length() - 1);
        }
        return val;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String line = scan.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            String r = st.nextToken();
            String n = st.nextToken();
            String val = pow(formatNumber(r), n);
            if (val.startsWith("0.")) {
                System.out.println(val.substring(1));
            } else {
                System.out.println(val);
            }
        }
        scan.close();
    }
}
