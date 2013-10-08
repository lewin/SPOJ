import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

public class xoinc
{
    private static BufferedReader f;
    private static PrintWriter out;
    private static int[] a, sum;
    private static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        
        int N = parseInt(f.readLine());
        sum = new int[N+1];
        a = new int[N+1];
        int i, j;
        for(i = 1; i <= N; i++) a[N-i+1] = parseInt(f.readLine());
        
        dp = new int[N+1][N+1];
        
        for(i = 1; i <= N; i++)
        {
            sum[i] = sum[i-1] + a[i];
            for(j = 1; j <= N; j++)
            {
                dp[i][j] = max(dp[i][j], dp[i][j-1]);
                if(i >= 2*j-1)
                    dp[i][j] = max(dp[i][j], sum[i] - dp[i-(2*j-1)][2*j-1]);
                if(i >= 2*j)
                    dp[i][j] = max(dp[i][j], sum[i] - dp[i-2*j][2*j]);
            }
        }
        
        System.out.println(dp[N][1]);
        System.out.flush();
        System.exit(0);
    }
}