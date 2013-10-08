import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class cowcar
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int N = parseInt(st.nextToken()), M = parseInt(st.nextToken()),
            D = parseInt(st.nextToken()), L = parseInt(st.nextToken()), i;
        int[] S = new int[N];
        for(i = 0; i < N; i++) S[i] = parseInt(f.readLine());
        Arrays.sort(S);
        int total = 0;
        
        for(i = 0; i < N; i++)
            if(S[i] - D*(total/M) >= L)
                total++;
        
        System.out.println(total);
        System.out.flush();
        System.exit(0);
    }
}