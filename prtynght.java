import java.io.*;
import java.util.*;

public class prtynght {
	private static Reader in;
	private static PrintWriter out;
	
	private static boolean [][] grid;
	private static int N, M, cidx, eidx;
	private static int [][] part;
	private static int [] idx, eadj, elast, eprev;
	private static boolean [] color, vis;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		while (true) {
			N = in.nextInt ();
			if (N == 0) break;
			M = in.nextInt ();
			
			grid = new boolean[N][N];
			
			for (int i = 0; i < M; i++) {
				int a = in.nextInt (), b = in.nextInt ();
				grid[a][b] = grid[b][a] = true;
			}
			
			part = new int [N][2];
			idx = new int [N];
			cidx = 0;
			boolean ok = true;
			for (int i = 0; ok && i < N; i++)
				ok &= mark (i);
			
			for (int i = 0; ok && i < N; i++)
				for (int j = 0; ok && j < N; j++)
					ok &= !grid [i][j];
			
			eadj = new int [2 * N];
			elast = new int [cidx];
			eprev = new int [2 * N];
			eidx = 0;
			Arrays.fill (elast, -1);
			
			for (int i = 0; ok && i < N; i++)
				addEdge (part [i][0], part [i][1]);
			
			color = new boolean [cidx];
			vis = new boolean [cidx];
			for (int i = 0; ok && i < cidx; i++) {
				if (!vis [i])
					ok &= color (i, true);
			}
			out.println (ok ? "YES" : "NO");
		}
		out.close ();
		System.exit (0);
	}
	
	private static boolean mark (int cur) {
		for (; idx [cur] < 2; ) {
			int [] list = new int [N];
			int back = 0;
			list [back++] = cur;
			outer : for (int i = 0; i < N; i++) {
				for (int j = 0; j < back; j++)
					if (!grid [i][list [j]])
						continue outer;
				
				list [back++] = i;
			}
			
			for (int i = 0; i < back; i++)
				for (int j = i + 1; j < back; j++)
					grid [list [i]][list [j]] = grid [list [j]][list [i]] = false;
			
			for (int i = 0; i < back; i++) {
				if (idx [list [i]] == 2) return false;
				part [list [i]][idx [list [i]]++] = cidx;
			}
			cidx++;
			
//			print (list, back);
		}
		return true;
	}
	
	private static void print (int [] arr, int lim) {
		System.out.print ("[" + arr [0]);
		for (int i = 1; i < lim; i++)
			System.out.print (", " + arr [i]);
		System.out.println("]");
	}
	
	private static void addEdge (int a, int b) {
		eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
		eadj [eidx] = a; eprev [eidx] = elast [b]; elast [b] = eidx++;
	}
	
	private static boolean color (int node, boolean cur) {
		if (vis [node]) return color [node] == cur;
		vis [node] = true;
		color [node] = cur;
		
		for (int e = elast [node]; e != -1; e = eprev [e]) {
			if (!color (eadj [e], !cur))
				return false;
		}
		
		return true;
	}
	
	static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte [] buffer;
		private int bufferPointer, bytesRead;
		
		public Reader () {
			din = new DataInputStream (System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
		
		public Reader (String file_name) throws IOException {
			din = new DataInputStream (new FileInputStream (file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
		
		public String readLine () throws IOException {
			byte [] buf = new byte[1024];
			int cnt = 0, c;
			while ((c = read ()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String (buf, 0, cnt);
		}
		
		public int nextInt () throws IOException {
			int ret = 0;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg)
				c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}
		
		public long nextLong () throws IOException {
			long ret = 0;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg)
				c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}
		
		public double nextDouble () throws IOException {
			double ret = 0, div = 1;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg)
				c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read ()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
			if (neg)
				return -ret;
			return ret;
		}
		
		private void fillBuffer () throws IOException {
			bytesRead = din.read (buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}
		
		private byte read () throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer ();
			return buffer[bufferPointer++];
		}
		
		public void close () throws IOException {
			if (din == null)
				return;
			din.close ();
		}
	}
}
