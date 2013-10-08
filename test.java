import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class test
{
    private static BufferedReader f;

    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int a = parseInt(f.readLine());
        while(a != 42)
        {
            System.out.println(a);
            System.out.flush();
            a = parseInt(f.readLine());
        }
        
        System.exit(0);
    }
}