import java.io.*;
import java.util.*;

public class esjail
{
    private static Reader in;
    private static PrintWriter out;
    
    private static int [] eadj, eprev, elast;
    private static int eidx;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            int n = in.nextInt (), k = in.nextInt (), m = in.nextInt ();
            if (n == -1 && k == -1 && m == -1) break;
            boolean [] locked = new boolean [n];
            int [] unlock = new int [n];
            eadj = new int [2 * m]; eprev = new int [2 * m];
            elast = new int [n]; eidx = 0;
            Arrays.fill (elast, -1);
            Arrays.fill (unlock, -1);
            for (int i = 0; i < k; i++) {
                int a = in.nextInt () - 1, b = in.nextInt () - 1;
                unlock [a] = b; locked [b] = true;
            }            
            for (int i = 0; i < m; i++) {
                int c = in.nextInt () - 1, d = in.nextInt () - 1;
                addEdge (c, d);
            }
            boolean [] first = new boolean [n], second = new boolean [n];
            int [] queue = new int [2 * n];
            int front = 0, back = 0;
            queue [back++] = 0;
            boolean pos = false;
            while (front < back) {
                int cur = queue [front++];
                if (cur == n - 1) {
                    pos = true;
                    break;
                }
                if (second [cur]) continue;
                first [cur] = true;
                if (locked [cur]) continue;
                if (unlock [cur] != -1) {
                    int to = unlock [cur];
                    locked [to] = false;
                    if (first [cur]) queue [back++] = to;
                }
                second [cur] = true;
                for (int e = elast [cur]; e != -1; e = eprev [e])
                    if (!first [eadj [e]])
                        queue [back++] = eadj [e];
            }
            out.println (pos ? "Y" : "N");
        }
    }
    
    private static void addEdge (int a, int b) {
        eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
        eadj [eidx] = a; eprev [eidx] = elast [b]; elast [b] = eidx++;
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