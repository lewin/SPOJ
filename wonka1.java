import java.io.*;
import java.util.*;

public class wonka1
{
    private static Reader in;
    private static PrintWriter out;
    public static final double EPS = 0.00001,
                               PI = 3.1415926535897932384626434;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            int h1x = in.nextInt (), h1y = in.nextInt (),
                b1x = in.nextInt (), b1y = in.nextInt (),
                h2x = in.nextInt (), h2y = in.nextInt (),
                b2x = in.nextInt (), b2y = in.nextInt ();
            // convert rate to radians
            double rate1 = in.nextDouble () * PI / 180.,
                   rate2 = in.nextDouble () * PI / 180.;      
            // compute lengths of poles
            double pole1 = Math.sqrt (sq (b1x - h1x) + sq (b1y - h1y)),
                   pole2 = Math.sqrt (sq (b2x - h2x) + sq (b2y - h2y)),
                   base  = Math.sqrt (sq (b2x - b1x) + sq (b2y - b1y));
            if (pole1 + pole2 > base - EPS) {
                // compute initial angles using law of cosines
                double temp1 = Math.sqrt (sq (h1x - b2x) + sq (h1y - b2y)),
                       temp2 = Math.sqrt (sq (h2x - b1x) + sq (h2y - b1y));
                double initial_1 = Math.acos ((sq (base) + sq (pole1) - sq(temp1)) / (2 * base * pole1)),
                       initial_2 = Math.acos ((sq (base) + sq (pole2) - sq(temp2)) / (2 * base * pole2));
                // law of cosines to find angles
                double angle1 = Math.abs (initial_1 - Math.acos ((sq (pole1) + sq (base) - sq (pole2)) / (2 * pole1 * base))),
                       angle2 = Math.abs (initial_2 - Math.acos ((sq (pole2) + sq (base) - sq (pole1)) / (2 * pole2 * base)));
                double time1 = angle1 / rate1;
                double time2 = angle2 / rate2;
                if (Double.isNaN (time1) || Double.isNaN (time2)) out.println (-1);
                else out.printf ("%.3f\n", Math.abs (time1 - time2));
            }   
            else out.println (-1);
        }
    }
    
    private static double sq (double d) {
        return d * d;
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