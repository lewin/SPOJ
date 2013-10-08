import java.io.*;
import java.util.*;
import static java.lang.Math.max;

public class gss1
{
    private static Reader in;
    private static PrintWriter out;
    private static int [] arr, sum;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int n = in.nextInt (); arr = new int [n + 1]; sum = new int [n + 1];
        for (int i = 1; i <= n; i++) {
            arr [i] = in.nextInt ();
            sum [i] = arr [i] + sum [i - 1];
        }
        IntervalTree root = new IntervalTree (1, n);
        int t = in.nextInt ();
        while (t-- > 0) {
            int a = in.nextInt (), b = in.nextInt ();
            out.println (root.query (a, b).maxSum);
        }
    }
    
    static class IntervalTree {
        public IntervalTree Lchild = null,
                            Rchild = null;
        public int leftSum, rightSum, maxSum, allSum, start, end;
        
        public IntervalTree () {}
        
        public IntervalTree (int _start, int _end) {
            start = _start; end = _end;
            if (start != end) {
                int mid = (start + end) >> 1;
                Lchild = new IntervalTree (start, mid);
                Rchild = new IntervalTree (mid + 1, end);
                join (this, Lchild, Rchild);
            }
            else leftSum = rightSum = maxSum = allSum = arr [start];
        }
        
        public IntervalTree query (int a, int b) {
            if (a == start && end == b) return this;
            int mid = (start + end) >> 1;
            if (a > mid) return Rchild.query (a, b);
            if (b <= mid) return Lchild.query (a, b);
            IntervalTree ans = new IntervalTree ();
            join (ans, Lchild.query (a, mid), Rchild.query (mid + 1, b));
            return ans;
        }
        
        public void join (IntervalTree parent, IntervalTree Lchild, IntervalTree Rchild) {
            parent.allSum = Lchild.allSum + Rchild.allSum;
            parent.leftSum = max (Lchild.leftSum, Lchild.allSum + Rchild.leftSum);
            parent.rightSum = max (Rchild.rightSum, Rchild.allSum + Lchild.rightSum);
            parent.maxSum = max (max (Lchild.maxSum, Rchild.maxSum), Lchild.rightSum + Rchild.leftSum);
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