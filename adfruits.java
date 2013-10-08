import java.io.*;
import java.util.*;

public class adfruits {
	private static BufferedReader		in;
	private static PrintWriter	out;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new InputStreamReader (System.in));
		out = new PrintWriter (System.out, true);
		String line = in.readLine();
		while (line != null) {
			String [] sp = line.split (" ");
			String a = combinef (sp [0], sp [1]); int x1 = a.length();
			String b = combinef (sp [1], sp [0]); int x2 = b.length();
			String c = combiner (sp [0], sp [1]); int x3 = c.length();
			String d = combiner (sp [1], sp [0]); int x4 = d.length();
			if (x1 <= x2 && x1 <= x3 && x1 <= x4) out.println (a);
			else if (x2 <= x1 && x2 <= x3 && x2 <= x4) out.println (b);
			else if (x3 <= x1 && x3 <= x2 && x3 <= x4) out.println (c);
			else if (x4 <= x1 && x4 <= x2 && x4 <= x3) out.println (d);
			line = in.readLine ();
		}
	}
	
	private static String combinef (String a, String b) {
		char [] arr = a.toCharArray(), brr = b.toCharArray();
		int j = 0;
		for (int i = 0; i < arr.length && j < brr.length; i++)
			if (arr [i] == brr [j]) j++;
		return new String (arr) + new String (brr, j, brr.length - j);
	}
	
	private static String combiner (String a, String b) {
		char [] arr = a.toCharArray(), brr = b.toCharArray();
		int j = brr.length - 1;
		for (int i = arr.length - 1; i >= 0 && j >= 0; i--)
			if (arr [i] == brr [j]) j--;
		return new String (brr, 0, j + 1) + new String (arr);
	}
}
