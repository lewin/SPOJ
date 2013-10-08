import java.io.*;
public class tricount{
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(f.readLine()); long n;
        while(t-->0){n=Long.parseLong(f.readLine());
            System.out.println((n*(n+2)*(2*n+1)/8));
        }
    }
}