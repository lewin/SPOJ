import java.io.*;
import java.util.*;

public class mtreecol
{
    private static Reader in;
    private static PrintWriter out;
    
    private static long [] par, cost, time, add;
    private static ArrayList <Integer> [] child;

    public static void main (String [] args) throws IOException {
        long start = System.currentTimeMillis ();
        in = new Reader ("mtreecol.in");
        out = new PrintWriter (System.out, true);
        
        while (true) {
            int N = in.nextInt (), R = in.nextInt () - 1;
            if (N == 0 && R == -1) break;
            cost = new long [N];
            par = new long [N];
            add = new long [N];
            time = new long [N];
            child = new ArrayList [N];
            
            for (int i = 0; i < N; i++) {
                cost [i] = in.nextInt ();
                time [i] = 1;
                add [i] = 0;
                child [i] = new ArrayList <Integer> ();
            }
            
            for (int i = 0; i < N - 1; i++) {
                int a = in.nextInt () - 1, b = in.nextInt () - 1;
                par [b] = a;
                child [a].add (b);
            }
            par [R] = -1;
            
            long ans = 0, t = 1;
            boolean [] vis = new boolean [N];
            int count = 0;
            PriorityQueue <Triplet> pq = new PriorityQueue <Triplet> ();
            for (int i = 0; i < N; i++) pq.add (new Triplet (i, cost [i], 1));
            while (count < N) {
                int node = (int)pq.poll ().a;
                if (vis [node]) continue;
                count++;
                vis [node] = true;
                if (par [node] == -1) {
                    ans += cost [node] * t + add [node];
                    t += time [node];
                    for (int e : child [node])
                        par [e] = -1;
                } else {
                    int p = (int)par [node];
                    long ncost = cost [p] + cost [node],
                         nadd = add [p] + add [node] + cost [node] * time [p],
                         ntime = time [p] + time [node];
                    cost [p] = ncost;
                    add [p] = nadd;
                    time [p] = ntime;
                    for (int e : child [node]) {
                        par [e] = p;
                        child [p].add (e);
                    }
                    pq.add (new Triplet (p, cost [p], time [p]));
                }
            }
            
            out.println (ans);
            out.println ("Time: " + (System.currentTimeMillis () - start) / 1000. + "s");
        }
    }
    
    static class Triplet implements Comparable <Triplet> {
        public long a, b, c;
        
        public Triplet (long a, long b, long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        public int compareTo (Triplet other) {
            return (int)Math.signum (other.b * c - other.c * b);
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