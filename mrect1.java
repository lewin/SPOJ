import java.io.*;
import java.util.*;

public class mrect1
{
    private static Reader in;
    private static PrintWriter out;
    
    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int n = in.nextInt ();
        PointX[] xpos = new PointX [n + 1];
        PointY[] ypos = new PointY [n + 1];
        xpos [0] = new PointX (-1, -1);
        ypos [0] = new PointY (-1, -1);
        for (int i = 1; i <= n; i++) {
            int x = in.nextInt ();
            int y = in.nextInt ();
            xpos [i] = new PointX (x, y);
            ypos [i] = new PointY (x, y);
        }
        Arrays.sort (xpos); Arrays.sort(ypos);
        int k = in.nextInt (); 
        for (int i = 0; i < k; i++) {
            int count = 0;
            int x1 = in.nextInt (), y1 = in.nextInt (), 
                x2 = in.nextInt (), y2 = in.nextInt ();
                
            PointX a = new PointX (x1, y1 - 1);
            PointX b = new PointX (x1, y2);
            int c = binarySearchX (b, xpos);
            int d = binarySearchX (a, xpos);
            count += c - d;
            
            a = new PointX (x2, y1 - 1);
            b = new PointX (x2, y2);
            c = binarySearchX (b, xpos);
            d = binarySearchX (a, xpos);
            count += c - d;
            
            x2--;
            PointY e = new PointY (x1, y1);
            PointY f = new PointY (x2, y1);
            c = binarySearchY (f, ypos);
            d = binarySearchY (e, ypos);
            count += c - d;
            
            e = new PointY (x1, y2);
            f = new PointY (x2, y2);
            c = binarySearchY (f, ypos);
            d = binarySearchY (e, ypos);
            count += c - d;
            
            out.println (count);
        }
    }
    
    private static int binarySearchX (PointX key, PointX [] arr) {
        int lo = 0, hi = arr.length, mid = (lo + hi) >> 1;
        while (lo < hi) {
            int comp = key.compareTo (arr [mid]);
            if (comp < 0) hi = mid;
            else if (comp == 0) return mid;
            else lo = mid;
            mid = (lo + hi) >> 1;
            if (mid == lo) break;
        }
        return lo;
    }
    
    private static int binarySearchY (PointY key, PointY [] arr) {
        int lo = 0, hi = arr.length, mid = (lo + hi) >> 1;;
        while (lo < hi) {
            int comp = key.compareTo (arr [mid]);
            if (comp < 0) hi = mid;
            else if (comp == 0) return mid;
            else lo = mid;
            mid = (lo + hi) >> 1;
            if (mid == lo) break;
        }
        return lo;
    }
    
    
    static class PointX implements Comparable <PointX>  {
        public int x, y;
        public PointX (int _x, int _y) {x = _x; y = _y;}
        public int compareTo (PointX o) {
            if (x == o.x) return y - o.y;
            return x - o.x;
        }
        public String toString () {
            return x+" "+y;
        }
    }
    
    static class PointY implements Comparable <PointY>  {
        public int x, y;
        public PointY (int _x, int _y) {x = _x; y = _y;}
        public int compareTo (PointY o) {
            if (y == o.y) return x - o.x;
            return y - o.y;
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