import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class ae1b
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int n = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        int s = parseInt(st.nextToken());
        
        int t = k*s;
        
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        st = new StringTokenizer(f.readLine());
        
        while(st.hasMoreTokens())
            pq.add(-parseInt(st.nextToken()));
            
        while(t > 0)
            t += pq.poll();
        
        System.out.println( (n-pq.size()) );
        System.out.flush();
        
        System.exit(0);
    }
    
}