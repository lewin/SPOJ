import java.io.*;
import java.util.*;

public class sensornt {
	private static Reader in;
	private static PrintWriter out;
	
	public static int INF = 1 << 29;
	private static int N, M;
	
	static class Edge implements Comparable <Edge> {
		public int from, to, weight;
		
		public Edge (int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		public int compareTo (Edge other) {
			return other.weight - weight;
		}
	}
	
	private static boolean [][] grid;
	
	private static void addEdge (int a, int b) {
		grid [a][b] = grid [b][a] = true;
	}
	
	private static void removeEdge (int a, int b) {
		grid [a][b] = grid [b][a] = false;
	}
	
	private static int [] par, size;
	private static int root (int node) {
		if (par [node] == node) return node;
		else return par [node] = root (par [node]);
	}
	
	private static void join (int a, int b) {
		int x1 = root (a), x2 = root (b);
		if (size [x1] < size [x2]) {
			int t = x1; x1 = x2; x2 = t;
		}
		
		size [x1] += size [x2];
		par [x2] = x1;
	}
	
	private static boolean connected() {
		return size[root(0)] == N;
	}
	
	private static boolean [] color;
	public static void spread (int node, boolean now) {
		boolean [] vis = new boolean [N];
		int [] queue = new int [N];
		int front = 0, back = 0;
		queue [back++] = node;
		color [node] = now;
		vis [node] = true;
		while (front < back) {
			int cur = queue [front++];
			for (int i = 0; i < N; i++)
				if (!vis [i] && grid [i][cur]) {
					vis [i] = true;
					queue [back++] = i;
					color [i] = now;
				}
					
		}
	}
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		while (true) {
			N = in.nextInt();
			if (N == 0) break;
			M = in.nextInt();
			
			Edge [] edges = new Edge [M];
			
			for (int i = 0; i < M; i++) {
				int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
				edges [i] = new Edge (a, b, c);
			}
			Arrays.sort (edges);
			
			grid = new boolean [N][N];
			size = new int [N];
			par = new int [N];
			for (int i = 0; i < N; i++) {
				size [i] = 1;
				par [i] = i;
			}
			
			int front = 0, back = 0;
			while (!connected()) {
				addEdge (edges [back].to, edges [back].from);
				back++;
			}
			int minDiff = edges [front].weight - edges [back - 1].weight;
			
			while (front < back && back < M) {
				int a = edges [front].to, b = edges [front].from; 
				front++;
				removeEdge (a, b);
				
				color = new boolean [N];
				spread (a, false);
				spread (b, true);
				boolean found = color [a] == color [b];
				
				while (!found && back < M) {
					addEdge (edges [back].to, edges [back].from);
					back++;
					if (color [edges [back - 1].to] != color [edges [back - 1].from])
						found = true;
				}

				if (found)
					if (edges [front].weight - edges [back - 1].weight < minDiff)
						minDiff = edges [front].weight - edges [back - 1].weight;
			}
			
			out.println (minDiff);
		}
		
		out.close();
		System.exit(0);
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
