import java.io.*;
import java.util.*;

public class tetra
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int T = in.nextInt ();
        while (T-- > 0) {
            double u = in.nextDouble (), W = in.nextDouble (), V = in.nextDouble (),
                   v = in.nextDouble (), w = in.nextDouble (), U = in.nextDouble ();
                   
            double u_ = v * v + w * w - U * U,
                   v_ = w * w + u * u - V * V,
                   w_ = u * u + v * v - W * W,
                   vol = 4 * u * u * v * v * w * w - 
                         u * u * u_ * u_ - v * v * v_ * v_ -
                         w * w * w_ * w_ + u_ * v_ * w_,
                   volume = Math.sqrt (vol) / 12.;
                   
            double s = (u + W + v) / 2., plane1, plane2, plane3, plane4;
            plane1 = Math.sqrt (s * (s - u) * (s - W) * (s - v));
            s = (U + V + W) / 2.;
            plane2 = Math.sqrt (s * (s - U) * (s - V) * (s - W));
            s = (v + U + w) / 2.;
            plane3 = Math.sqrt (s * (s - v) * (s - U) * (s - w));
            s = (w + V + u) / 2.;
            plane4 = Math.sqrt (s * (s - w) * (s - V) * (s - u));
            
            double area = plane1 + plane2 + plane3 + plane4;
            out.printf ("%.4f\n", 3 * volume / area);
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