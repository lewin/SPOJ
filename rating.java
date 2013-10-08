import java.io.*;
import java.util.*;

public class rating
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt ();
        tree = new int [100010];
        Pair [] p = new Pair [N];
        for (int i = 0; i < N; i++)
            p [i] = new Pair (in.nextInt(), in.nextInt(), i);
        Arrays.sort (p);
        
        int[] ans = new int [N];
        int sub = 0, len = 1;
        ans[p[0].idx] = 0; add(p[0].a);
        for (int i = 1; i < N; i++) {
            if (p[i - 1].a == p[i].a &&
                p[i - 1].b == p[i].b) {
                    sub += len++;
                }
            else {
                len = 1;
                sub = 0;
            }
            ans [p[i].idx] = query (p[i].a) - sub;
            add (p[i].a);
        }
        
        for (int i = 0; i < N; i++) {
            out.println (ans [i]);
        }
        
        out.close();
        System.exit(0);
    }
    
    private static int[] tree;
    private static void add (int n) {
        for (int i = n; i < tree.length; i += (i & -i))
            tree [i]++;
    }
    
    private static int query (int n) {
        int sum = 0;
        for (int i = n; i > 0; i -= (i & -i))
            sum += tree[i];
        return sum;
    }
    
    static class Pair implements Comparable <Pair> {
        public int a, b, idx;
        
        public Pair (int a, int b, int idx) {
            this.a = a;
            this.b = b;
            this.idx = idx;
        }
        
        public int compareTo (Pair other) {
            return b == other.b ? a - other.a : b - other.b;
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