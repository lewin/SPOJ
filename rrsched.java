import java.io.*;
import java.util.*;

public class rrsched
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt ();
        
        PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
        for (int i = 1; i <= N; i++)
            pq.add (new Pair (in.nextInt (), i));
            
        int [] tree = new int [N + 1];
        int last = 0, count = N, prev = 1;
        long [] t = new long [N + 1];
        while (pq.size () > 0) {
            int time = pq.peek ().time, idx = pq.peek ().idx; pq.poll ();
            int get = 0;
            for (int i = idx; i > 0; i -= (i & -i)) get += tree [i];
            if (prev < idx)
                t [idx] = (time - last) * (count) + idx - get;
            for (int i = idx; i <= N; i += (i & -i)) tree [i]++;
            last = time; count--; prev = idx;
        }
        
        for (int i = 1; i <= N; i++)
            out.println (t [i]);
        out.close ();
        System.exit (0);
    }
    
    static class Pair implements Comparable <Pair> {
        public int time, idx;
        
        public Pair (int _time, int _idx) {
            time = _time; idx = _idx;
        }
        
        public int compareTo (Pair other) {
            return time == other.time ? idx - other.idx : time - other.time;
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