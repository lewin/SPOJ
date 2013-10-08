import java.io.*;
import java.util.*;

public class barn
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt (), M = in.nextInt ();
        
        SegmentTree root = new SegmentTree (0, N - 1);
        for (int i = 0; i < N; i++)
            root.insert (i, i, in.nextInt ());
            
        Pair [] req = new Pair [M];
        for (int i = 0; i < M; i++)
            req [i] = new Pair (in.nextInt () - 1, in.nextInt () - 1);
        Arrays.sort (req);
        
        int count = 0;
        for (int i = 0; i < M; i++) {
            if (root.query (req [i].a, req [i].b) > 0) {
                root.insert (req [i].a, req [i].b, -1);
                count++;
            }
        }
        
        out.println (count);
        out.close ();
        System.exit (0);
    }
    
    static class Pair implements Comparable <Pair> {
        public int a, b;
        
        public Pair (int a, int b) {
            this.a = a; this.b = b;
        }
        
        public int compareTo (Pair other) {
            return b == other.b ? other.a - a : b - other.b;
        }
    }
    
    static class SegmentTree {
        public SegmentTree lc = null, rc = null;
        public int lazy, min, start, end;
        
        public SegmentTree (int start, int end) {
            this.start = start;
            this.end = end;
            if (end != start) {
                int mid = (start + end) >> 1;
                lc = new SegmentTree (start, mid);
                rc = new SegmentTree (mid + 1, end);
            }
        }
        
        public void modify (int val) {
            lazy += val;
            min += val;
        }
        
        public void push () {
            if (lc != null) {
                lc.modify (lazy);
                rc.modify (lazy);
                min = Math.min (lc.min, rc.min);
            }
            lazy = 0;
        }
        
        public void insert (int s, int e, int val) {
            if (s > end || start > e) return;
            if (start == s && end == e) {
                modify (val);
                return;
            }
            push ();
            int mid = (start + end) >> 1;
            if (e <= mid)
                lc.insert (s, e, val);
            else if (s >= mid + 1)
                rc.insert (s, e, val);
            else {
                lc.insert (s, mid, val);
                rc.insert (mid + 1, e, val);
            }
            if (lc != null) min = Math.min (lc.min, rc.min);
        }
        
        public int query (int s, int e) {
            if (start == s && end == e) return min;
            push ();
            int mid = (start + end) >> 1;
            if (e <= mid)
                return lc.query (s, e);
            else if (s >= mid + 1)
                return rc.query (s, e);
            else
                return Math.min (lc.query (s, mid), rc.query (mid + 1, e));
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine()throws IOException{byte[] buf=new byte[1024];int cnt=0,c;
        while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt()throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong()throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble()throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer()throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read()throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close()throws IOException{if(din==null) return;din.close();}
}