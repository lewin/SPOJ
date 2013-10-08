import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class ae00
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int N = parseInt(f.readLine());
        int i;
        
        long total = 0;
        for(i = 1; N >= i*i; i++) total += (N/i - (i-1));
        
        System.out.println(total);
        System.out.flush();
        System.exit(0);
    }
}