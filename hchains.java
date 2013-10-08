import java.io.*;
import java.util.*;

public class hchains {
	private static Reader		in;
	private static PrintWriter	out;
	private static int []		eadj, elast, eprev;
	private static int			eidx;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		int T = in.nextInt ();
		while (T-- > 0) {
			int N = in.nextInt ();
			Point [] points = new Point [N];
			int x = in.nextInt (), y = in.nextInt (), ax = in.nextInt (), bx = in
					.nextInt (), mx = in.nextInt (), ay = in.nextInt (), by = in
					.nextInt (), my = in.nextInt ();
			
			points[0] = new Point (x, y, 0);
			for (int i = 1; i < N; i++)
				points[i] = new Point (
						(int) (((long) points[i - 1].x * ax + bx) % mx),
						(int) (((long) points[i - 1].y * ay + by) % my), i);
			
			eadj = new int [2 * N];
			elast = new int [N];
			eprev = new int [2 * N];
			Arrays.fill (elast, -1);
			eidx = 0;
			
			Arrays.sort (points, new Comparator <Point> () {
				public int compare (Point a, Point b) {
					
					return a.x - b.x;
				}
			});
			
			for (int i = 1; i < N; i++)
				if (points[i - 1].x == points[i].x) {
					addEdge (points[i - 1].idx, points[i].idx);
					i++;
				}
			
			Arrays.sort (points, new Comparator <Point> () {
				public int compare (Point a, Point b) {
					
					return a.y - b.y;
				}
			});
			
			for (int i = 1; i < N; i++)
				if (points[i - 1].y == points[i].y) {
					addEdge (points[i - 1].idx, points[i].idx);
					i++;
				}
			
			boolean [] A = new boolean [N];
			boolean [] vis = new boolean [N];
			int [] queueA = new int [N], queueB = new int [N];
			int frontA = 0, backA = 0, frontB = 0, backB = 0;
			for (int i = 0; i < N; i++) {
				if (!vis[i]) {
					queueA[backA++] = i;
					vis[i] = true;
					A[i] = true;
					while (frontA < backA || frontB < backB) {
						while (frontA < backA) {
							int cur = queueA[frontA++];
							for (int e = elast[cur]; e != -1; e = eprev[e]) {
								if (!vis[eadj[e]]) {
									queueB[backB++] = eadj[e];
									A[eadj[e]] = false;
									vis[eadj[e]] = true;
								}
							}
						}
						while (frontB < backB) {
							int cur = queueB[frontB++];
							for (int e = elast[cur]; e != -1; e = eprev[e]) {
								if (!vis[eadj[e]]) {
									queueA[backA++] = eadj[e];
									A[eadj[e]] = true;
									vis[eadj[e]] = true;
								}
							}
						}
					}
				}
			}
			
			for (int i = 0; i < N; i++)
				out.print (A[i] ? "A" : "B");
			out.println ();
		}
		System.exit (0);
	}
	
	private static void addEdge (int a, int b) {
		
		eadj[eidx] = b;
		eprev[eidx] = elast[a];
		elast[a] = eidx++;
		eadj[eidx] = a;
		eprev[eidx] = elast[b];
		elast[b] = eidx++;
	}
	
	static class Point {
		public int	x, y, idx;
		
		public Point (int x, int y, int idx) {
			
			this.x = x;
			this.y = y;
			this.idx = idx;
		}
	}
	
	static class Reader {
		final private int			BUFFER_SIZE	= 1 << 16;
		private DataInputStream	din;
		private byte []			buffer;
		private int					bufferPointer, bytesRead;
		
		public Reader () {
			
			din = new DataInputStream (System.in);
			buffer = new byte [BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
		
		public Reader (String file_name) throws IOException {
			
			din = new DataInputStream (new FileInputStream (file_name));
			buffer = new byte [BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
		
		public String readLine () throws IOException {
			
			byte [] buf = new byte [1024];
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
