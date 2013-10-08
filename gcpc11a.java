import java.io.*;
import java.util.*;

public class gcpc11a {
	private static Reader		in;
	private static PrintWriter	out;
	private static boolean [] prime;
	private static int [] primes;
	private static final int max = 1000000;
	
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		prime = new boolean [max + 1];
		Arrays.fill (prime, true);
		prime [0] = prime [1] = false;
		for (int i = 4; i <= max; i += 2) prime [i] = false;
		int count = 1;
		for (int i = 3; i <= max; i += 2)
			if (prime [i]) {
				for (int j = i + i; j <= max; j += i)
					prime [j] = false;
				count++;
			}
		int idx = 0;
		primes = new int [count];
		for (int i = 0; i <= max; i++)
			if (prime [i])
				primes [idx++] = i;
		
		int t = in.nextInt();
		while (t-- > 0) {
			long n = in.nextLong(), k = in.nextLong();
			out.println (solve (n, k));
		}
	}
	
	private static long solve (long n, long k) {
		int [] count = new int [primes.length];
		for (int i = 0; i < count.length && primes [i] <= k; i++) {
			while (k % primes [i] == 0) {
				count [i]++;
				k /= primes [i];
			}
		}
		long div = Long.MAX_VALUE;
		for (int i = 0; i < count.length; i++) {
			if (count [i] > 0) {
				long mult = primes [i];
				long sum = 0;
				while (mult <= n) {
					sum += n / mult;
					if (Long.MAX_VALUE / primes [i] < mult) break;
					mult *= primes [i];
				}
				div = Math.min (div, sum / count [i]);
			}
		}
		if (k > 1) {
			long mult = k;
			long sum = 0;
			while (mult <= n) {
				sum += n / mult;
				if (Long.MAX_VALUE / k < mult) break;
				mult *= k;
			}
			div = Math.min (div, sum);
		}
		return div;
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
