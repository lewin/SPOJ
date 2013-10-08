import java.io.*;
import java.util.*;

public class maketree {
	private static Reader in;
	private static PrintWriter out;
	private static int [] eadj, eprev, elast, epar, indeg;
	private static int eidx;

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int N = in.nextInt(), K = in.nextInt();
		eadj = new int [10 * K];
		eprev = new int [10 * K];
		epar = new int [N];
		elast = new int [N];
		indeg = new int [N];
		Arrays.fill (elast, -1);
		Arrays.fill (epar, 1);
		eidx = 0;
		
		for (int i = 0; i < K; i++) {
			int W = in.nextInt();
			for (int j = 0; j < W; j++)
				addEdge (i, in.nextInt() - 1);
		}
		
		int [] queue = new int [N];
		int front = 0, back = 0;
		boolean [] vis = new boolean [N];
		int par = 0;
		for (int i = 0; i < N; i++)
			if (indeg [i] == 0) {
				queue [back++] = i;
				epar [i] = par;
				par = i + 1;
				vis [i] = true;
			}
		
		while (front < back) {
			int cur = queue [front++];
			for (int e = elast [cur]; e != -1; e = eprev [e]) {
				if (!vis [eadj [e]] && --indeg [eadj [e]] == 0) {
					vis [eadj [e]] = true;
					epar [eadj [e]] = cur + 1;
					queue [back++] = cur;
				}
			}
		}
		
		for (int i = 0; i < N; i++)
			out.println (epar [i]);
		
		out.close();
		System.exit(0);
	}
	
	private static void addEdge (int a, int b) {
		eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
		indeg [b]++;
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
