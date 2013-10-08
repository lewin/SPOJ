import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class movmrbl
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(f.readLine()); int n, k, p, r;
        StringTokenizer st; String line;
        while(t-->0)
        {
            line = f.readLine();
            while(line.length() < 1) line = f.readLine();
            st = new StringTokenizer(line);
            n = parseInt(st.nextToken());
            k = parseInt(st.nextToken());
            p = k; r = k-n;
            if(n > k)
            {
                r = 0;
                p = n;
            }
            System.out.println(p + " " + r);
            System.out.flush();
        }
        System.exit(0);
    }
}