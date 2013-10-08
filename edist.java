import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class edist
{
    private static BufferedReader f;
    public static final int MATCH = 1;
    public static final int INSERT = 2;
    public static final int DELETE = 3;
    public static final int REPLACE = 4;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        
        int T = parseInt(f.readLine());
        
        while(T-- > 0)
        {
            System.out.println(match(f.readLine(), f.readLine()));
            System.out.flush();
        }
        System.exit(0);
    }
    
    private static int match(String x, String y)
    {
        if(x == null || x.length() == 0 || y == null || y.length() == 0) return 0;
        
        char[] a = x.toCharArray();
        char[] b = y.toCharArray();        
        
        int n = a.length;
        int m = b.length;        
        
        int[][] dp = new int[n+1][m+1];
        int[][] parent = new int[n+1][m+1];
        int i, j;
        
        dp[0][0] = 0; parent[0][0] = 0;
        
        for(i = 1; i <= n; i++) {
            parent[i][0] = INSERT; 
            dp[i][0] = dp[i-1][0] + insert(a[i-1]);
        }
        
        for(i = 1; i <= m; i++) {
            parent[0][i] = DELETE; 
            dp[0][i] = dp[0][i-1] + delete(b[i-1]);
        }
        
        for(i = 1; i <= n; i++) {
            for(j = 1; j <= m; j++) {
                if(a[i-1] == b[j-1])  {
                    dp[i][j] = dp[i-1][j-1] + match(); 
                    parent[i][j] = MATCH;
                } else {
                    dp[i][j] = dp[i-1][j-1] + replace(a[i-1],b[j-1]); 
                    parent[i][j] = REPLACE;
                }
                if(dp[i-1][j] + insert(a[i-1]) < dp[i][j]) {
                    dp[i][j] = dp[i-1][j] + insert(a[i-1]); 
                    parent[i][j] = INSERT;
                }
                if(dp[i][j-1] + delete(b[j-1]) < dp[i][j]) {
                    dp[i][j] = dp[i][j-1] + delete(b[j-1]); 
                    parent[i][j] = DELETE;
                }
            }
        }
        
        return dp[n][m];
    }
    
    private static int replace(char a, char b)
    {
        return 1;
    }
    
    private static int delete(char a)
    {
        return 1;
    }
    
    private static int insert(char b)
    {
        return 1;
    }
    
    private static int match()
    {
        return 0;
    }
}