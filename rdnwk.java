import java.io.*;
import java.util.*;

public class rdnwk {
	private static Reader in;
	private static PrintWriter out;
	
	public static int INF = 1 << 27;

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int T = in.nextInt();
		int test = 0;
		
		while (T-- > 0) {
			int N = in.nextInt();
			int [][] arr = new int [N][N];
			
			for (int i = 0; i < N; i++) {
				arr [i][i] = 0;
				for (int j = i + 1; j < N; j++) {
					arr [i][j] = in.nextInt();
					if (arr [i][j] == -1) arr [i][j] = INF;
					arr [j][i] = arr [i][j];
				}
			}
			
			int P = in.nextInt();
			int [] order = new int [P];
			for (int i = 0; i < P; i++)
				order [i] = in.nextInt() - 1;
			
			int [][][] dist = new int [P + 1][N][N];
			dist [0] = arr;
			for (int i = 1; i <= P; i++)
				for (int j = 0; j < N; j++) {
					Arrays.fill (dist [i][j], INF);
					dist [i][j][j] = 0;
				}
			
			int [][] curbest = new int [N][N];
			for (int i = 0; i < N; i++)
				System.arraycopy (arr [i], 0, curbest [i], 0, N);
			
			for (int n = 1; n <= P; n++) {
				int k = order [n - 1];
				for (int i = 0; i < N; i++)
					for (int j = 0; j < N; j++) {
						if (curbest [i][k] + curbest [k][j] < dist [n][i][j])
							dist [n][i][j] = curbest [i][k] + curbest [k][j];
						if (dist [n][i][j] < curbest [i][j])
							curbest [i][j] = dist [n][i][j];
					}
			}
			
			int Q = in.nextInt();
			out.printf ("Case %d:", ++test);
			while (Q-- > 0) {
				int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
				b--; c--;
				out.printf (" %d", dist [a][b][c] == INF ? -1 : dist [a][b][c]);
			}
			out.println();
		}
		out.close();
		System.exit(0);
	}

	static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public Reader() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public Reader(String file_name) throws IOException {
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[1024];
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
			if (neg)
				return -ret;
			return ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}
