import java.io.*;
import java.util.*;

public class hackers
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            int c = in.nextInt (), l = in.nextInt (), h = in.nextInt ();
            if (c == -1 && l == -1 && h == -1) break;
            ArrayList <Edge> [] grid = new ArrayList [c];
            for (int i = 0; i < c; i++) grid [i] = new ArrayList <Edge> ();
            for (int i = 0; i < l; i++) {
                int a = in.nextInt () - 1, b = in.nextInt () - 1, w = in.nextInt ();
                grid [a].add (new Edge (b, w));
                grid [b].add (new Edge (a, w));
            }
            int [][] cap = new int [c][c];
            for (int i = 0; i < c; i++) {
                PriorityQueue <Edge> pq = new PriorityQueue <Edge> ();
                boolean [] visited = new boolean [c];
                pq.add (new Edge (i, 0));
                int node, weight;
                while (pq.size () > 0) {
                    Edge deq = pq.poll ();
                    node = deq.to; weight = deq.weight;
                    if (visited [node]) continue;
                    visited [node] = true;
                    cap [i][node] = weight;
                    for (Edge e : grid [node]) 
                        if (!visited [e.to])
                            pq.add (new Edge (e.to, Math.max (weight, e.weight)));
                }
            }
            int s, t;
            s = in.nextInt () - 1; t = in.nextInt () - 1;
            out.print (cap [s][t]);
            for (int i = 1; i < h; i++) {
                s = in.nextInt () - 1; 
                t = in.nextInt () - 1;
                out.print (" " + cap [s][t]);
            }
            out.println ();
        }
    }
    
    static class Edge implements Comparable <Edge> {
        public int to, weight;
        public Edge (int _to, int _weight) {
            to = _to;
            weight = _weight;
        }
        
        public int compareTo (Edge other) {
            return weight - other.weight;
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