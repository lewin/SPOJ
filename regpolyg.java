import java.io.*;
import java.util.*;

public class regpolyg {
	private static BufferedReader in;
	private static PrintWriter out;
	
	private static double EPS = 1e-6;
	
	public static void main (String [] args) throws IOException {
		in = new BufferedReader (new InputStreamReader (System.in));
		out = new PrintWriter (System.out, true);
		
		while (true) {
			String line = in.readLine();
			if (line.equals ("END")) break;
			
			String [] s = line.split (" ");
			double x1 = Double.parseDouble (s [0]);
			double y1 = Double.parseDouble (s [1]);
			
			line = in.readLine();
			s = line.split (" ");
			double x2 = Double.parseDouble (s [0]);
			double y2 = Double.parseDouble (s [1]);
			
			line = in.readLine();
			s = line.split (" ");
			double x3 = Double.parseDouble (s [0]);
			double y3 = Double.parseDouble (s [1]);
			
			
			double a = Math.sqrt ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
			double b = Math.sqrt ((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
			double c = Math.sqrt ((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
			
			double angle1 = Math.acos ((a * a + b * b - c * c) / (2 * a * b)) / Math.PI;
			double angle2 = Math.acos ((a * a + c * c - b * b) / (2 * a * c)) / Math.PI;
			double angle3 = Math.acos ((b * b + c * c - a * a) / (2 * b * c)) / Math.PI;
			
			for (int i = 3; i <= 1000; i++) {
				if (eq ((double)((int)(angle1 * i + EPS)) / i, angle1) &&
						eq ((double)((int)(angle2 * i + EPS)) / i, angle2) &&
						eq ((double)((int)(angle3 * i + EPS)) / i, angle3)) {
					out.println (i);
					break;
				}
			}
		}
		
		out.close();
		System.exit(0);
	}
	
	private static boolean eq (double a, double b) {
		return Math.abs (a - b) < EPS;
	}
}
