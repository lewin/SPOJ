import java.io.*;
import java.util.*;

public class coins
{
    private static BufferedReader in;
    private static PrintWriter out;
    private static Map<Integer, Long> mp;
    private static int[] f;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out, true);
        mp = new HashMap<Integer, Long>();
        mp.put(0, 0L);
        String line;
        while(true)
        {
            line = in.readLine();
            if(line == null) break;
            int n = Integer.parseInt(line);
            out.println(maxConvert(n));
        }
        System.exit(0);
    }
    
    public static long maxConvert (int n)
    {
        if(n == 0) return 0;
        long half = n/2, third = n/3, fourth = n/4;
        
        Long test = mp.get(n/2);
        if(test == null) {
            half = maxConvert(n/2);
            mp.put(n/2, half);
        }
        else half = test;
        
        test = mp.get(n/3);
        if(test == null) {
            third = maxConvert(n/3);
            mp.put(n/3, third);
        }
        else third = test;
        
        test = mp.get(n/4);
        if(test == null) {
            fourth = maxConvert(n/4);
            mp.put(n/4, fourth);
        }
        else fourth = test;
        
        long max = Math.max(n, half+third+fourth);
        mp.put(n, max);
        return max;
    }
}