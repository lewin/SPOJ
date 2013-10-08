import java.io.*;
import java.util.*;

public class portalun {
	private static BufferedReader in;
	private static PrintWriter out;
	
	private static int [] eadj, eprev, elast;
	private static int eidx;
	
	private static void addEdge (int a, int b) {
		if (b > a) {a ^= b; b ^= a; a ^= b;}
		eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
	}

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader (System.in));
		out = new PrintWriter(System.out, true);
		
		String line = in.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer (line);
			int N = Integer.parseInt (st.nextToken()), M = Integer.parseInt (st.nextToken ());
			
			eadj = new int [M];
			eprev = new int [M];
			elast = new int [N];
			eidx = 0;
			Arrays.fill (elast, -1);
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer (in.readLine ());
				int a = Integer.parseInt (st.nextToken ()),
					b = Integer.parseInt (st.nextToken ());
				addEdge (a, b);
			}
			
			int [] nim = new int [N];
			for (int i = 0; i < N; i++) {
				boolean [] seen = new boolean [N];
				for (int e = elast [i]; e != -1; e = eprev [e]) {
					seen [nim [eadj [e]]] = true;
				}
				int k = 0;
				for(; seen [k]; k++);
				nim [i] = k;
			}
			
			int K = Integer.parseInt (in.readLine ());
			int xor = 0;
			while (K-- > 0)
				xor ^= nim [Integer.parseInt (in.readLine ())];
			
			out.println (xor == 0 ? "I lose" : "I win");
			line = in.readLine();
		}
		out.close();
		System.exit(0);
	}
}