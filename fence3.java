import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class fence3 {
    public static Segment[] fences;
    public static double minDist;
    public static double xMax, yMax, xPos, yPos;
    
    public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        int F = Integer.parseInt(f.readLine());
        fences = new Segment[F];
        int i;
        double x1, x2, y1, y2;
        xMax = 0;
        yMax = 0;
        double xA = 0; // average
        double yA = 0;
        StringTokenizer st;
        for(i = 0; i < F; i++)
        {
            st = new StringTokenizer(f.readLine());
            x1 = Double.parseDouble(st.nextToken());
            y1 = Double.parseDouble(st.nextToken());
            x2 = Double.parseDouble(st.nextToken());
            y2 = Double.parseDouble(st.nextToken());
            fences[i] = new Segment(new Point(x1,y1),new Point(x2,y2));
            xMax = Math.max(xMax,Math.max(x1,x2));
            yMax = Math.max(yMax,Math.max(y1,y2));
            xA = (x1+x2)/2.0;
            yA = (y1+y2)/2.0;
        }
        xA/=F; yA/=F;
        
        Point p = new Point(xA,yA);
        
        double dist = totalDist(p);
        
        minDist = dist;
            
        xPos = xA;
        yPos = yA;
        double step = Math.max(xMax,yMax)/2; 
        double test = 1000000;
       
        while(true)
        {
            if(p.x-step>=0)
            {
                p.x-=step;
                test = totalDist(p);
                p.x+=step;
            }
            if(test < minDist)
            {
                xPos = p.x-step;
                yPos = p.y;
                minDist = test;
                p.x-=step;
            }
            if(p.y-step>=0)
            {
                p.y-=step;
                test = totalDist(p);
                p.y+=step;
            }
            if(test < minDist)
            {
                yPos = p.y-step;
                xPos = p.x;
                minDist = test;
                p.y-=step;
            }
            if(p.x+step<=xMax)
            {
                p.x+=step;
                test = totalDist(p);
                p.x-=step;
            }
            if(test < minDist)
            {
                xPos = p.x+step;
                yPos = p.y;
                minDist = test;
                p.x+=step;
            }
            if(p.y-step<=yMax)
            {
                p.y+=step;
                test = totalDist(p);
                p.y-=step;
            }
            if(test < minDist)
            {
                yPos = p.y+step;
                xPos = p.x;
                minDist = test;
                p.y+=step;
            }
            
            step/=2;
            
            if(step < 0.0001)
                break;
        }
            
        System.out.printf("%.1f %.1f %.1f\n",xPos,yPos,minDist);
        System.exit(0);
    }
    
    public static double totalDist(Point p)
    {
        double dist = 0;
        for(int i = 0; i < fences.length; i++)
            dist += fences[i].ptDist(p);
            
        return dist;
    }
    
    public static double dot(Point a, Point b, Point c)
    {
        Point ab = new Point(b.x-a.x,b.y-a.y);
        Point bc = new Point(c.x-b.x,c.y-b.y);
        return ab.x*bc.x + ab.y*bc.y;
    }
    
    public static double cross(Point a, Point b, Point c)
    {
        Point ab = new Point(b.x-a.x,b.y-a.y);
        Point ac = new Point(c.x-a.x,c.y-a.y);
        return ab.x*ac.y - ab.y*ac.x;
    }
    
    public static double distance(Point a, Point b)
    {
        return Math.sqrt( (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y) );
    }
    
    static class Point
    {
        public double x, y;
        
        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
    }
    
    static class Segment
    {
        public Point a, b;
        public double length;
        
        public Segment(Point a, Point b)
        {
            this.a = a;
            this.b = b;
            length = distance(a,b);
        }
        
        public double ptDist(Point c)
        {
            if(dot(a,b,c) > 0) return distance(b,c);
            if(dot(b,a,c) > 0) return distance(a,c);
            if(length == 0) return distance(a,c);
            return Math.abs(cross(a,b,c)/length);
        }
        
        public boolean ok(double test, boolean x)
        {
           return x ? test >= Math.min(a.x,b.x) && test <= Math.max(a.x,b.x):
                      test >= Math.min(a.y,b.y) && test <= Math.min(a.y,b.y);
        }
    }
}