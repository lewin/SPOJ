import java.io.*;
import java.util.*;

public class myq7 {
	private static Reader in;
	private static PrintWriter out;
	
	private static int [] part, size;
	
	private static int findPart (int node) {
		if (part [node] == node) return node;
		return part [node] = findPart (part [node]);
	}
	
	private static boolean join (int a, int b) {
		int x = findPart (a), y = findPart (b);
		if (x == y) return false;
		if (size [y] > size [x]) {
			x ^= y; y ^= x; x ^= y;
		}
		part [y] = x;
		size [x] += size [y];
		return true;
	}
	
	static class Edge implements Comparable <Edge> {
		public int from, to, cost1, cost2;
		
		public Edge (int from, int to, int cost1, int cost2) {
			this.from = from;
			this.to = to;
			this.cost1 = cost1;
			this.cost2 = cost2;
		}
		
		public int compareTo (Edge other) {
			return this.cost2 - this.cost1 - other.cost2 + other.cost1;
		}
	}

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int T = in.nextInt();
		while (T-- > 0) {
			int N = in.nextInt(), M = in.nextInt();
			
			Edge [] edges = new Edge [M];
			for (int i = 0; i < M; i++)
				edges [i] = new Edge (in.nextInt() - 1, in.nextInt() - 1, in.nextInt(), in.nextInt());
			Arrays.sort (edges);
			
			part = new int [N];
			size = new int [N];
			for (int i = 0; i < N; i++) {
				part [i] = i;
				size [i] = 1;
			}
			
			long res = 0;
			int k = 0;
			for (; k < M && edges [k].cost2 <= edges [k].cost1; k++) {
				res += edges [k].cost2;
				join (edges [k].from, edges [k].to);
			}
			
			for (; k < M; k++)
				res += join (edges [k].from, edges [k].to) ? edges [k].cost2 : edges [k].cost1;
				
			out.println (res);
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
