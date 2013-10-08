import java.io.*;
import java.util.*;

public class ctain
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt ();
        while (t-- > 0) {
            int n = in.nextInt ();
            int ans = process (n);
            if (ans == -1) out.println ("NO");
            else out.println (ans);
        }
    }
    
    private static int process (int n) throws IOException {
        switch (n) {
            case 1 : return one ();
            case 2 : return two ();
            case 3 : return three ();
            case 4 : return four ();
        }
        return -1;
    }
    
    private static int one () throws IOException {
        int initial = in.nextInt ();
        int goal = in.nextInt ();
        if (initial == goal) return 0;
        if (goal == 0) return 1;
        return -1;
    }
    
    private static int two () throws IOException {
        int [] initial = new int [2];
        for (int i = 0; i < 2; i++)
            initial [i] = in.nextInt ();
        int [] goal = new int [2];
        for (int i = 0; i < 2; i++)
            goal [i] = in.nextInt ();
        boolean [][] visited = new boolean [50][50];
        LinkedList <int []> queue = new LinkedList <int []> ();
        LinkedList <Integer> moves = new LinkedList <Integer> ();
        queue.add (initial); moves.add (0);
        while (queue.size () > 0) {
            int [] state = queue.removeFirst ();
            int move = moves.removeFirst ();
            if (visited [state [0]][state [1]]) continue;
            visited [state [0]][state [1]] = true;
            if (state [0] == goal [0] && state [1] == goal [1])
                return move;
                
            if (state [0] > 0) {
                queue.add (new int [] {0, state [1]});
                moves.add (move + 1);
                if (initial [1] - state [1] > state [0]) {
                    queue.add (new int [] {0, state [1] + state [0]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0] - (initial [1] - state [1]), initial [1]});
                    moves.add (move + 1);
                }
            }
            if (state [1] > 0) {
                queue.add (new int [] {state [0], 0});
                moves.add (move + 1);
                if (initial [0] - state [0] > state [1]) {
                    queue.add (new int [] {state [1] + state [0], 0});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {initial [0], state [1] - (initial [0] - state [0])});
                    moves.add (move + 1);
                }
            }
        }
        return -1;
    }
    
    private static int three () throws IOException {
        int [] initial = new int [3];
        for (int i = 0; i < 3; i++)
            initial [i] = in.nextInt ();
        int [] goal = new int [3];
        for (int i = 0; i < 3; i++)
            goal [i] = in.nextInt ();
        boolean [][][] visited = new boolean [50][50][50];
        LinkedList <int []> queue = new LinkedList <int []> ();
        LinkedList <Integer> moves = new LinkedList <Integer> ();
        queue.add (initial); moves.add (0);
        while (queue.size () > 0) {
            int [] state = queue.removeFirst ();
            int move = moves.removeFirst ();
            if (visited [state [0]][state [1]][state [2]]) continue;
            visited [state [0]][state [1]][state [2]] = true;
            if (state [0] == goal [0] && state [1] == goal [1] && state [2] == goal [2])
                return move;
                
            if (state [0] > 0) {
                queue.add (new int [] {0, state [1], state [2]});
                moves.add (move + 1);
                if (initial [1] - state [1] > state [0]) {
                    queue.add (new int [] {0, state [1] + state [0], state [2]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0] - (initial [1] - state [1]), initial [1], state [2]});
                    moves.add (move + 1);
                }
                if (initial [2] - state [2] > state [0]) {
                    queue.add (new int [] {0, state [1], state [2] + state [0]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0] - (initial [2] - state [2]), state [1], initial [2]});
                    moves.add (move + 1);
                }
            }
            if (state [1] > 0) {
                queue.add (new int [] {state [0], 0, state [2]});
                moves.add (move + 1);
                if (initial [0] - state [0] > state [1]) {
                    queue.add (new int [] {state [1] + state [0], 0, state [2]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {initial [0], state [1] - (initial [0] - state [0]), state [2]});
                    moves.add (move + 1);
                }
                if (initial [2] - state [2] > state [1]) {
                    queue.add (new int [] {state [0], 0, state [2] + state [1]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0], state [1] - (initial [2] - state [2]), initial [2]});
                    moves.add (move + 1);
                }
            }
            if (state [2] > 0) {
                queue.add (new int [] {state [0], state [1], 0});
                moves.add (move + 1);
                if (initial [0] - state [0] > state [2]) {
                    queue.add (new int [] {state [0] + state [2], state [1], 0});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {initial [0], state [1], state [2] - (initial [0] - state [0])});
                    moves.add (move + 1);
                }
                if (initial [1] - state [1] > state [2]) {
                    queue.add (new int [] {state [0], state [1] + state [2], 0});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0],  initial [1], state [2] - (initial [1] - state [1])});
                    moves.add (move + 1);
                }
            }
        }
        return -1;
    }
    
    private static int four () throws IOException {
        int [] initial = new int [4];
        for (int i = 0; i < 4; i++)
            initial [i] = in.nextInt ();
        int [] goal = new int [4];
        for (int i = 0; i < 4; i++)
            goal [i] = in.nextInt ();
        boolean [][][][] visited = new boolean [50][50][50][50];
        LinkedList <int []> queue = new LinkedList <int []> ();
        LinkedList <Integer> moves = new LinkedList <Integer> ();
        queue.add (initial); moves.add (0);
        while (queue.size () > 0) {
            int [] state = queue.removeFirst ();
            int move = moves.removeFirst ();
            if (visited [state [0]][state [1]][state [2]][state [3]]) continue;
            visited [state [0]][state [1]][state [2]][state [3]] = true;
            if (state [0] == goal [0] && state [1] == goal [1] && state [2] == goal [2] && state [3] == goal [3])
                return move;
                
            if (state [0] > 0) {
                queue.add (new int [] {0, state [1], state [2], state [3]});
                moves.add (move + 1);
                if (initial [1] - state [1] > state [0]) {
                    queue.add (new int [] {0, state [1] + state [0], state [2], state [3]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0] - (initial [1] - state [1]), initial [1], state [2], state [3]});
                    moves.add (move + 1);
                }
                if (initial [2] - state [2] > state [0]) {
                    queue.add (new int [] {0, state [1], state [2] + state [0], state [3]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0] - (initial [2] - state [2]), state [1], initial [2], state [3]});
                    moves.add (move + 1);
                }
                if (initial [3] - state [3] > state [0]) {
                    queue.add (new int [] {0, state [1], state [2], state [3] + state [0]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0] - (initial [3] - state [3]), state [1], state [2], initial [3]});
                    moves.add (move + 1);
                }
            }
            if (state [1] > 0) {
                queue.add (new int [] {state [0], 0, state [2], state [3]});
                moves.add (move + 1);
                if (initial [0] - state [0] > state [1]) {
                    queue.add (new int [] {state [1] + state [0], 0, state [2], state [3]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {initial [0], state [1] - (initial [0] - state [0]), state [2], state [3]});
                    moves.add (move + 1);
                }
                if (initial [2] - state [2] > state [1]) {
                    queue.add (new int [] {state [0], 0, state [2] + state [1], state [3]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0], state [1] - (initial [2] - state [2]), initial [2], state [3]});
                    moves.add (move + 1);
                }
                if (initial [3] - state [3] > state [1]) {
                    queue.add (new int [] {state [0], 0, state [2], state [3] + state [1]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0], state [1] - (initial [3] - state [3]), state [2], initial [3]});
                    moves.add (move + 1);
                }
            }
            if (state [2] > 0) {
                queue.add (new int [] {state [0], state [1], 0, state [3]});
                moves.add (move + 1);
                if (initial [0] - state [0] > state [2]) {
                    queue.add (new int [] {state [0] + state [2], state [1], 0, state [3]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {initial [0], state [1], state [2] - (initial [0] - state [0]), state [3]});
                    moves.add (move + 1);
                }
                if (initial [1] - state [1] > state [2]) {
                    queue.add (new int [] {state [0], state [1] + state [2], 0, state [3]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0],  initial [1], state [2] - (initial [1] - state [1]), state [3]});
                    moves.add (move + 1);
                }
                if (initial [3] - state [3] > state [2]) {
                    queue.add (new int [] {state [0], state [1], 0, state [3] + state [2]});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0], state [1], state [2] - (initial [3] - state [3]), initial [3]});
                    moves.add (move + 1);
                }
            }
            if (state [3] > 0) {
                queue.add (new int [] {state [0], state [1], state [2], 0});
                moves.add (move + 1);
                if (initial [0] - state [0] > state [3]) {
                    queue.add (new int [] {state [0] + state [3], state [1], state [2], 0});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {initial [0], state [1], state [2], state [3] - (initial [0] - state [0])});
                    moves.add (move + 1);
                }
                if (initial [1] - state [1] > state [3]) {
                    queue.add (new int [] {state [0], state [1] + state [3], state [2], 0});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0],  initial [1], state [2], state [3] - (initial [1] - state [1])});
                    moves.add (move + 1);
                }
                if (initial [2] - state [2] > state [3]) {
                    queue.add (new int [] {state [0], state [1], state [3] + state [2], 0});
                    moves.add (move + 1);
                } else {
                    queue.add (new int [] {state [0], state [1], initial [2], state [3] - (initial [2] - state [2])});
                    moves.add (move + 1);
                }
            }
        }
        return -1;
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