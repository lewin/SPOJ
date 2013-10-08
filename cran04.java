import java.io.*;
import java.util.*;

public class cran04 {
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader (System.in));
        out = new PrintWriter(System.out, true);
        
        int T = Integer.parseInt (in.readLine());
        StringTokenizer st;
        while (T-- > 0) {
            st = new StringTokenizer (in.readLine());
            int N = Integer.parseInt (st.nextToken());
            int K = Integer.parseInt (st.nextToken());
            
            char [] s = in.readLine().toCharArray();
            long res = 0;
            int [] count = new int [N + 1];
            int ccount = 0;
            count [0]++;
            for (char c : s) {
                if (c == '1') ccount++;
                if (ccount >= K) {
                    res += count [ccount - K];
                }
                count [ccount]++;
            }
            
            out.println (res);
        }
        out.close();
        System.exit(0);
    }
}
