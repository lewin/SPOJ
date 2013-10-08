import java.io.*;
import java.util.*;

public class acode
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out, true);
        String line = in.readLine();
        while(line.charAt(0) != '0') {
            out.println(count(line.toCharArray()));
            line = in.readLine();
        }
    }
    
    private static long count(char[] arr) {
        long[] dp = new long[arr.length+1];
        dp[0] = 1;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > '0' && arr[i] <= '9') dp[i+1] += dp[i];
            if(i > 0) {
                int test = (arr[i-1]-'0')*10+(arr[i]-'0');
                if(test >= 10 && test <= 26) dp[i+1] += dp[i-1];
            }
        }
        return dp[arr.length];
    }
}