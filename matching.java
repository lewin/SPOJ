import java.io.*;
import java.util.*;

public class matching
{
    private static Reader in;
    private static PrintWriter out;
    
    public static final int INF = 1 << 29;
    public static int N, M, P;
    
    private static int [] eadj, elast, eprev;
    private static int eidx;
        
    private static int [] flow, capa, now;
    private static int [] level;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        N = in.nextInt (); M = in.nextInt (); P = in.nextInt ();
        
        int size = 2 * (P + N + M);
        eadj = new int [size]; elast = new int [N + M + 2];
        eprev = new int [size]; eidx = 0;
        Arrays.fill (elast, -1);
        
        flow = new int [size]; capa = new int [size]; now = new int [N + M + 2];
        level = new int [N + M + 2];
        
        for (int i = 1; i <= N; i++) add_edge (0, i, 1);
        for (int i = 0; i < P; i++)  add_edge (in.nextInt (), in.nextInt () + N, INF);
        for (int i = 1; i <= M; i++) add_edge (i + N, N + M + 1, 1);
            
        out.println (dinic (0, N + M + 1));
        out.close ();
        System.exit (0);
    }
    
    private static void add_edge (int a, int b, int c) {
        eadj [eidx] = b; flow [eidx] = 0; capa [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
        eadj [eidx] = a; flow [eidx] = 0; capa [eidx] = c; eprev [eidx] = elast [b]; elast [b] = eidx++;
    }
    
    private static int dinic (int source, int sink) {
        int res, flow = 0;
        while (bfs (source, sink)) {
            System.arraycopy (elast, 0, now, 0, N + M + 2);
            while ((res = dfs (source, INF, sink)) > 0) flow += res;
        }
        return flow;
    }
    
    private static boolean bfs (int source, int sink) {
        Arrays.fill (level, -1);
        int front = 0, back = 0;
        int [] queue = new int [N + M + 2];
        
        level [source] = 0;
        queue [back++] = source;
        
        while (front < back && level [sink] == -1) {
            int node = queue [front++];
            for (int e = elast [node]; e != -1; e = eprev [e]) {
                int to = eadj [e];
                if (level [to] == -1 && flow [e] < capa [e]) {
                    level [to] = level [node] + 1;
                    queue [back++] = to;
                }
            }
        }
        
        return level [sink] != -1;
    }
    
    private static int dfs (int cur, int curflow, int goal) {
        if (cur == goal) return curflow;
        
        for (int e = now [cur]; e != -1; now [cur] = e = eprev [e]) {
            if (level [eadj [e]] > level [cur] && flow [e] < capa [e]) {
                int res = dfs (eadj [e], Math.min (curflow, capa [e] - flow [e]), goal);
                if (res > 0) {
                    flow [e] += res;
                    flow [e ^ 1] -= res;
                    return res;
                }
            }
        }
        return 0;
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