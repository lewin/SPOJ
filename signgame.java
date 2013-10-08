import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class signgame
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
            arr = new int [N];
            for (int i = 0; i < N; i++)
                arr [i] = in.nextInt ();
            IntervalTree root = new IntervalTree (0, N - 1);
                
            int M = in.nextInt ();
            while (M-- > 0) {
                int c = in.nextInt (), a = in.nextInt (), b = in.nextInt ();
                if (c == 0) root.flip (a, b);
                else out.println (root.query (a, b).maxSum);
            }
        }
    }
    
    static class IntervalTree {
        private boolean lazy;
        public IntervalTree Lchild = null,
                            Rchild = null;
        public int leftMaxSum, rightMaxSum, maxSum, 
                   leftMinSum, rightMinSum, minSum, 
                   allSum, start, end;
        
        public IntervalTree () {}
        
        public IntervalTree (int start, int end) {
            this.start = start; this.end = end;
            lazy = false;
            if (start != end) {
                int mid = (start + end) >> 1;
                Lchild = new IntervalTree (start, mid);
                Rchild = new IntervalTree (mid + 1, end);
                join (this, Lchild, Rchild);
            }
            else leftMaxSum = rightMaxSum = maxSum = 
                 leftMinSum = rightMinSum = minSum = 
                 allSum = arr [start];
        }
        
        public IntervalTree query (int a, int b) {
            if (lazy) flip();
            if (a == start && end == b) return this;
            int mid = (start + end) >> 1;
            if (a > mid) return Rchild.query (a, b);
            if (b <= mid) return Lchild.query (a, b);
            IntervalTree ans = new IntervalTree ();
            join (ans, Lchild.query (a, mid), Rchild.query (mid + 1, b));
            return ans;
        }
        
        public void flip() {
            int temp = maxSum; maxSum = -minSum; minSum = -temp;
            temp = leftMaxSum; leftMaxSum = -leftMinSum; leftMinSum = -temp;
            temp = rightMaxSum; rightMaxSum = -rightMinSum; rightMinSum = -temp;
            allSum = -allSum;
            
            if (Rchild != null) {
                Rchild.lazy ^= true;
                Lchild.lazy ^= true;
            }
            lazy = false;
        }
        
        public void flip (int a, int b) {
            if (a == start && b == end) {
                lazy ^= true; return;
            }
            if (lazy) flip();
            int mid = (start + end) >> 1;
            if (a > mid) Rchild.flip (a, b);
            else if (b <= mid) Lchild.flip (a, b);
            else {Lchild.flip (a, mid); Rchild.flip (mid + 1, b);}
            join (this, Lchild, Rchild);
        }
        
        public void join (IntervalTree parent, IntervalTree Lchild, IntervalTree Rchild) {
            if (Lchild.lazy) Lchild.flip(); if (Rchild.lazy) Rchild.flip();
            parent.allSum = Lchild.allSum + Rchild.allSum;
            parent.leftMaxSum = max (Lchild.leftMaxSum, Lchild.allSum + Rchild.leftMaxSum);
            parent.rightMaxSum = max (Rchild.rightMaxSum, Rchild.allSum + Lchild.rightMaxSum);
            parent.maxSum = max (max (Lchild.maxSum, Rchild.maxSum), Lchild.rightMaxSum + Rchild.leftMaxSum);
            parent.leftMinSum = min (Lchild.leftMinSum, Lchild.allSum + Rchild.leftMinSum);
            parent.rightMinSum = min (Rchild.rightMinSum, Rchild.allSum + Lchild.rightMinSum);
            parent.minSum = min (min (Lchild.minSum, Rchild.minSum), Lchild.rightMinSum + Rchild.leftMinSum);
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
    }public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;
    }public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);
    }public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
    }public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;
    }public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;
    }private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;
    }private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];
    }public void close() throws IOException{if(din==null) return;din.close();}
}