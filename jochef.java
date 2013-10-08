import java.io.*;
import java.util.*;
import static java.lang.Integer.*;

public class jochef
{
    public static int INF = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader in =  new BufferedReader (new InputStreamReader (System.in));
        int R, C, F, i, j, best, next, prev;
        int[][] rocks; char[] line;
        int[] height, left, right;
        while(true)
        {
            StringTokenizer st = new StringTokenizer (in.readLine ());
            R = parseInt (st.nextToken ());
            C = parseInt (st.nextToken ());
            if (R == 0 && C == 0) break;
            F = parseInt (st.nextToken ());
            
            height = new int[C+1];
            left = new int[C+1];
            right = new int[C+1];
            Arrays.fill (height, 0);
            Arrays.fill (left, INF);
            Arrays.fill (right, INF);
            
            best = -1;
            for (i = 0; i < R; i++) {
                line = in.readLine ().toCharArray ();
                next = prev = 0;
                while (next < C && line[next] == 'H') next++;
                for (j = 0; j < C; j++)
                {
                    if (j == next) {
                        height[j] = 0;
                        left[j] = right[j] = INF; next++;
                        while (next < C && line[next] == 'H') next++;
                        prev = 0;
                    }
                    else {
                        height[j]++; prev++;
                        if (left[j] > prev) left[j] = prev;
                        if (right[j] > next-j) right[j] = next-j;
                    }
                    if (best < height[j] * (left[j]+right[j]-1))
                        best = height[j] * (left[j]+right[j]-1);
                }
            }
            
            System.out.println ((long)(best)*(long)(F));
            System.out.flush ();
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