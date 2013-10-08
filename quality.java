import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class quality
{
    private static BufferedReader in;
    
    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int i = 1; 
        int a, b, c, d, e, f;
        int time, solve;
        String line = in.readLine();
        while(line != null)
        {
            st = new StringTokenizer(line);
            a = parseInt(st.nextToken());
            b = parseInt(st.nextToken());
            c = parseInt(st.nextToken());
            d = parseInt(st.nextToken());
            e = parseInt(st.nextToken());
            f = parseInt(st.nextToken());
            time = solve = 0;
            if(a != 0)
            {
                time += (a+(d-1)*20*60);
                solve++;
            }
            if(b != 0)
            {
                time += (b+(e-1)*20*60);
                solve++;
            }
            if(c != 0)
            {
                time += (c+(f-1)*20*60);
                solve++;
            }
            
            System.out.format("team %d: %d, %d%n",i++,solve,time);
            System.out.flush();
            
            line = in.readLine();
        }
        System.exit(0);
    }
}