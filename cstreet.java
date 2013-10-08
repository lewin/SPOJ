import java.io.*;
import java.util.*;

public class cstreet
{
    private static Reader in;
    private static PrintWriter out;
    public static ArrayList <Edge>[] grid;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            int p = in.nextInt (), n = in.nextInt (), m = in.nextInt ();
            grid = new ArrayList [n];
            for (int i = 0; i < n; i++)
                grid [i] = new ArrayList <Edge> ();
            for (int i = 0, a, b, w; i < m; i++) {
                a = in.nextInt () - 1;
                b = in.nextInt () - 1;
                w = in.nextInt ();
                grid [a].add (new Edge (b, w));
                grid [b].add (new Edge (a, w));
            }
            out.println (minSpanningTree () * p);
        }
    }
    
    private static int minSpanningTree () {
        int nodes = grid.length;
        boolean[] visited = new boolean [nodes];
        int i, treeSize, treeCost, node, weight;
        PriorityQueue <Edge> pq = new PriorityQueue <Edge> ();
        for (i = 0; i < nodes; i++) visited [i] = false;
        treeCost = 0; treeSize = 0;
        pq.add (new Edge (0, 0));
        Edge dequeue;
        while (treeSize < nodes) {
            dequeue = pq.poll();
            node = dequeue.to; weight = dequeue.weight;
            if(visited[node]) continue;
            treeSize++; treeCost += weight;
            visited[node] = true;
            for (Edge e : grid[node])
                pq.add (new Edge (e.to, e.weight));
        }
        return treeCost;
    }
    
    static class Edge implements Comparable<Edge> {
        public int to, weight;
        
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
        
        public int compareTo(Edge e) {
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