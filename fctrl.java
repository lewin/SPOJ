import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class fctrl
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        for(int i = 0; i < T; i++)
        {
            int k = parseInt(f.readLine());
            
            int count = 0;
            int five = 5;
            
            while(five <= k)
            {
                count += k/five;
                five *= 5;
            }
            
            System.out.println(count);
            System.out.flush();
        }
        System.exit(0);
    }
}