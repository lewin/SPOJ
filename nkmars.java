import java.io.*;
import java.util.*;

public class nkmars
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt ();
        Rect [] lines = new Rect [2 * N];
        for (int i = 0; i < N; i++) {
            int x1 = in.nextInt (), y1 = in.nextInt (), x2 = in.nextInt (), y2 = in.nextInt ();
            lines [2 * i] = new Rect (x1, y1, y2, 1);
            lines [2 * i + 1] = new Rect (x2, y1, y2, -1);
        }
        Arrays.sort (lines);
        
        long area = 0;
        int [] active = new int [30001];
        for (int j = lines [0].ydown; j <= lines [0].yup; j++)
            active [j] += lines [0].add;
        for (int i = 1; i < lines.length; i++) {
            if (lines [i].xval != lines [i - 1].xval) {
                int count = 0;
                for (int j = 0; j <= 30000; j++)
                    if (active [j] > 0)
                        count++;
                if (count > 0) count--;
                area += (lines [i].xval - lines [i - 1].xval) * count;
            }
            for (int j = lines [i].ydown; j <= lines [i].yup; j++)
                active [j] += lines [i].add;
        }
        
        out.println (area);
        out.close ();
        System.exit (0);
    }
    
    static class Rect implements Comparable <Rect> {
        public int xval, ydown, yup, add;
        
        public Rect (int xval, int ydown, int yup, int add) {
            this.xval = xval; this.yup = yup;
            this.ydown = ydown; this.add = add;
        }
        
        public int compareTo (Rect other) {
            return xval - other.xval;
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