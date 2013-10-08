import java.io.*;
import java.util.*;

public class fibosum
{
    private static Reader in;
    private static PrintWriter out;
    public static final int mod = 1000000007;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int T = in.nextInt ();
        
        while (T-- > 0) {
            int N = in.nextInt (), M = in.nextInt ();
            long ans = fib (M + 2) - fib (N + 1);
            if (ans < 0) ans += mod;
            out.println (ans);
        }
    }
    
    private static long fib (int n) {
        if (n == 0 || n == 1) return 1;
        long [][] C = fast_exp (new long [][] {{1, 1}, {1, 0}}, n - 2);
        return (C [0][0] + C [0][1]) % mod;
    }
    
    private static long [][] fast_exp (long [][] A, int e) {
        long [][] res = new long [][] {{1, 0}, {0, 1}};
        while (e > 0) {
            if ((e & 1) == 1)
                res = matrix_mult (res, A);
            e >>= 1;
            A = matrix_mult (A, A);
        }
        return res;
    }
    
    private static long [][] matrix_mult (long [][] A, long [][] B) {
        int N = A.length;
        long [][] C = new long [N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                for (int k = 0; k < N; k++)
                    C [i][k] = (C [i][k] + A [i][j] * B [j][k]) % mod;
        return C;
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