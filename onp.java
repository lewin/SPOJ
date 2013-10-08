import java.io.*;
import java.util.*;

public class onp
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out, true);
        int n = Integer.parseInt(in.readLine());
        char[] sequence;
        while(n-->0){
            sequence = in.readLine().toCharArray();
            out.println(convert(sequence));
        }
        System.exit(0);
    }
    
    private static String convert(char[] in)
    {
        Stack<Character> operator = new Stack<Character>();
        Queue<Character> output = new LinkedList<Character>();
        int i;
        for(i = 0; i < in.length; i++) {
            if(Character.isLetter(in[i]))
                output.offer(in[i]);
            else if(get(in[i]) > 0) {
                while(operator.size() > 0 && get(operator.peek())>0 &&
                      get(in[i]) > get(operator.peek()))
                    output.offer(operator.pop());
                operator.push(in[i]);
            }
            else if(in[i] == '(')
                operator.push('(');
            else if(in[i] == ')')
                while(operator.size() > 0) {
                    if(operator.peek() == '(') {
                        operator.pop();
                        break;
                    }
                    output.offer(operator.peek());
                    operator.pop();
                }
            
        }
        while(operator.size()>0)
            output.add(operator.pop());
        String res = "";
        while(output.size()>0)
            res += output.poll();
        return res;
    }
    
    private static int get(char sign) {
        switch(sign) {
            case '+' : return 1;
            case '-' : return 2;
            case '*' : return 3;
            case '/' : return 4;
            case '^' : return 5;
        }
        return -1;
    }
}