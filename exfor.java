import java.io.*;
import java.util.*;

public class exfor
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            out.println (f (s (in.nextInt ()), s (in.nextInt ()), s (in.nextInt ()), s (in.nextInt ()),
                            s (in.nextInt ()), s (in.nextInt ()), s (in.nextInt ()), s (in.nextInt ()),
                            s (in.nextInt ()), s (in.nextInt ())) ? "1" : "0");
        }
    }
    
    private static boolean s (int a) {
        return a != 0;
    }
    
    private static boolean f (boolean x1, boolean x2, boolean x3, boolean x4, boolean x5, 
                            boolean x6, boolean x7, boolean x8, boolean x9, boolean x10) {
         return (x1 || x2)^(x1 || x3)^(x1 || x4)^(x1 || x5)^(x1 || x6)^(x1 || x7)^(x1 || x8)^(x1 || x9)^
            (x1 || x10)^(x2 || x3)^(x2 || x4)^(x2 || x5)^(x2 || x6)^(x2 || x7)^(x2 || x8)^(x2 || x9)^(x2 || x10)^
            (x3 || x4)^(x3 || x5)^(x3 || x6)^(x3 || x7)^(x3 || x8)^(x3 || x9)^(x3 || x10)^(x4 || x5)^(x4 || x6)^
            (x4 || x7)^(x4 || x8)^(x4 || x9)^(x4 || x10)^(x5 || x6)^(x5 || x7)^(x5 || x8)^(x5 || x9)^(x5 || x10)^
            (x6 || x7)^(x6 || x8)^(x6 || x9)^(x6 || x10)^(x7 || x8)^(x7 || x9)^(x7 || x10)^(x8 || x9) ^ (x8 || x10) ^
            (x9 || x10) ^ (x1 || x2 || x3) ^ (x1 || x2 || x4) ^ (x1 || x2 || x5) ^ (x1 || x2 || x6) ^
            (x1 || x2 || x7) ^ (x1 || x2 || x8) ^ (x1 || x2 || x9) ^ (x1 || x2 || x10) ^ (x1 || x3 || x4) ^ 
            (x1 || x3 || x5) ^(x1 || x3 || x6) ^ (x1 || x3 || x7) ^ (x1 || x3 || x8) ^ (x1 || x3 || x9) ^ 
            (x1 || x3 || x10) ^ (x1 || x4 || x5) ^(x1 || x4 || x6) ^ (x1 || x4 || x7) ^ (x1 || x4 || x8) ^ 
            (x1 || x4 || x9) ^ (x1 || x4 || x10) ^ (x1 || x5 || x6) ^(x1 || x5 || x7) ^ (x1 || x5 || x8) ^ 
            (x1 || x5 || x9) ^ (x1 || x5 || x10) ^ (x1 || x6 || x7) ^ (x1 || x6 || x8) ^(x1 || x6 || x9) ^ 
            (x1 || x6 || x10) ^ (x1 || x7 || x8) ^ (x1 || x7 || x9) ^ (x1 || x7 || x10) ^ (x1 || x8 || x9) ^
            (x1 || x8 || x10) ^ (x1 || x9 || x10) ^ (x2 || x3 || x4) ^ (x2 || x3 || x5) ^ (x2 || x3 || x6) ^
            (x2 || x3 || x7) ^(x2 || x3 || x8) ^ (x2 || x3 || x9) ^ (x2 || x3 || x10) ^ (x2 || x4 || x5) ^ 
            (x2 || x4 || x6) ^ (x2 || x4 || x7) ^(x2 || x4 || x8) ^ (x2 || x4 || x9) ^ (x2 || x4 || x10) ^ 
            (x2 || x5 || x6) ^ (x2 || x5 || x7) ^ (x2 || x5 || x8) ^(x2 || x5 || x9) ^ (x2 || x5 || x10) ^
            (x2 || x6 || x7) ^ (x2 || x6 || x8) ^ (x2 || x6 || x9) ^ (x2 || x6 || x10) ^(x2 || x7 || x8) ^ 
            (x2 || x7 || x9) ^ (x2 || x7 || x10) ^ (x2 || x8 || x9) ^ (x2 || x8 || x10) ^ (x2 || x9 || x10) ^
            (x3 || x4 || x5) ^ (x3 || x4 || x6) ^ (x3 || x4 || x7) ^ (x3 || x4 || x8) ^ (x3 || x4 || x9) ^ 
            (x3 || x4 || x10) ^(x3 || x5 || x6) ^ (x3 || x5 || x7) ^ (x3 || x5 || x8) ^ (x3 || x5 || x9) ^
            (x3 || x5 || x10) ^ (x3 || x6 || x7) ^(x3 || x6 || x8) ^ (x3 || x6 || x9) ^ (x3 || x6 || x10) ^
            (x3 || x7 || x8) ^ (x3 || x7 || x9) ^ (x3 || x7 || x10) ^(x3 || x8 || x9) ^ (x3 || x8 || x10) ^ 
            (x3 || x9 || x10) ^ (x4 || x5 || x6) ^ (x4 || x5 || x7) ^ (x4 || x5 || x8) ^(x4 || x5 || x9) ^
            (x4 || x5 || x10) ^ (x4 || x6 || x7) ^ (x4 || x6 || x8) ^ (x4 || x6 || x9) ^ (x4 || x6 || x10) ^
            (x4 || x7 || x8) ^ (x4 || x7 || x9) ^ (x4 || x7 || x10) ^ (x4 || x8 || x9) ^ (x4 || x8 || x10) ^ 
            (x4 || x9 || x10) ^(x5 || x6 || x7) ^ (x5 || x6 || x8) ^ (x5 || x6 || x9) ^ (x5 || x6 || x10) ^ 
            (x5 || x7 || x8) ^ (x5 || x7 || x9) ^(x5 || x7 || x10) ^ (x5 || x8 || x9) ^ (x5 || x8 || x10) ^
            (x5 || x9 || x10) ^ (x6 || x7 || x8) ^ (x6 || x7 || x9) ^(x6 || x7 || x10) ^ (x6 || x8 || x9) ^
            (x6 || x8 || x10) ^ (x6 || x9 || x10) ^ (x7 || x8 || x9) ^ (x7 || x8 || x10) ^(x7 || x9 || x10) ^
            (x8 || x9 || x10);
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