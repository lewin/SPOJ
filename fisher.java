import java.io.*;
import java.util.*;

public class fisher
{
    private static Reader in;
    private static PrintWriter out;
    private static int [][] time, toll;
    
    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        for (;;) {
            int n = in.nextInt (), m = in.nextInt ();
            if (n == 0 && m == 0) break;
            time = new int [n][n];
            toll = new int [n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    time [i][j] = in.nextInt ();
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    toll [i][j] = in.nextInt ();
                    
            int [] ans = dijkstraToll (0, n - 1, m);
            out.printf ("%d %d\n", ans [0], ans [1]);
        }
    }
    
    private static int[] dijkstraToll(int start, int goal, int lim) {
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        pq.add(new Edge(start,0,0));  Edge dequeue;
        int[] dist = new int[time.length], cost = new int[time.length];
        Arrays.fill(dist, -1);
        int node, _cost, weight, i;
        while(pq.size() > 0) {
            dequeue = pq.poll();
            node = dequeue.to; weight = dequeue.weight; _cost = dequeue.toll;
            if(dist[node] != -1 && dist[node] < weight) continue;
            dist[node] = weight; cost[node] = _cost;
            for (i = 0; i < time.length; i++)
                if(node != i && time [node][i] + _cost <= lim)
                    pq.add(new Edge(i,weight+toll [node][i],_cost+time [node][i]));
        }
        return new int[] {dist[goal], cost[goal]};
    }
    
    static class Edge implements Comparable<Edge>
    {
        public int to, weight, toll;
        
        public Edge(int to, int weight, int toll) {
            this.to = to;
            this.weight = weight;
            this.toll = toll;
        }
        
        public int compareTo(Edge e) {
            if(toll == e.toll)
                return weight - e.weight;
            return toll - e.toll;
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