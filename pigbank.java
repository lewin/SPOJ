import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class pigbank
{
    private static BufferedReader f;
    public static final int INF = 1000000000;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        StringTokenizer st;
        int w,i,N, j, val, weight;
        int[] dp;
        while(T-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            w = - parseInt(st.nextToken()) + parseInt(st.nextToken());
            dp = new int[w+1];
            Arrays.fill(dp, INF);
            dp[0] = 0;
            N = parseInt(f.readLine());
            for(i = 0; i < N; i++)
            {
                st = new StringTokenizer(f.readLine());
                val = parseInt(st.nextToken());
                weight = parseInt(st.nextToken());
                for(j = weight; j <= w; j++)
                    if(dp[j] > dp[j-weight]+val)
                        dp[j] = dp[j-weight]+val;
            }
            
            if(dp[w] == INF)
                System.out.println("This is impossible.");
            else
                System.out.format("The minimum amount of money in the piggy-bank is %d.%n", dp[w]);
            System.out.flush();
        }
        System.exit(0);
    }
}