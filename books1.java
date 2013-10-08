import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class books1
{
    private static BufferedReader f;
    
    public static void main(String[] args) throws IOException {
        f = new BufferedReader(new InputStreamReader(System.in));
        
        int T = parseInt(f.readLine());
        StringTokenizer st;
        int[] books, splits; int num, s, i, j, lo, hi, mid;
        while(T-- > 0)
        {
            st = new StringTokenizer(f.readLine());
            num = parseInt(st.nextToken());
            s = parseInt(st.nextToken());
            
            st = new StringTokenizer(f.readLine());
            books = new int[num];
            lo = hi = 0;
            for(i = 0; i < num; i++)
            {
                books[i] = parseInt(st.nextToken());
                hi += books[i];
                lo = Math.max(lo, books[i]);
            }
            
            splits = new int[s-1];
            while(lo < hi)
            {
                mid = (lo+hi)>>1; 
                if(assign(mid, splits, books)) hi = mid;
                else lo = mid+1;
            }
            
            assign(lo, splits, books);
            
            System.out.print(books[0]);
            j = 0;
            for(i = 1; i < num; i++)
            {
                if(j < splits.length && i == splits[j])
                {
                    System.out.print(" /");
                    j++;
                }
                System.out.print(" " + books[i]);
            }
            
            System.out.println();
            System.out.flush();
        }
        System.exit(0);
    }
    
    private static boolean assign(int num, int[] splits, int[] books)
    {
        int s = splits.length;
        int sum = 0;
        for(int i = books.length-1; i >= 0; i--)
        {
            sum += books[i];
            if(sum >= num)
            {
                if(sum > num) i++;
                sum = 0;
                if(s <= 0) return false;
                splits[--s] = i;
            }
            
            if(i == s)
            {// assign all books to remaining scribes
                for(; i > 0; i--)
                    splits[--s] = i;
                break;
            }
        }
        return s == 0 && sum < num;
    }
}