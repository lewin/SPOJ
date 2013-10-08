import java.io.*;
import java.util.*;

public class skiing {
	private static Reader in;
	private static PrintWriter out;
	
	static class Interval {
		public double start, end;
		
		public Interval (double start, double end) {
			this.start = start;
			this.end = end;
		}
	}
	
	static class State implements Comparable <State> {
		public double time;
		
		public State (double time) {
			this.time = time;
		}
		
		public int compareTo (State other) {
			return (int)Math.signum (time - other.time);
		}
	}
	
	private static double calc (double from, double to) {
		int upper = N + 10;
		double [] poly = new double [upper];
		poly [0] = 1;
		
		for (int i = 1; i < N; i++) {
			if (times [i].start >= to)
				continue;
			if (times [i].end <= from)
				return 0;
			
			double [] next = new double [upper];
			double div = times [i].end - times [i].start;
			for (int j = 0; j < upper; j++)
				next [j] = poly [j] * times [i].end / div;
			for (int j = 0; j < upper - 1; j++)
				next [j + 1] -= poly [j] / div;
			poly = next;
		}
		
		for (int i = 0; i < upper; i++)
			poly [i] /= (double)(i + 1);
		
		
		double cf = from, ct = to;
		double ans = 0;
		for (int i = 0; i < upper; i++) {
			ans += (ct - cf) * poly [i];
			ct *= to; cf *= from;
		}
		
		return ans;
	}
	
	private static Interval [] times;
	private static int N;
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		N = in.nextInt();
		times = new Interval [N];
		double stop = 1 << 29;
		double start = 0;
		for (int i = 0; i < N; i++) {
			double a = in.nextDouble(), b = in.nextDouble();
			if (b < stop) stop = b;
			times [i] = new Interval (a, b);
		}
		
		PriorityQueue <State> pq = new PriorityQueue <State> ();
		pq.add (new State (times [0].start));
		if (times [0].end <= stop) pq.add (new State (times [0].end));
		for (int i = 1; i < N; i++) {
			if (times [i].start <= stop && times [i].start >= times [0].start)
				pq.add (new State (times [i].start));
			if (times [i].end <= stop && times [i].end >= times [0].start)
				pq.add (new State (times [i].end));
		}
		
		double prob = 0;
		State prev = pq.poll();
		while (pq.size() > 0) {
			State cur = pq.poll();
			prob += calc (prev.time, cur.time);
			prev = cur;
		}
		prob /= (times [0].end - times [0].start);
		
		out.printf ("%.8f\n", prob);
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
