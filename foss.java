import java.io.*;
import java.util.*;

public class foss
{
    private static Reader in;
    private static PrintWriter out;
    public static final double EPS = 0.0000000001;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int t = in.nextInt ();
        while (t-- > 0) {
            int n = in.nextInt ();
            Point [] p = new Point [n];
            for (int i = 0; i < n; i++)
                p [i]= new Point (in.nextInt (), in.nextInt ());
                
            out.println (maxDist (p));
        }
    }
    
    private static long dist (Point a, Point b) {
        return (long)(a.x - b.x) * (a.x - b.x) + (long)(a.y - b.y) * (a.y - b.y);
    }
    
    private static long maxDist (Point [] p) {
        if (p.length == 1) return 0;
        if (p.length == 2) return dist (p [0], p [1]);
        p = convexHull (p);
        int N = p.length;
        
        // ternary search for farthest point
        int lo = 1, hi = N - 1, idx = -1;
        while (lo < hi) {
            // handle last cases manually
            if (hi == lo) {idx = lo; break;}
            if (hi == lo + 1) {
                long f1 = dist (p [0], p [lo]),
                     f2 = dist (p [0], p [hi]);
                if (f2 > f1) idx = hi;
                else         idx = lo;
                break;
            }
            if (hi == lo + 2) {
                long f1 = dist (p [0], p [lo]),
                     f2 = dist (p [0], p [lo + 1]),
                     f3 = dist (p [0], p [hi]);
                if (f3 >= f2 && f3 >= f1) idx = hi;
                if (f2 >= f3 && f2 >= f1) idx = lo + 1;
                if (f1 >= f2 && f1 >= f3) idx = lo;
                break;
            }
            
            int p1 = (2 * lo + hi) / 3, p2 = (lo + 2 * hi) / 3;
            long f1 = dist (p [0], p [p1]), f2 = dist (p [0], p [p2]);
            if (f1 < f2)  lo = p1;
            else          hi = p2;
        }
        
        long max = dist (p [0], p [idx]);
        int i = 1;
        while (i < idx && idx < N) {
            long prev = dist (p [i], p [idx]);
            while (idx < N - 1 && dist (p [i], p [idx + 1]) > prev) {idx++; prev = dist (p [i], p [idx]);}
            max = Math.max (max, prev);
            i++;
        }
        
        return max;
    }
    
    private static Point [] convexHull (Point [] p) {
        int numPoints = p.length;
        Point m = new Point (0, 0);
        for (int i = 0; i < numPoints; i++) {
            m.x += p [i].x / numPoints;
            m.y += p [i].y / numPoints;
        }
        
        for (int i = 0; i < numPoints; i++)
            p [i].angle = Math.atan2 (p [i].y - m.y, p [i].x - m.x);
            
        Arrays.sort (p);
        
        Point [] hull = new Point [numPoints];
        hull [0] = p [0]; hull [1] = p [1];
        
        int hullPos = 2;
        for (int i = 2; i < numPoints - 1; i++) {
            while (hullPos > 1 && cross (hull [hullPos - 2], hull [hullPos - 1], p [i]) < -EPS) 
                hullPos--;
            hull [hullPos++] = p [i];
        }
        // add last point
        Point p2 = p [numPoints - 1];
        while (hullPos > 1 && cross (hull [hullPos - 2], hull [hullPos - 1], p2) < -EPS) 
            hullPos--;
        int hullStart = 0;
        boolean flag = false;
        do {
            flag = false;
            if (hullPos - hullStart >= 2 && cross (hull [hullStart], p2, hull [hullPos - 1]) > EPS) {
                p2 = hull [--hullPos];
                flag = true;
            }
            if (hullPos - hullStart >= 2 && cross (p2, hull [hullStart + 1], hull [hullStart]) > EPS) {
                hullStart++;
                flag = true;
            }
        } while (flag);
        hull [hullPos++] = p2;
        Point [] cleanHull = new Point [hullPos - hullStart];
        for (int i = hullStart; i < hullPos; i++)
            cleanHull [i - hullStart] = hull [i];
        return cleanHull;
    }
    
    private static double zCrossProduct (Point a, Point b) {
        return a.x * b.y - a.y * b.x;
    } 
    
    private static double cross (Point a, Point b, Point c) {
        Point ab = new Point (a.x - b.x, a.y - b.y);
        Point bc = new Point (b.x - c.x, b.y - c.y);
        return zCrossProduct (ab, bc);
    }
    
    static class Point implements Comparable<Point> {
        public long x, y;
        public double angle;
        
        public Point (long x, long y) {
            this.x = x; this.y = y;
            angle = 0;
        }
        
        public int compareTo (Point other) {
            double test = angle - other.angle;
            if (test < -EPS) return -1;
            if (test > EPS)  return 1;
            return 0;
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name)throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine()throws IOException{byte[] buf=new byte[1024];int cnt=0,c;
        while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt()throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong()throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');
        if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble()throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer()throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read()throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close()throws IOException{if(din==null) return;din.close();}
}