import java.io.*;
import java.util.*;

public class horrible
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int T = in.nextInt ();
        while (T-- > 0) {
            int N = in.nextInt (), C = in.nextInt ();
            SegmentTree root = new SegmentTree (1, N);
            
            for (int i = 0; i < C; i++) {
                switch (in.nextInt ()) {
                    case 0 :
                        root.update (in.nextInt (), in.nextInt (), in.nextInt ());
                        break;
                    case 1 : 
                        out.println (root.query (in.nextInt (), in.nextInt ()));
                        break;
                }
            }
        }
    }
    
    static class SegmentTree {
        public SegmentTree lc = null, rc = null;
        public long sum, lazy;
        public int start, end;
        
        public SegmentTree (int start, int end) {
            this.start = start;
            this.end = end;
            if (start != end) {
                int mid = (start + end) >> 1;
                lc = new SegmentTree (start, mid);
                rc = new SegmentTree (mid + 1, end);
            }
        }
        
        public long range () {
            return end - start + 1;
        }
        
        public void push () {
            if (lc != null) {
                lc.sum += lc.range () * lazy;
                lc.lazy += lazy;
                rc.sum += rc.range () * lazy;
                rc.lazy += lazy;
                sum = lc.sum + rc.sum;
            }
            lazy = 0;
        }
        
        public void update (int b, int e, int val) {
            if (b > end || start > e) return;
            if (b == start && e == end) {
                sum += range () * val;
                lazy += val;
                return;
            }
            
            push ();
            
            int mid = (start + end) >> 1;
            if (mid >= e) lc.update (b, e, val);
            else if (mid < b) rc.update (b, e, val);
            else {
                lc.update (b, mid, val);
                rc.update (mid + 1, e, val);
            }
            sum = lc.sum + rc.sum;
        }
        
        public long query (int b, int e) {
            if (b > end || start > e) return 0;
            if (b == start && e == end) {
                return sum;
            }
            
            push ();
            
            int mid = (start + end) >> 1;
            if (mid >= e) return lc.query (b, e);
            else if (mid < b) return rc.query (b, e);
            else return lc.query (b, mid) + rc.query (mid + 1, e);
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