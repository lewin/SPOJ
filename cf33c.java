import java.io.*;
import java.util.*;

public class cf33c {
	private static Reader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int T = in.nextInt();
		while (T-- > 0) {
			int N = in.nextInt();
			int [] arr = new int [N];
			for (int i = 0; i < N; i++)
				arr [i] = in.nextInt();
			
			int [] pre = new int [N], suf = new int [N];
			pre [0] = arr [0]; suf [N - 1] = arr [N - 1];
			for (int i = 1; i < N; i++) {
				pre [i] = pre [i - 1] + arr [i];
				suf [N - i - 1] = suf [N - i] + arr [i];
			}
			
			int sub = Math.min (0, pre [0]);
			pre [0] -= 2 * sub;
			for (int i = 1; i < N; i++) {
				if (pre [i] < sub)
					sub = pre [i];
				pre [i] -= 2 * sub;
			}
			sub = Math.min (0, suf [N - 1]);
			suf [N - 1] -= 2 * sub;
			for (int i = N - 2; i >= 0; i--) {
				if (suf [i] < sub)
					sub = suf [i];
				suf [i] -= 2 * sub;
			}

			int ans = 0;
			for (int i = 1; i < N; i++) {
				if (pre [i - 1] + suf [i] > ans)
					ans = pre [i - 1] + suf [i];
			}
			
			out.println (ans);
		}
		
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
