import java.io.*;
import java.util.*;

public class stead {
	private static Reader		in;
	private static PrintWriter	out;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		N = in.nextInt(); B = in.nextInt();
		int [][] arr = new int [N][B];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < B; j++)
				arr [i][j] = in.nextInt();
		int [] caps = new int [B];
		for (int i = 0; i < B; i++)
			caps [i] = in.nextInt();
		
		int E = 2 * (N * B + N + B);
		int P = N + B + 2;
		flow = new int [E];
		capa = new int [E];
		now = new int [P];
		eadj = new int [E];
		eprev = new int [E];
		elast = new int [P];
		level = new int [P];
		eidx = 0;
		Arrays.fill (elast, -1);
		
		for (int i = 1; i <= B; i++)
			add_edge (N + i, N + B + 1, caps [i - 1]);
		for (int i = 1; i <= N; i++)
			add_edge (0, i, 1);
		
		int sum = 0;
		for (int i = 1; i <= B; i++) {
			for (int j = 1; j <= N; j++)
				add_edge (j, N + arr [j - 1][i - 1], 1);
			sum += dinic (0, N + B + 1);
			if (sum == N) {
				out.println (i);
				break;
			}
		}
		out.close();
		System.exit(0);
	}
	
	private static int []	flow, capa, now, eadj, eprev, elast;
	private static int eidx, N ,B;
	public static final int INF = 1 << 27;
	
	private static void add_edge (int a, int b, int c) {
		eadj [eidx] = b; flow [eidx] = 0; capa [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
		eadj [eidx] = a; flow [eidx] = 0; capa [eidx] = c; eprev [eidx] = elast [b]; elast [b] = eidx++;
	}
	
	private static int dinic (int source, int sink) {
		int res, flow = 0;
		while (bfs (source, sink)) {
			System.arraycopy (elast, 0, now, 0, elast.length);
			while ((res = dfs (source, INF, sink)) > 0)
				flow += res;
		}
		return flow;
	}
	
	private static int []	level;
	
	private static boolean bfs (int source, int sink) {
		Arrays.fill (level, -1);
		int front = 0, back = 0;
		int [] queue = new int [level.length];
		
		level[source] = 0;
		queue[back++] = source;
		
		while (front < back && level[sink] == -1) {
			int node = queue[front++];
			for (int e = elast[node]; e != -1; e = eprev[e]) {
				int to = eadj[e];
				if (level[to] == -1 && flow[e] < capa[e]) {
					level[to] = level[node] + 1;
					queue[back++] = to;
				}
			}
		}
		
		return level[sink] != -1;
	}
	
	private static int dfs (int cur, int curflow, int goal) {
		if (cur == goal)
			return curflow;
		
		for (int e = now[cur]; e != -1; now[cur] = e = eprev[e]) {
			if (level[eadj[e]] > level[cur] && flow[e] < capa[e]) {
				int res = dfs (eadj[e], Math.min (curflow, capa[e] - flow[e]), goal);
				if (res > 0) {
					flow[e] += res;
					flow[e ^ 1] -= res;
					return res;
				}
			}
		}
		return 0;
	}
	
	static class Reader {
		final private int			BUFFER_SIZE	= 1 << 16;
		private DataInputStream	din;
		private byte []			buffer;
		private int					bufferPointer, bytesRead;
		
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
				if (c == '\n') break;
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
			if (neg) c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg) return -ret;
			return ret;
		}
		
		public long nextLong () throws IOException {
			long ret = 0;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg) c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg) return -ret;
			return ret;
		}
		
		public double nextDouble () throws IOException {
			double ret = 0, div = 1;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg) c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (c == '.') while ((c = read ()) >= '0' && c <= '9')
				ret += (c - '0') / (div *= 10);
			if (neg) return -ret;
			return ret;
		}
		
		private void fillBuffer () throws IOException {
			bytesRead = din.read (buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1) buffer[0] = -1;
		}
		
		private byte read () throws IOException {
			if (bufferPointer == bytesRead) fillBuffer ();
			return buffer[bufferPointer++];
		}
		
		public void close () throws IOException {
			if (din == null) return;
			din.close ();
		}
	}
}
