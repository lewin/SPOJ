import java.io.*;
import java.util.*;

public class threetw1 {
	private static Reader		in;
	private static PrintWriter	out;
	private static int x1, y1, x2, y2, x3, y3;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		int T = in.nextInt();
		while (T-- > 0) {
			x1 = in.nextInt(); y1 = in.nextInt();
			x2 = in.nextInt(); y2 = in.nextInt();
			x3 = in.nextInt(); y3 = in.nextInt();
			
			double cx = (x1 + x2 + x3) / 3., cy = (y1 + y2 + y3) / 3.;
			double step = 5;
			double min = dist (cx, cy);
			while (step > 1e-10) {
				if (dist (cx + step, cy) < min) {
					min = dist (cx + step, cy);
					cx += step;
				}
				if (dist (cx - step, cy) < min) {
					min = dist (cx - step, cy);
					cx -= step;
				}
				if (dist (cx, cy + step) < min) {
					min = dist (cx, cy + step);
					cy += step;
				}
				if (dist (cx, cy - step) < min) {
					min = dist (cx, cy - step);
					cy -= step;
				}
				step *= 0.99;
			}
			out.printf ("%.8f %.8f\n", cx, cy);
		}
	}
	private static double dist (double cx, double cy) {
		return Math.sqrt ((cy - y1) * (cy - y1) + (cx - x1) * (cx - x1)) + 
				 Math.sqrt ((cy - y2) * (cy - y2) + (cx - x2) * (cx - x2)) + 
				 Math.sqrt ((cy - y3) * (cy - y3) + (cx - x3) * (cx - x3));
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
