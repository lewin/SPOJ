import java.io.*;
import java.util.*;

public class mminpaid {
	private static Reader in;
	private static PrintWriter out;
	
	static class Pair implements Comparable <Pair> {
		public int mask, node, weight;
		
		public Pair (int mask, int node, int weight) {
			this.mask = mask;
			this.node = node;
			this.weight = weight;
		}
		
		public int compareTo (Pair other) {
			return weight - other.weight;
		}
	}
	
	private static int [] eadj, eadj2, elast, eocost, encost, eprev;
	private static int eidx;
	
	private static void addEdge (int a, int b, int c, int ocost, int ncost) {
		eadj [eidx] = b; eadj2 [eidx] = c; eocost [eidx] = ocost; encost [eidx] = ncost; eprev [eidx] = elast [a]; elast [a] = eidx++;
		eadj [eidx] = a; eadj2 [eidx] = c; eocost [eidx] = ocost; encost [eidx] = ncost; eprev [eidx] = elast [b]; elast [b] = eidx++;
	}

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int N = in.nextInt(), M = in.nextInt();
		
		eadj = new int [2 * M];
		eadj2 = new int [2 * M];
		elast = new int [N];
		eocost = new int [2 * M];
		encost = new int [2 * M];
		eprev = new int [2 * M];
		eidx = 0;
		Arrays.fill (elast, -1);
		
		for (int i = 0; i < M; i++) {
			int a = in.nextInt(), b = in.nextInt(), c = in.nextInt(), ncost = in.nextInt(), ocost = in.nextInt();
			addEdge (a - 1, b - 1, c - 1, ocost, ncost);
		}
		
		PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
		int [][] dist = new int [N][1 << N];
		for (int i = 0; i < N; i++)
			Arrays.fill (dist [i], 1 << 29);
		
		pq.add (new Pair (1, 0, 0));
		dist [0][1] = 0;
		
		while (pq.size() > 0) {
			Pair deq = pq.poll();
			int node = deq.node, mask = deq.mask, weight = deq.weight;
			if (dist [node][mask] != weight) continue;
			for (int e = elast [node]; e != -1; e = eprev [e]) {
				int next = eadj [e];
				int nmask = mask | (1 << next);
				int cost = eocost [e];
				if ((mask & (1 << eadj2 [e])) > 0) cost = encost [e];
				if (weight + cost < dist [eadj [e]][nmask])
					pq.add (new Pair (nmask, next, dist [eadj [e]][nmask] = weight + cost));
			}
		}
		
		int minCost = 1 << 29;
		for (int i = 0; i < 1 << N; i++)
			if (dist [N - 1][i] < minCost)
				minCost = dist [N - 1][i];
		
		out.println (minCost == 1 << 29 ? "impossible" : minCost);
		out.close();
		System.exit(0);
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
