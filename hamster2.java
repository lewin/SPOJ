import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class hamster2
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            double vo = in.nextInt (); int m = in.nextInt (); vo *= vo;
            Pair[] cross = new Pair [2*m]; int T = 0;
            for (int i = 0; i < m; i++) {
                double x = in.nextInt (), y1 = in.nextInt (), y2 = in.nextInt (), p;
                double [] theta = new double [4]; boolean flagL = false, flagU = false;
                if ((p = vo * vo - 10.0 * (10.0 * x * x + 2 * y1 * vo)) >= 0) {
                    flagL = true;
                    theta [0] = atan ((vo - sqrt (p)) / (10.0 * x));
                    theta [1] = atan ((vo + sqrt (p)) / (10.0 * x));
                }
                if (y1 != y2 && (p = vo * vo - 10.0 * (10.0 * x * x + 2 * y2 * vo)) >= 0) {
                    flagU = true;
                    theta [2] = atan ((vo - sqrt (p)) / (10.0 * x));
                    theta [3] = atan ((vo + sqrt (p)) / (10.0 * x));
                }
                if (flagL && flagU) {
                    cross [T++] = new Pair (theta [0], theta [2]);
                    cross [T++] = new Pair (theta [3], theta [1]);
                }
                else if (flagL) {
                    if (y1 == y2) {
                        cross [T++] = new Pair (theta [0], theta [0]);
                        cross [T++] = new Pair (theta [1], theta [1]);
                    } else {
                        cross [T++] = new Pair (theta [0], theta [1]);
                    }
                }
            }
            Arrays.sort (cross, 0, T);
            PriorityQueue<Double> pq = new PriorityQueue<Double>();
            int max = 0;
            for (int i = 0; i < T; i++) {
                pq.add (cross [i].second);
                while (pq.size() > 0 && pq.peek() < cross [i].first) pq.poll();
                max = max (max, pq.size());
            }
            out.println (max);
        }
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