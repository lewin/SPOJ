import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class ctgame
{
    private static BufferedReader f;
    public static int INF = 1000000000;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = parseInt(f.readLine());
        int R, C, i, j, best, index;
        int[][] rocks; char[] line;
        int[] height, left, right;
        while(T-- > 0)
        {
            StringTokenizer st = new StringTokenizer(f.readLine());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            ArrayList<Integer>[] temp = new ArrayList[R];
            rocks = new int[R][0];
            for(i = 0; i < R; i++)
            {
                temp[i] = new ArrayList<Integer>();
                temp[i].add(-1);
            }
            
            for(i = 0; i < R; i++)
            {
                line = f.readLine().toCharArray();
                for(j = 0; j < C*2; j += 2)
                    if(line[j] == 'R')
                        temp[i].add(j/2);
            }
            
            for(i = 0; i < R; i++) 
            {
                temp[i].add(C);
                Collections.sort(temp[i]);
                rocks[i] = new int[temp[i].size()];
                j = 0;
                for(int k : temp[i])
                    rocks[i][j++] = k;
            }
            
            height = new int[C+1];
            left = new int[C+1];
            right = new int[C+1];
            Arrays.fill(height, 0);
            Arrays.fill(left, INF);
            Arrays.fill(right, INF);
            
            best = -1;
            for(i = 0; i < R; i++)
            {
                index = 1;
                for(j = 0; j < C; j++)
                {
                    if(j == rocks[i][index])
                    {
                        height[j] = 0;
                        left[j] = right[j] = INF;
                        while(j == rocks[i][index])
                            index++;
                    }
                    else
                    {
                        height[j]++;
                        if(left[j] > j-rocks[i][index-1]) left[j] = j-rocks[i][index-1];
                        if(right[j] > rocks[i][index] - j) right[j] = rocks[i][index]-j;
                    }
                    if(best < height[j] * (left[j]+right[j]-1))
                        best = height[j] * (left[j]+right[j]-1);
                }
            }
            
            System.out.println(best*3);
            System.out.flush();
            
            f.readLine();
        }
        
        System.exit(0);
    }
}