import java.io.*;
import java.util.*;

public class george {
	private static Reader in;
	private static PrintWriter out;
	
	private static int [] eadj, elast, eprev, ecost;
	private static int eidx;
	
	private static void addEdge (int a, int b, int c) {
		eadj [eidx] = b; ecost [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
		eadj [eidx] = a; ecost [eidx] = c; eprev [eidx] = elast [b]; elast [b] = eidx++;
	}
	
	private static int findTime (int a, int b) {
		for (int e = elast [a]; e != -1; e = eprev [e])
			if (eadj [e] == b)
				return ecost [e];
		return -1;
	}
	
	static class Edge implements Comparable <Edge> {
		public int to, weight;
		
		public Edge (int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
		
		public int compareTo (Edge other) {
			return weight - other.weight;
		}
	}
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		int N = in.nextInt(), M = in.nextInt();
		int A = in.nextInt(), B = in.nextInt(), K = in.nextInt(), G = in.nextInt();
		A--; B--;
		
		eadj = new int [2 * M];
		elast = new int [N];
		eprev = new int [2 * M];
		ecost = new int [2 * M];
		Arrays.fill (elast, -1);
		eidx = 0;
		
		int [] path = new int [G];
		int [] time = new int [G];
		int [] sumTime = new int [G];
		for (int i = 0; i < G; i++)
			path [i] = in.nextInt() - 1;
		
		for (int i = 0; i < M; i++) {
			int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
			a--; b--;
			addEdge (a, b, c);
		}
		
		for (int i = 0; i < G - 1; i++)
			time [i] = findTime (path [i], path [i + 1]);
		sumTime [0] = time [0];
		for (int i = 1; i < G - 1; i++)
			sumTime [i] = sumTime [i - 1] + time [i];
		
		
		
		int [] dist = new int [N];
		Arrays.fill (dist, 1 << 29);
		PriorityQueue <Edge> pq = new PriorityQueue <Edge> ();
		pq.add (new Edge (A, dist [A] = K));
		int idx = 0;
		
		while (pq.size() > 0) {
			Edge deq = pq.poll();
			
			if (deq.weight != dist [deq.to]) continue;
			
			while (idx < G - 2 && sumTime [idx] < deq.weight) idx++;
			int first = path [idx], second = path [idx + 1];
			
			for (int e = elast [deq.to]; e != -1; e = eprev [e]) {
				int wait;
				if ((first == deq.to && second == eadj [e]) ||
						(second == deq.to && first == eadj [e])) {
					wait = Math.max (0, sumTime [idx] - deq.weight);
				} else wait = 0;
				if (ecost [e] + wait + deq.weight < dist [eadj [e]])
					pq.add (new Edge (eadj [e], dist [eadj [e]] = ecost [e] + wait + deq.weight));
			}
		}
		out.printf ("%d\n", dist [B] - K);
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
