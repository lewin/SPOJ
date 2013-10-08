import java.io.*;
import java.util.*;

public class powtow {
	private static Reader in;
	private static PrintWriter out;
	
	public static long [] pow5;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int C = in.nextInt();
		
		pow5 = new long [10];
		pow5 [0] = 1;
		for (int i = 1; i < 10; i++)
			pow5 [i] = pow5 [i - 1] * 5;
		
		while (C-- > 0) {
			int a = in.nextInt(), b = in.nextInt();
			if (b == 0) out.println (1);
			else if (a == 0)
				out.println (b % 2 == 1 ? 1 : 0);
			else if ((a == 2 && b <= 4) || (a == 3 && b <= 2) || 
					a <= 999999999 && b <= 1)
				out.println (tower (a, b, 9, 9));
			else if (a % 10 == 0)
				out.printf ("...%09d\n", 0);
			else
				out.printf ("...%09d\n", tower (a, b, 9, 9));
		}
		out.close();
		System.exit(0);
	}
	
	private static long tower (long base, long left, int m2, int m5) {
		if (m2 == 0 && m5 == 0) return 0;
		long exp;
		if (m5 > 0) exp = tower (base, left - 1, m2 + 1, m5 - 1);
		else exp = tower (base, left - 1, m2 - 1, 0);
		
		long mod = (1 << m2) * pow5 [m5];
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = (res * base) % mod;
			exp >>= 1;
			base = (base * base) % mod;
		}
		return res;
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
