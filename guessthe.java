import java.io.*;
import java.util.*;

public class guessthe
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int [][] pre = new int [21][];
        pre [2] = new int [0]; pre [3] = new int [0];
        pre [4] = new int [] {2}; pre [5] = new int [0];
        pre [6] = new int [] {2, 3}; pre [7] = new int [0];
        pre [8] = new int [] {2, 4}; pre [9] = new int [] {3};
        pre [10] = new int [] {2, 5}; pre [11] = new int [0];
        pre [12] = new int [] {2, 3, 4, 6}; pre [13] = new int [0];
        pre [14] = new int [] {2, 7}; pre [15] = new int [] {3, 5};
        pre [16] = new int [] {2, 4, 8}; pre [17] = new int [0];
        pre [18] = new int [] {2, 3, 6, 9}; pre [19] = new int [0];
        pre [20] = new int [] {2, 4, 5, 10};
        int [] div = new int [21];
        Arrays.fill (div, 1);
        div [4] = 2; div [6] = 6; div [8] = 4; div [9] = 3;
        div [10] = 10; div [12] = 12; div [14] = 14; div [15] = 15;
        div [16] = 8; div [18] = 18; div [20] = 20;
        while (true) {
            char [] arr = in.readLine ().toCharArray ();
            if (arr [0] == '*') break;
            if (arr [0] == 'N') out.println (-1);
            else {
                long ans = 1;
                boolean [] used = new boolean [21];
                outer : for (int i = 1; i < arr.length; i++) {
                    if (arr [i] == 'Y') {
                        for (int j = 0; j < pre [i + 1].length; j++) {
                            if (!used [pre [i + 1][j]]) { 
                                ans = -1;
                                break outer;
                            }
                        }
                        ans *= (i + 1);
                        ans /= div [i + 1];
                        used [i + 1] = true;
                    } else {
                        boolean change = false;
                        switch (i + 1) {
                            case 2: case 3: case 4: case 5: case 7:
                            case 8: case 9: case 11: case 13: case 16:
                            case 17: case 19: break;
                            case 6: if (used [2] && used [3]) change = true; break;
                            case 10: if (used [2] && used [5]) change = true; break;
                            case 12: if (used [3] && used [4]) change = true; break;
                            case 14: if (used [2] && used [7]) change = true; break;
                            case 15: if (used [3] && used [5]) change = true; break;
                            case 18: if (used [2] && used [9]) change = true; break;
                            case 20: if (used [4] && used [5]) change = true; break;
                        }
                        if (change) {
                            ans = -1;
                            break outer;
                        }
                    }
                }
                out.println (ans);
            }
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