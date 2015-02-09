import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/** 
 * poj1008 : <b>Maya Calendar</b> <br/>
 * <p>
 * 解题思路：<br/>
 * 主要是注意边界情况
 * </p>
 * @author Ervin.zhang
 */
public class Main {
    public Map<String, HaabMonth> haabMap = new HashMap<String, HaabMonth> ();
    public String[] periods = new String[]{"", "imix", "ik", "akbal", "kan", "chicchan", "cimi", 
            "manik", "lamat", "muluk", "ok", "chuen", "eb", "ben", "ix", "mem", "cib", "caban", "eznab", "canac", "ahau"};
    
    public Main() {
        String[] haabMonthName = {"pop", "no", "zip", "zotz", "tzec", "xul", "yoxkin", "mol", "chen", "yax", "zac", "ceh", "mac", "kankin", "muan", "pax", "koyab", "cumhu", "uayet"};
        for(int i = 0; i < haabMonthName.length; i++) {
            HaabMonth hm = new HaabMonth();
            hm.month = i;
            haabMap.put(haabMonthName[i], hm);
        }
    }
    
    public long calHaabDays(String year, String monthName, String day) {
        int y = Integer.parseInt(year);
        HaabMonth hm = haabMap.get(monthName);
        int d = Integer.parseInt(day);
        long result = 0;
        result += y * 365;
        result += 20 * hm.month;
        result += d + 1;
        return result;
    }
    
    public String getTzolkinCalendar(long days) {
        long y = (days - 1) / 260;
        long p = (days - 260 * y - 1) % 20 + 1;
        long d = (days - 260 * y - 1) % 13 + 1;
        String period = periods[(int) (p)];
        return d + " " + period + " " + y;
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scan = new Scanner(System.in);
        String line = null;
        line = scan.nextLine();
        StringTokenizer st = new StringTokenizer(line);
        int row = Integer.parseInt(line);
        String[] results = new String[row];
        for(int i = 0; i < row; i++) {
            line = scan.nextLine();
            st = new StringTokenizer(line);
            String day = st.nextToken();
            day = day.substring(0, day.length() - 1);
            String month = st.nextToken();
            String year = st.nextToken();
            long days = main.calHaabDays(year, month, day);
            results[i] = main.getTzolkinCalendar(days);
        }
        System.out.println(row);
        for(String r : results) {
            System.out.println(r);
        }
        scan.close();
    }
    
    private class HaabMonth {
        public int month;
    }
}

