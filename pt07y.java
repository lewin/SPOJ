import java.io.*;
import java.util.*;

public class pt07y
{
    private static Reader in;
    private static PrintWriter out;
    private static ArrayList<Integer>[] grid;
    private static boolean visited[];
    private static int count;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int n = in.nextInt(), m = in.nextInt(), i, a, b;
        grid = new ArrayList[n];
        for(i = 0; i < n; i++) grid[i] = new ArrayList<Integer>();
        for(i = 0; i < m; i++)
        {
            a = in.nextInt()-1; b = in.nextInt()-1;
            grid[a].add(b);
            grid[b].add(a);
        }
        visited = new boolean[n];
        count = 0;
        if(dfs(0,-1) && count == n) out.println("YES");
        else out.println("NO");
        System.exit(0);
    }
    
    public static boolean dfs(int node, int prev)
    {
        if(visited[node]) return false;
        count++;
        visited[node] = true;
        for(int i : grid[node])
        {
            if(prev == i) continue;
            
            if(!dfs(i, node)) return false;
        }
        return true;
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
            buf[cnt++] = (byte)c;
            if(c == '\n') break;
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