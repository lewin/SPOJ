import java.io.*;
import java.util.*;

public class dcepc206 {
	private static Reader in;
	private static PrintWriter out;
	
	static class Pair implements Comparable <Pair> {
		public int val, idx;
		
		public Pair (int val, int idx) {
			this.val = val;
			this.idx = idx;
		}
		
		public int compareTo (Pair other) {
			return val == other.val ? idx - other.idx : other.val - val;
		}
	}
	
	public static int [] tree;
	
	private static void update (int pos) {
		for (int i = pos; i < tree.length; i += (i & -i))
			tree [i]++;
	}
	
	private static int query (int pos) {
		int res = 0;
		for (int i = pos; i > 0; i -= (i & -i))
			res += tree [i];
		return res;
	}

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int T = in.nextInt();
		while (T-- > 0) {
			int N = in.nextInt();
			tree = new int [N + 1];
			Pair [] arr = new Pair [N];
			for (int i = 0; i < N; i++)
				arr [i] = new Pair (in.nextInt(), i + 1);
			Arrays.sort (arr);
			
			long res = 0;
			for (int i = 0; i < N; i++) {
				res += (long)(i - query (arr [i].idx)) * arr [i].val;
				update (arr [i].idx);
			}
			
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
