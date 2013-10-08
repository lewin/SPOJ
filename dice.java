import java.io.*;
import java.util.*;

public class dice {
	private static Reader in;
	private static PrintWriter out;
	
	public static void main (String [] args) throws IOException {
		in = new Reader ();
		out = new PrintWriter (System.out, true);
		
		while (true) {
			double [][] arr = new double [3][4];
			boolean ok = false;
			for (int i = 0; i < 3; i++)
				ok |= ((arr [i][0] = in.nextInt()) != 0);
			if (!ok) break;
			
			for (int j = 1; j < 4; j++)
				for (int i = 0; i < 3; i++)
					arr [i][j] = in.nextInt();
			
			rref (arr);
			
			ok = true;
			for (int i = 0; i < 3; i++) {
				boolean zero = true;
				for (int j = 0; j < 3; j++)
					if (arr [i][j] != 0)
						zero = false;
				if (zero && arr [i][3] != 0)
					ok = false;
				
			}
		}
	}
	

	private static void rref (double [][] M) {
		int row = M.length;
		if (row == 0)
			return;
		
		int col = M[0].length;
		
		int lead = 0;
		for (int r = 0; r < row; r++) {
			if (lead >= col)
				return;
			
			int k = r;
			while (M[k][lead] == 0) {
				k++;
				if (k == row) {
					k = r;
					lead++;
					if (lead == col)
						return;
				}
			}
			
			if (k != r) {
				double [] temp = M [r];
				M [r] = M [k];
				M [k] = temp;
			}
			
			double lv = M[r][lead];
			for (int j = 0; j < col; j++)
				M[r][j] = M[r][j] / lv;
			
			for (int i = 0; i < row; i++) {
				if (i != r) {
					lv = M[i][lead];
					for (int j = 0; j < col; j++)
						M[i][j] -= lv * M [r][j];
				}
			}
			lead++;
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
