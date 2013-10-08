import java.io.*;
import java.util.*;

public class zuma
{
    private static Reader in;
    private static PrintWriter out;
    public static final int INF = 1 << 25;
    private static int n, k;
    private static int [] arr, memo [][];
    
    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        n = in.nextInt (); k = in.nextInt ();
        arr = new int [n];
        for (int i = 0; i < n; i++)
            arr [i] = in.nextInt ();
        memo = new int [n][n][k];
        out.println (recurse (0, n - 1, 0));
    }
    
    private static int recurse (int left, int right, int consec) {
        if (left > right) return 0;
        if (left == right) return k - 1 - consec;
        if (memo [left][right][consec] != 0) return memo [left][right][consec];
        int res = INF;
        if (consec == k - 1) res = recurse (left + 1, right, 0);
        else res = 1 + recurse (left, right, consec + 1);
        for (int i = left + 1; i <= right; i++) {
            if (arr [left] != arr [i]) continue;
            int test = recurse (left + 1, i - 1, 0) +
                       recurse (i, right, Math.min (k - 1, consec + 1));
            if (test < res) res = test;
        }
        
        return memo [left][right][consec] = res;
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