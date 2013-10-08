import java.io.*;
import java.util.*;

public class gcj101ab
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        for (int p = 1; p <= t; p++) {
            int d = in.nextInt (), i = in.nextInt (), 
                m = in.nextInt (), n = in.nextInt ();
            int [] array = new int [n + 1];
            for (int ii = 1; ii <= n; ii++) array [ii] = in.nextInt ();
            int [][] dp = new int [n + 1][256];
            // first is index, second is the last value
            int best = 0;
            for (int ii = 1; ii <= n; ii++) // index of array
                for (int j = 0; j < 256; j++) {// last value
                    best = dp [ii - 1][j] + d;
                    int change = Math.abs (j - array [ii]);
                    if (ii == 1) best = change;
                    if (m == 0) {
                        if (dp [ii - 1][j] + change < best)
                            best = dp [ii - 1][j] + change;
                    }
                    else
                    for (int k = 0; k < 256; k++) { // previous value
                        int insert = (Math.max (Math.abs (j - k) - 1, 0) / m) * i;
                        if (dp [ii - 1][k] + change + insert < best)
                            best = dp [ii - 1][k] + change + insert;
                    }
                    dp [ii][j] = best;
                }
            best = dp [n][0];
            for (int ii = 1; ii < 256; ii++)
                if (dp [n][ii] < best)
                    best = dp [n][ii];
            out.printf ("Case #%d: %d\n", p, best);
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close() throws IOException{if(din==null) return;din.close();}
}