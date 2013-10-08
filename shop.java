import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class shop
{
    private static BufferedReader f;
    public static int INF = 1000000000;
    public static final int[] dx = new int[] {-1,0,1,0};
    public static final int[] dy = new int[] {0,-1,0,1};
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st;
        
        int w, h, i, j, sx, sy, gx, gy;
        char[] line; int[][] grid;
        PriorityQueue<Position> pq;
        boolean[][] visited;
        Position dequeue;
        int x, y, cost, nx, ny;
        while(true)
        {
            st = new StringTokenizer(f.readLine());
            w = parseInt(st.nextToken());
            h = parseInt(st.nextToken());
            if(w == 0 && h == 0) break;
            grid = new int[w][h];
            sx = sy = gx = gy = 0;
            for(j = 0; j < h; j++)
            {
                line = f.readLine().toCharArray();
                for(i = 0; i < w; i++)
                {
                    if(line[i] == 'S')
                    {
                        sx = i;
                        sy = j;
                    }
                    else if(line[i] == 'D')
                    {
                        gx = i;
                        gy = j;
                    }
                    else if(line[i] == 'X')  grid[i][j] = INF;
                    else grid[i][j] = line[i] - '0'; 
                }
            }
            
            pq = new PriorityQueue<Position>();
            visited = new boolean[w][h];
            
            pq.add(new Position(sx,sy,0));
            cost = 0;
            while(pq.size() > 0)
            {
                dequeue = pq.poll();
                x = dequeue.x; y = dequeue.y;
                cost = dequeue.cost;
                
                if(x == gx && y == gy) break;
                if(visited[x][y]) continue;
                visited[x][y] = true;
                for(i = 0; i < 4; i++)
                {
                    nx = x+dx[i];
                    ny = y+dy[i];
                    if(nx < 0 || nx >= w || ny < 0 || ny >= h || visited[nx][ny] || grid[nx][ny] == INF) continue;
                    
                    pq.add(new Position(nx,ny,cost+grid[nx][ny]));
                }
            }
            
            System.out.println(cost);
            System.out.flush();
            f.readLine();
        }
        System.exit(0);
    }
    
    static class Position implements Comparable<Position>
    {
        public int x, y, cost;
        public Position(int x, int y, int cost)
        {
            this.x = x; this.y = y;
            this.cost = cost;
        }
        public int compareTo(Position other)
           {return cost - other.cost;}
    }
}