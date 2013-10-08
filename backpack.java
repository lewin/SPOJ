import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class backpack
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(f.readLine());
        int[] dp, temp, val, vol, ind;
        int[][] att; boolean[] isatt;
        int cap, n, i, a, j, k, m;
        StringTokenizer st;
        while(t-->0)
        {
            st = new StringTokenizer(f.readLine());
            cap = parseInt(st.nextToken());
            n = parseInt(st.nextToken());
            dp = new int[cap+1];
            vol = new int[n]; val = new int[n];
            att = new int[n][3]; ind = new int[n];
            isatt = new boolean[n];
            for(i = 0; i < n; i++)
            {
                st = new StringTokenizer(f.readLine());
                vol[i] = parseInt(st.nextToken());
                val[i] = parseInt(st.nextToken())*vol[i];
                a = parseInt(st.nextToken());
                if(a == 0) continue; a--;
                att[a][ind[a]++] = i;
                isatt[i] = true;
            }
            
            for(i = 0; i < n; i++)
            {
                if(isatt[i]) continue;
                temp = new int[cap+1];
                for(k = cap; k >= vol[i]; k--)
                    temp[k] = dp[k-vol[i]]+val[i];
                    
                for(j = 0; j < ind[i]; j++)
                {
                    m = att[i][j];
                    for(k = cap; k >= vol[i]+vol[m]; k--)
                    {
                        if(temp[k] < dp[k-vol[i]-vol[m]]+val[i]+val[m])
                            temp[k] = dp[k-vol[i]-vol[m]]+val[i]+val[m];
                        if(temp[k] < temp[k-vol[m]]+val[m])
                            temp[k] = temp[k-vol[m]]+val[m];
                    }
                }
                
                for(k = 0; k <= cap; k++)
                    if(dp[k] < temp[k])
                        dp[k] = temp[k];
            }
            
            System.out.println(dp[cap]);
            System.out.flush();
        }
        System.exit(0);
    }
}