import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class bytesm2
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        int w, h, i, j, max; StringTokenizer st;
        int[][] grid;
        while(T-->0)
        {
            st = new StringTokenizer(f.readLine());
            w = parseInt(st.nextToken());
            h = parseInt(st.nextToken());
            grid = new int[w][h+2];
            for(i = 0; i < w; i++)
            {
                for(j = 1; j <= h; j++)
                {
                    if(!st.hasMoreTokens()) st = new StringTokenizer(f.readLine());
                    grid[i][j] = parseInt(st.nextToken());
                }
                grid[i][0] = grid[i][h+1] = -100000000;
            }
            
            for(i = 1; i < w; i++)
                for(j = 1; j <= h; j++)
                    grid[i][j] += max(grid[i-1][j-1], grid[i-1][j], grid[i-1][j+1]);
                
            max = 0;
            for(j = 1; j <= h; j++)
                max = max(max, grid[w-1][j]);
            
            System.out.println(max);
            System.out.flush();
        }
        System.exit(0);
    }
    
    private static int max(int... x)
    {
        int max = -Integer.MAX_VALUE;
        for(int i : x) if(max < i) max = i;
        return max;
    }
}