import java.io.*;
import java.util.*;

public class vmili
{
    private static Reader in;
    private static PrintWriter out;
    public static final double EPSILON = -0.000001;

    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        int N = in.nextInt();
        Point[] points = new Point[N];
        for(int i = 0; i < N; i++)
            points[i] = new Point(in.nextDouble(), in.nextDouble());
        
        int count = 0;
        while(points.length >= 3)
        {
            count++;
            points = notConvexHull(points);
        }
        
        out.println(count);
        System.exit(0);
    }
    
    private static Point[] notConvexHull(Point[] p)
    {
        int i;
        
        Point m = new Point(0,0);
        for(i = 0; i < p.length; i++)
        {
            m.x += p[i].x/p.length;
            m.y += p[i].y/p.length;
            p[i].ind = i;
        }
        
        for(i = 0; i < p.length; i++)
            p[i].angle = Math.atan2(p[i].y-m.y,p[i].x-m.x);
        
        Arrays.sort(p);
        Point[] hull = new Point[p.length];
        hull[0] = p[0];
        hull[1] = p[1];
        int hullPos = 2;
        
        for(i = 2; i < p.length-1; i++)
        {
            while(hullPos > 1 && cross_1(hull[hullPos-2], hull[hullPos-1], p[i]) < EPSILON) hullPos--;
            hull[hullPos++] = p[i];
        }
        // add last point
        Point p2 = p[p.length-1];
        
        while(hullPos > 1 && cross_1(hull[hullPos-2], hull[hullPos-1], p2) < EPSILON) hullPos--;
        
        int hullStart = 0;
        boolean flag = false;
        do
        {
            flag = false;
            if(hullPos - hullStart >= 2 && cross_2(p2, hull[hullPos-1], hull[hullStart]) < EPSILON)
            {
                p2 = hull[--hullPos];
                flag = true;
            }
            if(hullPos - hullStart >= 2 && cross_2(hull[hullStart+1], hull[hullStart], p2) < EPSILON)
            {
                hullStart++;
                flag = true;
            }
        }while(flag);
        
        hull[hullPos++] = p2;
         
        boolean[] used = new boolean[p.length];
        for(i = hullStart; i < hullPos; i++)
            used[hull[i].ind] = true;  
        
        Point[] notHull = new Point[p.length - (hullPos-hullStart)];
        int ind = 0;
        for(i = 0; i < p.length; i++)
            if(!used[i]) notHull[ind++] = p[i];
        
        return notHull;
    }
    
    private static double zCrossProduct(Point a, Point b)
    {
        return a.x*b.y - a.y*b.x;
    }
    
    private static double cross_1(Point a, Point b, Point c)
    {// vectors ab and bc
        Point ab = new Point(a.x-b.x,a.y-b.y);
        Point bc = new Point(b.x-c.x,b.y-c.y);
        return zCrossProduct(ab, bc);
    }
    
    
    private static double cross_2(Point a, Point b, Point c)
    {// vectors ab and ca
        Point ab = new Point(a.x-b.x, a.y-b.y);
        Point ca = new Point(c.x-a.x, c.y-a.y);
        return zCrossProduct(ab, ca);
    }
    
    static class Point implements Comparable
    {
        public double x,y;
        public double angle;
        public int ind;
        
        public Point(double x, double y)
        {
            this.x = x; this.y = y;
            angle = 0;
        }
        
        public int compareTo(Object other)
        {
            if((angle - ((Point)other).angle) < 0.000001)
                return -1;
            if((angle - ((Point)other).angle) > 0.000001)
                return 1;
            return 0;
        }
        
        public String toString()
        {
            return x + " " + y;
        }
    }
}

/** Faster input **/
class Reader
{
    final private int BUFFER_SIZE = 1 << 16;
    
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    
    public Reader()
    {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }
    
    public Reader(String file_name) throws IOException
    {
        din = new DataInputStream(new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }
    
    public String readLine() throws IOException
    {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while( (c=read()) != -1) {
            buf[cnt++] = (byte)c;
            if(c == '\n') break;
        }
        return new String(buf,0,cnt);
    }
    
    public int nextInt() throws IOException
    {
        int ret = 0;
        byte c = read();
        while (c <= ' ') c = read();
        boolean neg = c == '-';
        if (neg) c = read();
        do {
            ret = ret * 10 + c - '0';
            c = read();
        } while (c >= '0' && c <= '9');
        if (neg) return -ret;
        return ret;
    }
    
    public long nextLong() throws IOException
    {
        long ret = 0;
        byte c = read();
        while (c <= ' ') c = read();
        boolean neg = c == '-';
        if (neg) c = read();
        do {
            ret = ret * 10 + c - '0';
            c = read();
        } while (c >= '0' && c <= '9');
        if (neg) return -ret;
        return ret;
    }
    
    public double nextDouble() throws IOException
    {
        double ret = 0, div = 1;
        byte c = read();
        while(c <= ' ') c = read();
        boolean neg = c == '-';
        if(neg) c = read();
        do {
            ret = ret * 10 + c - '0';
            c = read();
        } while (c >= '0' && c <= '9');
        if(c == '.')
            while((c=read()) >= '0' && c <= '9') {
                div *= 10;
                ret = ret + (c - '0') / div;
            }
        if (neg) return -ret;
        return ret;
    }
    
    private void fillBuffer() throws IOException
    {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1) buffer[0] = -1;
    }
    
    private byte read() throws IOException
    {
        if (bufferPointer == bytesRead) fillBuffer();
        return buffer[bufferPointer++];
    }
    
    public void close() throws IOException
    {
        if(din == null) return;
        din.close();
    }
}