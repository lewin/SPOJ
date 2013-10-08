import java.io.*;
import java.util.*;

public class parsums {
	private static Reader in;
	private static PrintWriter out;
	
	private static int [] val;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		while (true) {
			int N = in.nextInt();
			if (N == 0) break;
			
			int [] arr = new int [N];
			for (int i = 0; i < N; i++)
				arr [i] = in.nextInt();
			
			val = new int [N];
			val [0] = arr [0];
			for (int i = 1; i < N; i++)
				val [i] = val [i - 1] + arr [i];
			int sumAll = val [N - 1];
			
			SegmentTree root = new SegmentTree (0, N - 1);
			int count = 0;
			for (int i = 0; i < N; i++) {
				root.update (i, i, sumAll);
				root.update (0, N - 1, -arr [i]);
				if (root.query (0, N - 1) >= 0)
					count++;
			}
			
			out.println (count);
		}
		out.close();
		System.exit(0);
	}
	
	static class SegmentTree {
		public SegmentTree lchild = null, rchild = null;
		public int lazy, start, end, min;
		
		public SegmentTree (int start, int end) {
			this.start = start;
			this.end = end;
			if (start != end) {
				int mid = (start + end) >> 1;
				lchild = new SegmentTree (start, mid);
				rchild = new SegmentTree (mid + 1, end);
				min = Math.min (lchild.min, rchild.min);
				lazy = 0;
			} else min = val [start];
		}
		
		public void push() {
			if (lchild != null) {
				lchild.lazy += lazy;
				rchild.lazy += lazy;
				lchild.min += lazy;
				rchild.min += lazy;
			}
			join();
			lazy = 0;
		}
		
		public void join() {
			if (lchild != null)
				min = Math.min (lchild.min, rchild.min);
		}
		
		public int query (int s, int e) {
			if (s == start && e == end) return min;
			if (s > end || start > e) return Integer.MAX_VALUE;
			push();
			int mid = (start + end) >> 1;
			int ans = 0;
			if (mid >= e) ans = lchild.query (s, e);
			else if (mid < s) ans = rchild.query (s, e);
			else ans = Math.min (lchild.query (s, mid), rchild.query (mid + 1, e));
			join();
			return ans;
		}
		
		public void update (int s, int e, int val) {
			if (s == start && e == end) {
				lazy += val;
				min += val;
				return;
			}
			if (s > end || start > e) return;
			push();
			int mid = (start + end) >> 1;
			if (mid >= e) lchild.update (s, e, val);
			else if (mid < s) rchild.update (s, e, val);
			else {
				lchild.update (s, mid, val);
				rchild.update (mid + 1, e, val);
			}
			join();
		}
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
