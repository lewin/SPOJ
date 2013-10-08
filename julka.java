import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.math.BigInteger;

public class julka
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        BigInteger total, more, n;
        for(int i = 0; i < 10; i++)
        {
            total = new BigInteger(f.readLine());
            more = new BigInteger(f.readLine());
            n = total.subtract(more).shiftRight(1);
            System.out.println( n.add(more) );
            System.out.println( n );
            System.out.flush();
        }
        System.exit(0);        
    }
}