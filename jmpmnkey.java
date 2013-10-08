import java.io.*;
import java.util.*;

public class jmpmnkey {
	private static Reader in;
	private static PrintWriter out;
	
	private static int [] eadj, elast, eprev;
	private static int eidx;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		while (true) {
			int N = in.nextInt(), M = in.nextInt();
			if (N == 0 && M == 0) break;
			
			eadj = new int [2 * M];
			elast = new int [N];
			eprev = new int [2 * M];
			eidx = 0;
			Arrays.fill (elast, -1);
			for (int i = 0; i < M; i++) {
				int a = in.nextInt(), b = in.nextInt();
				addEdge (a, b);
			}
			
			int [] prev = new int [1 << N];
			int [] move = new int [1 << N];
			boolean [] vis = new boolean [1 << N];
			
			int state = (1 << N) - 1;
			int [] queue = new int [1 << N];
			
			int front = 0, back = 0;
			queue [back++] = state;
			vis [state] = true;
			prev [state] = -1;
			move [state] = -1;
			while (front < back) {
				int cur = queue [front++];
				
				for (int i = 0; i < N; i++) {
					int p = (cur & (~(1 << i)));
					int nstate = 0;
					for (int j = 0; j < N; j++) {
						if ((p & (1 << j)) != 0) {
							for (int e = elast [j]; e != -1; e = eprev [e])
								nstate |= 1 << eadj [e];
						}
					}
					
					if (vis [nstate]) continue;
					prev [nstate] = cur;
					move [nstate] = i;
					vis [nstate] = true;
					queue [back++] = nstate;
				}
			}
			
			if (!vis [0]) {
				out.println ("Impossible");
				continue;
			}
			
			int moveCount = 0;
			int current = 0;
			Stack <Integer> st = new Stack <Integer> ();
			while (prev [current] != -1) {
				moveCount++;
				st.push (move [current]);
				current = prev [current];
			}
			
			out.printf ("%d:", moveCount);
			while (!st.empty()) out.printf (" %d", st.pop());
			out.println();
		}
		
		out.close();
		System.exit(0);
	}
	
	private static void addEdge (int a, int b) {
		eadj [eidx] = b; eprev [eidx] = elast [a]; elast [a] = eidx++;
		eadj [eidx] = a; eprev [eidx] = elast [b]; elast [b] = eidx++;
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
