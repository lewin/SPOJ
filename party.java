import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class party
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(f.readLine());
        
        int n = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        int[] dp; int i, j, a, b, used, fun;
        while(n != 0 && k != 0)
        {
            dp = new int[n+1];
            Arrays.fill(dp, -1);
            dp[0] = 0;
            fun = used = 0;
            for(i = 0; i < k; i++)
            {
                st = new StringTokenizer(f.readLine());
                a = parseInt(st.nextToken());
                b = parseInt(st.nextToken());
                for(j = n; j >= a; j--)
                    if(dp[j-a] >= 0 && dp[j] < dp[j-a] + b)
                        dp[j] = dp[j-a] + b;
            }
            for(i = 0; i <= n; i++)
                if(dp[i] > fun)
                {
                    fun = dp[i];
                    used = i;
                }
            
            System.out.format("%d %d%n", used, fun);
            System.out.flush();
            
            f.readLine();
            st = new StringTokenizer(f.readLine());
            n = parseInt(st.nextToken());
            k = parseInt(st.nextToken());
        }
        
        System.exit(0);
    }
}