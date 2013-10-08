import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class ykh
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(f.readLine());
        int B, c, i; StringTokenizer st;
        int[] a, b; double[] o;
        double best;
        String line;
        while(t-->0)
        {
            line = f.readLine();
            while(line.length() < 1) line = f.readLine();
            st = new StringTokenizer(line);
            B = parseInt(st.nextToken());
            c = parseInt(st.nextToken());
            best = 0.0;
            a = new int[c]; b = new int[c];
            o = new double[c];
            for(i = 0; i < c; i++)
            {
                st = new StringTokenizer(f.readLine());
                a[i] = parseInt(st.nextToken());
                b[i] = parseInt(st.nextToken());
                o[i] = (double)a[i]/(2.0*b[i]);
            }
        }
    }
}