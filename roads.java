import java.io.*;
import java.util.*;

public class roads
{
    private static Reader in;
    private static PrintWriter out;
    private static ArrayList<Edge>[] grid;
        
    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int t =  in.nextInt(), k, n, r, s, d, w, c, i;
        while(t-->0) {
            k = in.nextInt();
            n = in.nextInt();
            r = in.nextInt();
            grid = new ArrayList[n];
            for(i = 0; i < n; i++) grid[i] = new ArrayList<Edge>();
            for(i = 0; i < r; i++) {
                s = in.nextInt()-1;
                d = in.nextInt()-1;
                w = in.nextInt();
                c = in.nextInt();
                grid[s].add(new Edge(d,w,c));
            }
            
            out.println(dijkstraToll(0,k));
        }
    }
    
    private static int dijkstraToll(int start, int lim) {
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        pq.add(new Edge(start,0,0));
        Edge dequeue;
        int[] dist = new int[grid.length], cost = new int[dist.length];
        Arrays.fill(dist, -1);
        int node, toll, weight;
        while(pq.size() > 0) {
            dequeue = pq.poll();
            node = dequeue.to;
            weight = dequeue.weight;
            toll = dequeue.toll;
            if(dist[node] != -1 && dist[node] < weight) continue;
            dist[node] = weight;
            cost[node] = toll;
            for(Edge e : grid[node]) 
                if(toll + e.toll <= lim)
                    pq.add(new Edge(e.to,weight+e.weight,toll+e.toll));
        }
        return dist[dist.length-1];
    }
    
    static class Edge implements Comparable<Edge>
    {
        public int to, weight, toll;
        
        public Edge(int to, int weight, int toll)
        {
            this.to = to;
            this.weight = weight;
            this.toll = toll;
        }
        
        public int compareTo(Edge e)
        {
            if(toll == e.toll)
                return weight - e.weight;
            return toll - e.toll;
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