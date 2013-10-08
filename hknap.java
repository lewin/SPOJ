import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class hknap
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        int N, S, Y, C;
        int w, v, i, j; int[] dp;
        StringTokenizer st;
        while(T-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            N = parseInt(st.nextToken());
            S = parseInt(st.nextToken());
            Y = parseInt(st.nextToken());
            C = parseInt(st.nextToken());
            dp = new int[Y+1];
            for(i = 0; i < N; i++)
            {
                st = new StringTokenizer(f.readLine());
                w = parseInt(st.nextToken());
                v = parseInt(st.nextToken());
                for(j = v; j <= Y; j++)
                    if(dp[j] < dp[j-v] + w)
                        dp[j] = dp[j-v] + w;
            }
            long best = 0;
            for(i = 0; i <= Y; i++)
                if(dp[i] > best) best = dp[i];
            long ans = best;
            if(best > C) ans = best*S - C*(S-1);
            
            System.out.println(ans);
            System.out.flush();
        }
        System.exit(0);
    }
}