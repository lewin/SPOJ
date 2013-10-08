import java.io.*;
import java.util.*;

public class micemaze
{
    private static Reader in;
    private static PrintWriter out;
    public static final int INF = 1000000000;
    public static ArrayList<Edge>[] grid;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int n = in.nextInt(), e = in.nextInt()-1, t = in.nextInt(), m = in.nextInt(), i, a, b, w;
        grid = new ArrayList[n];
        for(i = 0; i < n; i++) grid[i] = new ArrayList<Edge>();
        for(i = 0; i < m; i++) {
            a = in.nextInt()-1;
            b = in.nextInt()-1;
            w = in.nextInt();
            grid[b].add(new Edge(a, w));
        }
        int[] dist = new int[n];
        out.println(dijkstra(dist, e, t));
        System.exit(0);
    }
    
    private static int dijkstra(int[] dist, int start, int goal)
    {
        Arrays.fill(dist, INF);
        boolean[] visited = new boolean[dist.length];
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
        queue.offer(new Edge(start,0));
        Edge dequeue; int node, weight, count = 0;
        while(queue.size() != 0)
        {
            dequeue = queue.poll();
            node = dequeue.to;
            weight = dequeue.weight;
            if(visited[node]) continue;
            visited[node] = true;
            dist[node] = weight;
            if(dist[node] > goal) break;
            count++;
            for(Edge e : grid[node])
                if(!visited[e.to])
                    queue.offer(new Edge(e.to, e.weight + weight));
        }
        return count;
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