import java.io.*;
import java.util.*;

public class mcl
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out, true);
        
        String line = in.readLine();
        char[] array; int i; int[] count;
        int max; char letter;
        while(line != null) {
            array = line.toUpperCase().toCharArray(); 
            count = new int[26];
            for(i = 0; i < array.length; i++)
                if(array[i] >= 'A' && array[i] <= 'Z') 
                    count[array[i] - 'A']++;
            max = 0; letter = (char)('A'-1);
            for(i = 0; i < 26; i++)
                if(count[i] > max) {
                    max = count[i];
                    letter = (char)(i+'A');
                }
            out.println(letter + " " + max);
            line = in.readLine();
        }
        System.exit(0);
    }
}