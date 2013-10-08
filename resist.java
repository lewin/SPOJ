import java.io.*;
import java.util.*;

public class resist
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            int n = in.nextInt (), m = in.nextInt (), i, j, a, b, r;
            double [][] resist = new double [n][n];
            for (i = 0; i < m; i++) {
                a = in.nextInt () - 1;
                b = in.nextInt () - 1;
                r = in.nextInt ();
                if (resist [a][b] != 0)
                    resist [a][b] = 1.0 / resist [a][b] + 1.0 / (double)r;
                else 
                    resist [a][b] = resist [b][a] = r;
            }
            boolean changed;
            int first, second; double series;
            do {
                changed = false;
                outer : for (i = 0; i < n; i++) {
                    first = second = -1;
                    for (j = 0; j < n; j++)
                        if (resist [i][j] != 0) {
                            if (first == -1) first = j;
                            else if (second == -1) second = j;
                            else continue outer;
                        }
                    if (second != -1) {
                        series = resist [first][i] + resist [second][i];
                        if (resist [first][second] != 0)
                            resist [first][second] = 1.0 / series + 1.0 / resist [first][second];
                        else 
                            resist [first][second] = series;
                        resist [second][first] = resist [first][second];
                        resist [first][i] = resist [i][first] =
                        resist [second][i] = resist [i][second] = 0;
                        changed = true;
                    }
                    else if (first != -1) {
                        resist [first][i] = resist [i][first] = 0;
                        changed = true;
                    }
                }
            } while (changed);
            out.printf ("%.2f\n", resist [0][n-1]);
            if (in.readLine()== null) break;
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
    
    public boolean available() throws IOException{
        return din.available()!=0;
    }
    
    public void close() throws IOException{
        if(din==null) return;
        din.close();
    }
}