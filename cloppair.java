import java.io.*;
import java.util.*;

public class cloppair {
	private static Reader		in;
	private static PrintWriter	out;
	public static final int INF = 1 << 26;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		int N = in.nextInt();
		Point [] points = new Point [N];
		for (int i = 0; i < N; i++) {
			points [i] = new Point (in.nextInt(), in.nextInt(), i);
		}
		State res = closestPair (points);
		out.printf ("%d %d %.6f\n", res.p1, res.p2, res.dist);
		out.close();
		System.exit(0);
	}
	
	private static State closestPair(Point[] points) {
		Arrays.sort (points);
		double min = 1e9;
		int p1 = -1, p2 = -1;
		int left = 0;
		
		TreeSet <Point> curset = new TreeSet <Point>(
				new Comparator <Point> () {
					public int compare (Point a, Point b) {
						return a.y == b.y ? a.x - b.x : a.y - b.y;
					}
				});
		
		for (Point p : points) {
			while (p.x - points [left].x > min)
				curset.remove (points [left++]);
			for (Point next : curset.subSet (
					new Point (p.x, (int)(p.y - min), 0),
					new Point (p.x, (int)(p.y + min), 0))) {
				double temp = dist (p, next);
				if (temp < min) {
					min = temp;
					p1 = p.idx; p2 = next.idx;
				}
			}
			curset.add (p);
		}
		
		return new State (p1, p2, min);
	}
	
	private static double dist (Point p1, Point p2) {
		return Math.sqrt ((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
	}
	
	static class Point implements Comparable <Point> {
		public int x, y, idx;
		
		public Point (int x, int y, int idx) {
			this.x = x;
			this.y = y;
			this.idx = idx;
		}
		
		public int compareTo (Point other) {
			if (x == other.x) return y - other.y;
			return x - other.x;
		}
	}

	static class State {
		public int p1, p2;
		public double dist;
		
		public State (int p1, int p2, double dist) {
			if (p2 < p1) {p2 ^= p1; p1 ^= p2; p2 ^= p1;}
			this.p1 = p1;
			this.p2 = p2;
			this.dist = dist;
		}
	}
	static class Reader {
		final private int			BUFFER_SIZE	= 1 << 16;
		private DataInputStream	din;
		private byte []			buffer;
		private int					bufferPointer, bytesRead;
		
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
				if (c == '\n') break;
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
			if (neg) c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg) return -ret;
			return ret;
		}
		
		public long nextLong () throws IOException {
			long ret = 0;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg) c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (neg) return -ret;
			return ret;
		}
		
		public double nextDouble () throws IOException {
			double ret = 0, div = 1;
			byte c = read ();
			while (c <= ' ')
				c = read ();
			boolean neg = (c == '-');
			if (neg) c = read ();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read ()) >= '0' && c <= '9');
			if (c == '.') while ((c = read ()) >= '0' && c <= '9')
				ret += (c - '0') / (div *= 10);
			if (neg) return -ret;
			return ret;
		}
		
		private void fillBuffer () throws IOException {
			bytesRead = din.read (buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1) buffer[0] = -1;
		}
		
		private byte read () throws IOException {
			if (bufferPointer == bytesRead) fillBuffer ();
			return buffer[bufferPointer++];
		}
		
		public void close () throws IOException {
			if (din == null) return;
			din.close ();
		}
	}
}
