import java.io.*;
import java.util.*;

public class twodel2 {
	private static Reader in;
	private static PrintWriter out;
	
	static class Pair implements Comparable <Pair> {
		public long val, pos;
		
		public Pair (long val, long pos) {
			this.val = val;
			this.pos = pos;
		}
		
		public int compareTo (Pair other) {
			return (int)(Math.signum (pos - other.pos));
		}
	}

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		
		int N = in.nextInt();
		
		long [] pos = new long [N + 1];
		for (int i = 1; i <= N; i++)
			pos [i] = in.nextInt();
		
		SplayTree tree = new SplayTree ();
		tree.insert (new Node (new Pair (Math.abs (pos [0] - pos [1]), 0)));
		for (int i = 1; i < N; i++) {
			System.out.println (tree.put(pos [i + 1], pos [i]));
			tree.root.lazy += Math.abs (pos [i] - pos [i - 1]);
			tree.getMin (tree.root);
			System.out.println (tree);
		}
		
		out.println (tree.getMin(tree.root));
		out.println (tree);
//		out.close();
//		System.exit(0);
		
		long [] dp = new long [N];
		Arrays.fill (dp, 1l << 40);
		dp [0] = Math.abs (pos [1] - pos [0]);
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j < i - 1; j++) {
				if (dp [j] + Math.abs (pos [i] - pos [j]) < dp [i - 1])
					dp [i - 1] = dp [j] + Math.abs (pos [i] - pos [j]);
				dp [j] += Math.abs (pos [i] - pos [i - 1]);
			}
			System.out.println (Arrays.toString (dp));
		}
		
		long min = Long.MAX_VALUE;
		for (int i = 0; i < N; i++)
			if (dp [i] < min)
				min = dp [i];
				
		out.println (min);
		
		out.close();
		System.exit(0);
	}
	
	static class Node implements Comparable <Node> {
		public Pair pair;
		public long mina, minb, lazy;
		public Node left, right, par;
		
		public Node (Pair pair) {
			this.pair = pair;
		}
		
		public int compareTo (Node other) {
			return pair.compareTo (other.pair);
		}
		
		public void join() {
			mina = pair.val - pair.pos;
			minb = pair.val + pair.pos;
			if (left != null) {
				if (right == null) {
					mina = Math.min (mina, left.mina);
					minb = Math.min (minb, left.minb);
				} else {
					mina = Math.min (mina, Math.min (left.mina, right.mina));
					minb = Math.min (minb, Math.min (left.minb, right.minb));
				}
			} else if (right != null) {
				mina = Math.min (mina, right.mina);
				minb = Math.min (minb, right.minb);
			}
		}
		
		public void push() {
			if (left != null) {
				left.lazy += lazy;
				left.mina += lazy;
				left.minb += lazy;
				left.pair.val += lazy;
			}
			if (right != null) {
				right.lazy += lazy;
				right.mina += lazy;
				right.minb += lazy;
				right.pair.val += lazy;
			}
			lazy = 0;
		}
	}
	
	static class SplayTree {
		public Node root;
		
		public SplayTree() {
			root = null;
		}
		
		public long getMin (Node n) {
			n.push();
			long min = n.pair.val;
			if (n.left != null)
				min = Math.min (getMin (n.left), min);
			if (n.right != null)
				min = Math.min (getMin (n.right), min);
			n.join();
			return min;
		}
		
		public long put (long pos, long pos2) {
			Node s = new Node (new Pair (0, pos));
			splay (find (s));
			long a = Long.MAX_VALUE >> 2, b = root.minb;
			if (root.left != null)
				a = root.left.mina;
			if (root.right != null)
				b = Math.min (b, root.right.minb);
			s.pair.val = Math.min (a + pos, b - pos);
			s.pair.pos = pos2;
			insert (s);
			s.join();
			return s.pair.val;
		}
		
		public void insert (Node n) {
			if (root == null) {
				root = n;
				root.join();
			} else {
				Node temp1 = find (n);
				
				temp1.push();
				if (temp1.compareTo (n) > 0) {
					n.par = temp1;
					temp1.left = n;
					temp1 = temp1.left;
				} else {
					n.par = temp1;
					temp1.right = n;
					temp1 = temp1.right;
				}
				splay (temp1);
			}
		}
		
		public Node find (Node n) {
			Node temp1, temp2;
			temp1 = temp2 = root;
			while (temp2 != null) {
				temp2.push();
				temp1 = temp2;
				if (temp2.compareTo (n) > 0)
					temp2 = temp2.left;
				else
					temp2 = temp2.right;
			}
			return temp1;
		}
		
		public void splay (Node n) {
			if (n == root) return;
			if (n.par == root) {
				if (n == n.par.left)
					rightrotate (root);
				else
					leftrotate (root);
			} else {
				Node p = n.par, g = p.par;
				
				if (n == p.left && p == g.left) {
					rightrotate (g);
					rightrotate (p);
				} else if (n == p.right && p == g.right) {
					leftrotate (g);
					leftrotate (p);
				} else if (n == p.right & p == g.left) {
					leftrotate (p);
					rightrotate (g);
				} else if (n == p.left && p == g.right) {
					rightrotate (p);
					leftrotate (g);
				}
				splay (n);
			}
		}
		
		private void rightrotate (Node n) {
			Node x = n.left;
			n.left = x.right;
			if (x.right != null)
				x.right.par = n;
			x.right = n;
			if (n.par != null)
				if (n == n.par.right)
					n.par.right = x;
				else
					n.par.left = x; 
			x.par = n.par;
			n.par = x;
			if (n == root) 
				root = x;
			n.join();
			x.join();
		}
		
		private void leftrotate (Node n) {
			Node x = n.right;
			n.right = x.left;
			if (x.left != null)
				x.left.par = n;
			x.left = n;
			if (n.par != null)
				if (n == n.par.left)
					n.par.left = x;
				else
					n.par.right = x;
			x.par = n.par;
			n.par = x;
			if (n == root)
				root = x;
			n.join();
			x.join();
		}
		
		public String toString() {
			return toString (root, true);
		}
		
		public String toString (Node n, boolean first) {
			String res = "";
			if (n.left != null)
				res += toString (n.left, false);
			res += " (" + n.pair.val + "," + n.pair.pos + ")" + (first ? "*" : "") + " ";
			if (n.right != null)
				res += toString (n.right, false);
			return res;
		}
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
