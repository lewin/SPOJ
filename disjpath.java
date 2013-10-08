import java.io.*;
import java.util.*;

public class disjpath
{
    private static BufferedReader in;
    private static PrintWriter out;
    public static Stack <Integer> path;
    public static final int INF = 1000000000;
    public static int [][] grid;
    public static int n, k;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        for (int t = 1; ; t++) {
            StringTokenizer st = new StringTokenizer (in.readLine ());
            k = Integer.parseInt (st.nextToken ());
            n = Integer.parseInt (st.nextToken ());
            if (n == 0 && k == 0) break;
            grid = new int [2 * n + 1][2 * n + 1];
            for (int i = 1; i <= n; i++) {
                st = new StringTokenizer (in.readLine ());
                while (st.hasMoreTokens ()) {
                    int a = Integer.parseInt (st.nextToken ());
                    grid [i + n][a] = 1;
                }
                grid [i][i + n] = 1;
            }
            out.println ("Case " + t + ":");
            int flow = networkFlow (1 + n, 2, 2 * n);
            if (flow < k) out.println ("Impossible");
            else {
                while (k-- > 0) {
                    backtrack ();
                    out.print (path.pop ());
                    while (path.size () > 0)
                        out.print (" " + path.pop ());
                    out.println ();
                }
            }
            out.println ();
        }
    }
    
    private static int networkFlow (int source, int sink, int nodes) {
        if (source == sink) return INF;
        int maxFlow, maxLoc, pathCap, curNode, nextNode, i, j;
        int totalFlow = 0;
        int[] prevNode = new int [nodes+1], flow = new int [nodes+1];
        boolean[] visited = new boolean [nodes+1];
        outer : while (true) {
            for (i = 0; i <= nodes; i++) {
                prevNode [i] = -1;
                flow [i] = 0;
                visited [i] = false;
            }
            flow [source] = INF;
            while (true) {
                maxFlow = 0; maxLoc = -1;
                for (i = 0; i <= nodes; i++)
                    if (flow [i] > maxFlow && !visited [i]) {
                        maxFlow = flow [i];
                        maxLoc = i;
                    }
                if (maxLoc == -1) break outer;
                if (maxLoc == sink) break;
                visited [maxLoc] = true;
                for (i = 0; i <= nodes; i++)
                    if (!visited [i] && 
                            flow [i] < Math.min (maxFlow, grid [maxLoc][i])) {
                        prevNode [i] = maxLoc;
                        flow [i] = Math.min (maxFlow, grid [maxLoc][i]);
                    }
            }
            pathCap = flow [sink];
            totalFlow += pathCap;
            curNode = sink;
            while (curNode != source)
            {
                nextNode = prevNode [curNode];
                grid [nextNode][curNode] -= pathCap;
                grid [curNode][nextNode] += pathCap;
                curNode = nextNode;
            }
            if (totalFlow == k) break;
        }
        return totalFlow;
    }
    
    private static void backtrack () {
        int cur = 2;
        path = new Stack <Integer> ();
        while (cur != 1) {
            path.add (cur);
            // find a backwards pointing arc
            for (int i = 1; i <= n; i++) 
                if (cur != i && grid [cur][i + n] == 1) {
                    grid [cur][i + n] = 0;
                    cur = i;
                    break;
                }    
        }
        path.add (cur);
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