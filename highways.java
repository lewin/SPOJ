import java.io.*;
import java.util.*;

public class highways
{
    private static Reader in;
    private static PrintWriter out;
    public static ArrayList <Edge> [] grid;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt (), n, m, a, b, w, s, e, i, z; int[] r;
        while (t-->0) {
            n = in.nextInt (); m = in.nextInt ();
            s = in.nextInt () - 1; e = in.nextInt () - 1;
            grid = new ArrayList [n]; r = new int [n];
            for (i = 0; i < n; i++) grid [i] = new ArrayList <Edge> ();
            for (i = 0; i < m; i++) {
                a = in.nextInt () - 1;
                b = in.nextInt () - 1;
                w = in.nextInt ();
                grid [a].add (new Edge (b, w));
                grid [b].add (new Edge (a, w));
            }
            
            out.println ((z = dijkstra (r, s, e)) == -1 ? "NONE" : z);
        }
        System.exit (0);
    }
    
    private static int dijkstra (int [] dist, int start, int finish) {
        Arrays.fill (dist, -1);
        boolean[] visited = new boolean [dist.length];
        PriorityQueue <Edge> pq = new PriorityQueue <Edge> ();
        pq.add (new Edge (start, 0));
        Edge dequeue; int node, weight = -1;
        while (pq.size () != 0) {
            dequeue = pq.poll ();
            // unpack
            node = dequeue.to; weight = dequeue.weight;
            if (visited [node]) continue;
            // set states
            visited [node] = true;
            dist [node] = weight;
            if (node == finish) break;
            // update
            for (Edge e : grid [node])
                if (!visited [e.to])
                    pq.add (new Edge (e.to, e.weight + weight));
        }
        return dist [finish];
    }
    
    static class Edge implements Comparable<Edge> {
        public int to, weight;
        
        public Edge (int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
        
        public int compareTo (Edge e) {
            return weight - e.weight;
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