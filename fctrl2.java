import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;
import java.math.BigInteger;

public class fctrl2
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        BigInteger[] big = new BigInteger[101];
        big[1] = BigInteger.ONE;
        for(int i = 2; i <= 100; i++)
            big[i] = big[i-1].multiply(new BigInteger("" + i));
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        for(int i = 0; i < T; i++)
        {
            int k = parseInt(f.readLine());
            System.out.println(big[k]);
            System.out.flush();
        }
        System.exit(0);
    }
}