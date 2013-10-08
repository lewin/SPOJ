import java.io.*;
import java.util.*;

public class mlaserp
{
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        StringTokenizer st = new StringTokenizer (in.readLine());
        int w = Integer.parseInt(st.nextToken()),
            h = Integer.parseInt(st.nextToken());
        boolean [][] grid = new boolean [w][h];
        int cx1 = -1, cy1 = -1, cx2 = -1, cy2 = -1;
        for (int j = 0; j < h; j++) {
            char [] line = in.readLine ().toCharArray ();
            for (int i = 0; i < w; i++) {
                if (line [i] == 'C') {
                    if (cx1 == -1)
                        {cx1 = i; cy1 = j;}
                    else
                        {cx2 = i; cy2 = j;}
                }
                grid [i][j] = (line [i] == '*');
            }
        }
        
        int [] queueX = new int [100000];
        int [] queueY = new int [100000];
        int front = 0, back = 0;
        queueX [front] = cx1;
        queueY [front++] = cy1;
        int x, y;
        int [][] path = new int [w][h];
        outer : while (front != back) {
            x = queueX [back];
            y = queueY [back++];
            int cnt = path [x][y];
            // up
            for (int i = x - 1; i >= 0; i--) {
                if (grid [i][y]) break;
                if (path [i][y] == 0) {
                    path [i][y] = cnt + 1;
                    queueX [front] = i;
                    queueY [front++] = y;
                    if (i == cx2 && y == cy2)
                        break outer;
                }
            }
            // down
            for (int i = x + 1; i < w; i++) {
                if (grid [i][y]) break;
                if (path [i][y] == 0) {
                    path [i][y] = cnt + 1;
                    queueX [front] = i;
                    queueY [front++] = y;
                    if (i == cx2 && y == cy2)
                        break outer;
                }
            }
            // left
            for (int i = y - 1; i >= 0; i--) {
                if (grid [x][i]) break;
                if (path [x][i] == 0) {
                    path [x][i] = cnt + 1;
                    queueX [front] = x;
                    queueY [front++] = i;
                    if (x == cx2 && i == cy2)
                        break outer;
                }
            }
            // right
            for (int i = y + 1; i < h; i++) {
                if (grid [x][i]) break;
                if (path [x][i] == 0) {
                    path [x][i] = cnt + 1;
                    queueX [front] = x;
                    queueY [front++] = i;
                    if (x == cx2 && i == cy2)
                        break outer;
                }
            }
        }
        
        out.println (path [cx2][cy2] - 1);
    }
}