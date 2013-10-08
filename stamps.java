import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class stamps
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        
        int n = parseInt(f.readLine());
        int a, b, i, j = 1;; StringTokenizer st;
        PriorityQueue<Integer> pq;
        while(n-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            a = parseInt(st.nextToken());
            b = parseInt(st.nextToken());
            st = new StringTokenizer(f.readLine());
            pq = new PriorityQueue<Integer>();
            for(i = 0; i < b; i++)
                pq.add(-parseInt(st.nextToken()));
            while(a > 0 && pq.size() > 0)
                a += pq.poll();
            System.out.format("Scenario #%d:%n", j++);
            if(a > 0) System.out.println("impossible");
            else System.out.println( (b-pq.size()) );
            System.out.println();
            System.out.flush();
        }
        System.exit(0);
    }
}