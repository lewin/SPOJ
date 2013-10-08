import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class cattleb
{
    private static Reader in;
    private static PrintWriter out;
    public static final int INF = 1000000000;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int N = in.nextInt (), R = in.nextInt (), BX = in.nextInt (),
            BY = in.nextInt (), BVX = in.nextInt (), BVY = in.nextInt (), i,
            T = 0, x, y, vx, vy;;
        Pair[] tint = new Pair [N];
        double close, diff, s, d;
        for (i = 0; i < N; i++) {
            x = in.nextInt () - BX; y = in.nextInt () - BY;
            vx = in.nextInt () - BVX; vy = in.nextInt () - BVY;
            if (vx == 0 && vy == 0) {
                if (sdist (x, y) <= R * R)
                    tint [T++] = new Pair (0, INF);
            }
            else {
                close = -(x * vx + y * vy) / dist (vx, vy);
                s = sdist (x, y); d = dist (vx, vy);
                if (R * R >= s - close * close) {
                    diff = sqrt (R * R - s + close * close);
                    tint [T++] = new Pair (max (close - diff, 0.0) / d, (close + diff) / d);
                }
            }
        }
        Arrays.sort (tint, 0, T);
        PriorityQueue<Double> pq = new PriorityQueue<Double>();
        int best = 0;
        for (i = 0; i < T; i++) {
            pq.add (tint [i].second);
            while (pq.size() > 0 && pq.peek() < tint [i].first) pq.poll();
            best = max (best, pq.size());
        }
        out.println (best);
    }
    
    private static double sdist (double dx, double dy) {
        return dx * dx + dy * dy;
    }
    
    private static double dist (double dx, double dy) {
        return sqrt (dx * dx + dy * dy);
    }
    
    static class Pair implements Comparable <Pair> {
        public double first, second;
        public Pair (double _first, double _second) {
            first = _first;
            second = _second;
        }
        public int compareTo (Pair other) {
            double diff = first - other.first;
            if (diff < 0) return -1;
            if (diff > 0) return  1;
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