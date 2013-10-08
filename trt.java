import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class trt
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(f.readLine());
        int[] v = new int[n];
        int i, j;
        for(i = 0; i < n; i++) v[i] = parseInt(f.readLine());
        
        int[] prev = new int[n+4];
        int[] dp = new int[n+4];
        for(i = 1; i <= n; i++)
        {
            for(j = 0; j < i; j++)
            {
                dp[j] = Math.max(dp[j],prev[j]+i*v[n-i+j]);
                dp[j+1] = Math.max(dp[j+1],prev[j]+i*v[j]);
            }
            prev = dp;
            dp = new int[n+4];
        }
        int max = 0;
        for(int k:prev) max = Math.max(max,k);
        System.out.println(max);
        System.out.flush();
        System.exit(0);
    }
}