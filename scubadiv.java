import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class scubadiv
{
    private static Reader in;
    public static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        int t = in.nextInt(), o, n, p, o_i, n_i, w_i, i, j, k, max = 1659, min, po, pn;
        int[][] dp;
        while(t-->0) {
            dp = new int[max+1][max+1];
            o = in.nextInt();  n = in.nextInt(); p = in.nextInt();
            for(i = 0; i <= max; i++) Arrays.fill(dp[i], INF);
            min = INF; dp[0][0] = 0; po = pn = 0;
            for(i = 0; i < p; i++) {
                o_i = in.nextInt(); n_i = in.nextInt(); w_i = in.nextInt();
                po += o_i; pn += n_i;
                if(po > max) po = max;
                if(pn > max) pn = max;
                for(j = po; j >= o_i; j--)
                    for(k = pn; k >= n_i; k--)
                        if(dp[j][k] > dp[j-o_i][k-n_i] + w_i) {
                            dp[j][k] = dp[j-o_i][k-n_i] + w_i;
                            if(j >= o && k >= n && dp[j][k] < min)  min = dp[j][k];
                        }
            }
            
            System.out.println(min);
            System.out.flush();
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