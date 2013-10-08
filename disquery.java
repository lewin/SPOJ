import java.io.*;
import java.util.*;

public class disquery
{
    private static Reader in;
    private static PrintWriter out;
    
    private static int N;
    
    private static int [] eadj, elast, eprev, ecost;
    private static int eidx;
    private static void addEdge (int a, int b, int c) {
        eadj [eidx] = b; ecost [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
        eadj [eidx] = a; ecost [eidx] = c; eprev [eidx] = elast [b]; elast [b] = eidx++;
    }
    
    private static int [][] anc, min, max;
    private static int [] depth;
    private static void init () {
        anc = new int [20][N]; min = new int [20][N]; max = new int [20][N];
        int [] topo = new int [N];
        depth = new int [N];
        boolean [] vis = new boolean [N];
        int front = 0, back = 0;
        topo [back++] = 0;
        vis [0] = true;
        anc [0][0] = -1;
        depth [0] = 0;
        while (front < back) {
            int cur = topo [front++];
            for (int e = elast [cur]; e != -1; e = eprev [e]) {
                if (vis [eadj [e]]) continue;
                vis [eadj [e]] = true;
                topo [back++] = eadj [e];
                depth [eadj [e]] = depth [cur] + 1;
                anc [0][eadj [e]] = cur;
                min [0][eadj [e]] = ecost [e];
                max [0][eadj [e]] = ecost [e];
            }
        }
        
        for (int i = 1; i < 20; i++)
            for (int j = 0; j < N; j++) {
                anc [i][j] = anc [i - 1][j] == -1 ? -1 :
                        anc [i - 1][anc [i - 1][j]];
                min [i][j] = anc [i - 1][j] == -1 ? min [i - 1][j] :
                        Math.min (min [i - 1][j], min [i - 1][anc [i - 1][j]]);
                max [i][j] = anc [i - 1][j] == -1 ? max [i - 1][j] :
                        Math.max (max [i - 1][j], max [i - 1][anc [i - 1][j]]);
            }
    }
    
    private static int [] query (int a, int b) {
        if (depth [a] < depth [b]) {
            a ^= b; b ^= a; a ^= b;
        }
        
        if (a == b) return new int [] {0, 0};
        
        int [] ret = new int [] {-1, 2000000};
        int diff = depth [a] - depth [b];
        for (int i = 0; (1 << i) <= diff; i++)
            if (((1 << i) & diff) != 0) {
                if (max [i][a] > ret [0]) ret [0] = max [i][a];
                if (min [i][a] < ret [1]) ret [1] = min [i][a];
                a = anc [i][a];
            }
            
        if (a == b) return ret;
        
        int log = 0;
        while ((1 << log) < depth [a]) log++;
        
        for (int i = log; i >= 0; i--) {
            if (anc [i][a] != anc [i][b]) {
                if (max [i][a] > ret [0]) ret [0] = max [i][a];
                if (min [i][a] < ret [1]) ret [1] = min [i][a];
                a = anc [i][a];
                if (max [i][b] > ret [0]) ret [0] = max [i][b];
                if (min [i][b] < ret [1]) ret [1] = min [i][b];
                b = anc [i][b];
            }
        }
        
        if (max [0][a] > ret [0]) ret [0] = max [0][a];
        if (min [0][a] < ret [1]) ret [1] = min [0][a];
        a = anc [0][a];
        if (max [0][b] > ret [0]) ret [0] = max [0][b];
        if (min [0][b] < ret [1]) ret [1] = min [0][b];
        b = anc [0][b];
        
        assert (a == b);
        return ret;
    }

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        N = in.nextInt ();
        eadj = new int [2 * N]; elast = new int [N];
        eprev = new int [2 * N]; ecost = new int [2 * N];
        eidx = 0; Arrays.fill (elast, -1);
        for (int i = 0; i < N - 1; i++)
            addEdge (in.nextInt () - 1, in.nextInt () - 1, in.nextInt ());
            
        init();
        int M = in.nextInt ();
        for (int i = 0; i < M; i++) {
            int [] ans = query (in.nextInt () - 1, in.nextInt () - 1);
            out.println (ans [1] + " " + ans [0]);
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