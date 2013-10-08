import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class sumtrian
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        
        int N = parseInt(f.readLine());
        
        while(N-- > 0)
        {
            int b = parseInt(f.readLine());
            int[][] tri = new int[b][b];
            for(int i = 0; i < b; i++)
            {
                StringTokenizer st = new StringTokenizer(f.readLine());
                for(int j = 0; j <= i; j++)
                    tri[j][i] = parseInt(st.nextToken());
            }
            
            System.out.println(findMax(tri));
            System.out.flush();
        }
        System.exit(0);        
    }
    
    public static int findMax(int[][] triangle)
    {
        for(int i = triangle.length-1; i > 0; i--)
            for(int j = 0; j < i; j++)
                triangle[j][i-1] += Math.max(triangle[j][i],triangle[j+1][i]);
        return triangle[0][0];
    }
}