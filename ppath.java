import java.io.*;
import java.util.*;

public class ppath
{
    private static Reader in;
    private static PrintWriter out;
    private static boolean [] prime;

    public static void main (String [] args) throws IOException {
        System.gc ();
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        prime = new boolean [10000];
        Arrays.fill (prime, true);
        prime [0] = false; prime [1] = false;
        prime [2] = true;
        for (int i = 4; i <= 9999; i += 2)
            prime [i] = false;
        for (int i = 3; i <= 9999; i += 2) {
            if (prime [i])
                for (int j = 2 * i; j <= 9999; j += i)
                    prime [j] = false;
        }
        int [] f = new int [10000]; int m = 0;
        for (int i = 1001; i <= 9999; i++) 
            if (prime [i])
                f [i] = m++;
        int [][] path = new int [m][m];
        for (int i = 0; i < m; i++)
            Arrays.fill (path [i], 1 << 25);
        System.out.println (m);
        for (int i = 1001; i <= 9999; i += 2) {
            if (prime [i]) {
                int a = i / 1000, b = (i / 100) % 10, c = (i / 10) % 10, d = i % 10, e;
                for (int p = 0; p <= 9; p++) {
                    if (p != 0 && prime [e = (p * 1000 + b + c + d)])
                        path [f [e]][f [i]] = path [f [i]][f [e]] = 1;
                    if (prime [e = (a + p * 100 + c + d)])
                        path [f [e]][f [i]] = path [f [i]][f [e]] = 1;
                    if (prime [e = (a + b + p * 10 + d)])
                        path [f [e]][f [i]] = path [f [i]][f [e]] = 1;
                    if (prime [e = (a + b + c + p)])
                        path [f [e]][f [i]] = path [f [i]][f [e]] = 1;
                }
                path [f [i]][f [i]] = 0;
            }
        }
        for (int i = 0; i < m; i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < m; k++)
                    if (path [i][k] + path [k][j] < path [i][j])
                        path [i][j] = path [i][k] + path [k][j];
        int t = in.nextInt ();
        while (t-- > 0) {
            int prime1 = f [in.nextInt ()], prime2 = f [in.nextInt ()];
            if (path [prime1][prime2] == 1 << 25)
                out.println ("Impossible");
            else out.println (path [prime1][prime2]);
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