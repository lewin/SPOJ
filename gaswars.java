import java.io.*;
import java.util.*;

public class gaswars
{
    private static Reader in;
    private static PrintWriter out;
    public static final int INF = 1000000000,
                            NULL = -52395424;
    private static int [][] cap, ocap;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            int n = in.nextInt (), m = in.nextInt (), k =  in.nextInt (),
                l = in.nextInt (), g = in.nextInt ();
            cap = new int [n + 2][n + 2];
            ocap = new int [n + 2][n + 2];
            for (int i = 0; i <= n + 1; i++)
                for (int j = 0; j <= n + 1; j++)
                    ocap [i][j] = cap [i][j] = NULL;
            for (int i = 0; i < m; i ++) {
                int a = in.nextInt ();
                int b = in.nextInt ();
                int c = in.nextInt ();
                ocap [a][b] = ocap [b][a] = cap [a][b] = cap [b][a] = c;
            }
            for (int i = 0; i < k; i++) {
                int a = in.nextInt ();
                ocap [0][a] = ocap [a][0] = cap [0][a] = cap [a][0] = INF;
            }
            for (int i = 0; i < l; i++) {
                int a = in.nextInt ();
                ocap [n + 1][a] = ocap [a][n + 1] = cap [n + 1][a] = cap [a][n + 1] = INF;
            }
            
            int [] ans = min_cost (0, n + 1, g);
            if (ans [1] < g) ans [0] = -1;
            else ans [0] *= 100;
            out.println (ans [0]);
        }
    }
    
    private static int [] min_cost (int source, int sink, int goal) {
        int[] pi = new int [cap.length], dist = new int [cap.length];
        int ans = 0, total = 0; int[] h;
        while ((h = dijkstra (pi, source, sink)) != null) {
            if (h [0] > ans) ans = h [0];
            total += h [1];
            if (total >= goal) break;
        }
        return new int [] {ans, total};
    }
    
    private static int[] dijkstra (int [] dist, int source, int sink) {
        Arrays.fill (dist, NULL);
        int[] prev = new int [dist.length], capa = new int [dist.length];
        boolean[] visited = new boolean [dist.length];
        PriorityQueue <Edge> pq = new PriorityQueue <Edge> ();
        pq.add (new Edge (source, 0, -1, INF));
        Edge dequeue; int node, weight, _prev, to, _cap;
        while (pq.size () != 0) {
            dequeue = pq.poll ();
            // unpack
            node = dequeue.to; weight = dequeue.weight; 
            _prev = dequeue.prev; _cap = dequeue.cap;
            if (visited [node]) continue;
            // set states
            visited [node] = true; dist [node] = weight;
            prev [node] = _prev; capa [node] = _cap;
            if (node == sink) break;
            // update
            for (to = 0; to < cap.length; to++)
                if (cap [node][to] > 0 && !visited [to]) {
                    int max = weight;
                    if (ocap [node][to] != INF && ocap [node][to] > max)
                        max = ocap [node][to];
                    pq.add (new Edge (to, max, node, 
                        Math.min (_cap, cap [node][to])));
                }
        }
        if (dist [sink] == NULL) return null; // no path
        augment (prev, capa [sink], source, sink);
        return new int [] {dist [sink], capa [sink]};
    }
    
    private static void augment (int[] prev, int pathCap, int source, int sink) {
        int curNode = sink, nextNode;
        while (curNode != source) {
            nextNode = prev [curNode];
            
            cap [nextNode][curNode] -= pathCap;
            cap [curNode][nextNode] += pathCap;
            
            curNode = nextNode;
        }
    }
    
    static class Edge implements Comparable<Edge> {
        public int to, weight, prev, cap;
        
        public Edge (int to, int weight, int prev, int cap) {
            this.to = to; this.weight = weight;
            this.prev = prev; this.cap = cap;
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