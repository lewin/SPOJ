import java.io.*;

public class intest
{
   public static void main(String[] args) throws IOException
   {
       Parser in = new Parser(System.in);
       int n = in.nextInt();
       int k = in.nextInt();
       int count = 0;
       for(int i = 0; i < n; i++)
       {
       	int a = in.nextInt();
       	if(a%k==0) count++;
       }
       PrintWriter out = new PrintWriter(System.out, true);
       out.println(count);
       System.exit(0);
   }
}

class Parser
{
   final private int BUFFER_SIZE = 1 << 16;

   private DataInputStream din;
   private byte[] buffer;
   private int bufferPointer, bytesRead;

   public Parser(InputStream in)
   {
      din = new DataInputStream(in);
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
   }

   public int nextInt() throws IOException
   {
      int ret = 0;
      byte c = read();
      while (c <= ' ') c = read();
      boolean neg = c == '-';
      if (neg) c = read();
      do
      {
         ret = ret * 10 + c - '0';
         c = read();
      } while (c > ' ');
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
}