import java.io.*;
import java.util.*;

public class pebble
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        
        String line = in.readLine ();
        int count = 0;
        while (line != null) {
            out.printf ("Game #%d: %d\n", ++count, score (line));
            line = in.readLine ();
        }
    }
    
    private static int score (String line) {
        char curBit = line.charAt (line.length () - 1);
        int count = 1;
        for (int i = line.length () - 2; i >= 0; i--) {
            if (curBit != line.charAt (i)) {
                count++;
                curBit = line.charAt (i);
            }
        }
        if (line.charAt (0) == '0') count--;
        return count;
    }
}