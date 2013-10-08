import java.io.*;
import java.util.*;

public class RS2D {
	private static Reader in;
	private static PrintWriter out;
	
	private static int [] eadj, eprev, elast, ecost;
	private static int eidx;
	
	private static void addEdge (int a, int b, int c) {
		eadj [eidx] = b; ecost [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
	}
	
	static class Edge implements Comparable <Edge> {
		public int node;
		public long weight;
		
		public Edge (int node, long weight) {
			this.node = node;
			this.weight = weight;
		}
		
		public int compareTo (Edge other) {
			return (int)(Math.signum(weight - other.weight));
		}
	}

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		eadj = new int [1000000];
		ecost = new int [1000000];
		eprev = new int [1000000];
		elast = new int [1000000];
		
		int T = in.nextInt();
		while (T-- > 0) {
			int N = in.nextInt(), M = in.nextInt();
			
			eidx = 0;
			Arrays.fill (elast, -1);
			
			for (int i = 0; i < M; i++) {
				int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
				addEdge (a - 1, b - 1, c);
			}
			
			long [] dist = new long [N];
			Arrays.fill (dist, 1l << 40);
			PriorityQueue <Edge> pq = new PriorityQueue <Edge> ();
			pq.add (new Edge (0, dist [0] = 0));
			while (pq.size() > 0) {
				Edge deq = pq.poll();
				int node = deq.node;
				long weight = deq.weight;
				if (dist [node] != weight) continue;
				
				for (int e = elast [node]; e != -1; e = eprev [e]) {
					if (weight + ecost [e] < dist [eadj [e]])
						pq.add(new Edge (eadj [e], dist [eadj [e]] = weight + ecost [e]));
				}
			}
			
			out.printf ("Rafael will find his love for %d RS2D.\n", dist [N - 1]);
		}
		
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
