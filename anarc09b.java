import java.io.*;
import java.util.*;
import static java.lang.Long.parseLong;

public class anarc09b
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        long w, h;
        for(;;)
        {
            st = new StringTokenizer(f.readLine());
            w = parseLong(st.nextToken());
            h = parseLong(st.nextToken());
            if(w == 0 && h == 0) break;
            System.out.println(lcm(w,h));
            System.out.flush();
        }
        System.exit(0);
    }
    
    private static long lcm(long x, long y)
    {
        long gcd = gcd(x,y);
        return (x / gcd) * (y / gcd);
    }
    
    private static long gcd(long x, long y)
    {
        if(y > x){x^=y;y^=x;x^=y;}
        while(y != 0)
        {
            x %= y;
            if(y > x){x^=y;y^=x;x^=y;}
        }
        return x;
    }
}