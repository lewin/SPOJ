import java.io.*;
import java.util.*;

public class boballs2
{
    private static Reader in;
    private static PrintWriter out;
    public static int boxX, boxY;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        boxX = in.nextInt ();
        boxY = in.nextInt ();
        int n = in.nextInt ();
        Ball [] bounce = new Ball [n];
        for (int i = 0; i < n; i++)
            bounce [i] = new Ball (in.nextInt (), in.nextInt (), in.nextInt (), in.nextInt ());
        int k = in.nextInt ();
        Point [] p = new Point [n];
        for (int i = 0; i < k; i++) {
            if (i > 0) out.println ();
            long t = in.nextLong ();
            for (int j = 0; j < n; j++)
                p [j] = bounce [j].calc (t);
            Arrays.sort (p);
            for (int j = 0; j < n; j++)
                out.println (p[j]);
        }
    }
    
    static class Ball {
        public long x, y, vx, vy;
        public Ball (long _x, long _y, long _vx, long _vy) {
            x = _x; y = _y; vx = _vx; vy = _vy;
        }
        public Point calc (long time) {
            long newx = (x + vx * time) % (2 * boxX);
            long newy = (y + vy * time) % (2 * boxY);
            if (newx <= 0) newx += 2 * boxX;
            if (newy <= 0) newy += 2 * boxY;
            if (newx > boxX) newx = 2 * boxX - newx;
            if (newy > boxY) newy = 2 * boxY - newy;
            return new Point (newx, newy);
        }
    }
    
    static class Point implements Comparable <Point> {
        public int x, y;
        public Point (long _x, long _y) {
            x = (int)_x; y = (int)_y;
        }
        public int compareTo (Point o) {
            if (o.x == x) return y - o.y;
            return x - o.x;
        }
        public String toString () {
            return x+" "+y;
        }
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