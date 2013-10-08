import java.io.*;
import java.util.*;

public class nagay1 {
	private static BufferedReader in;
	private static PrintWriter out;

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader (System.in));
		out = new PrintWriter(System.out, true);
		
		StringTokenizer st = new StringTokenizer (in.readLine ());
		int N = Integer.parseInt (st.nextToken ()), M = Integer.parseInt (st.nextToken ());
		char [][] curgrid = new char [N][M];
		for (int i = 0; i < N; i++)
			curgrid [i] = in.readLine().toCharArray();
		
		int vx = 0, vy = 0, jx = 0, jy = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				if (curgrid [i][j] == 'V') {
					vx = i; vy = j;
				}
				if (curgrid [i][j] == 'J') {
					jx = i; jy = j;
				}
			}
		
		int dist = 0;
		while (true) {
			boolean [][] vis = new boolean [N][M];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < M; j++)
					vis [i][j] = (curgrid [i][j] == '+');
			
			int [] queuex = new int [N * M];
			int [] queuey = new int [N * M];
			int front = 0, back = 0;
			if (!vis [vx][vy]) {
				queuex [back] = vx; queuey [back++] = vy;
				vis [vx][vy] = true;
			}
			boolean found = false;
			while (front < back) {
				int cx = queuex [front], cy = queuey [front++];
				if (cx == jx && cy == jy) {
					found = true;
					break;
				}
				if (cx - 1 >= 0 && !vis [cx - 1][cy]) {
					queuex [back] = cx - 1;
					queuey [back++] = cy;
					vis [cx - 1][cy] = true;
				} 
				if (cx + 1 < N && !vis [cx + 1][cy]) {
					queuex [back] = cx + 1;
					queuey [back++] = cy;
					vis [cx + 1][cy] = true;
				}
				if (cy - 1 >= 0 && !vis [cx][cy - 1]) {
					queuex [back] = cx;
					queuey [back++] = cy - 1;
					vis [cx][cy - 1] = true;
				}
				if (cy + 1 < M && !vis [cx][cy + 1]) {
					queuex [back] = cx;
					queuey [back++] = cy + 1;
					vis [cx][cy + 1] = true;
				}
			}
			
			if (!found) break;
			char [][] ngrid = new char [N][M];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < M; j++) {
					if (curgrid [i][j] == '+') {
						ngrid [i][j] = '+';
						if (i - 1 >= 0)
							ngrid [i - 1][j] = '+';
						if (i + 1 < N)
							ngrid [i + 1][j] = '+';
						if (j - 1 >= 0)
							ngrid [i][j - 1] = '+';
						if (j + 1 < M)
							ngrid [i][j + 1] = '+';		
					}
				}
			curgrid = ngrid;
			dist++;
		}
		
		out.println (dist);
		out.close();
		System.exit(0);
	}
}
