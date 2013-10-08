import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class mtotalf
{
    private static BufferedReader f;
    public static int[][] grid;
    public static final int INF = 1000000000;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int n = parseInt(f.readLine()), i;
        grid = new int[52][52];
        StringTokenizer st;
        int a, b, w;
        for(i = 0; i < n; i++)
        {
            st = new StringTokenizer(f.readLine());
            a = st.nextToken().charAt(0) - 'A';
            b = st.nextToken().charAt(0) - 'A';
            if(a > 25) a = a + 'A' - 'a' + 26;
            if(b > 25) b = b + 'A' - 'a' + 26;
            w = parseInt(st.nextToken());
            grid[a][b] += w;
        }
        
        System.out.println(networkFlow(0,25,51));
        System.out.flush();
        System.exit(0);
    }
    
    private static int networkFlow(int source, int sink, int nodes)
    {
        if(source == sink) return INF;
        int maxFlow, maxLoc, pathCap, curNode, nextNode, i, j;
        int totalFlow = 0;
        int[][] dist = new int[nodes+1][nodes+1];
        for(i = 0; i <= nodes; i++) for(j = 0; j <= nodes; j++) dist[i][j] = grid[i][j];
        int[] prevNode = new int[nodes+1];
        int[] flow = new int[nodes+1];
        boolean[] visited = new boolean[nodes+1];
        
        outer : while(true)
        {
            for(i = 0; i <= nodes; i++)
            {
                prevNode[i] = -1;
                flow[i] = 0;
                visited[i] = false;
            }
            flow[source] = INF;
            
            while(true)
            {
                maxFlow = 0; maxLoc = -1;
                for(i = 0; i <= nodes; i++)
                    if(flow[i] > maxFlow && !visited[i])
                    {
                        maxFlow = flow[i];
                        maxLoc = i;
                    }
                
                if(maxLoc == -1) break outer;
                if(maxLoc == sink) break;
                
                visited[maxLoc] = true;
                
                for(i = 0; i <= nodes; i++)
                    if(!visited[i] && flow[i] < Math.min(maxFlow, dist[maxLoc][i]))
                    {
                        prevNode[i] = maxLoc;
                        flow[i] = Math.min(maxFlow, dist[maxLoc][i]);
                    }
            }
            
            pathCap = flow[sink];
            totalFlow += pathCap;
            
            curNode = sink;
            while(curNode != source)
            {
                nextNode = prevNode[curNode];
                dist[nextNode][curNode] -= pathCap;
                dist[curNode][nextNode] += pathCap;
                curNode = nextNode;
            }
        }
        
        return totalFlow;
    }
}