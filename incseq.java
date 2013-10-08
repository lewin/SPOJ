import java.io.*;
import java.util.*;

public class incseq
{
    private static Reader in;
    private static PrintWriter out;
    private static int mod = 5000000;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt(), K = in.nextInt();
        tree = new int [N + 2][K + 1];
        update (1, 0, 1);
        PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
        for (int i = 2; i <= N + 1; i++)
            pq.add (new Pair (i, in.nextInt()));
            
        while (pq.size() > 0) {
            int c = pq.poll().a;
            for (int i = K - 1; i >= 0; i--)
                update (c, i + 1, read (c, i));
        }
        
        out.println (read (N + 1, K));
        out.close ();
        System.exit (0);
    }
    
    private static int [][] tree;
    private static int read (int n, int k) {
        int sum = 0;
        for (int i = n; i > 0; i -= (i & -i)) {
            sum += tree[i][k];
            if (sum >= mod) sum -= mod;            
        }
        return sum;
    }
    
    private static void update (int n, int k, int val) {
        if (val == 0) return;
        for (int i = n; i < tree.length; i += (i & -i)) {
            tree[i][k] += val;
            if (tree[i][k] >= mod) tree[i][k] -= mod;
        }
    }
    
    static class Pair implements Comparable <Pair> {
        public int a, b;
        public Pair (int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        public int compareTo (Pair other) {
            return b == other.b ? other.a - a : b - other.b;
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