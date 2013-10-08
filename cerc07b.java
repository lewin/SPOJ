import java.io.*;
import java.util.*;

public class cerc07b
{
    private static Reader in;
    private static PrintWriter out;
    private static int x, y;
    private static int [] grid;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            x = in.nextInt (); y = in.nextInt ();
            grid = new int [x];
            if (x == 0 && y == 0) break;
            for (int i = 0; i < x; i++) {
                char [] line = in.readLine ().toCharArray ();
                for (int j = 0; j < y; j++)
                    if (line [j] == 'X')
                        grid [i] |= (1 << j);
            }
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < (1 << y); i++) {
                int c = move (i);
                if (c < min) min = c;
            }
            if (min == Integer.MAX_VALUE) out.println ("Damaged billboard.");
            else out.printf ("You have to tap %d tiles.\n", min);
        }
    }
    
    private static int move (int mask) {
        int [] tempGrid = new int [x];
        System.arraycopy (grid, 0, tempGrid, 0, x);
        int moveCount = 0;
        for (int i = 0; i < y; i++) 
            if ((mask & (1 << i)) > 0) {
                tempGrid [0] ^= (1 << i);
                if (i > 0) tempGrid [0] ^= (1 << (i - 1));
                if (i < y - 1) tempGrid [0] ^= (1 << (i + 1));
                if (x > 1) tempGrid [1] ^= (1 << i);
                moveCount++;
            }
        for (int i = 1; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if ((tempGrid [i - 1] & (1 << j)) > 0) {
                    tempGrid [i] ^= (1 << j);
                    if (j > 0) tempGrid [i] ^= (1 << (j - 1));
                    if (j < y - 1) tempGrid [i] ^= (1 << (j + 1));
                    if (i < x - 1) tempGrid [i + 1] ^= (1 << j);
                    tempGrid [i - 1] ^= (1 << j);
                    moveCount++;
                }
            }
        }
        return (tempGrid [x - 1] == 0) ? moveCount : Integer.MAX_VALUE;
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