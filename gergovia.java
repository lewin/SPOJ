import java.io.*;
import java.util.*;

public class gergovia
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt ();
        long total = 0;
        int [] queuep = new int [N], queuen = new int [N];
        int [] idxp = new int [N], idxn = new int [N];
        
        int frontp = 0, backp = 0, frontn = 0, backn = 0;
        int c = in.nextInt ();
        if (c > 0) {
            queuep [backp] = c;
            idxp [backp++] = 0;
        } else {
            queuen [backn] = c;
            idxn [backn++] = 0;
        }
        
        for (int i = 1; i < N; i++) {
            c = in.nextInt ();
            if (c >= 0) {
                while (c > 0 && frontn < backn) {
                    int d = queuen [frontn], j = idxn [frontn];
                    if (c >= -d) {
                        frontn++;
                        c += d;
                        total += -d * (i - j);
                    } else {
                        queuen [frontn] += c;
                        total += c * (i - j);
                        c = 0;
                        break;
                    }
                }
                if (c > 0) {
                    queuep [backp] = c;
                    idxp [backp++] = i;
                }
            } else {
                while (c < 0 && frontp < backp) {
                    int d = queuep [frontp], j = idxp [frontp];
                    if (-c >= d) {
                        frontp++;
                        c += d;
                        total += d * (i - j);
                    } else {
                        queuep [frontp] += c;
                        total += -c * (i - j);
                        c = 0;
                        break;
                    }
                }
                if (c < 0) {
                    queuen [backn] = c;
                    idxn [backn++] = i;
                }
            }
        }
        out.println (total);
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