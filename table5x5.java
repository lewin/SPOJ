import java.io.*;
import java.util.*;

public class table5x5
{
    private static BufferedReader in;
    private static PrintWriter out;
    public static int goal;
    public static int[] row;
    public static char[] word;
    public static char[] maxR, maxC;// max row, max col
    public static boolean[] used;
    public static int[][][][][] numSoln;
    
    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        
        numSoln = new int[6][6][6][6][6];
        row = new int[5];
        maxR = new char[5];
        maxC = new char[5];
        Arrays.fill(maxR,(char)('A'-1));
        Arrays.fill(maxC,(char)('A'-1));
        used = new boolean[26];
        goal = Integer.parseInt(in.readLine());
        findW();
        numSoln = new int[6][6][6][6][6];
        row = new int[5];
        Arrays.fill(maxR,(char)('A'-1));
        Arrays.fill(maxC,(char)('A'-1));
        used = new boolean[26];
        word = in.readLine().toCharArray();
        findN();
        System.exit(0);
    }
    
    public static void findN()
    {
        int count = 0;
        char letter;
        for(int i = 0; i < 25; i++) // for each position
        {
            row[i/5]++;
            for(letter = (char)(Math.max(maxC[i%5],maxR[i/5])+1); letter < word[i]; letter++)
            {
                if(!used[letter-'A']) // can place letter here
                {
                    put(letter, i/5, i%5); // place the letter
                    count += count('A',row[0],row[1],row[2],row[3],row[4]);
                    used[letter-'A'] = false;
                }
            }
            used[letter-'A'] = true;
            maxR[i/5] = maxC[i%5] = letter;
        }
        count++;
        out.println(count);
        out.flush();
    }
    
    public static void findW()
    {
        int count = 0;
        int temp = 0;
        char letter;
        for(int i = 0; i < 25; i++) // for each position
        {
            row[i/5]++;
            for(letter = (char)(Math.max(maxC[i%5],maxR[i/5])+1); letter < 'Z'; letter++)
            {
                if(!used[letter-'A'])
                {
                    put(letter,i/5,i%5);
                    temp = count('A',row[0],row[1],row[2],row[3],row[4]);
                    
                    if(temp+count>=goal) break;
                    count+=temp;
                    used[letter-'A'] = false;
                }
            }
            used[letter-'A'] = true;
            maxC[i%5] = maxR[i/5] = letter;
            out.print(letter);
        }
        out.println();
        out.flush();
    }
    
    public static void put(char letter, int r, int c)
    {
        numSoln = new int[6][6][6][6][6];
        numSoln[5][5][5][5][5] = 1;
        maxR[r] = maxC[c] = letter;
        used[letter-'A'] = true;
    }
    
    public static int count(char letter, int a, int b, int c, int d, int e)
    {
        if(numSoln[a][b][c][d][e] != 0) return numSoln[a][b][c][d][e];
        if(used[letter-'A']) return numSoln[a][b][c][d][e] = count((char)(letter+1),a,b,c,d,e);
        int total = 0;
        if(a < 5 && maxR[0] < letter && letter > maxC[a]) total += count((char)(letter+1),a+1,b,c,d,e);
        if(a > b && maxR[1] < letter && letter > maxC[b]) total += count((char)(letter+1),a,b+1,c,d,e);
        if(b > c && maxR[2] < letter && letter > maxC[c]) total += count((char)(letter+1),a,b,c+1,d,e);
        if(c > d && maxR[3] < letter && letter > maxC[d]) total += count((char)(letter+1),a,b,c,d+1,e);
        if(d > e && maxR[4] < letter && letter > maxC[e]) total += count((char)(letter+1),a,b,c,d,e+1);
        return numSoln[a][b][c][d][e] = total;
    }
}
