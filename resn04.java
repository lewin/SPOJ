import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class resn04
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(f.readLine());
        int n, moves, i; StringTokenizer st;
        while(t-->0)
        {
            n = parseInt(f.readLine());
            st = new StringTokenizer(f.readLine());
            moves = 0;
            for(i = 1; i <= n; i++)
                moves += parseInt(st.nextToken())/i;
                
            if((moves&1)==0) System.out.println("BOB");
            else System.out.println("ALICE");
            System.out.flush();
        }
        System.exit(0);
    }
}