import java.io.*;
import java.util.*;

public class stargate
{
    private static BufferedReader in;
    private static PrintWriter out;
    
    private static int [] ptr, size;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        
        String [] st;
        int [] commands = new int [6];
        ptr = new int [6000000];
        size = new int [6000000];
        String line = in.readLine ();
        while (line != null) {
            st = line.split (" ");
            char c = st [0].charAt (0);
            commands = new int [] {1, 1, 1, 1, 0};
            switch (c) {
                case 'd' : case 'D' :
                    int N = Integer.parseInt (st [1]);
                    for (int i = 0; i < N; i++) {
                        ptr [i] = i;
                        size [i] = 1;
                    }
                    break;
                case 'c' : case 'C' :
                    for (int i = 1; i < st.length; i++)
                        commands [i - 1] = Integer.parseInt (st [i]);
                    commands [0]--; commands [1]--;
                    connect (commands);
                    break;
                case 'q' : case 'Q' : 
                    for (int i = 1; i < st.length; i++)
                        commands [i - 1] = Integer.parseInt (st [i]);
                    commands [0]--; commands [1]--;
                    int [] a = query (commands);
                    out.printf ("%d %d\n", a [0], a [1]);
                    break;
            }
            
            line = in.readLine ();
        }
    }
    
    private static int [] query (int [] x) {
        int [] ans = new int [2];
        for (int i = 0; i < x [2]; i++) {
            if (query (x [0] + i * x [4], x [1] + i * x [3]))
                ans [0]++;
            else ans [1]++;
        }
        return ans;
    }
    
    private static void connect (int [] x) {
        for (int i = 0; i < x [2]; i++)
            join (x [0] + i * x [4], x [1] + i * x [3]);
    }
    
    private static int find (int a) {
        int p1 = ptr [a];
        while (p1 != ptr [p1]) ptr [a] = p1 = ptr [p1];
        return ptr [a] = p1;
    }
    
    private static boolean query (int a, int b) {
        return find (a) == find (b);
    }
    
    private static void join (int a, int b) {
        int p1 = find (a), p2 = find (b);
        if (p1 == p2) return;
        if (size [p1] < size [p2]) {
            p1 ^= p2; p2 ^= p1; p1 ^= p2;
        }
        size [p1] += size [p2];
        ptr [p2] = p1;
    }
}