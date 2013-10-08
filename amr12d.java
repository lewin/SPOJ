import java.io.*;
import java.util.*;

public class amr12d {
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader (System.in));
        out = new PrintWriter(System.out, true);
        
        int T = Integer.parseInt (in.readLine());
        while (T-- > 0) {
            String s = in.readLine();
            boolean ok = true;
            outer : for (int i = 0; i <= s.length(); i++)
                for (int j = i + 1; j <= s.length(); j++) {
                    if (!s.contains (rev (s.substring (i, j)))) {
                        ok = false;
                        break outer;
                    }                        
                }
            out.println (ok ? "YES" : "NO");
        }
        out.close();
        System.exit(0);
    }
    
    private static String rev (String s) {
        StringBuffer b = new StringBuffer ();
        for (int i = s.length() - 1; i >= 0; i--)
            b.append (s.charAt (i));
        return b.toString();
    }
}
