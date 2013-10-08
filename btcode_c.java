import java.io.*;
import java.util.*;

public class btcode_c {
	private static Reader in;
	private static PrintWriter out;
	
	private static long [] vals;
	static class Pair implements Comparable <Pair> {
		public int a, b;
		
		public Pair (int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		public int compareTo (Pair other) {
			return b - other.b;
		}
	}
	
	static class SegmentTree {
		public SegmentTree lchild = null, rchild = null;
		public int start, end, lazy, max, idx;
		
		public SegmentTree (int start, int end) {
			if (start != end) {
				int mid = (start + end) >> 1;
				lchild = new SegmentTree (start, mid);
				rchild = new SegmentTree (mid + 1, end);
				join();
			} else idx = start;
		}
		
		public void join() {
			if (lchild != null) {
				lchild.lazy += lazy;
				lchild.max += lazy;
				rchild.lazy += lazy;
				rchild.max += lazy;
			}
			lazy = 0;
			if (lchild != null) {
				if (lchild.max > rchild.max) {
					max = lchild.max;
					idx = lchild.idx;
				} else {
					max = rchild.max;
					idx = rchild.idx;
				}
			}
		}
		
		public void update (int s, int e, int val) {
			if (s == start && e == end) {
				max += val;
				lazy += val;
				return;
			}
			if (s > e || s > end || start > e) return;
			
			join();
			int mid = (start + end) >> 1;
			if (mid >= e) lchild.update (s, e, val);
			else if (mid < s) rchild.update (s, e, val);
			else {
				lchild.update (s, mid, val);
				rchild.update (mid + 1, e, val);
			}
			join();
		}
		
		public int [] query (int s, int e) {
			join();
			if (s == start && e == end) 
				return new int [] {max, idx};
			if (s > e || s > end || start > e) 
				return new int [] {-1, -1};
			
			int mid = (start + end) >> 1;
			int [] ans = new int [2];
			if (mid >= e) ans = lchild.query (s, e);
			else if (mid < s) ans = rchild.query (s, e);
			else {
				int [] a1 = lchild.query (s, mid);
				int [] a2 = rchild.query (mid + 1, e);
				ans = a1[0] > a2[0] ? a1 : a2;
			}
			join();
			return ans;
		}
		
		
	}
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int N = in.nextInt();
		
		Pair [] pairs = new Pair [N];
		for (int i = 0; i < N; i++)
			pairs [i] = new Pair (in.nextInt(), in.nextInt());
		Arrays.sort (pairs);
		
		SegmentTree root = new SegmentTree (0, N - 1);
		for (int i = 0; i < N; i++) {
			System.out.println (pairs [i].a + " " + pairs [i].b);
			switch (pairs [i].a) {
				case 1 :
					root.update (i + 1, N - 1, 1);
					break;
				case 2 :
					root.update (0, i - 1, 1);
					break;
				case 3 :
					root.update (i, i, 1);
					break;
				case 4 :
					root.update (i, i, -1);
					root.update (0, N - 1, 1);
					break;
			}
		}
		
		int [] res = root.query (0, N - 1);
		out.println (res [0] + " " + res [1] + " " + pairs [res [1]].b);
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
