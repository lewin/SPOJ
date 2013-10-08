import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class bytese1
{
    private static BufferedReader f;
    public static final int[] dx = new int[] {-1,0,1,0};
    public static final int[] dy = new int[] {0,-1,0,1};
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int K = parseInt(f.readLine());
        int t, hx, hy, x, y, i, j, cost, nx, ny, ax, ay;
        int[][] grid; PriorityQueue<Position> pq;
        StringTokenizer st; Position dequeue;
        boolean[][] visited;
        while(K-->0)
        {
            st = new StringTokenizer(f.readLine());
            x = parseInt(st.nextToken());
            y = parseInt(st.nextToken());
            grid = new int[x][y];
            for(i = 0; i < x; i++)
            {
                st = new StringTokenizer(f.readLine());
                for(j = 0; j < y; j++)
                    grid[i][j] = parseInt(st.nextToken());
            }
            st = new StringTokenizer(f.readLine());
            hx = parseInt(st.nextToken())-1; hy = parseInt(st.nextToken())-1;
            t = parseInt(st.nextToken());
            pq = new PriorityQueue<Position>();
            visited = new boolean[x][y];
            
            pq.add(new Position(0,0,grid[0][0]));
            cost = 0;
            while(pq.size() > 0)
            {
                dequeue = pq.poll();
                ax = dequeue.x; ay = dequeue.y;
                cost = dequeue.cost;
                
                if(ax == hx && ay == hy) break;
                if(visited[ax][ay]) continue;
                if(cost > t) break;
                visited[ax][ay] = true;
                
                for(i = 0; i < 4; i++)
                {
                    nx = ax+dx[i];
                    ny = ay+dy[i];
                    if(nx < 0 || nx >= x || ny < 0 || ny >= y || visited[nx][ny])continue;
                    
                    pq.add(new Position(nx,ny,cost+grid[nx][ny]));
                }
            }
            
            if(cost <= t)
            {
                System.out.println("YES"); 
                System.out.println(t-cost);
            }
            else  System.out.println("NO");
            System.out.flush();
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