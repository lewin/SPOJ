import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class countisl
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int N = parseInt(f.readLine());
        StringTokenizer st; char[] line;
        while(N-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            int n = parseInt(st.nextToken());
            int m = parseInt(st.nextToken());
            
            boolean[][] grid = new boolean[n][m];
            
            for(int i = 0; i < n; i++)
            {
                line = f.readLine().toCharArray();
                for(int j = 0; j < m; j++)
                    grid[i][j] = line[j] == '#';
            }
            
            int count = 0;
            boolean[][] visited = new boolean[n][m];
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++)
                    if(grid[i][j] && !visited[i][j])
                    {
                        Stack<Integer> sx = new Stack<Integer>();
                        Stack<Integer> sy = new Stack<Integer>();
                        int x, y;
                        sx.push(i); sy.push(j);
                        while(sx.size() > 0)
                        {
                            x = sx.pop(); y = sy.pop();
                            if(x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || visited[x][y] || !grid[x][y]) 
                                continue;
                                
                            visited[x][y] = true;
                            
                            sx.push(x+1);   sy.push(y  );
                            sx.push(x-1);   sy.push(y  );
                            sx.push(x  );   sy.push(y+1);
                            sx.push(x  );   sy.push(y-1);
                            sx.push(x+1);   sy.push(y+1);
                            sx.push(x+1);   sy.push(y-1);
                            sx.push(x-1);   sy.push(y+1);
                            sx.push(x-1);   sy.push(y-1);
                        }
                        
                        count++;
                    }
                    
            System.out.println(count);
            System.out.flush();
        }
        System.exit(0);
    }
    
    public static void mark(int x, int y, boolean[][] grid, boolean[][] marked)
    {
        if(x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || marked[x][y] || !grid[x][y]) 
            return;
        
        marked[x][y] = true;
        
        mark(x+1,y,grid,marked);
        mark(x-1,y,grid,marked);
        mark(x,y+1,grid,marked);
        mark(x,y-1,grid,marked);
        mark(x+1,y+1,grid,marked);
        mark(x+1,y-1,grid,marked);
        mark(x-1,y+1,grid,marked);
        mark(x-1,y-1,grid,marked);
    }
}