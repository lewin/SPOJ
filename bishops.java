import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class bishops
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        BigInteger two = new BigInteger ("2");
        for (;;) {
            String line = in.readLine ();
            if (line == null) break;
            BigInteger num = new BigInteger (line);
            if (num.equals (BigInteger.ONE)){
                out.println (num);
            } else {
                num = num.shiftLeft (1).subtract (two);
                out.println (num);
            }
        }
    }
}