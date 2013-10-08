import java.io.*;
import java.util.*;

public class matsum
{
    private static Reader in;
    private static PrintWriter out;
    public static int[][] tree;
    public static int max_x, max_y;
    
    
    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int t = in.nextInt();
        int n; String command;
        StringTokenizer st; int x1, y1, x2, y2, val;
        while(t-->0)
        {
            n = in.nextInt();
            tree = new int[n+1][n+1];
            max_x = max_y = n;
            while(true)
            {
                command = in.nextWord ();
                if(command.equals("END")) break;
                if(command.equals("SET"))
                {
                    x1 = in.nextInt () + 1;
                    y1 = in.nextInt () + 1;
                    val = in.nextInt () - readSingle (x1, y1);
                    update(x1,y1,val);
                } else {
                    x1 = in.nextInt () + 1;
                    y1 = in.nextInt () + 1;
                    x2 = in.nextInt () + 1;
                    y2 = in.nextInt () + 1;
                    val = readRange(x1,y1,x2,y2);
                    out.println(val);
                }
            }
            out.println();
        }
        System.exit(0);
    }
    
    public static int readRange(int x1, int y1, int x2, int y2) {
        return read(x2,y2)-read(x1-1,y2)-read(x2,y1-1)+read(x1-1,y1-1);
    }
    
    public static int readSingle (int x, int y) {
        int sum = tree [x][y];
        if (x > 0) {
            int z = x - (x & -x);
            x--;
            while (x != z) {
                int t = y;
                if (t > 0) {
                    int q = t - (t & -t);
                    t--;
                    while (t != q) {
                        sum -= tree [x][t];
                        t -= (t & -t);
                    }
                }
                x -= (x & -x);
            }
        }
        return sum;
    }
    
    public static int read(int x, int y)
    {
        int sum = 0, y1;
        while(x > 0) {
            y1 = y;
            while(y1 > 0) {
                sum += tree[x][y1];
                y1 -= (y1 & -y1);
            }
            x -= (x & -x);
        }
        return sum;
    }
    
    public static void update(int x, int y, int val){
        int y1;
        while(x <= max_x) {
            y1 = y;
            while(y1 <= max_y) {
                tree[x][y1] += val;
                y1 += (y1 & -y1);
            }
            x += (x & -x);
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
    
    public String nextWord () throws IOException {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while( (c=read()) != -1) {
            if(c == ' ' || c == '\n') break;
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