import java.io.*;
import java.util.*;
import java.math.*;

public class cointoss {
	private static Reader in;
	
	public static void main (String [] args) throws IOException {
		for (int i = (in = new Reader()).nextInt(); i > 0; i--) System.out.printf ("%s.00\n", new BigInteger ("0").setBit (in.nextInt() + 1).subtract (BigInteger.ONE).xor (new BigInteger ("0").setBit (in.nextInt() + 1).subtract (BigInteger.ONE)));
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
	}
}
