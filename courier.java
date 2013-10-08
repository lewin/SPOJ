import java.io.*;
import java.util.*;

public class courier
{
    private static Reader in;
    private static PrintWriter out;
    public static final int INF = 1 << 25;
    private static int [][] grid, requests;
    private static int n, z, m, b;

    public static void main (String [] args) throws IOException {
        int [] bitcount = new int [1 << 12];
        for (int i = 0; i < 1 << 12; i++) {
            int t = i;
            while (t > 0) {
                if (t % 2 == 1)
                    bitcount [i]++;
                t >>= 1;
            }
        }
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            n = in.nextInt (); m = in.nextInt (); b = in.nextInt () - 1;
            grid = new int [n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill (grid [i], INF);
                grid [i][i] = 0;
            }
            for (int i = 0; i < m; i++) {
                int a = in.nextInt () - 1, c = in.nextInt () - 1, d = in.nextInt ();
                grid [a][c] = Math.min (grid [a][c], d);
                grid [c][a] = Math.min (grid [c][a], d);
            }
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    for(int j = 0; j < n; j++)
                        if (grid [i][k] + grid [k][j] < grid [i][j])
                            grid [i][j] = grid [i][k] + grid [k][j];
            z = in.nextInt ();
            requests = new int [12][2]; int idx = 0;
            int [] end = new int [12];
            int [] invend = new int [100];
            for (int i = 0; i < z; i++) {
                int a = in.nextInt () - 1, c = in.nextInt () - 1, d = in.nextInt ();
                for (int j = 0; j < d; j++) {
                    requests [idx][0] = a;
                    requests [idx][1] = c;
                    idx++;
                }
                end [i] = c;
                invend [c] = i;
            }
            int [][] dp = new int [1 << idx][z];
            for (int i = 1; i < 1 << idx; i++) {
                Arrays.fill (dp [i], INF);
                for (int j = 0; j < idx; j++) {
                    if ((i & (1 << j)) > 0) {
                        int travel = grid [requests [j][0]][requests [j][1]], prev = i - (1 << j);
                        if (bitcount [i] == 1)
                            dp [i][invend [requests [j][1]]] = grid [b][requests [j][0]] + travel;
                        else {
                            for (int k = 0; k < z; k++)
                                if (dp [prev][k] + grid [end [k]][requests [j][0]] + travel < dp [i][invend [requests [j][1]]])
                                    dp [i][invend [requests [j][1]]] = dp [prev][k] + grid [end [k]][requests [j][0]] + travel;
                        }
                    }
                }
            }
            int min = Integer.MAX_VALUE, last = (1 << idx) - 1;
            for (int i = 0; i < z; i++) 
                if (dp [last][i] + grid [end [i]][b] < min)
                    min = dp [last][i] + grid [end [i]][b];
            out.println (min);
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