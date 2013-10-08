import java.io.*;
import java.util.*;

public class hexboard
{
    private static Reader in;
    private static PrintWriter out;
    private static char [] top = {'_', '/', ' ', '\\'};
    private static char [] bottom = {'\\', '_', '/', ' '};

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        while (true) {
            int n = in.nextInt ();
            if (n == -1) break;
            printHex (n);
            out.println ("***");
        }
    }
    
    private static void printHex (int n) {
        int start = 2 * n - 1; int p = 1;
        for (; start >= 0; start -= 2, p += 4) {
            for (int i = 0; i < start; i++) out.print (" ");
            for (int i = 0; i < p; i++)  out.print (top [i % 4]);
            out.println ();
        }
        for (int i = 0; i < 2 * n; i++) {
            if (i % 2 == 0) {
                for (int j = 1; j <= 4 * (n - 1); j++) out.print (top [j % 4]);
                out.println ("/ \\");
            } else {
                for (int j = 0; j < 4 * (n - 1); j++) out.print (bottom [j % 4]);
                out.println ("\\_/");
            }
        }
        for (int i = 2; i < 2 * n; i += 2) {
            for (int j = 0; j < i - 1; j++) out.print (" ");
            for (int j = 0; j < n - i / 2; j++) out.print (" \\_/");
            out.println ();
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