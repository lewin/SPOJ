import java.io.*;
import java.util.*;

public class cerc07k
{
    private static Reader in;
    private static PrintWriter out;
    
    public static final int INF = 1 << 26;
    public static final int [] pass = new int [] {1, 2, 4, 8};
    public static final char [] door = new char [] {'B', 'Y', 'R', 'G'};
    public static final char [] unlock = new char [] {'b', 'y', 'r', 'g'};
    public static final int [] dx = new int [] {-1, 0, 1, 0},
                               dy = new int [] {0, -1, 0, 1};

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        while (true) {
            int R = in.nextInt(), C = in.nextInt();
            if (R == 0 && C == 0) break;
            char[][] maze = new char [R][C];
            for (int i = 0; i < R; i++)
                maze[i] = in.readLine().toCharArray();
                
            int startx = -1, starty = -1;
            outer: for (int i = 0; i < R; i++)
                for (int j = 0; j < C; j++)
                    if (maze[i][j] == '*') {
                        startx = i; starty = j;
                        break outer;
                    }
                    
            int [][] queuex = new int [16][R * C], queuey = new int [16][R * C];
            int [] front = new int [16], back = new int [16];
            int [][][] dist = new int [16][R][C];
            for (int i = 0; i < 16; i++)
                for (int j = 0; j < R; j++)
                    Arrays.fill (dist [i][j], INF);
                    
            queuex [0][back [0]] = startx; queuey [0][back [0]++] = starty;
            dist [0][startx][starty] = 0;
            int min = INF;
            for (int i = 0; i < 16; i++) {
                while (front [i] < back [i]) {
                    int curx = queuex [i][front[i]], cury = queuey [i][front[i]++];
                    for (int j = 0; j < 4; j++) {
                        int nx = curx + dx[j], ny = cury + dy[j];
                        if (nx < R && nx >= 0 && ny < C && ny >= 0) {
                            boolean ok = maze[nx][ny] != '#';
                            for (int t = 0; ok && t < 4; t++)
                                if ((i & pass [t]) == 0) {
                                    if (maze[nx][ny] == door [t]) {
                                        ok = false;
                                        break;
                                    }
                                }
                            if (!ok) continue;
                            int s = i;
                            for (int t = 0; t < 4; t++)
                                if (maze[nx][ny] == unlock [t])
                                    s |= pass [t];
                            if (dist [s][nx][ny] != INF) continue;
                            queuex [s][back[s]] = nx; queuey [s][back[s]++] = ny;
                            dist [s][nx][ny] = dist [i][curx][cury] + 1;
                            if (maze[nx][ny] == 'X') {
                                if (dist [s][nx][ny] < min)
                                    min = dist [s][nx][ny];
                            }
                        }
                    }
                }
            }
            
            if (min == INF) out.println ("The poor student is trapped!");
            else out.printf ("Escape possible in %d steps.\n", min);
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