import java.io.*;
import java.util.*;

public class contcity {
	public static final int INF = 1 << 29;
	private static Reader in;
	private static PrintWriter out;

	private static int N, M;
	private static Graph match;

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);

		int T = in.nextInt();
		
		while (T-- > 0) {
			int N = in.nextInt(), M = in.nextInt(), H = in.nextInt();
			
			int total = 0;
			int [] survivors = new int [N + 1];
			for (int i = 1; i <= N; i++)
				total += (survivors [i] = in.nextInt());
			
			int [][] dist = new int [N + 1][N + 1];
			for (int i = 1; i <= N; i++) {
				Arrays.fill (dist [i], INF);
				dist [i][i] = 0;
			}
			
			for (int i = 0; i < M; i++) {
				int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
				if (c < dist [a][b])
					dist [a][b] = dist [b][a] = c;
			}
			
			for (int k = 1; k <= N; k++)
				for (int i = 1; i <= N; i++)
					for (int j = 1; j <= N; j++)
						dist [i][j] = Math.min (dist [i][j], dist [i][k] + dist [k][j]);
			
			Helicopter [] hel = new Helicopter [H];
			for (int i = 0; i < H; i++)
				hel [i] = new Helicopter (in.nextInt(), in.nextInt(), in.nextInt());
			Arrays.sort (hel);
			
			match = new Graph (2 + N + H, 2 * (N + H + N * H));
			for (int i = 1; i <= N; i++)
				match.addEdge (0, i, survivors [i]);
			
			int cur = 0;
			for (int i = 0; i < H; i++) {
				for (int j = 1; j <= N; j++)
					if (dist [hel [i].pos][j] - 1 <= hel [i].arrive)
						match.addEdge (j, i + 1 + N, hel [i].cap);
				match.addEdge (i + 1 + N, N + H + 1, hel [i].cap);
				if ((cur += dinic (0, N + H + 1)) == total) {
					out.printf ("All people can be rescued in %d day(s).\n", hel [i].arrive);
					break;
				}
			}
			
			if (cur != total)
				out.printf ("%d survivor(s) can be rescued.\n", cur);
		}
		out.close();
		System.exit(0);
	}
	
	static class Helicopter implements Comparable <Helicopter> {
		public int pos, cap, arrive;
		
		public Helicopter (int arrive, int pos, int cap) {
			this.arrive = arrive;
			this.pos = pos;
			this.cap = cap;
		}
		
		@Override
		public int compareTo (Helicopter other) {
			return arrive - other.arrive;
		}
	}
	
	static class Graph {
		public int [] eadj, elast, eprev, ecost, eflow;
		public int eidx, N, M;
		
		public Graph (int N, int M) {
			this.N = N;	this.M = M;
			eadj = new int [M];
			elast = new int [N];
			eprev = new int [M];
			ecost = new int [M];
			eflow = new int [M];
			eidx = 0;
			Arrays.fill (elast, -1);
		}
		
		public void addEdge (int a, int b, int c) {
			eadj [eidx] = b; eflow [eidx] = 0; ecost [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
			eadj [eidx] = a; eflow [eidx] = c; ecost [eidx] = c; eprev [eidx] = elast [b]; elast [b] = eidx++;
		}
	}

	private static int[] level;

	private static int dinic(int source, int sink) {
		int res, flow = 0;
		while (bfs(source, sink)) {
			while ((res = dfs(source, INF, sink)) > 0)
				flow += res;
		}
		return flow;
	}

	private static boolean bfs(int source, int sink) {
		level = new int [match.N];
		Arrays.fill(level, -1);
		int front = 0, back = 0;
		int[] queue = new int[match.N];

		level[source] = 0;
		queue[back++] = source;

		while (front < back && level[sink] == -1) {
			int node = queue[front++];
			for (int e = match.elast[node]; e != -1; e = match.eprev[e]) {
				int to = match.eadj[e];
				if (level[to] == -1 && match.eflow[e] < match.ecost[e]) {
					level[to] = level[node] + 1;
					queue[back++] = to;
				}
			}
		}

		return level[sink] != -1;
	}

	private static int dfs(int cur, int curflow, int goal) {
		if (cur == goal)
			return curflow;

		for (int e = match.elast[cur]; e != -1; e = match.eprev[e]) {
			if (level[match.eadj[e]] > level[cur] && match.eflow[e] < match.ecost[e]) {
				int res = dfs(match.eadj[e], Math.min(curflow, match.ecost[e] - match.eflow[e]), goal);
				if (res > 0) {
					match.eflow[e] += res;
					match.eflow[e ^ 1] -= res;
					return res;
				}
			}
		}
		return 0;
	}

	static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public Reader() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public Reader(String file_name) throws IOException {
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[1024];
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
			if (neg)
				return -ret;
			return ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}
