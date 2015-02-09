import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/** 
 * poj1091 : <b>跳蚤</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static long[] num;
    public static long[] s;
    public static BigInteger m;
    public static BigInteger n;
    public static BigInteger per = new BigInteger("0");
    
    public static void init() {
        Scanner scan = new Scanner(System.in);
        n = scan.nextBigInteger();
        m = scan.nextBigInteger();
        
        num = calPrimeFactor(m);
        s = new long[num.length];
        BigInteger result = pow(m, n);
        for(int i = 0; i < num.length; i++) {
            per = new BigInteger("0");
            get(0, 0, i + 1);
            if(i % 2 == 0) {
                result = result.subtract(per);
            } else {
                result = result.add(per);
            }
        }
        System.out.println(result);
        scan.close();
    }
    

    /**
     * 求质因子
     * @param x
     * @return
     */
    public static long[] calPrimeFactor(BigInteger x) {
        List<BigInteger> factors = new ArrayList<BigInteger>();
        //////////////////
        for(int i = 2; i * i <= x.longValue(); i++) {
            if(x.mod(new BigInteger(i + "")) .equals(new BigInteger("0"))) {
                factors.add(new BigInteger(i + ""));
                while(x.mod(new BigInteger(i + "")) .equals(new BigInteger("0"))) {
                    x = x.divide(new BigInteger(i + ""));
                }
            }
        }
        if(!x.equals(new BigInteger(1 + ""))) {
            factors.add(x);
        }
        long[] fs = new long[factors.size()];
        for(int i = 0; i < fs.length; i++) {
            fs[i] = factors.get(i).longValue();
        }
        return fs;
    }
    
    public static BigInteger pow(BigInteger a, BigInteger b) {
        BigInteger r = new BigInteger("1");
        for(int i = 0; i < b.longValue(); i++) {
            r = r.multiply(new BigInteger(a + ""));
        }
        return r;
    }
    
    
    /**
     * @param a
     * @param b
     * @param c 公共质因子的个数
     * @return
     */
    public static void get(long a, long b, long c) {
        if(b == c) {
            BigInteger t = m;
            for(int i = 0; i < c; i++) {
                t = t.divide(new BigInteger(s[i] + ""));
            }
            per = per.add(pow(t, n));
        } else {
            for(int i = (int) a; i < num.length; i++) {
                s[(int)b] = num[i];
                get(i + 1, b + 1, c);
            }
        }
    }
    
    public static void main(String[] args) {
        init();
    }
}

//public static long maxCommonDivisor(long a, long b) {
//  a++;
//  long big = a > b ? a : b;
//  long small = a <= b ? a : b;
//  if(big % small == 0) {
//      return small;
//  }
//  long quotient = big / small;
//  return maxCommonDivisor(small, big - small * quotient);
//}
//
//public static boolean validate(long[] data) {
//  if(maxCommonDivisor(data) == 1) {
//      return true;
//  }
//  return false;
//}

//public static long maxCommonDivisor(long[] data) {
//  if(data.length == 0) {
//      return 0;
//  } 
//  if(data.length == 1) {
//      return data[0];
//  }
//  long maxDivisor = data[0];
//  for(int i = 1; i < data.length; i++) {
//      maxDivisor = maxCommonDivisor(maxDivisor, data[i]);
//  }
//  return maxDivisor;
//}

//public static long cal(int n, int m) {
//  long[] data = new long[n];
//  Arrays.fill(data, 1);
//  long total = 1;
//  for(int i = 0; i < m; i++) {
//      total *= n;
//  }
//  long q = 1;
//  long count = 0;
//  for(int i = 0; i < total; i++) {
//      for(int j = 0; j < data.length; j++) {
//          q = 1;
//          for(int k = 0; k < j; k++) {
//              q *= m;
//          }
//          data[j] = i % (q * m) / q + 1;
//      }
//      boolean b = validate(data);
//      if(b) {
//          count ++;
////            for(int z = data.length - 1; z >= 0; z--) {
////                System.out.print(data[z] + "  ");
////            }
////            System.out.println();
//      }
//  }
//  return count;
//}


