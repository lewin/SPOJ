import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class gny07a
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        StringTokenizer st;
        int n, i = 1; String word;
        while(T-->0)
        {
            st = new StringTokenizer(f.readLine());
            n = parseInt(st.nextToken())-1; word = st.nextToken();
            
            System.out.format("%d %s%n", i++, word.substring(0,n) + word.substring(n+1));
            System.out.flush();
        }
        System.exit(0);
    }
}