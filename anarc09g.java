import java.io.*;
import java.util.*;

public class anarc09g
{
    private static Reader in;
    private static PrintWriter out;
    private static int [] elast, eprev, eadj;
    private static int eidx;
    private static boolean [] visited;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int p = 1;
        while (true) {
            int n = in.nextInt (), t = in.nextInt ();
            if (n == 0 && t == 0) break;
            elast = new int [n];
            eprev = new int [n * n + 1];
            eadj = new int [n * n + 1];
            Arrays.fill (elast, -1);
            int count = 0;
            for (int i = 0; i < t; i++) {
                int a = in.nextInt () - 1, b = in.nextInt () - 1;
                visited = new boolean [n];
                if (ok (a, b, -1))
                    addEdge (a, b);
                else count++;
            }
            out.printf ("%d. %d\n", p++, count);
        }
    }
    
    private static boolean ok (int target, int cur, int prev) {
        if (target == cur) return false;
        if (visited [cur]) return true;
        visited [cur] = true;
        for (int e = elast [cur]; e != -1; e = eprev [e]) {
            if (eadj [e] == prev) continue;
            if (!ok (target, eadj [e], cur)) return false;
        }
        return true;
    }
    
    private static void addEdge (int a, int b) {
        eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
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