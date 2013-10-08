import java.io.*;
import java.util.*;

public class hellokit
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out, true);
        StringTokenizer st;
        char[] word; int len, i, j, k;
        while(true) {
            st = new StringTokenizer(in.readLine());
            word = st.nextToken().toCharArray();
            if(word.length == 1 && word[0] == '.') break;
            len = Integer.parseInt(st.nextToken());
            for(i = 0; i < word.length; i++) // start char
            {
                for(j = 0; j < len; j++) // repeat
                    for(k = 0; k < word.length; k++)
                        out.print(word[(i+k)%word.length]);
                out.println();
            }
        }
    }
}