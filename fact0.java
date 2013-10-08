import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class fact0
{
    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE  = new BigInteger("1");
    private final static BigInteger TWO  = new BigInteger("2");
    private final static SecureRandom random = new SecureRandom();
    private static PrintWriter out;
    private static Map <BigInteger, Integer> mp;

    public static void main (String [] args) throws IOException {
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        while (true) {
            BigInteger n = new BigInteger (in.readLine ());
            if (n.compareTo (ZERO) == 0) break;
            mp = new HashMap <BigInteger, Integer> ();
            factor (n);
            Set <Map.Entry <BigInteger, Integer>> e = mp.entrySet ();
            Iterator exp = e.iterator ();
            int j = 0; Key [] keys = new Key [e.size()];
            while (exp.hasNext ()) {
                Map.Entry a = (Map.Entry) exp.next ();
                keys [j++] = new Key (a.getKey ().toString (), a.getValue ().toString ());
            }
            Arrays.sort (keys, 0, j);
            for (int i = 0; i < j - 1; i++)
                out.print(keys[i] + " x ");
            out.println (keys [j - 1]);
        }
    }
    
    private static BigInteger rho(BigInteger N) {
        BigInteger divisor;
        BigInteger c  = new BigInteger(N.bitLength(), random);
        BigInteger x  = new BigInteger(N.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do {
            x  =  x.multiply(x).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            xx = xx.multiply(xx).mod(N).add(c).mod(N);
            divisor = x.subtract(xx).gcd(N);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    private static void factor(BigInteger N) {
        if (N.compareTo(ONE) == 0) return;
        if (N.isProbablePrime(5)) {
            Integer p = mp.remove (N);
            if (p == null) p = new Integer (0);
            p = (Integer)(p.intValue()+1);
            mp.put (N, p);
            return; 
        }
        BigInteger divisor = rho(N);
        factor(divisor);
        factor(N.divide(divisor));
    }
    
    static class Key implements Comparable <Key> {
        public String factor, exp;
        
        public Key (String _factor, String _exp) {
            factor = _factor;
            exp = _exp;
        }
        
        public int compareTo (Key o) {
            int p = factor.length ();
            int q = o.factor.length ();
            if (p > q) return 1;
            if (q > p) return -1;
            return factor.compareTo (o.factor);
        }
        
        public String toString () {
            return factor + "^" + exp;
        }
    }
}