import java.io.*;
import java.util.*;
import static java.lang.Double.parseDouble;
import static java.lang.Math.sqrt;

public class pir
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(f.readLine());
        StringTokenizer st;
        double U,V,W,u,v,w,a,b,c,d,X,Y,Z,x,y,z, volume;
        while(T-->0)
        {
            st=new StringTokenizer(f.readLine());
            U=parseDouble(st.nextToken());
            V=parseDouble(st.nextToken());
            w=parseDouble(st.nextToken());
            W=parseDouble(st.nextToken());
            v=parseDouble(st.nextToken());
            u=parseDouble(st.nextToken());
            X=(w-U+v)*(U+v+w);
            x=(U-v+w)*(v-w+U);
            Y=(u-V+w)*(V+w+u);
            y=(V-w+u)*(w-u+V);
            Z=(v-W+u)*(W+u+v);
            z=(W-u+v)*(u-v+W);
            a=sqrt(x*Y*Z);
            b=sqrt(y*Z*X);
            c=sqrt(z*X*Y);
            d=sqrt(x*y*z);
            volume=(sqrt((-a+b+c+d)*(a-b+c+d)*(a+b-c+d)*(a+b+c-d))/(192*u*v*w));
            System.out.format("%.4f%n",volume);
            System.out.flush();
        }
        System.exit(0);
    }
}