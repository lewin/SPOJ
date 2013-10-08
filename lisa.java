import java.io.*;
import java.util.*;

public class lisa
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            char [] expression = in.readLine ().toCharArray ();
            int n = expression.length;
            long [][] dp_min = new long [n][n];
            for (int i = 0; i < n; i++)
                Arrays.fill (dp_min [i], Long.MAX_VALUE / 2);
            long [][] dp_max = new long [n][n];
            for (int i = 0; i < n; i += 2)
                dp_min [i][0] = dp_max [i][0] = expression [i] - '0';
            for (int j = 2; j < n; j += 2)
                for (int i = 0; i + j < n; i += 2)
                    for (int k = i + 1; k <= i + j - 1; k += 2) {
                        boolean add = expression [k] == '+';
                        int left = k - i - 1, right = i + j - k - 1;
                        if (add) {
                            if (dp_min [i][left] + dp_min [k + 1][right] < dp_min [i][j])
                                dp_min [i][j] = dp_min [i][left] + dp_min [k + 1][right];
                            if (dp_max [i][left] + dp_max [k + 1][right] > dp_max [i][j])
                                dp_max [i][j] = dp_max [i][left] + dp_max [k + 1][right];
                        } else {
                            if (dp_min [i][left] * dp_min [k + 1][right] < dp_min [i][j])
                                dp_min [i][j] = dp_min [i][left] * dp_min [k + 1][right];
                            if (dp_max [i][left] * dp_max [k + 1][right] > dp_max [i][j])
                                dp_max [i][j] = dp_max [i][left] * dp_max [k + 1][right];
                        }
                    }
            out.printf ("%d %d\n", dp_max [0][n - 1], dp_min [0][n - 1]);
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