import java.io.*;
import java.util.*;

public class faketsp
{
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();
        int ind1, ind2, ind3;
        double x, y, px, py;
        double totalDist = 0;
        ind1 = line.lastIndexOf('(');
        ind2 = line.lastIndexOf(',');
        ind3 = line.lastIndexOf(')');
        px = Double.parseDouble(line.substring(ind1+1,ind2));
        py = Double.parseDouble(line.substring(ind2+2,ind3));
        line = in.readLine();
        while(line != null)
        {
            ind1 = line.lastIndexOf('(');
            ind2 = line.lastIndexOf(',');
            ind3 = line.lastIndexOf(')');
            x = Double.parseDouble(line.substring(ind1+1,ind2));
            y = Double.parseDouble(line.substring(ind2+2,ind3));
            totalDist += Math.sqrt( (px-x)*(px-x) + (py-y)*(py-y) );
            px = x; py = y;
            System.out.printf("The salesman has traveled a total of %.3f kilometers.\n", totalDist);
            System.out.flush();
            line = in.readLine();
        }
        System.exit(0);
    }
}