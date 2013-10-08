import java.io.*;
import java.util.*;

public class assembly {
	private static BufferedReader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new InputStreamReader (System.in));
		out = new PrintWriter (System.out, true);
		
		boolean first = true;
		while (true) {
			int N = Integer.parseInt (in.readLine());
			if (N == 0) break;
			
			if (!first) out.println ();
			first = false;

			int [] to = new int [26];
			char [] arr = new char [N];
			StringTokenizer st = new StringTokenizer (in.readLine());
			for (int i = 0; i < N; i++) {
				arr [i] = st.nextToken().charAt (0);
				to [arr [i] - 'a'] = i;
			}
			
			int [][] costMat = new int [N][N];
			int [][] toMat = new int [N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer (in.readLine());
				for (int j = 0; j < N; j++) {
					String [] s = st.nextToken().split ("-");
					costMat [i][j] = Integer.parseInt (s [0]);
					toMat [i][j] = to [s [1].charAt (0) - 'a'];
				}
			}
			
			int Q = Integer.parseInt (in.readLine());
			while (Q-- > 0) {
				String s = in.readLine();
				int M = s.length();
				int [] array = new int [M];
				for (int i = 0; i < M; i++)
					array [i] = to [s.charAt (i) - 'a'];
				
				int [][][] dp = new int [N][M][M];
				for (int i = 0; i < N; i++)
					for (int k = 0; k < M; k++)
						Arrays.fill (dp [i][k], 1 << 29);
				
				for (int i = 0; i < M; i++) {
					dp [array [i]][i][i] = 0;
				}
				
				for (int j = 1; j < M; j++)
					for (int i = 0; i < M - j; i++)  {
						for (int a = 0; a < N; a++)
							for (int b = 0; b < N; b++) {
								for (int k = i; k < i + j; k++) {
									dp [toMat [a][b]][i][i + j] = 
										Math.min (dp [toMat [a][b]][i][i + j],
												dp [a][i][k] + dp [b][k + 1][i + j] + costMat [a][b]);
								}
							}
					}
				
				long minCost = Long.MAX_VALUE;
				int letter = -1;
				for (int i = 0; i < N; i++)
					if (dp [i][0][M - 1] < minCost) {
						minCost = dp [i][0][M - 1];
						letter = i;
					}
				
				out.printf ("%d-%s\n", minCost, arr [letter]);
			}
		}
		out.close();
		System.exit(0);
	}
}

