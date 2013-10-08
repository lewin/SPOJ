import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class mcoins
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int k = parseInt(st.nextToken());
        int l = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        int i;
        boolean[] dp = new boolean[1000001]; // true if A can win, false otherwise
        for(i = 1; i < k; i++)
            dp[i] = !dp[i-1];
        for(i = k; i < l; i++)
            dp[i] = !dp[i-1] || !dp[i-k];
        for(i = l; i <= 1000000; i++)
            dp[i]=  !dp[i-1] || !dp[i-k] || !dp[i-l];
        st = new StringTokenizer(f.readLine());
        for(i = 0; i < m; i++)
            if(dp[parseInt(st.nextToken())]) System.out.print("A");
            else System.out.print("B");
        System.out.println();
        System.out.flush();
        System.exit(0);
    }
}