import java.io.*;
import java.util.*;

public class amr11h {
	private static Reader in;
	private static PrintWriter out;
	private static int mod = 1000000007;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int T = in.nextInt ();
		while (T-- > 0) {
			int N = in.nextInt ();
			
			int [] arr = new int[N];
			int min = Integer.MAX_VALUE, max = 0;
			for (int i = 0; i < N; i++) {
				arr[i] = in.nextInt ();
				if (arr[i] > max)
					max = arr[i];
				if (arr[i] < min)
					min = arr[i];
			}
			
			int res1 = 0;
			int prev_idx = -1, max_idx = 0, min_idx = 0;
			for (int i = 0; i < N; i++) {
				if (arr[i] == min) {
					if (max_idx < i)
						max_idx = i;
					while (max_idx < N && arr[max_idx] != max)
						max_idx++;
					
					if (max_idx == N)
						break;
					
					res1 += (int) (((long) (i - prev_idx) * (N - max_idx)) % mod);
					prev_idx = i;
				} else if (arr[i] == max) {
					if (min_idx < i)
						min_idx = i;
					while (min_idx < N && arr[min_idx] != min)
						min_idx++;
					
					if (min_idx == N)
						break;
					
					res1 += (int) (((long) (i - prev_idx) * (N - min_idx)) % mod);
					prev_idx = i;
				}
				if (res1 >= mod)
					res1 -= mod;
			}
			
			int res2 = 0;
			int num_min = 0, num_max = 0, rest = 0;
			for (int i = 0; i < N; i++) {
				if (arr[i] == min)
					num_min++;
				else if (arr[i] == max)
					num_max++;
				else rest++;
			}
			
			res2 += (int) ((mod_pow (2, rest) * (mod_pow (2, num_min) - 1) * (mod_pow (2, num_max) - 1)) % mod);
			
			out.println (res1 + " " + res2);
		}
		out.close ();
		System.exit (0);
	}
	
	private static long mod_pow (long b, int e) {
		long res = 1;
		while (e > 0) {
			if ((e & 1) == 1)
				res = (res * b) % mod;
			b = (b * b) % mod;
			e >>= 1;
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
