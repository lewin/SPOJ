import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

public class mtwalk
{
    private static BufferedReader f;
    private static int[][] grid;
    private static boolean[][] visited;
    public static final int[] dx = new int[] {-1,0,1,0};
    public static final int[] dy = new int[] {0,-1,0,1};
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(f.readLine());
        grid = new int[n+2][n+2];
        int i, j;
        StringTokenizer st;
        visited = new boolean[n+2][n+2];
        for(i = 1; i <= n; i++) {
            st = new StringTokenizer(f.readLine());
            visited[i][0] = visited[i][n+1] = visited[n+1][i] = visited[0][i] = true;
            for(j = 1; j <= n; j++) {
                grid[i][j] = parseInt(st.nextToken());  
                visited[i][j] = false;
            }
        }
        visited[0][0] = visited[0][n+1] = visited[n+1][0] = visited[n+1][n+1] = true;
        PriorityQueue<State> pq = new PriorityQueue<State>();
        pq.add(new State(1,1,grid[1][1], grid[1][1]));
        State dequeue = null;
        int x, y;
        
        while(pq.size() > 0) {
            dequeue = pq.poll();
            x = dequeue.x; y = dequeue.y;
            if(x == n && y == n) break;
            if(visited[x][y]) continue;
            visited[x][y] = true;
            for(i = 0; i < 4; i++)
                if(!visited[x+dx[i]][y+dy[i]])
                    pq.add(new State(x+dx[i], y+dy[i],
                        Math.min(dequeue.min, grid[x+dx[i]][y+dy[i]]), 
                        Math.max(dequeue.max, grid[x+dx[i]][y+dy[i]])));
        }
        
        System.out.println(dequeue.change);
        System.out.flush();
        System.exit(0);
    }
    
    static class State implements Comparable<State>
    {
        public int x, y, min, max, change;
        
        public State(int x, int y, int min, int max) {
            this.x = x; this.y = y; this.min = min;
            this.max = max; change = max - min;
        }
        
        public int compareTo(State other) {
            return change - other.change;
        }
    }
}