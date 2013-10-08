import java.io.*;
import java.util.*;

public class crscntry
{
    private static Reader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int d = in.nextInt(), n, ai, ti, max, i, j, temp;
        int[] a = new int[2000], t =  new int[2000], dp, prev;
        while(d-->0)
        {
            ai = max = 0;
            while(true)
            {
                a[ai++] = in.nextInt();
                if(a[ai-1] == 0) break;
            }ai--;
            while(true)
            {
                ti = 0;
                while(true)
                {
                    t[ti++] = in.nextInt();
                    if(t[ti-1] == 0) break;
                }ti--;
                if(ti == 0) break;
                dp = new int[ti];
                for(i = 0; i < ai; i++)
                {
                    prev = dp;
                    dp = new int[ti];
                    dp[0] = prev[0];
                    if(a[i] == t[0]) dp[0]++;
                    for(j = 1; j < ti; j++)
                    {
                        temp = 0;
                        if(a[i] == t[j]) temp = 1+prev[j-1];
                        
                        if(dp[j-1] > temp)    temp = dp[j-1];
                        if(prev[j-1] > temp)  temp = prev[j-1];
                        if(prev[j] > temp)    temp = prev[j];
                        
                        dp[j] = temp;
                    }
                }
                if(dp[ti-1] > max) max = dp[ti-1];
            }
            out.println(max);
        }
        System.exit(0);
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