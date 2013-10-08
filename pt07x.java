import java.io.*;
import java.util.*;

public class pt07x
{
    private static Reader in;
    private static PrintWriter out;
    private static int [][] dp;
    private static ArrayList <Integer> [] grid;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int n = in.nextInt ();
        grid = new ArrayList [n];
        dp = new int [n][2];
        for (int i = 0; i < n; i++) grid [i] = new ArrayList <Integer> ();
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt () - 1;
            int b = in.nextInt () - 1;
            grid [a].add (b);
            grid [b].add (a);
        }
        
        int [] sorted = new int [n];
        toposort (sorted);
        boolean [] use = new boolean [n];
        for (int i = n - 1; i >= 0; i--) {
            int node = sorted [i];
            use [node] = true;
            dp [node][1] = 1; // use this vertex
            dp [node][0] = 0; // don't use this vertex
            for (int k : grid [node]) {
                if (!use [k]) continue;
                dp [node][1] += Math.min (dp [k][1], dp [k][0]);
                dp [node][0] += dp [k][1];
            }
        }
        
        out.println (Math.min (dp [0][0], dp [0][1]));
    }
    
    private static void toposort (int[] sorted) {
        int [] queue = new int [sorted.length]; 
        boolean [] visited = new boolean [queue.length];
        int i, x, y, ind = 0, front = 0, back = 0;
        queue [front++] = 0;
        visited [0] = true;
        while(front != back) {
            x = queue [back++];
            sorted[ind++] = x;
            for (int k : grid [x])
                if (!visited [k]) {
                    queue [front++] = k;
                    visited [k] = true;
                }
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