import java.io.*;
import java.util.*;

public class blinnet
{
    private static Reader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int s = in.nextInt();
        int p, i, b, w, m, j, treeSize, treeCost, node, weight; 
        boolean[] visited;
        Edge dequeue;
        while(s-->0)
        {
            p = in.nextInt();
            ArrayList<Edge>[] grid = new ArrayList[p];
            for(i = 0; i < p; i++)
            {
                in.readLine();
                grid[i] = new ArrayList<Edge>();
                m = in.nextInt();
                for(j = 0; j < m; j++)
                {
                    b = in.nextInt()-1;
                    w = in.nextInt();
                    grid[i].add(new Edge(b,w));
                }
            }
            
            visited = new boolean[p];
            
            PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
            
            treeCost = 0; treeSize = 0;
            queue.add(new Edge(0, 0));
            
            while(treeSize < p)
            {
                dequeue = queue.poll();
                node = dequeue.to;
                weight = dequeue.weight;
                if(visited[node]) continue;
                
                treeSize++;
                treeCost += weight;
                visited[node] = true;
                
                for(Edge e : grid[node])
                    queue.add(new Edge(e.to, e.weight));
            }
            
            System.out.println(treeCost);
            System.out.flush();
        }
        System.exit(0);
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