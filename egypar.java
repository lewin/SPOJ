import java.io.*;
import java.util.*;

public class egypar {
	private static Reader in;
	private static PrintWriter out;
	
	private static int N, S, idx;
	private static int [] votes;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int T = in.nextInt ();
		int test = 0;
		
		while (T-- > 0) {
			N = in.nextInt ();
			S = in.nextInt ();
			in.readLine ();
			votes = new int[N];
			for (int i = 0; i < N; i++) {
				String s = in.next ();
				votes [i] = in.nextInt();
				if (s.equals ("ACM"))
					idx = i;
			}
			
			int d = D (), s = S ();
			out.printf ("Case %d: ", ++test);
			out.println (d == s ? "No difference" : d < s ? "S" : "D");
		}
		out.close ();
		System.exit (0);
	}
	
	private static int D () {
		PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
		for (int i = 0; i < N; i++)
			pq.add (new Pair (votes[i], 1, i));
		
		int [] count = new int[N];
		for (int j = 0; j < S; j++) {
			Pair p = pq.poll ();
			count[p.c]++;
			pq.add (new Pair (p.a, p.b + 1, p.c));
		}
		return count[idx];
	}
	
	private static int S () {
		PriorityQueue <Pair> pq = new PriorityQueue <Pair> ();
		for (int i = 0; i < N; i++)
			pq.add (new Pair (votes[i], 1, i));
		
		int [] count = new int[N];
		for (int j = 0; j < S; j++) {
			Pair p = pq.poll ();
			count[p.c]++;
			pq.add (new Pair (p.a, p.b + 2, p.c));
		}
		return count[idx];
	}
	
	static class Pair implements Comparable <Pair> {
		public int a, b, c;
		
		public Pair (int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		public int compareTo (Pair other) {
			return a * other.b == b * other.a ? c - other.c : other.a * b - a * other.b;
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
		
		public String next () throws IOException {
			byte [] buf = new byte[1024];
			int cnt = 0, c;
			while ((c = read ()) != -1) {
				if (c <= ' ')
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
