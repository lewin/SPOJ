import java.io.*;
import java.util.*;

public class gss5
{
    private static Reader in;
    private static PrintWriter out;
    private static int [] arr;
    
    
    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int T = in.nextInt ();
        while (T-- > 0) {
            int N = in.nextInt ();
            arr = new int [N + 1];
            for (int i = 1; i <= N; i++)
                arr [i] = arr [i - 1] + in.nextInt ();
            IntervalTree root = new IntervalTree (0, N);
            int M = in.nextInt ();
            for (int i = 0; i < M; i++) {
                int a = in.nextInt(), b = in.nextInt(), c = in.nextInt(), d = in.nextInt();
                out.println (root.maxQuery(c, d) - root.minQuery(a - 1, b - 1));
            }
        }
    }
    
    static class IntervalTree {
        public IntervalTree Lchild = null, Rchild = null;
        public int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE, start, end;
        
        public IntervalTree (int start, int end) {
            this.start = start;
            this.end = end;
            if (start != end) {
                int mid = (start + end) >> 1;
                Lchild = new IntervalTree (start, mid);
                Rchild = new IntervalTree (mid + 1, end);
                max = Math.max (Lchild.max, Rchild.max);
                min = Math.min (Lchild.min, Rchild.min);
            } else max = min = arr [start];
        }
        
        public int maxQuery (int a, int b) {
            if (a == start && b == end) return max;
            int mid = (start + end) >> 1;
            if (mid < a) return Rchild.maxQuery (a, b);
            else if (mid >= b) return Lchild.maxQuery (a, b);
            return Math.max (Lchild.maxQuery (a, mid), Rchild.maxQuery (mid + 1, b));
        }
        
        public int minQuery (int a, int b) {
            if (a == start && b == end) return min;
            int mid = (start + end) >> 1;
            if (mid < a) return Rchild.minQuery (a, b);
            else if (mid >= b) return Lchild.minQuery (a, b);
            return Math.min (Lchild.minQuery (a, mid), Rchild.minQuery (mid + 1, b));
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