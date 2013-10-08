import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class hamster1
{
    private static Reader in;
    private static PrintWriter out;
    private static double angle, score;
    private static double vo, k1, k2;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-->0) {
            vo = in.nextInt ();
            k1 = in.nextInt ();
            k2 = in.nextInt ();
            ternary_search ();
            out.printf ("%.3f %.3f\n", angle, score);
        }
    }
    
    private static void ternary_search () {
        double f1, f2, lo = 0, hi = PI / 2;
        while (lo < hi) {
            if (hi - lo <= .001) {
                angle = (lo + hi) / 2.0;
                score = f (angle);
                return;
            }
            
            f1 = (2.0 * lo + hi) / 3.0;
            f2 = (lo + 2.0 * hi) / 3.0;
            if ( f (f1) < f (f2)) lo = f1;
            else                  hi = f2;
        }
    }
    
    private static double f (double angle) {
        return horizontal (angle) * k1 + vertical (angle) * k2;
    }
    
    private static double horizontal (double angle) {
        return vo * vo * sin (2.0 * angle) / 10.0;
    }
    
    private static double vertical (double angle) {
        double vy = vo * sin (angle);
        return vy * vy / 20.0;
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    public Reader(){
        din=new DataInputStream(System.in);
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public Reader(String file_name) throws IOException{
        din=new DataInputStream(new FileInputStream(file_name));
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public String readLine() throws IOException{
        byte[] buf=new byte[64]; // line length
        int cnt=0,c;
        while((c=read())!=-1){
            if(c=='\n')break;
            buf[cnt++]=(byte)c;
        }
        return new String(buf,0,cnt);
    }

    public int nextInt() throws IOException{
        int ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    } 

    public long nextLong() throws IOException{
        long ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    }

    public double nextDouble() throws IOException{
        double ret=0,div=1;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c = read();
        do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')
            ret+=(c-'0')/(div*=10);
        if(neg)return -ret;
        return ret;
    }
    
    private void fillBuffer() throws IOException{
        bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
        if(bytesRead==-1)buffer[0]=-1;
    }
    
    private byte read() throws IOException{
        if(bufferPointer==bytesRead)fillBuffer();
        return buffer[bufferPointer++];
    }
    
    public void close() throws IOException{
        if(din==null) return;
        din.close();
    }
}