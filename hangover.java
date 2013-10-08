import java.io.*;
import java.util.*;

public class hangover
{
    private static Reader in;
    private static PrintWriter out;
    private static final double EPS = 0.000000001;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int i, j;
        double[] hang = new double[300];
        int[] ans = new int[531];
        int lastStart = 0;
        Arrays.fill(ans,-1);
        for(i = 1; i < 300; i++)
            hang[i] = hang[i-1] + (1/(double)(i+1));
        j = 1;
        for(i = 0; i <= 520; i++)
        {
            ans[i] = j;
            while((int)(hang[j]*100) <= i) j++;
        }
        double n;
        while(true)
        {
            n = in.nextDouble()+EPS;
            if(n == EPS) break;
            out.printf("%d card(s)\n", ans[(int)(n*100)]);
        }
        System.exit(0);
    }
    
    public static int binarySearch(double key, double[] a)
    {
        int lo = 0, hi = a.length-1, mid = (lo+hi)>>1;
        while(lo < hi)
        {
            if(a[mid] > key) hi = mid - 1;
            else if(a[mid] == key) return mid;
            else lo = mid + 1;
            mid = (lo+hi)>>1;
        }
        if(a[lo] == key) lo--;
        return lo;
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