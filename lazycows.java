import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class lazycows
{
    private static BufferedReader f;
    public static final int INF = 1000000000;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        StringTokenizer st;
        int i, x, y, n1, n2, t;
        int i1, i2, l1, l2, c1, c2;
        int[] cow1, cow2;
        min_t[] min;
        while(T-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            int n, k;
            n = parseInt(st.nextToken());
            k = parseInt(st.nextToken());
            cow1 = new int[1000];
            cow2 =  new int[1000];
            n1 = n2 = 0;
            for(i = 0; i < n; i++)
            {
                st = new StringTokenizer(f.readLine());
                x = parseInt(st.nextToken());
                y = parseInt(st.nextToken());
                if(x == 1) cow1[n1++] = y;
                else cow2[n2++] = y;
            }
            
            Arrays.sort(cow1, 0, n1);
            Arrays.sort(cow2, 0, n2);
            
            min = new min_t[k+1];
            
            for(i = 0; i <= k; i++)
            {
                min[i] = new min_t();
                min[i].topmin = min[i].botmin = min[i].bothmin = min[i].spanmin = INF;
            }
                
            min[0].topmin = min[0].botmin = min[0].bothmin = min[0].spanmin = 0;
            
            i1 = i2 = 0;
            c1 = c2 = 0;
            l1 = l2 = -1;
            while(i1 < n1 || i2 < n2)
            {
                if(i1 < n1) c1 = cow1[i1];
                else c1 = -1;
                if(i2 < n2) c2 = cow2[i2];
                else c2 = -1;
                
                if(i1 == 0 && i2 == 0)
                {
                    if(c1 == c2)
                    {
                        min[1].spanmin = 2;
                        min[2].bothmin = 2;
                        l1 = l2 = c1;
                        i1++; i2++;
                    }
                    else if(c1 == -1 || c2 < c1)
                    {
                        min[1].spanmin = 2;
                        min[1].botmin = 1;
                        l1 = l2 = c2;
                        i2++;
                    }
                    else
                    {
                        min[1].spanmin = 2;
                        min[1].topmin = 1;
                        l1 = l2 = c1;
                        i1++;
                    }
                    min[0].topmin = min[0].botmin = min[0].bothmin = min[0].spanmin = INF;
                    continue;
                }
                
                if(c1 == -1)
                {
                    for(i = k; i > 0; i--)
                    {
                        min[i].topmin = INF;
                        min[i].botmin += c2-l2;
                        t = l1;
                        if(t < l2) t = l2;
                        t = min[i].bothmin+c2-t;
                        if(min[i].botmin > t) min[i].botmin = t;
                        t = l1;
                        if(t < l2) t = l2;
                        min[i].bothmin += 2*(c2-t);
                        min[i].spanmin += 2*(c2-t);
                        t = min[i-1].topmin;
                        if(min[i-1].botmin < t) t = min[i-1].botmin;
                        if(min[i-1].bothmin < t) t = min[i-1].bothmin;
                        if(min[i-1].spanmin < t) t =  min[i-1].spanmin;
                        if(min[i].botmin > t+1) min[i].botmin = t+1;
                    }
                    l2 = c2;
                    i2++;
                }
                else if(c2 == -1)
                {
                    for(i = k ; i > 0; i--)
                    {
                        min[i].botmin = INF;
                        min[i].topmin += c1-l1;
                        t = l1;
                        if(t < l2) t = l2;
                        t =  min[i].bothmin+c1-t;
                        if(min[i].topmin > t) min[i].topmin = t;
                        t = l1;
                        if(t < l2) t = l2;
                        min[i].bothmin += 2*(c1-t);
                        min[i].spanmin += 2*(c1-t);
                        t = min[i-1].topmin;
                        if(min[i-1].botmin < t) t = min[i-1].botmin;
                        if(min[i-1].bothmin < t) t = min[i-1].bothmin;
                        if(min[i-1].spanmin < t) t =  min[i-1].spanmin;
                        if(min[i].topmin > t+1) min[i].topmin = t+1;
                    }
                    l1 = c1;
                    i1++;
                }
                else if(c1 == c2)
                {
                    for(i = k; i > 0; i--)
                    {
                        min[i].topmin = INF;
                        min[i].botmin = INF;
                        t = l1;
                        if(t < l2) t = l2;
                        min[i].bothmin += (c1-t)+(c2-t);
                        min[i].spanmin += (c1-t)+(c2-t);
                        t = min[i-1].topmin;
                        if(min[i-1].botmin < t) t = min[i-1].botmin;
                        if(min[i-1].bothmin < t) t = min[i-1].bothmin;
                        if(min[i-1].spanmin < t) t =  min[i-1].spanmin;
                        if(min[i].spanmin > t+2) min[i].spanmin = t+2;
                        t = min[i-1].botmin+c2-l2+1;
                        if(t < min[i].bothmin) min[i].bothmin = t;
                        t = min[i-1].topmin+c1-l1+1;
                        if(t < min[i].bothmin) min[i].bothmin = t;
                        
                        if(i >= 2)
                        {
                            t = min[i-2].topmin;
                            if(min[i-2].botmin < t) t = min[i-2].botmin;
                            if(min[i-2].bothmin < t) t = min[i-2].bothmin;
                            if(min[i-2].spanmin < t) t =  min[i-2].spanmin;
                            if(min[i].bothmin > t+2) min[i].bothmin = t+2;
                        }
                    }
                    l1 = c1; l2 = c2;
                    i1++; i2++;
                }
                else if(c1 < c2)
                {
                    for(i = k; i > 0; i--)
                    {
                        min[i].botmin = INF;
                        min[i].topmin += c1-l1;
                        t = l1;
                        if(t < l2) t = l2;
                        t = min[i].bothmin + c1-t;
                        if(min[i].topmin > t) min[i].topmin = t;
                        t = l1;
                        if(t < l2) t = l2;
                        min[i].bothmin += 2*(c1-t);
                        min[i].spanmin += 2*(c1-t);
                        t = min[i-1].topmin;
                        if(min[i-1].botmin < t) t = min[i-1].botmin;
                        if(min[i-1].bothmin < t) t = min[i-1].bothmin;
                        if(min[i-1].spanmin < t) t =  min[i-1].spanmin;
                        if(min[i].spanmin > t+2) min[i].spanmin = t+2;
                        if(min[i].topmin > t+1) min[i].topmin = t+1;
                        t = min[i-1].botmin + c1-l2 + 1;
                        if(min[i].bothmin > t) min[i].bothmin = t;
                    }
                    l1 = c1;
                    i1++;
                }
                else if(c2 < c1)
                {
                    for(i = k; i > 0; i--)
                    {
                        min[i].topmin = INF;
                        min[i].botmin += c2-l2;
                        t = l1;
                        if(t < l2) t = l2;
                        t = min[i].bothmin + c2-t;
                        if(min[i].botmin > t) min[i].botmin = t;
                        t = l1;
                        if(t < l2) t = l2;
                        min[i].bothmin += 2*(c2-t);
                        min[i].spanmin += 2*(c2-t);
                        t = min[i-1].topmin;
                        if(min[i-1].botmin < t) t = min[i-1].botmin;
                        if(min[i-1].bothmin < t) t = min[i-1].bothmin;
                        if(min[i-1].spanmin < t) t =  min[i-1].spanmin;
                        if(min[i].spanmin > t+2) min[i].spanmin = t+2;
                        if(min[i].botmin > t+1) min[i].botmin = t+1;
                        t = min[i-1].topmin + c2-l1+1;
                        if(min[i].bothmin > t) min[i].bothmin = t;
                    }
                    l2 = c2;
                    i2++;
                }
                for(i = 1; i <= k; i++)
                {
                    if(min[i].spanmin > INF) min[i].spanmin = INF;
                    if(min[i].bothmin > INF) min[i].bothmin = INF;
                    if(min[i].botmin > INF) min[i].botmin = INF;
                    if(min[i].topmin > INF) min[i].topmin = INF;
                }
            }
            t = INF;
            for(i = 1; i <= k; i++)
            {
                if(min[i].spanmin < t) t = min[i].spanmin;
                if(min[i].bothmin < t) t = min[i].bothmin;
                if(min[i].botmin < t) t = min[i].botmin;
                if(min[i].topmin < t) t =  min[i].topmin;
            }
            
            System.out.println(t);
            System.out.flush();
        }
        System.exit(0);
    }
    
    static class min_t
    {
        public int topmin, botmin, bothmin, spanmin;
        
        public min_t(){super();}
    }
}