import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class eqbox
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine()), a, b, x, y;
        StringTokenizer st;
        while(T-->0)
        {
            st = new StringTokenizer(f.readLine());
            a = parseInt(st.nextToken());
            b = parseInt(st.nextToken());
            x = parseInt(st.nextToken());
            y = parseInt(st.nextToken());
            if(b < a){a^=b;b^=a;a^=b;}
            if(y < x){x^=y;y^=x;x^=y;}
            if(x < a && y < b) System.out.println("Escape is possible.");
            else System.out.println("Box cannot be dropped.");
            System.out.flush();
        }
        System.exit(0);
    }
}