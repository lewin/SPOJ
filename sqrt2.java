import java.io.*;
import java.util.*;
import java.math.*;

public class sqrt2
{
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        out = new PrintWriter(System.out);
        BigDecimal two = new BigDecimal("2");
        BigDecimal guess = new BigDecimal("1.414");
        BigDecimal val = two;
        
        BigDecimal next;
        int cur = guess.scale();
        int max = 40000;
        while((cur*=2) <= 2000000)
        {
            if(cur > max) cur = max;
            next = val.divide(guess,cur,BigDecimal.ROUND_DOWN);
            guess = (next.add(guess)).divide(two,cur,BigDecimal.ROUND_DOWN);
            if(cur == max) break;
        }
        out.println(guess);
        out.println("Time: " + (System.currentTimeMillis()-start)/1000.0 + "s");
    }
    
    public static String root(int n) {
        String s = "" + n;
        if(s.length() == 1)
            s = "0" + s;
        BigInteger p = BigInteger.ZERO, r = BigInteger.ZERO, twenty = new BigInteger("" + 20);;
        int y,x;
        for(int i = 0; i < 1000; i++) {
            try {
                y = Integer.parseInt(s.substring(i,i+2));
            }
            catch(StringIndexOutOfBoundsException e) {
                y = 0;
            }
            r = r.multiply(new BigInteger("" + 100));
            r = r.add(new BigInteger("" + y));
            for(x = 9; x >= 0; x--) {
                BigInteger test = p.multiply(new BigInteger("" + 20));
                test = test.add(new BigInteger("" + x));
                test = test.multiply(new BigInteger("" + x));
                if(test.compareTo(r) <= 0){ 
                    r = r.subtract(test);
                    break;
                }
            }
            p = new BigInteger(""+p+x);
            if(r.compareTo(BigInteger.ZERO) == 0)
                return "";
        }
        return ""+p;
    }
}