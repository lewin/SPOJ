import java.io.*;
import java.util.*;

public class trstage
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            int n = in.nextInt (), m = in.nextInt (),
                p = in.nextInt (), a = in.nextInt () - 1,
                b = in.nextInt () - 1;
            if (n == 0 && m == 0 && p == 0 && a == -1 && b == -1) break;
            boolean [][] visited = new boolean [1 << n][m];
            int [] horses = new int [n];
            for (int i = 0; i < n; i++)
                horses [i] = in.nextInt ();
            int [][] grid = new int [m][m];
            for (int i = 0; i < p; i++) {
                int c = in.nextInt () - 1, d = in.nextInt () - 1, l = in.nextInt ();
                grid [c][d] = grid [d][c] = l;
            }
            PriorityQueue <State> pq = new PriorityQueue <State> ();
            pq.add (new State (0, 0, a));
            double min = -1;
            while (pq.size () > 0) {
                int used, node; double time;
                State deq = pq.poll ();
                time = deq.time; used = deq.used;
                node = deq.node;
                if (visited [used][node]) continue;
                visited [used][node] = true;
                if (node == b) {min = time; break;}
                for (int i = 0; i < m; i++) {
                    if (grid [node][i] == 0) continue;
                    for (int j = 0; j < n; j++) {
                        if ((used & (1 << j)) > 0) continue;
                        pq.add (new State (time + ((double)grid [node][i] / horses [j]),
                            used | (1 << j), i));
                    }
                }
            }
            if (min == -1) out.println ("Impossible");
            else out.printf ("%.3f\n", min);
        }
    }
    
    static class State implements Comparable <State> {
        public int used, node;
        public double time;
        
        public State (double _time, int _used, int _node) {
            time = _time;
            used = _used;
            node = _node;
        }
        
        public int compareTo (State other) {
            if (time > other.time) return 1;
            if (time < other.time) return -1;
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