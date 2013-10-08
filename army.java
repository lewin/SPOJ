import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class army
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int t = parseInt(f.readLine());
        int max, a; boolean godzilla;
        StringTokenizer st;
        while(t-->0)
        {
            f.readLine(); f.readLine();
            godzilla = true; max = 0;
            st = new StringTokenizer(f.readLine());
            while(st.hasMoreTokens())
            {
                a = parseInt(st.nextToken());
                if(a > max) max = a;
            }
            st = new StringTokenizer(f.readLine());
            while(st.hasMoreTokens())
            {
                a = parseInt(st.nextToken());
                if(a > max){ max = a; godzilla = false;}
            }
            
            if(godzilla) System.out.println("Godzilla");
            else         System.out.println("MechaGodzilla");
            System.out.flush();
        }
        System.exit(0);
    }
}