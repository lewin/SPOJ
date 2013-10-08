import java.io.*;
import java.util.*;

public class kgss
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int N = in.nextInt ();
        IntervalTree a = new IntervalTree(1, N);
        for (int i = 1; i <= N; i++)
            a.update (i, in.nextInt ());
        int Q = in.nextInt (), s, e;
        for (int i = 0; i < Q; i++) {
            char command = in.nextChar();
            switch (command) {
                case 'Q' : {
                    s = in.nextInt ();
                    e = in.nextInt ();
                    out.println(a.maxSum(s,e));
                    break;
                } case 'U' : {
                    s = in.nextInt ();
                    e = in.nextInt ();
                    a.update (s, e);
                    break;
                }
            }
        }
    }
    
    static class IntervalTree {
        public int max, max2, start, end;
        public IntervalTree child1 = null, child2 = null;
        
        public IntervalTree(int start, int end) {
            this.start = start; this.end = end;
            if(start != end) {
                child1 = new IntervalTree(start, (start+end)/2);
                child2 = new IntervalTree((start+end)/2+1, end);
            }
        }
        
        public int [] maxQuery(int a, int b) {
            if (a > b) return new int [] {-Integer.MAX_VALUE, -Integer.MAX_VALUE};
            if(a <= start && end <= b) return new int [] {max, max2};
            if(start > b || a > end) return new int [] {-Integer.MAX_VALUE, -Integer.MAX_VALUE};
            
            int [] c1 = child1.maxQuery (a, b);
            int [] c2 = child2.maxQuery (a, b);
            
            int [] ret = new int [] { c1 [0], c2 [0], c1 [1], c2 [1] };
            Arrays.sort (ret);
            
            return new int [] { ret [3], ret [2] };
        }
        
        public int maxSum(int a, int b) { 
            int [] res = maxQuery (a, b);
            return res [0] + res [1];
        }
        
        public void update(int a, int val) {
            if(a == start && a == end) {
                max = val;
                max2 = 0;
                return;
            }
            if(start > a || a > end) return;
            if(child1 == null) return;
            int mid = (start + end) >> 1;
            if (a > mid) child2.update(a,val);
            else         child1.update(a,val);
            max = Math.max (child1.max, child2.max);
            max2 = Math.max (Math.min (child1.max, child2.max),
                             Math.max (child1.max2, child2.max2));
            if (max2 > max) {max^=max2;max2^=max;max^=max2;}
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
    
    public char nextChar() throws IOException{
        byte c=read();
        while(c<=' ')c=read();
        return (char)c;
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