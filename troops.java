import java.io.*;
import java.util.*;

public class troops
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            int n = in.nextInt ();
            Troop [] troops = new Troop [n];
            for (int i = 0; i < n; i++)
                troops [i] = new Troop (in.nextInt (), in.nextInt (), in.nextInt ());
            Arrays.sort (troops);
            PriorityQueue <Troop> pq = new PriorityQueue <Troop> (n, new TroopComp ());
            int time = troops [n - 1].t; int idx = n - 1;
            int sand = 0;
            out : for (int b = time; b > 0; b--) {
                while (idx >= 0 && troops [idx].t >= b)
                    pq.offer (troops [idx--]);
                Troop d = null;
                do {
                    if (pq.size () == 0) continue out;
                    d = pq.peek ();
                    if (d.c == 0) pq.poll ();
                } while (d.c == 0);
                sand += d.s;
                d.c--;
            }
            out.println (sand);
        }
    }
    
    static class TroopComp implements Comparator <Troop> {
        public int compare (Troop a, Troop b) {
            return b.s - a.s;
        }
        public boolean equals (Object c) {
            return false;
        }
    }
    
    static class Troop implements Comparable <Troop> {
        public int c, t, s;
        public Troop (int _c, int _t, int _s) {
            c = _c; t = _t; s = _s;
        }
        public int compareTo (Troop o) {
            return t - o.t;
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close() throws IOException{if(din==null) return;din.close();}
}