import java.io.*;
import java.util.*;

public class aggrcow
{
    private static Reader in;
    private static PrintWriter out;
    private static int[] x;
    private static int c, n;
    
    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int t = in.nextInt();
        int i, lo, hi, mid;
        while(t-->0)
        {
            n = in.nextInt();
            c = in.nextInt();
            x = new int[n];
            for(i = 0; i < n; i++)
                x[i] = in.nextInt();
            Arrays.sort(x);
            lo = 0; hi = x[n-1]-x[0];
            while(lo < hi)
            {
                mid = (lo+hi)>>1;
                if(lo == mid) break;
                if(can(mid)) lo = mid;
                else         hi = mid-1;
            }
            
            if(can(hi)) lo = hi;
            out.println(lo);
        }
    }
    
    private static boolean can(int space)
    {
        int prev = x[0];
        int tc = c, next;
        while(0<--tc)
        {
            next = binarySearch(prev+space);
            if(x[next] < prev+space) next++;
            if(next >= n) return false;
            prev = x[next];
        }
        return true;
    }
    
    private static int binarySearch(int key)
    {
        int lo = 0, hi = x.length-1, mid;
        while(lo < hi)
        {
            mid = (lo+hi)>>1;
            if(x[mid] > key)       hi = mid - 1;
            else if(x[mid] == key) return mid;
            else                   lo = mid + 1;
        }
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