import java.io.*;
import java.util.*;

public class assign4
{
    private static Reader in;
    private static PrintWriter out;
    private static final int INF = 1 << 25;
    private static int N;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt(), m, n, i, j;
        while (t-->0) {
            m = in.nextInt(); n = in.nextInt();
            N = m + n + 2;
            cap =  new int [N][N];
            cost = new int [N][N];
            for (i = 1; i <= m; i++)
                addEdge (0, i, in.nextInt (), 0);
            for (i = m + 1; i <= m + n; i++)
                addEdge (i, m + n + 1, in.nextInt (), 0);
            for (i = 1; i <= m; i++)
                for (j = m + 1; j <= m + n; j++)
                    addEdge (i, j, INF, in.nextInt ());
            out.println (flow (0, N - 1)[1]);
        }
        System.exit(0);
    }
    
    private static int [][] cap, cost;
    private static void addEdge (int x, int y, int w, int c) {
        cap [x][y] = w;
        cost [x][y] = c;
        cost [y][x] = -c;
    }
    
    private static int [] pot;
    private static int [] flow (int source, int sink) {
        int ans_flow = 0, ans_cost = 0;
        pot = new int [N];
        
        while (true) {        
            boolean [] used = new boolean [N];
            int [] dist = new int [N], prev = new int [N];
            Arrays.fill (dist, INF);
            dist [source] = 0;
            
            while (true) {
                int x = -1;
                for (int i = 0; i < N; i++)
                    if (dist [i] != INF && !used [i] 
                        && (x == -1 || dist [i] < dist [x]))
                            x = i;
                            
                if (x == -1) break;
                
                used [x] = true;
                for (int i = 0; i < N; i++)
                    if (cap [x][i] > 0 
                        && dist [x] + cost [x][i] + pot [x] - pot [i] < dist [i]) {
                            dist [i] = dist [x] + cost [x][i] + pot [x] - pot [i];
                            prev [i] = x;
                    }
            }
            
            if (!used [sink]) break;
            
            int ansf = INF, ansc = 0;
            for (int x = sink; x != source; x = prev [x]) 
                ansf = Math.min (ansf, cap [prev [x]][x]);
             
            for (int x = sink; x != source; x = prev [x]) {
                ansc += cost [prev [x]][x] * ansf;
                cap [prev [x]][x] -= ansf;
                cap [x][prev [x]] += ansf;
            }
            
            for (int i = 0; i < N; i++)
                pot [i] += dist [i];
                
            ans_cost += ansc;
        }
        
        return new int [] {ans_flow, ans_cost};
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