import java.io.*;
import java.util.*;

public class dfloor
{
    private static Reader in;
    private static PrintWriter out;
    private static ArrayList <Point> moves, ans;
    private static int x, y;
    private static int [] grid;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            x = in.nextInt (); y = in.nextInt ();
            if (x == 0 && y == 0) break;
            grid = new int [y];
            for (int i = 0; i < y; i++)
                grid [i] = in.nextInt (2);
            
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < (1 << x); i++) {
                moves = new ArrayList <Point> (x * y);
                int c = move (i);
                if (c < min) {
                    min = c;
                    ans = new ArrayList <Point> (moves);
                }
            }
            if (min == Integer.MAX_VALUE) out.println (-1);
            else {
                out.println (min);
                Collections.sort (ans);
                for (Point p : ans)
                    out.println (p);
            }
        }
    }
    
    private static int move (int mask) {
        int [] tempGrid = new int [y];
        System.arraycopy (grid, 0, tempGrid, 0, y);
        int moveCount = 0;
        for (int i = 0; i < x; i++) 
            if ((mask & (1 << i)) > 0) {
                tempGrid [0] ^= (1 << i);
                if (i > 0) tempGrid [0] ^= (1 << (i - 1));
                if (i < x - 1) tempGrid [0] ^= (1 << (i + 1));
                tempGrid [1] ^= (1 << i);
                moveCount++;
                moves.add (new Point (x - i, 1));
            }
        for (int i = 1; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if ((tempGrid [i - 1] & (1 << j)) == 0) {
                    tempGrid [i] ^= (1 << j);
                    if (j > 0) tempGrid [i] ^= (1 << (j - 1));
                    if (j < x - 1) tempGrid [i] ^= (1 << (j + 1));
                    if (i < y - 1) tempGrid [i + 1] ^= (1 << j);
                    tempGrid [i - 1] ^= (1 << j);
                    moveCount++;
                    moves.add (new Point (x - j, i + 1));
                }
            }
        }
        return (tempGrid [y - 1] == (1 << x) - 1) ? moveCount : Integer.MAX_VALUE;
    }
    
    static class Point implements Comparable <Point> {
        public int x, y;
        public Point (int _x, int _y) {
            x = _x; y = _y;
        }
        
        public int compareTo (Point other) {
            if (x == other.x) return y - other.y;
            return x - other.x;
        }
        
        public String toString () {
            return x+" "+y;
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
    public int nextChar()throws IOException{byte c=read();while(c<=' ')c=read();return(char)c;}
    public int nextInt(int b)throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*b+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
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