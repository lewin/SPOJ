import java.io.*;
import java.util.*;

public class mblast
{
    private static Reader in;
    private static PrintWriter out;
    private static int k;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        String a = in.readLine (), b = in.readLine ();
        k = in.nextInt ();
        out.println (match (a, b));
    }
    
    public static final int MATCH = 1;
    public static final int INSERT = 2;
    public static final int DELETE = 3;
    public static final int REPLACE = 4;
    
    private static int match(String x, String y)
    {
        if(x == null || x.length() == 0 || y == null || y.length() == 0) return 0;
        
        char[] a = x.toCharArray();
        char[] b = y.toCharArray();        
        
        int n = a.length;
        int m = b.length;        
        
        int[] prev = new int[m+1];
        int[] dp = new int[m+1];
        int i, j;
        
        prev[0] = 0;
        
        for(i = 1; i <= m; i++)
            prev[i] = prev[i-1] + k;
        
        for(i = 1; i <= n; i++) {
            prev [0] = (i - 1) * k;
            dp [0] = i * k;
            for(j = 1; j <= m; j++) {
                if(a[i-1] == b[j-1])  {
                    dp[j] = prev[j-1]; 
                } else {
                    dp[j] = prev[j-1] + replace(a[i-1],b[j-1]); 
                }
                if(prev[j] + k < dp[j]) {
                    dp[j] = prev[j] + k; 
                }
                if(dp[j-1] + k < dp[j]) {
                    dp[j] = dp[j-1] + k; 
                }
            }
            System.arraycopy (dp, 0, prev, 0, m+1);
            dp = new int[m+1];
        }
        
        return prev[m];
    }
    
    private static int replace(char a, char b) {
        return Math.abs (a - b);
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine()throws IOException{byte[] buf=new byte[2048];int cnt=0,c;
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