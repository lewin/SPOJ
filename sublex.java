import java.io.*;
import java.util.*;

public class sublex {
	private static BufferedReader in;
	private static PrintWriter out;

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader (System.in));
		out = new PrintWriter(System.out, true);
		
		char [] s = in.readLine().toCharArray();
		int N = s.length;
		int [] arr = suffix_array (s);
		int [] arr3 = new int [N];
		for (int i = 0; i < N; i++)
			arr3 [arr [i]] = i;
		int [] arr2 = lcp (s, arr, arr3);
		
		int [] pcount = new int [N];
		pcount [0] = N - arr3 [0];
		for (int i = 1; i < N; i++)
			pcount [i] = pcount [i - 1] + (N - arr3 [i]) - arr2 [i];
		
		int T = Integer.parseInt (in.readLine());
		while (T-- > 0) {
			int idx = Integer.parseInt (in.readLine());
			int lo = 0, hi = N - 1;
			while (lo < hi) {
				int mid = (lo + hi) >> 1;
				if (pcount [mid] < idx) lo = mid + 1;
				else hi = mid;
			}
			
			for (int i = arr3 [lo]; i <= N - (pcount [lo] - idx) - 1; i++)
				out.print (s [i]);
			out.println();
		}
		out.close();
		System.exit(0);
	}
	
	private static int [] suffix_array (char [] s) {
		int n = s.length;
		int [] id = new int [n];
		for (int i = 0; i < n; i++)
			id[i] = s[i] - 'a';
		
		for (int k = 1; k <= n; k <<= 1) {
			Triplet [] elem = new Triplet [n];
			for (int i = 0; i < n; i++)
				elem[i] = new Triplet (id[i], (i + k < n) ? id[i + k] : -1, i);
			Arrays.sort (elem);
			int cur = -1;
			for (int i = 0; i < n; i++) {
				if (i == 0
						|| !(elem[i].a == elem[i - 1].a && elem[i].b == elem[i - 1].b))
					cur++;
				id[elem[i].c] = cur;
			}
		}
		
		return id;
	}
	
	private static int [] lcp (char [] A, int [] order, int [] rank) {
		int n = order.length;
		int [] height = new int [n];
		
		int h = 0;
		for (int i = 0; i < n; i++) {
			if (order [i] > 0) {
				int j = rank [order [i] - 1];
				while (i + h < n && j + h < n 
						&& A [i + h] == A [j + h]) h++;
				height [order [i]] = h;
				if (h > 0) h--;
			}
		}
		
		return height;
	}
	
	static class Triplet implements Comparable <Triplet> {
		public int	a, b, c;
		
		public Triplet (int _a, int _b, int _c) {
			a = _a;
			b = _b;
			c = _c;
		}
		
		public int compareTo (Triplet other) {
			return (a == other.a) ? b - other.b : a - other.a;
		}
	}
}
