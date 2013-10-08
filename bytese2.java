import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class bytese2
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        Time[] times; int N, i, j, max, countS, countF;
        StringTokenizer st;
        while(T-->0)
        {
            N = parseInt(f.readLine());
            times = new Time[N];
            for(i = 0; i < N; i++)
            {
                st = new StringTokenizer(f.readLine());
                times[i] = new Time(parseInt(st.nextToken()), parseInt(st.nextToken()));
            }
            max = 0;
            for(i = 0; i < N; i++)
            {
                countS = 1;
                countF = 1;
                for(j = 0; j < N; j++)
                {
                    if(times[i].start < times[j].finish && times[i].start > times[j].start)
                        countS++;
                    if(times[i].finish < times[j].finish && times[i].finish > times[j].start)
                        countF++;
                }
                if(max < countS) max = countS;
                if(max < countF) max = countF;
            }
            System.out.println(max);
            System.out.flush();  
        }
        System.exit(0);
    }
    
    static class Time
    {
        public int start, finish;
        public Time(int start, int finish)
        {
            this.start = start;
            this.finish = finish;
        }
    }
}