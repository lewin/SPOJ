import java.io.*;
import java.util.*;

public class johnny
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int n = in.nextInt ();
        Candy [] candies = new Candy [n];
        for (int i = 0; i < n; i++) candies [i] = new Candy (in.nextInt (), i + 1);
        Arrays.sort (candies);
        int left = -1, right = n; long lSum = 0, rSum = 0;
        rSum += candies [--right].weight;
        int [] idx = new int [n]; int i = 0;
        outer : while (left != right) {
            while (lSum < rSum) {
                lSum += candies [++left].weight;
                idx [i++] = candies [left].index;
                if (left == right) break outer;
            }
            rSum += candies [--right].weight;
        }
        for (int j = 0; j < i; j++) out.println (idx [j]);
    }
    
    static class Candy implements Comparable <Candy> {
        public int index;
        public long weight;
        public Candy (long _weight, int _index) {
            weight = _weight; index = _index;
        }
        public int compareTo (Candy c) {
            return (weight == c.weight ? 0 : (weight > c.weight) ? 1 : -1);
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close() throws IOException{if(din==null) return;din.close();}
}