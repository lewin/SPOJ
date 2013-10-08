import java.io.*;
import java.util.*;

public class buglife
{
    private static Reader in;
    private static PrintWriter out;
    private static ArrayList <Integer> [] connect;
    private static int[] color;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt (), n, m, i, a, b, j=0;
        while (t-->0) {
            n = in.nextInt (); m = in.nextInt ();
            connect = new ArrayList [n];
            color = new int [n];
            for (i = 0; i < n; i++) connect [i] = new ArrayList <Integer> ();
            for (i = 0; i < m; i++) {
                a = in.nextInt () - 1; b = in.nextInt () - 1;
                connect [a].add (b);
                connect [b].add (a);
            }
            out.printf ("Scenario #%d:\n", ++j);
            boolean ok = true;
            for (i = 0; i < n; i++)
                if (color [i] == 0 && !clr (i, 1)) {
                    ok = false; break;
                }
            if (!ok)  out.println ("Suspicious bugs found!");
            else      out.println ("No suspicious bugs found!");
        }
    }
    
    private static boolean clr (int node, int c) {
        if (color [node] != 0) return color [node] == c;
        int nc = (c == 1 ? 2 : 1);
        color [node] = c; 
        for (int p : connect [node])
            if (!clr (p, nc)) 
                return false;
        return true;
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