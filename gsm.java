import java.io.*;
import java.util.*;

public class gsm {
	private static Reader in;
	private static PrintWriter out;
	
	private static double probpoint, probgame, probset, probmatch;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		while (true) {
			double prob = in.nextDouble();
			if (prob == -1) break;
			
			probpoint = prob;
			probgame = calc (probpoint, 4);
			probset = calc2 (probgame, 6);
			probmatch = probset * probset + 2 * (1 - probset) * probset * probset;
			
			out.printf ("%.12f %.12f %.12f\n", probgame, probset, probmatch);
		}
		
		out.close(); 
		System.exit(0);
	}
	
	public static double calc (double prob, int lim) {
		double [][] dp = new double [lim][lim];
		dp [0][0] = 1;
		for (int i = 1; i < lim; i++) {
			dp [i][0] = dp [i - 1][0] * prob;
			dp [0][i] = dp [0][i - 1] * (1 - prob);
		}
		
		for (int i = 1; i < lim; i++)
			for (int j = 1; j < lim; j++)
				dp [i][j] = dp [i - 1][j] * prob + dp [i][j - 1] * (1 - prob);
		
		double ans = 0;
		for (int i = 0; i < lim - 1; i++)
			ans += dp [lim - 1][i] * prob;
		
		ans += dp [lim - 1][lim - 1] * prob * prob / (2 * prob * prob - 2 * prob + 1);
		
		return ans;
	}
	
	public static double calc2 (double prob, int lim) {
		double [][] dp = new double [lim][lim];
		dp [0][0] = 1;
		for (int i = 1; i < lim; i++) {
			dp [i][0] = dp [i - 1][0] * prob;
			dp [0][i] = dp [0][i - 1] * (1 - prob);
		}
		
		for (int i = 1; i < lim; i++)
			for (int j = 1; j < lim; j++)
				dp [i][j] = dp [i - 1][j] * prob + dp [i][j - 1] * (1 - prob);
		
		double ans = 0;
		for (int i = 0; i < lim - 1; i++)
			ans += dp [lim - 1][i] * prob;
		
		double p = dp [lim - 1][lim - 1];
		ans += p * prob * prob;
		ans += p * 2 * prob * (1 - prob) * calc (probpoint, 7);
		
		return ans;
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
