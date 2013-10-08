import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class ftheelf
{
    private static Reader in;
    private static PrintWriter out;
    private static double v, h;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            v = in.nextDouble ();
            h = in.nextDouble ();
            if (v == -1 && h == -1) break;
            double g = 9.81;
            double lo = 0, hi = Math.PI / 2;
            while (lo < hi) {
                if (hi - lo <= .000000000001) {
                    out.printf ("%.12f %.12f\n", (hi + lo) / 2,  f ((hi + lo) / 2.0));
                    break;
                }
                double f1 = (2.0 * lo + hi) / 3.0;
                double f2 = (lo + 2.0 * hi) / 3.0;
                if (f (f1) < f (f2)) lo = f1;
                else hi = f2;
            }
        }
    }
    
    private static double f (double angle) {
        double s = sin (angle), c = cos (angle);
        return v * c / 9.8 * (v * s + sqrt (v * v * s * s + 2 * 9.8 * h));
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