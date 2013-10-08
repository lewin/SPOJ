import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class prime1
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        int i, m, n, j, k, p, s;
        int[] primes = new int[4000];
        j = 0;
        primes[j++] = 2;
        boolean prime;
        for(i = 3; i <= Math.sqrt(1000000000); i+=2)
        {
            prime = true;
            for(k = 0; k < j && primes[k] <= Math.sqrt(i); k++)
                if(i % primes[k] == 0)
                {
                    prime = false; 
                    break;
                }
           if(prime) primes[j++] = i;
        }
            
            
        f = new BufferedReader(new InputStreamReader(System.in));
        
        int T = parseInt(f.readLine());
        boolean[] isPrime = new boolean[100001];
        while(T-->0)
        {
            StringTokenizer st = new StringTokenizer(f.readLine());
            m = parseInt(st.nextToken());
            n = parseInt(st.nextToken());
            if(m < 2) m = 2;
            Arrays.fill(isPrime, true);
            for(i = 0; i < j; i++)
            {
                p = primes[i];
                s = 0;
                if(p >= m) s = p<<1;
                else s = m + ((p-m%p)%p);
                for(k = s; k <= n; k+=p) isPrime[k-m] = false;
            }
            
            for(i = m; i <= n; i++)
                if(isPrime[i-m]) System.out.println(i);
            
            System.out.println();
        }
        
        System.exit(0);
    }
}