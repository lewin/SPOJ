import java.io.*;
import java.util.*;

public class robotgri {
	private static BufferedReader		in;
	private static PrintWriter	out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new InputStreamReader (System.in));
		out = new PrintWriter (System.out, true);
		N = Integer.parseInt (in.readLine ());
		grid = new char [N][N];
		for (int i = 0; i < N; i++)
			grid [i] = in.readLine().toCharArray ();
		vis = new boolean [N][N];
		flood (0, 0);
		if (!vis [N - 1][N - 1]) {
			out.println ("INCONCEIVABLE");
			out.close();
			System.exit(0);
		}
		
		long [][] ways = new long [N][N];
		boolean [][] ok = new boolean [N][N];
		ways [N - 1][N - 1] = 1;
		ok [N - 1][N - 1] = true;
		long mod = (1l << 31) - 1;
		for (int i = N - 1; i >= 0; i--) {
			for (int j = N - 1; j >= 0; j--) {
				if (grid [i][j] == '#') continue;
				if (i < N - 1) {
					ways [i][j] = (ways [i][j] + ways [i + 1][j]) % mod;
					ok [i][j] |= ok [i + 1][j];
				}
				if (j < N - 1) {
					ways [i][j] = (ways [i][j] + ways [i][j + 1]) % mod;
					ok [i][j] |= ok [i][j + 1];
				}
			}
		}
		
		out.println (ok [0][0] ? ways [0][0] : "THE GAME IS A LIE");
		out.close();
		System.exit(0);
		
		
	}
	
	private static boolean [][] vis;
	private static int N;
	private static char [][] grid;
	
	private static void flood (int x, int y) {
		if (x < 0 || x >= N || y < 0 || y >= N || vis [x][y] || grid [x][y] == '#') return;
		
		vis [x][y] = true;
		flood (x - 1, y);
		flood (x + 1, y);
		flood (x, y - 1);
		flood (x, y + 1);
	}
}
