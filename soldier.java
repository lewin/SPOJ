import java.io.*;
import java.util.*;

public class soldier
{
    private static Reader in;
    private static PrintWriter out;
    
    static class Item implements Comparable <Item> {
        public int price, quality;
        
        public Item (int price, int quality) {
            this.price = price;
            this.quality = quality;
        }
        
        public int compareTo (Item other) {
            return price - other.price;
        }
    }
    
    private static ArrayList <Item> [] items;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        
        int N = in.nextInt(), T = in.nextInt();
        
        items = new ArrayList [6];
        for (int i = 0; i < 6; i++) items [i] = new ArrayList <Item> ();
        
        for (int i = 0; i < N; i++) {
            int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
            items [a - 1].add (new Item (b, c));
        }
        for (int i = 0; i < 6; i++) Collections.sort (items [i]);
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < items [i].size(); j++) {
                if (items[i].get(j).quality <= items[i].get(j - 1).quality) {
                    items[i].remove(j);
                    j--;
                }
            }
        }
        
        HashSet <Integer> qualities = new HashSet <Integer> ();
        qualities.add (0);
        for (int i = 0; i < 6; i++)
            for (Item k : items [i])
                qualities.add (k.quality);
                
        Integer [] search = qualities.toArray(new Integer[qualities.size()]);
        int lo = 0, hi = search.length - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) >> 1;
            if (can (search [mid], items, N)) lo = mid;
            else hi = mid - 1;
        }
        
        out.println (search [lo]);
        out.close();
        System.exit(0);
    }
    
    private static boolean can (int test, ArrayList <Item> [] items, int limit) {
        if (test == 0) return true;
        int cap = 0;
        for (int i = 0; i < 6; i++) {
            if (items[i].size() == 0) return false;
            int lo = 0, hi = items[i].size() - 1;
            while (lo < hi) {
                int mid = (lo + hi) >> 1;
                if (items[i].get(mid).quality < test) lo = mid + 1;
                else hi = mid;
            }
            cap -= items[i].get(lo).price;
            if (cap < 0) return false;
        }
        
        return true;
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