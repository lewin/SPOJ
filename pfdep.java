import java.io.*;
import java.util.*;

public class pfdep
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int n = in.nextInt (), m = in.nextInt ();
        boolean [][] grid = new boolean [n][n];
        int [] indegree = new int [n];
        for (int i = 0; i < m; i++) {
            int k = in.nextInt () - 1;
            indegree [k] = in.nextInt ();
            for (int j = 0; j < indegree [k]; j++)
                grid [in.nextInt () - 1][k] = true;
        }
        PriorityQueue <Integer> pq = new PriorityQueue <Integer> ();
        for (int i = 0; i < n; i++) 
            if (indegree [i] == 0) pq.add (i);
        boolean first = true;
        while (pq.size () > 0) {
            int node = pq.poll () + 1;
            if (first) out.print ("" + node);
            else       out.print (" " + node);
            first = false;
            node--;
            for (int i = 0; i < n; i++)
                if (grid [node][i])
                    if (--indegree [i] == 0)
                        pq.add (i);
        }
        out.println ();
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