import java.io.*;
import java.util.*;

public class importa
{
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        double [] exp = new double [27];
        exp [0] = 1; exp [1] = 0.95;
        for (int i = 2; i < 27; i++)
            exp [i] = exp [i - 1] * 0.95;
        for (;;) {
            String line = in.readLine ();
            if (line == null) break;
            int N = Integer.parseInt (line);
            int [][] grid = new int [27][27];
            int [] val = new int [27];
            for (int i = 0; i < 27; i++) {
                Arrays.fill (grid [i], 100000000);
                val [i] = -1000000000;
            }
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer (in.readLine ());
                int planet = (int)(st.nextToken ().charAt (0) - 'A');
                val [planet] = (int)(Double.parseDouble (st.nextToken ()) * 95);
                char [] connect = st.nextToken ().toCharArray ();
                for (int j = 0; j < connect.length; j++) {
                    if (connect [j] == '*') grid [planet][26] = 1;
                    else grid [planet][connect [j] - 'A'] = 1;
                }
            }
            for (int i = 0; i < 27; i++)
                for (int j = 0; j < 27; j++)
                    for (int k = 0; k < 27; k++)
                        if (grid [i][k] + grid [k][j] < grid [i][j])
                            grid [i][j] = grid [i][k] + grid [k][j];
            
            int max = 0; char planet = 'A' - 1;
            for (int i = 0; i < 26; i++) {
                if (grid [i][26] < 27)
                    val [i] *= exp [grid [i][26]];
                else
                    val [i] = 0;
                if (val [i] > max) {
                    max = val [i];
                    planet = (char) (i + 'A');
                }
            }
            out.println ("Import from " + planet);
        }
    }
}