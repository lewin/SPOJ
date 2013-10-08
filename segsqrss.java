import java.io.*;
import java.util.*;

public class segsqrss {
	private static Reader in;
	private static PrintWriter out;

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int T = in.nextInt(), t = 0;
		while (T-- > 0) {
			out.printf ("Case %d:\n", ++t);
			int N = in.nextInt(), Q = in.nextInt();
			arr = new long [N + 1];
			for (int i = 1; i <= N; i++)
				arr [i] = in.nextInt();
			SegmentTree root = new SegmentTree (1, N);
			while (Q-- > 0) {
				int cmd = in.nextInt();
				switch (cmd) {
				case 0 :
					root.update0 (in.nextInt(), in.nextInt(), in.nextInt());
					break;
				case 1 :
					root.update1 (in.nextInt(), in.nextInt(), in.nextInt());
					break;
				case 2 :
					out.println (root.query (in.nextInt(), in.nextInt()));
					break;
				}
			}
		}
		out.close();
		System.exit(0);
	}
	
	private static long [] arr;
	
	static class SegmentTree {
		public SegmentTree rchild = null, lchild = null;
		public long sq, sum, lazya, lazyb;
		public int start, end, mid;
		public boolean set;
		
		public SegmentTree (int start, int end) {
			this.start = start;
			this.end = end;
			mid = (start + end) >> 1;
			set = false;
			if (start != end) {
				lchild = new SegmentTree (start, mid);
				rchild = new SegmentTree (mid + 1, end);
				join();
			} else {
				sq = arr [start] * arr [start];
				sum = arr [start];
			}
		}
		
		public void join() {
			if (lchild != null) {
				sq = rchild.sq + lchild.sq;
				sum = rchild.sum + lchild.sum;
			}
		}
		
		public void push() {
			if (lchild != null) {
				if (set) {
					lchild.modify0 (lazya);
					rchild.modify0 (lazya);
					lazya = 0;
					set = false;
				}
				lchild.modify1(lazyb);
				rchild.modify1(lazyb);
				join();
			}
		}
		
		public void modify0 (long val) {
			set = true;
			lazya = val;
			lazyb = 0;
			sq = val * val * len();
			sum = val * len();
		}
		
		public void modify1 (long val) {
			sq += val * (2 * sum + len() * val);
			sum += val * len();
			lazyb = val;
		}
		
		public long len() {
			return end - start + 1;
		}
		
		public long query (int s, int e) {
			if (start == s && end == e)
				return sq;
			push();
			if (mid >= e) return lchild.query (s, e);
			else if (mid < s) return rchild.query (s, e);
			else return lchild.query (s, mid) + rchild.query (mid + 1, e);
		}
		
		public void update0 (int s, int e, long val) {
			if (start == s && end == e) {
				modify0 (val);
				return;
			}
			push();
			if (mid >= e) lchild.update0 (s, e, val);
			else if (mid < s) rchild.update0 (s, e, val);
			else {
				lchild.update0 (s, mid, val);
				rchild.update0 (mid + 1, e, val);
			}
			join();
		}
		
		public void update1 (int s, int e, long val) {
			if (start == s && end == e) {
				modify1 (val);
				return;
			}
			push();
			if (mid >= e) lchild.update1 (s, e, val);
			else if (mid < s) rchild.update1 (s, e, val);
			else {
				lchild.update1 (s, mid, val);
				rchild.update1 (mid + 1, e, val);
			}
			join();
		}
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
