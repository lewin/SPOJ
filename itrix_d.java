import java.io.*;
import java.util.*;

public class itrix_d
{
    private static Reader in;
    private static PrintWriter out;
    
    private static int [][] X, Y;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
    }
    
    private static int sum (int x1, int y1, int x2, int y2, boolean isX) {
        if (isX) return X [x2][y2] - X [x1 - 1][y2] - X [x2][y1 - 1] + X [x1 - 1][y1 - 1];
        else     return Y [x2][y2] - Y [x1 - 1][y2] - Y [x2][y1 - 1] + Y [x1 - 1][y1 - 1];
    }
    
    static class Board {
        public Board b1 = null, b2 = null, b3 = null, b4 = null;
        /*
         *  ---------
         * | b1 | b2 |
         *  ---- ----
         * | b3 | b4 |
         *  ---------
         */
        public int lazy, status, x1, x2, y1, y2; // 0 -> all X, 1 -> all Y, 2 -> combo
        
        public Board (int x1, int x2, int y1, int y2) {
            this.x1 = x1; this.x2 = x2; this.y1 = y1; this.y2 = y2;
            if (x1 != x2 && y1 != y2) {
                int midx = (x1 + x2) >> 1; 
                int midy = (y1 + y2) >> 1;
                b1 = new Board (x1, midx, y1, midy);
                b2 = new Board (midx + 1, x2, y1, midy);
                b3 = new Board (x1, midx, midy + 1, y2);
                b4 = new Board (midx + 1, x2, midy + 1, y2);
            }
        }
        
        public void modify () {
            if (status == 0) status = 1;
            else if (status == 1) status = 0;
        }
        
        private void push () {
            if (lazy == 1 && x1 != x2) {
                b1.modify ();
                b2.modify ();
                b3.modify ();
                b4.modify ();
                b1.lazy ^= 1;
                b2.lazy ^= 1;
                b3.lazy ^= 1;
                b4.lazy ^= 1;
                join ();
            }
            lazy = 0;
        }
        
        private void join () {
            if (b1.status == b2.status && b2.status == b3.status && b3.status == b4.status)
                status = b1.status;
            else status = 2;
        }
        
        public void flip (int s1, int t1, int s2, int t2) {
            if ((s1 > x2 || s2 < x1) && (t1 > y2 || t2 < y1)) return;
            if (x1 == s1 && y1 == t1 && x2 == s2 && y2 == t2) {
                modify ();
                lazy ^= 1;
                return;
            }
            push ();
            int midx = (x1 + x2) >> 1;
            int midy = (y1 + y2) >> 1;
        }
        
        public int query (int s1, int t1, int s2, int t2, boolean isX) {
            return 0;
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