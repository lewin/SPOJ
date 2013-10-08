import java.io.*;
import java.util.*;
public class majstor{
    public static void main(String[] a) throws IOException{
        Scanner in=new Scanner(System.in);
        int r=in.nextInt(),f,i,j,c=0,m=0;
        int[] p=co(in.next().toCharArray()),fr;
        f=in.nextInt();
        int[][] o=new int[r][3];
        for(i=0;i<f;i++){
            fr=co(in.next().toCharArray());
            for(j=0;j<r;j++){
                o[j][fr[j]]++;
                if(p[j]==(fr[j]+1)%3) c += 2;
                if(p[j]==fr[j]) c++;
            }  
        }
        for(i=0;i<r;i++)
            m+=Math.max(o[i][2]*2+o[i][0],Math.max(o[i][1]*2+o[i][2],o[i][0]*2+o[i][1]));
        System.out.println(c + "\n" + m);
    }
    private static int[] co(char[] a){int[] p=new int[a.length];for(int i=0;i<a.length;i++)switch(a[i]){case'S':{p[i]=0;break;}case'R':{p[i]=1;break;}case'P':{p[i]=2;break;}}return p;}
}