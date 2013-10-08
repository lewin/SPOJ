import java.io.*;
import java.util.*;

public class dcepca03 {
    private static Reader in;
    private static PrintWriter out;

    // PRIME AND TOTIENT SIEVER
    private static boolean [] isPrime;
    private static int [] prime;
    private static int idx, len;
    private static int [] phi;
    private static void phi_prime_sieve () {
        len = 10000;
        isPrime = new boolean [len + 1];
        prime = new int [len / 2]; phi = new int [len + 1];
        phi [0] = 0; phi [1] = 1;
        for (int i = 2; i <= len; i++) {
            isPrime [i] = true;
            phi [i] = i;
        }
        for (int i = 2; i <= len; i++) {
            if (isPrime [i]) {
                prime [idx++] = i;
                phi [i] = i - 1;
                for (int j = i + i; j <= len; j += i) {
                    isPrime [j] = false;
                    phi [j] /= i; phi [j] *= i - 1;
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        in = new Reader();
        out = new PrintWriter(System.out, true);
        
        phi_prime_sieve();
        long [] phi_s = new long [10001];
        phi_s[0] = 0;
        for (int i = 1; i <= 10000; i++)
            phi_s[i] = phi_s[i - 1] + phi[i];
        System.out.println (Arrays.toString (phi_s));
        int T = in.nextInt();
        while (T-- > 0) {
            int N = in.nextInt();
            out.println(phi_s [N] * phi_s [N]);
        }
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[1024];
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (c == '.')
                while ((c = read()) >= '0' && c <= '9')
                    ret += (c - '0') / (div *= 10);
            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}
