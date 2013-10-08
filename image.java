import java.io.*;
import java.util.*;

public class image
{
    private static Reader in;
    private static PrintWriter out;
    private static boolean [][] grid, visited;
    
    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        for (;;) {
            int row = in.nextInt (), col = in.nextInt (),
                x = in.nextInt (), y = in.nextInt ();
            if (row == 0 && col == 0 && x == 0 && y == 0) break;
            grid = new boolean [row + 2][col + 2];
            for (int i = 1; i <= row; i++) {
                char [] line = in.readLine ().toCharArray ();
                for (int j = 1; j <= col; j++)
                    grid [i][j] = line [j - 1] == 'X';
            }
            visited = new boolean [row + 2][col + 2];
            out.println (fill (x, y));
        }
    }
    
    private static int fill (int x, int y) {
        if (!grid [x][y]) return 0;
        int count = 0;   
        if (!grid [x + 1][y]) count++;
        if (!grid [x][y + 1]) count++;
        if (!grid [x - 1][y]) count++;
        if (!grid [x][y - 1]) count++;
        visited [x][y] = true;
        if (!visited [x + 1][y] && grid [x + 1][y])         count += fill (x + 1 ,y);
        if (!visited [x + 1][y + 1] && grid [x + 1][y + 1]) count += fill (x + 1, y + 1);
        if (!visited [x + 1][y - 1] && grid [x + 1][y - 1]) count += fill (x + 1, y - 1);
        if (!visited [x][y + 1] && grid [x][y + 1])         count += fill (x, y + 1);
        if (!visited [x][y - 1] && grid [x][y - 1])         count += fill (x, y - 1);
        if (!visited [x - 1][y] && grid [x - 1][y])         count += fill (x - 1, y);
        if (!visited [x - 1][y + 1] && grid [x - 1][y + 1]) count += fill (x - 1, y + 1);
        if (!visited [x - 1][y - 1] && grid [x - 1][y - 1]) count += fill (x - 1, y - 1);
        return count;
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    public Reader(){
        din=new DataInputStream(System.in);
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public Reader(String file_name) throws IOException{
        din=new DataInputStream(new FileInputStream(file_name));
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public String readLine() throws IOException{
        byte[] buf=new byte[64]; // line length
        int cnt=0,c;
        while((c=read())!=-1){
            if(c=='\n')break;
            buf[cnt++]=(byte)c;
        }
        return new String(buf,0,cnt);
    }

    public int nextInt() throws IOException{
        int ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    } 

    public long nextLong() throws IOException{
        long ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    }

    public double nextDouble() throws IOException{
        double ret=0,div=1;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c = read();
        do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')
            ret+=(c-'0')/(div*=10);
        if(neg)return -ret;
        return ret;
    }
    
    private void fillBuffer() throws IOException{
        bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
        if(bytesRead==-1)buffer[0]=-1;
    }
    
    private byte read() throws IOException{
        if(bufferPointer==bytesRead)fillBuffer();
        return buffer[bufferPointer++];
    }
    
    public void close() throws IOException{
        if(din==null) return;
        din.close();
    }
}