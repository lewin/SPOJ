import java.io.*;
import java.util.*;

public class bcake {
	private static BufferedReader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new InputStreamReader (System.in));
		out = new PrintWriter (System.out, true);
		
		int T = Integer.parseInt (in.readLine());
		StringTokenizer st;
		while (T-- > 0) {
			st = new StringTokenizer (in.readLine ());
			int H = Integer.parseInt (st.nextToken ());
			int W = Integer.parseInt (st.nextToken ());
			int K = Integer.parseInt (st.nextToken ());
			
			if (K == 0) {
				out.println (0);
				continue;
			}
			
			int [][] grid = new int [H + 1][W + 1];
			for (int i = 1; i <= H; i++) { 
				char [] line = in.readLine().toCharArray();
				for (int j = 1; j <= W; j++) {
					grid [i][j] = grid [i - 1][j] + grid [i][j - 1] 
					         + (line [j - 1] == 'C' ? 1 : 0) - grid [i - 1][j - 1];
				}
			}
			
			int min = Integer.MAX_VALUE;
			for (int i = 1; i <= H; i++)
				for (int j = i + 1; j <= H; j++)
					for (int k = 1; k <= W; k++)
						for (int m = k + 1; m <= W; m++) {
							int chips = grid [j][m] - grid [i][m] - grid [j][k] + grid [i][k];
							if (chips != K) continue;
							if ((m - k) * (j - i) < min)
								min = (m - k) * (j - i);
						}
			
			out.println (min == Integer.MAX_VALUE ? -1 : min);
		}
		
		out.close();
		System.exit(0);
	}
}
