import java.io.*;
import java.util.*;

public class rent
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            int n = in.nextInt ();
            Order [] orders = new Order [n];
            for (int i = 0; i < n; i++) {
                int a = in.nextInt (), b = in.nextInt (), c = in.nextInt ();
                orders [i] = new Order (a, a + b, c);
            }
            Arrays.sort (orders);
            IntervalTree root = new IntervalTree (0, n - 1);
            int [] dp = new int [n];
            for (int i = 0; i < n; i++) {
                int lo = 0, hi = i - 1, mid = (lo + hi) >> 1;
                while (lo < hi) {
                    if (orders [mid].end > orders [i].start) hi = mid - 1;
                    else lo = mid;
                    mid = (lo + hi) >> 1;
                    if (mid == lo) break;
                }
                dp [i] = root.maxQuery (0, lo) + orders [i].profit;
                root.update (i, dp [i]);
            }
            
            out.println (root.maxQuery (0, n - 1));
        }
    }
    
    static class IntervalTree {
        public int max, start, end;
        public IntervalTree child1 = null, child2 = null;
        
        public IntervalTree(int start, int end) {
            this.start = start;
            this.end = end;
            if(start != end) {
                child1 = new IntervalTree(start, (start+end)/2);
                child2 = new IntervalTree((start+end)/2+1, end);
            }
            max = 0;
        }
        
        public int maxQuery(int a, int b) {
            if(a <= start && end <= b) return max;
            if(start > b || a > end) return -Integer.MAX_VALUE;
            if(child1 == null) return max;
            
            return Math.max (child1.maxQuery (a, b), child2.maxQuery (a, b));
        }
        
        public void update(int idx, int val) {
            if (start == end) {
                max = val;
                return;
            }
            int mid = (start + end) >> 1;
            if (idx > mid) child2.update (idx, val);
            else           child1.update (idx, val);
            max = Math.max (child1.max, child2.max);
        }
    }
    
    static class Order implements Comparable <Order> {
        public int start, end, profit;
        public Order (int _start, int _end, int _profit) {
            start = _start; end = _end; profit = _profit;
        }
        public int compareTo (Order o) {
            return end - o.end;
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close() throws IOException{if(din==null) return;din.close();}
}