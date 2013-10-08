import java.io.*;
import java.util.*;

public class nicebtre {
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader (System.in));
        out = new PrintWriter(System.out, true);
        
        int T = Integer.parseInt (in.readLine());
        
        while (T-- > 0) {
            String s = in.readLine();
            if (s.equals ("l")) {
                out.println (0);
                continue;
            }
            int depth = 0, ans = 0;
            boolean [] lc = new boolean [s.length()];
            lc [0] = true;
            for (char c : s.toCharArray()) {
                if (c == 'n') {
                    depth++;
                    lc [depth] = true;
                } else if (c == 'l') {
                    while (!lc [depth])
                        depth--;
                    lc [depth] = false;
                }
                ans = Math.max (ans, depth);
            }
            out.println (ans);
        }
        out.close();
        System.exit(0);
    }
}
