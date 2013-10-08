import java.io.*;
import java.util.*;

public class clone
{
    private static Reader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int n, m, i, j; int[] arr, count; 
        for(;;) {
            n = in.nextInt();
            m = in.nextInt();
            if(n == 0 && m == 0) break;
            arr = new int[n]; count = new int[n+1];
            for(i = 0; i < n; i++) arr[i] = in.readDNA(m);
            Arrays.sort(arr);
            j = 0;
            for(i = 0; i < n; i++) {
                j = i+1;
                while(j < n && arr[j] == arr[i]) j++;
                count[j-i]++;
                i = j-1;
            }
            for(i = 1; i <= n; i++) out.println(count[i]);
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
    
    public int readDNA(int len) throws IOException 
    {
        int ret = 0;
        byte c = read();
        byte add = 0;
        while (c <= ' ') c = read();
        while(len-->0){
            switch(c)
            {
                case 'A' : {add = 0; break;}
                case 'C' : {add = 1; break;}
                case 'G' : {add = 2; break;}
                case 'T' : {add = 3; break;}
            }
            ret = ret * 4 + add;
            c = read();
        }
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