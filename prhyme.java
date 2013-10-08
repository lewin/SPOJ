import java.io.*;
import java.util.*;

public class prhyme
{
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new BufferedReader (new InputStreamReader (System.in));
        out = new PrintWriter (System.out, true);
        String line = in.readLine ();
        Node root = new Node ();
        while (line.length () > 0) {
            root.add (line);
            line = in.readLine ();
        }
        while ( (line=in.readLine()) != null)
            out.println (root.findRhyme (line));
            
        System.exit (0);
    }
    static class Node {
        public Node [] next;
        public ArrayList <String> wordList;
        public boolean sorted;
        public int count;
        
        public Node () {
            next = new Node [26];
            wordList = new ArrayList <String> (250000);
            sorted = true;
            count = 0;
        }
        public void add (String word) {
            count++;
            if (word.length () == 0) return;
            int idx = word.charAt (word.length () - 1) - 'a';
            if (next [idx] == null) next [idx] = new Node ();
            wordList.add (word); sorted = false;
            next [idx].add (word.substring (0, word.length () - 1));
        }   
        public String findRhyme (String word) {
            if (count == 0) return null;
            int last = word.charAt (word.length () - 1) - 'a';
            if (next [last].count > 1) return next [last].findRhyme (word.substring (0, word.length () - 1)) + (char)(last + 'a');
            if (!sorted) { Collections.sort (wordList); sorted = true;}
            String temp = wordList.get (0);
            if (temp.equals (word)) temp = wordList.get (1);
            return temp;
        }
    }
}

