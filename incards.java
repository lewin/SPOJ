import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class incards
{
    private static BufferedReader f;
    public static final int INF = 1000000000;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        StringTokenizer st; 
        int P, Q, i, j, a, b, w;
        ArrayList<Edge>[] fw;
        ArrayList<Edge>[] bk;
        while(T-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            P = parseInt(st.nextToken());
            Q = parseInt(st.nextToken());
            
            fw = new ArrayList[P+1];
            bk = new ArrayList[P+1];
            for(i = 1; i <= P; i++) 
            {
                fw[i] = new ArrayList<Edge>();
                bk[i] = new ArrayList<Edge>();
            }
            
            for(i = 0; i < Q; i++)
            {
                st = new StringTokenizer(f.readLine());
                a = parseInt(st.nextToken());
                b = parseInt(st.nextToken());
                w = parseInt(st.nextToken());
                fw[a].add(new Edge(b, w));
                bk[b].add(new Edge(a, w));
            }
            
            int sum = dijkstra(P,1,fw) + dijkstra(P,1,bk);
            
            System.out.println(sum);
            System.out.flush();
        }
        System.exit(0);
    }
    
    private static int dijkstra(int N, int start, ArrayList<Edge>[] grid)
    {
        int[] dist = new int[N+1];
        boolean[] visited = new boolean[N+1];
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
        queue.add(new Edge(start,0));
        Edge dequeue; int node, weight, cost = 0;
        while(queue.size() != 0)
        {
            dequeue = queue.poll();
            node = dequeue.to;
            weight = dequeue.weight;
            if(visited[node]) continue;
            visited[node] = true;
            dist[node] = weight;
            cost += weight;
            for(Edge e : grid[node])
                if(!visited[e.to])
                    queue.add(new Edge(e.to, e.weight + weight));
        }
        return cost;
    }
    
    static class Edge implements Comparable<Edge>
    {
        public int to, weight;
        
        public Edge(int to, int weight)
        {
            this.to = to;
            this.weight = weight;
        }
        
        public int compareTo(Edge e)
        {
            return weight - e.weight;
        }
    }
}