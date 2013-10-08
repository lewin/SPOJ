import java.io.*;
import java.util.*;

public class transj {
	private static Reader		in;
	private static PrintWriter	out;
	private static int N, M, T;
	private static int [] start, end, pass, cur;
	private static int max;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		while (true) {
			N = in.nextInt(); M = in.nextInt(); T = in.nextInt();
			if (N == 0 && M == 0 && T == 0) break;
			start = new int [T]; end = new int [T]; pass = new int [T];
			for (int i = 0; i < T; i++) {
				start [i] = in.nextInt();
				end [i] = in.nextInt();
				pass [i] = in.nextInt();
			}
			
			cur = new int [M + 1];
			find_max (0, 0);
			out.println (max);
		}
	}
	
	private static void find_max (int idx, int cur_total) {
		if (idx == T) {
			if (cur_total > max) max = cur_total;
			return;
		}
		
		find_max (idx + 1, cur_total);
		for (int i = start [idx]; i < end [idx]; i++)
			if (cur [i] + pass [idx] > N)
				return;
		
		for (int i = start [idx]; i < end [idx]; i++)
			cur [i] += pass [idx];
		find_max (idx + 1, cur_total + (end [idx] - start [idx]) * pass [idx]);
		for (int i = start [idx]; i < end [idx]; i++)
			cur [i] -= pass [idx];
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
