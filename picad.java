import java.io.*;
import java.util.*;

public class picad
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        for (int test = 0; test < 10; test++) {
            long p = in.nextLong (), k = in.nextLong (), m = in.nextLong ();
            Pair<Long>[] time = new Pair[(int)m];
            for (int i = 0; i < m; i++) {
                long a = in.nextLong (), b = in.nextLong ();
                time [i] = new Pair (a, b);
            }
            Arrays.sort (time);
            int max = 0, min = Integer.MAX_VALUE, size;
            PriorityQueue<Long> pq = new PriorityQueue<Long>();
            for (int i = 0; i < m; i++) {
                if (time [i].first > k) break;
                if (time [i].second < p) continue;
                
                pq.add (time [i].second);
                while (pq.size () > 0 && pq.peek () < time [i].first) pq.poll ();
                size = pq.size();
                if (size > max) max = size;
                if (time [i].first > p) size--;
                if (size < min) min = size;   
            }
            while (pq.size () > 0 && pq.peek () < k) pq.poll ();
            size = pq.size ();
            if (size < min) min = size;
            if (size > max) max = size;
            
            out.printf ("%d %d\n", min, max);
        }
    }
    
    static class Pair <T extends Number> implements Comparable <Pair <T>> {
        public T first, second;
        
        public Pair (T _first, T _second) {
            first = _first;
            second = _second;
        }
        
        public int compareTo (Pair other) {
            double a = first.doubleValue();
            double b = other.first.doubleValue();
            if (a > b) return 1;
            if (a < b) return -1;
            return 0;
        }   
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    public Reader(){
        din=new DataInputStream(System.in);
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public Reader(String file_name) throws IOException{
        din=new DataInputStream(new FileInputStream(file_name));
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public String readLine() throws IOException{
        byte[] buf=new byte[64]; // line length
        int cnt=0,c;
        while((c=read())!=-1){
            if(c=='\n')break;
            buf[cnt++]=(byte)c;
        }
        return new String(buf,0,cnt);
    }

    public int nextInt() throws IOException{
        int ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    } 

    public long nextLong() throws IOException{
        long ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    }

    public double nextDouble() throws IOException{
        double ret=0,div=1;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c = read();
        do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')
            ret+=(c-'0')/(div*=10);
        if(neg)return -ret;
        return ret;
    }
    
    private void fillBuffer() throws IOException{
        bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
        if(bytesRead==-1)buffer[0]=-1;
    }
    
    private byte read() throws IOException{
        if(bufferPointer==bytesRead)fillBuffer();
        return buffer[bufferPointer++];
    }
    
    public void close() throws IOException{
        if(din==null) return;
        din.close();
    }
}