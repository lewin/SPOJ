import java.io.*;
import java.util.*;

public class wrong {
	private static BufferedReader in;
	private static PrintWriter out;

	static class Pair {
		public int x, y;
		public Pair (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static HashSet <Pair> mp;
	public static int xpos, ypos, dir, cxpos, cypos, cdir;
	
	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader (System.in));
		out = new PrintWriter(System.out, true);
		
		char [] command = new String (in.readLine()).toCharArray();
		xpos = 0; ypos = 0; dir = 0;
		for (char c : command) {
			switch (c) {
			case 'F': switch (dir) {
			case 0:ypos++;break;
			case 1:xpos++;break;
			case 2:ypos--;break;
			case 3:xpos--;break;
			}
			break;
			case 'R':
				dir = (dir+1)%4;
			case 'L':
				dir = (dir+3)%4;
			}
		}
		
		mp = new HashSet <Pair> ();
		cxpos = 0; cypos = 0; cdir = 0;
		for (char c : command) {
			switch (c) {
			case 'F': mp.add(R()); mp.add(L()); 
			switch (dir) {
			case 0:cypos++;ypos--;break;
			case 1:cxpos++;xpos--;break;
			case 2:cypos--;ypos++;break;
			case 3:cxpos--;xpos++;break;
			} break;
			case 'R': mp.add(F()); mp.add(L()); 
			cdir = (cdir+1)%4; dir = (dir+3)%4; break;
			case 'L': mp.add(F()); mp.add(R()); 
			cdir = (cdir+3)%4; dir = (dir+1)%4; break;
			}
		}
		
		out.println (mp.size());
		out.close();
		System.exit(0);
	}
	
	private static Pair F () {
		return null;
	}
	
	private static Pair R () {
		return null;
	}
	
	private static Pair L () {
		return null;
	}
}
