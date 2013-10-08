import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class stone
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        int n, i;
        double [] x, y;
        StringTokenizer st;
        while(T-- > 0)
        {
            n = parseInt(f.readLine());
            x = new double [n + 1];
            y = new double [n + 1];
            for(i = 0; i < n; i++)
            {
                st = new StringTokenizer(f.readLine());
                x [i] = (double)parseInt(st.nextToken());
                y [i] = (double)parseInt(st.nextToken());
            }
            x[n] = x[0]; y[n] = y[0];
            double A = 0;
            for (i = 0; i < n; i++)
                A += x[i] * y[i + 1] - x[i + 1] * y[i];
            A /= 2.;
            double cx = 0, cy = 0;
            for (i = 0; i < n; i++) {
                cx += (x[i] + x[i + 1]) * (x[i] * y[i + 1] - x[i + 1] * y[i]);
                cy += (y[i] + y[i + 1]) * (x[i] * y[i + 1] - x[i + 1] * y[i]);
            }
            cx /= 6. * A; cy /= 6. * A;
            System.out.format("%.2f %.2f%n", cx, cy);
            System.out.flush();
        }
        System.exit(0);
    }
}