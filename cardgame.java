import java.io.*;
import java.util.*;

public class cardgame
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        int [] bitcount = new int [1 << 13];
        for (int i = 0; i < 1 << 13; i++) {
            int t = i;
            while (t > 0) {
                if (t % 2 == 1)
                    bitcount [i]++;
                t >>= 1;
            }
        }
        for (;;) {
            int [][] dice = new int [13][7];
            String line = in.readLine ();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer (line);
            for (int i = 0; i < 5; i++)
                dice [0][Integer.parseInt (st.nextToken ())]++;
            for (int j = 1; j < 13; j++) {
                st = new StringTokenizer (in.readLine ());
                for (int i = 0; i < 5; i++)
                    dice [j][Integer.parseInt (st.nextToken ())]++;
            }
            int [][] score = new int [13][13];
            for (int i = 0; i < 13; i++) 
                for (int j = 0; j < 13; j++)
                    score [i][j] = calculate (j, dice [i]);
            int [] dp = new int [1 << 13], parent = new int [1 << 13], save = new int [1 << 13];
            boolean [] bonus = new boolean [1 << 13];
            dp [0] = 0; parent [0] = -1; save [0] = 0; bonus [0] = false;
            for (int i = 1; i < 1 << 13; i++) 
                for (int j = 0; j < 13; j++) 
                    if ((i & (1 << j)) > 0) {
                        int prev = i - (1 << j);
                        boolean haveBonus = bonus [prev];
                        int newscore = dp [prev] + score [j][bitcount [i] - 1];
                        if (bitcount [i] == 6 && newscore >= 63) {
                            newscore += 35;
                            haveBonus = true;
                        }
                        if (newscore > dp [i]) {
                            dp [i] = newscore;
                            parent [i] = prev;
                            save [i] = score [j][bitcount [i] - 1];
                            bonus [i] = haveBonus;
                        }
                    }
            Stack <Integer> stack = new Stack <Integer> ();
            int last = (1 << 13) - 1;
            int cur = last;
            while (cur != 0) {
                stack.push (save [cur]);
                cur = parent [cur];
            }
            while (stack.size () > 0)
                out.print (stack.pop () + " ");
            out.print ((bonus [last] ? 35 : 0) + " ");
            out.println (dp [last]);
        }
    }
    
    private static int calculate (int mode, int [] arr) {
        mode++;
        int score = 0; boolean flag = false;
        switch (mode) {
            case 1 : return arr [1];
            case 2 : return arr [2] * 2;
            case 3 : return arr [3] * 3;
            case 4 : return arr [4] * 4;
            case 5 : return arr [5] * 5;
            case 6 : return arr [6] * 6;
            case 7 : 
                for (int i = 1; i <= 6; i++)
                    score += arr [i] * i;
                return score;
            case 8 : 
                for (int i = 1; i <= 6; i++) {
                    score += arr [i] * i;
                    if (arr [i] >= 3) flag = true;
                }
                return flag ? score : 0;
            case 9 : 
                for (int i = 1; i <= 6; i++) {
                    score += arr [i] * i;
                    if (arr [i] >= 4) flag = true;
                }
                return flag ? score : 0;
            case 10 : 
                for (int i = 1; i <= 6; i++)
                    if (arr [i] >= 5) flag = true;
                return flag ? 50 : 0;
            case 11 : 
                for (int i = 1; i <= 3; i++)
                    if (arr [i] >= 1 && arr [i + 1] >= 1 
                        && arr [i + 2] >= 1 && arr [i + 3] >= 1) 
                      flag = true;
                 return flag ? 25 : 0;
            case 12 : 
                for (int i = 1; i <= 2; i++)
                    if (arr [i] >= 1 && arr [i + 1] >= 1 
                        && arr [i + 2] >= 1 && arr [i + 3] >= 1
                        && arr [i + 4] >= 1) 
                      flag = true;
                 return flag ? 35 : 0;
            case 13 :
                for (int i = 1; i <= 6; i++)
                    for (int j = i + 1; j <= 6; j++) {
                        if (arr [i] >= 2 && arr [j] >= 3)
                            flag = true;
                        if (arr [j] >= 2 && arr [i] >= 3)
                            flag = true;
                    }
                 return flag ? 40 : 0;
        }
        return 0;
    }
}