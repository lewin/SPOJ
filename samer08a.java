import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class samer08a
{
    public static final int INF = 1000000000;
    public static int[][] fw, bk;
    
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        int n, m, s, d, i, j, a, b, w;
        int[] dist1, dist2;
        for(;;){
            n = in.nextInt();
            m = in.nextInt();
            if(m == 0 && n == 0) break;
            fw = new int[n][n];
            bk = new int[n][n];
            s = in.nextInt();
            d = in.nextInt();
            for(i = 0; i < m; i++){
                a = in.nextInt();
                b = in.nextInt();
                w = in.nextInt();
                fw[a][b] = w;
                bk[b][a] = w;
            }
            dist1 = new int[n];
            dist2 = new int[n];
            dijkstra(dist1, n, s, fw);
            dijkstra(dist2, n, d, bk);
            for(i = 0; i < n; i++)
                for(j = 0; j < n; j++)
                    if(dist1[i] + fw[i][j] + dist2[j] == dist1[d])
                        fw[i][j] = bk[j][i] = 0;  
            dijkstra(dist1, n, s, fw);
            System.out.println(dist1[d]);
            System.out.flush();
        }
        System.exit(0);
    }
    
    private static void dijkstra(int[] dist, int nodes, int start, int[][] grid)
    {
        Arrays.fill(dist, -1);
        boolean[] visited = new boolean[nodes+1];
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
        queue.add(new Edge(start,0));
        Edge dequeue; int node, weight, pre, i;
        while(queue.size() != 0)
        {
            dequeue = queue.poll();
            node = dequeue.to;
            weight = dequeue.weight;
            if(visited[node]) continue;
            visited[node] = true;
            dist[node] = weight;
            for(i = 0; i < nodes; i++)
                if(!visited[i] && grid[node][i] > 0)
                    queue.add(new Edge(i, grid[node][i] + weight));
        }
    }
    
    static class Edge implements Comparable<Edge>
    {
        public int to, weight, prev;
        
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

/** Faster input **/
class Reader
{
    final private int BUFFER_SIZE = 1 << 16;
    
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    
    public Reader()
    {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }
    
    public Reader(String file_name) throws IOException
    {
        din = new DataInputStream(new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }
    
    public String readLine() throws IOException
    {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while( (c=read()) != -1) {
            if(c == '\n') break;
            buf[cnt++] = (byte)c;
        }
        return new String(buf,0,cnt);
    }
    
    public int nextInt() throws IOException
    {
        int ret = 0;
        byte c = read();
        while (c <= ' ') c = read();
        boolean neg = c == '-';
        if (neg) c = read();
        do {
            ret = ret * 10 + c - '0';
            c = read();
        } while (c >= '0' && c <= '9');
        if (neg) return -ret;
        return ret;
    }
    
    public long nextLong() throws IOException
    {
        long ret = 0;
        byte c = read();
        while (c <= ' ') c = read();
        boolean neg = c == '-';
        if (neg) c = read();
        do {
            ret = ret * 10 + c - '0';
            c = read();
        } while (c >= '0' && c <= '9');
        if (neg) return -ret;
        return ret;
    }
    
    public double nextDouble() throws IOException
    {
        double ret = 0, div = 1;
        byte c = read();
        while(c <= ' ') c = read();
        boolean neg = c == '-';
        if(neg) c = read();
        do {
            ret = ret * 10 + c - '0';
            c = read();
        } while (c >= '0' && c <= '9');
        if(c == '.')
            while((c=read()) >= '0' && c <= '9') {
                div *= 10;
                ret = ret + (c - '0') / div;
            }
        if (neg) return -ret;
        return ret;
    }
    
    private void fillBuffer() throws IOException
    {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1) buffer[0] = -1;
    }
    
    private byte read() throws IOException
    {
        if (bufferPointer == bytesRead) fillBuffer();
        return buffer[bufferPointer++];
    }
    
    public void close() throws IOException
    {
        if(din == null) return;
        din.close();
    }
}