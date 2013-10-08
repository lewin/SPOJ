import java.io.*;
import java.util.*;

public class transfers {
	private static Reader in;
	private static PrintWriter out;
	
	public static final int INF = 1 << 29;
	public static final double EPS = 1e-6;
	private static int N;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int T = in.nextInt();
		while (T-- > 0) {
			N = in.nextInt();
			int [] arr = new int [N + 1];
			for (int i = 1; i <= N; i++)
				arr [i] = in.nextInt();
			
			double sum = 0;
			for (int i = 1; i <= N; i++)
				sum += arr [i];
			sum /= N;

			cap = new double [N + 2][N + 2];
			flow = new double [N + 2][N + 2];
			cost = new int [N + 2][N + 2];
			
			for (int i = 1; i <= N; i++)
				if (arr [i] < sum)
					addEdge (0, i, sum - arr [i], 0);
			for (int i = 1; i <= N; i++)
				for (int j = 1; j <= N; j++)
					if (arr [i] < sum && arr [j] > sum)
						addEdge (i, j, INF, 1);
			for (int i = 1; i <= N; i++)
				if (arr [i] > sum)
					addEdge (i, N + 1, arr [i] - sum, 0);
			
			out.println (flow (0, N + 1));
			int count = 0;
			for (int i = 1; i <= N; i++)
				for (int j = 1; j <= N; j++)
					if (cost [i][j] > 0 && flow [i][j] > EPS)
						count++;
			
			out.println (count);
			for (int i = 1; i <= N; i++)
				for (int j = 1; j <= N; j++)
					if (cost [i][j] > 0 && flow [i][j] > EPS)
						out.printf ("%d -> %d: %.8f\n", i - 1, j - 1, flow [i][j]);
		}
		out.close();
		System.exit(0);
	}
	
	private static double [][]	cap;
	private static double [][] flow;
	private static int [][] cost;
	
	private static void addEdge (int x, int y, double w, int c) {
		cap[x][y] = w;
		cap[y][x] = w;
		flow[y][x] = w;
		cost[x][y] = c;
		cost[y][x] = -c;
	}
	
	private static int flow (int source, int sink) {
		int ans_flow = 0, ans_cost = 0;
		int [] pot = new int [N + 2];
		
		while (true) {
			boolean [] used = new boolean [N + 2];
			int [] dist = new int [N + 2];
			int [] prev = new int [N + 2];
			Arrays.fill (dist, INF);
			dist[source] = 0;
			
			while (true) {
				int x = -1;
				for (int i = 0; i < N + 2; i++)
					if (dist[i] != INF && !used[i] && (x == -1 || dist[i] < dist[x]))
						x = i;
				
				if (x == -1)
					break;
				
				used[x] = true;
				for (int i = 0; i < N + 2; i++)
					if (!used [i] && cap[x][i] - flow[x][i] > EPS
							&& dist[x] + (cost [x][i] < 0 || flow[x][i] > EPS ? cost[x][i] : 0) + pot[x] - pot[i] < dist[i]) {
						dist[i] = dist[x] + (cost [x][i] < 0 || flow[x][i] > EPS ? cost[x][i] : 0) + pot[x] - pot[i];
						prev[i] = x;
					}
			}
			
			if (!used[sink])
				break;
			
			double ansf = INF;
			int ansc = 0;
			for (int x = sink; x != source; x = prev[x])
				ansf = Math.min (ansf, cap[prev[x]][x]);
			
			ans_flow += ansf;
			for (int x = sink; x != source; x = prev[x]) {
				ansc += (cost [prev[x]][x] < 0 || flow[prev[x]][x] > EPS ? cost[prev[x]][x] : 0);
				flow[prev[x]][x] += ansf;
				flow[x][prev[x]] -= ansf;
			}
			
			for (int i = 0; i < N + 2; i++)
				pot[i] += dist[i];
			
			ans_cost += ansc;
			System.out.println (ans_flow + " " + ans_cost);
		}
		
		return ans_cost;
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
