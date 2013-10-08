import java.io.*;
import java.util.*;

public class pbcgame
{
    private static Reader in;
    private static PrintWriter out;
    public static final int INF = 1000000000;
    public static int [][] grid;
    
    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int n1 = in.nextInt (), n2 = in.nextInt (), n3 = in.nextInt ();
        int N = n1 + n2 * 2 + n3 + 1;
        grid = new int [N + 1][N + 1];
        for (int i = 1; i <= n1; i++) grid [0][i] = 1;
        for (int i = n1 + 1; i <= n1 + n2; i++) grid [i][i + n2] = in.nextInt ();
        for (int i = 1; i <= n1; i++) {
            int k = in.nextInt ();
            for (int j = 0; j < k; j++) {
                int to = in.nextInt ();
                if (i >= to) continue;
                if (to > n1 + n2) to += n2;
                grid [i][to] = INF;
            }
        }
        for (int i = n1 + 1; i <= n1 + n2; i++) {
            int k = in.nextInt ();
            for (int j = 0; j < k; j++) {
                int to = in.nextInt ();
                if (i >= to) continue;
                if (to > n1 + n2) to += n2;
                grid [i + n2][to] = INF;
            }
        }
        for (int i = n1 + n2 * 2 + 1; i < N; i++) grid [i][N] = 1;
        out.println (networkFlow (0, N));
    }
    
    private static int networkFlow (int source, int sink) {
        if (source == sink) return INF;
        int nodes = grid.length;
        int maxFlow, maxLoc, pathCap, curNode, nextNode, i, j;
        int totalFlow = 0;
        int [][] dist = new int [nodes+1][nodes+1];
        for (i = 0; i < nodes; i++) 
            for (j = 0; j < nodes; j++) 
                dist [i][j] = grid [i][j];
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
                            flow [i] < Math.min (maxFlow, dist [maxLoc][i])) {
                        prevNode [i] = maxLoc;
                        flow [i] = Math.min (maxFlow, dist [maxLoc][i]);
                    }
            }
            pathCap = flow [sink];
            totalFlow += pathCap;
            curNode = sink;
            while (curNode != source)
            {
                nextNode = prevNode [curNode];
                dist [nextNode][curNode] -= pathCap;
                dist [curNode][nextNode] += pathCap;
                curNode = nextNode;
            }
        }
        return totalFlow;
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