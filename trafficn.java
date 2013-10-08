import java.io.*;
import java.util.*;

public class trafficn
{
    private static Reader in;
    private static PrintWriter out;
    private static ArrayList<Edge>[] fw, bk;
    public static final int INF = 100000000;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int f = in.nextInt (), n, m, k, s, t, i, a, b, w, best, temp;
        int [] dist1, dist2, prev; boolean [] path;
        while (f-- > 0) {
            n = in.nextInt (); m = in.nextInt ();
            k = in.nextInt (); s = in.nextInt ()-1;
            t = in.nextInt ()-1;
            fw = new ArrayList [n];
            bk = new ArrayList [n];
            for (i = 0; i < n; i++) { 
                fw [i] = new ArrayList <Edge> ();
                bk [i] = new ArrayList <Edge> ();
            }
            for (i = 0; i < m; i++) {
                a = in.nextInt ()-1;
                b = in.nextInt ()-1;
                w = in.nextInt ();
                fw [a].add (new Edge (b, w));
                bk [b].add (new Edge (a, w));
            }
            dist1 = new int [n]; dist2 = new int [n];
            dijkstra (dist1, s, fw);
            dijkstra (dist2, t, bk);
            
            best = dist1 [t];
            for (i = 0; i < k; i++) {
                a = in.nextInt ()-1;
                b = in.nextInt ()-1;
                w = in.nextInt ();
                if (dist1 [a] != -1 && dist2 [b] != -1 &&
                    (dist1 [a] + dist2 [b] + w < best || best == -1))
                   best = dist1 [a] + dist2 [b] + w;
                if (dist1 [b] != -1 && dist2 [a] != -1 &&
                    (dist1 [b] + dist2 [a] + w < best || best == -1))
                   best = dist1 [b] + dist2 [a] + w;
            }
            out.println (best);
        }
        System.exit(0);
    }
    
    private static void dijkstra (int [] dist, int start, ArrayList<Edge>[] grid) {
        Arrays.fill (dist, -1);
        boolean[] visited = new boolean [dist.length];
        PriorityQueue <Edge> queue = new PriorityQueue <Edge> ();
        queue.add (new Edge (start, 0));
        Edge dequeue; int node, weight;
        while (queue.size () != 0) {
            dequeue = queue.poll ();
            node = dequeue.to;
            weight = dequeue.weight;
            if (visited [node]) continue;
            visited [node] = true;
            dist [node] = weight;
            for (Edge e : grid [node])
                if (!visited [e.to])
                    queue.add (new Edge (e.to, e.weight + weight));
        }
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