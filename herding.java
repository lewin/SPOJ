import java.io.*;
import java.util.*;

public class herding
{
    private static BufferedReader in;
    private static PrintWriter out;
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;
    private static int [][] grid;
    private static boolean [][] visited;
    private static int n, m;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        StringTokenizer st = new StringTokenizer (in.readLine ());
        n = Integer.parseInt (st.nextToken ());
        m = Integer.parseInt (st.nextToken ());
        grid = new int [n][m];
        for (int i = 0; i < n; i++) {
            char [] line = in.readLine ().toCharArray ();
            for (int j = 0; j < m; j++) {
                switch (line [j]) {
                    case 'S' : grid [i][j] = SOUTH; break;
                    case 'W' : grid [i][j] = WEST;  break;
                    case 'N' : grid [i][j] = NORTH; break;
                    case 'E' : grid [i][j] = EAST;  break;
                }
            }
        }
        visited = new boolean [n][m];
        int count = 0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++)
                if (!visited [i][j]) {
                    mark (i, j);
                    count++;
                }
                
        out.println (count);
    }
    
    private static void mark (int x, int y) {
        if (visited [x][y]) return;
        visited [x][y] = true;
        
        if (x > 0)
            if (grid [x - 1][y] == SOUTH) 
                mark (x - 1, y); 
        if (y > 0)
            if (grid [x][y - 1] == EAST)
                mark (x, y - 1);
        if(x < n - 1)
            if (grid [x + 1][y] == NORTH)
                mark (x + 1, y);
        if (y < m - 1)
            if (grid [x][y + 1] == WEST)
                mark (x, y + 1);
                
        switch (grid [x][y]) {
            case NORTH : mark (x - 1, y); return;
            case WEST  : mark (x, y - 1); return;
            case SOUTH : mark (x + 1, y); return;
            case EAST  : mark (x, y + 1); return;
        }
        
    }
}