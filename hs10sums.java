import java.io.*;
import java.util.*;

public class hs10sums
{
    private static Reader in;
    private static PrintWriter out;
    
    private static ArrayList <ArrayList <Integer>> [] ways;
    private static int lim = 50;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        ways = new ArrayList [lim + 1];
        for (int i = 0; i <= lim; i++)
            ways [i] = new ArrayList <ArrayList <Integer>> ();
        rec (0, 0, new ArrayList <Integer> ());
        System.out.println ("Ready");
        int t = in.nextInt ();
        while (t-- > 0) {
            int n = in.nextInt ();
            if (ways [n].size () == 0)
                out.println ("Impossible");
            else {
                for (ArrayList <Integer> p : ways [n]) {
                    boolean first = true;
                    for (int k : p) {
                        if (!first) out.print ("+");
                        out.print (k);
                        first = false;
                    }
                    out.println ("=" + n);
                }
            }
        }
        System.exit (0);
    }
    
    private static void rec (int sum, int idx, ArrayList <Integer> list) {
        if (sum > lim) return;
        ways [sum].add (list);
        for (int i = idx + 1; i + sum <= lim; i++) {
            ArrayList <Integer> temp = new ArrayList <Integer> (list);
            temp.add (i);
            rec (sum + i, i, temp);
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