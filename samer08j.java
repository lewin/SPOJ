import java.io.*;
import java.util.*;

public class samer08j
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        for (;;) {
            int p = in.nextInt (), m = in.nextInt (), n = in.nextInt ();
            if (p == 0 && m == 0 && n == 0) break;
            Game game = new Game (p, m, n);
        }
    }
    
    static class Game {
        public Deck deck;
        public Player[] players;
        public int turn;
        public boolean clockwise;
        public Card top;
        public Game (int numPlayers, int cards_per_player, int numCards) throws IOException {
            players = new Player [numPlayers];
            for (int i = 0; i < numPlayers; i++)
                players [i] = new Player ();
                
            for (int i = 0; i < numPlayers; i++)
                for (int j = 0; j < cards_per_player; j++)
                    players [i].addCard (new Card (in.nextInt (), in.nextChar())); 
                    
            int N = numCards - numPlayers * cards_per_player;
            deck = new Deck(N);
            for (int i = 0; i < N; i++)
                deck.addCard (new Card (in.nextInt (), in.nextChar ()));
            run ();
        }
        public void run () {
            top = deck.draw ();
            turn = 0; clockwise = true;
            process ();
            if (!clockwise) turn = 0;
            play ();
        }
        public void play () {
            while (!turn ());
            out.printf ("%d\n", turn+1);
        }
        public boolean turn () {
            Card c = players [turn].playCard(top);
            if (players [turn].win ()) return true;
            boolean change = false;
            if (c == null) {
                Card temp = deck.draw ();
                if (temp.match (top)) {top = temp; change = true;}
                else players [turn].addCard (temp);
            }
            else {top = c; change = true;}
            next ();
            if (change) process ();
            return false;
        }
        public void next () {
            if (clockwise) turn = (turn + 1) % players.length;
            else turn = (turn - 1 + players.length) % players.length;
        }
        public void process () {
            boolean skipped = false;
            if (top.rank == 12) {
                clockwise = !clockwise; 
                next (); next ();
            }
            if (top.rank == 7) {
                players [turn].addCard (deck.draw ());
                players [turn].addCard (deck.draw ());
                skipped = true;
            }
            if (top.rank == 1) {
                players [turn].addCard (deck.draw ());
                skipped = true;
            }
            if (top.rank == 11)
                skipped = true;
            if (skipped) next ();
        }
    }
    
    static class Deck {
        public Card[] stock;
        public int front, back;
        public Deck (int numCards) {
            stock = new Card [numCards];
            front = back = 0;
        }
        public void addCard (Card c) {
            stock [back++] = c;
        }
        public Card draw () {
            return stock [front++];
        }
    }
    
    static class Player {
        public int[] hand;
        public int numCards, player;
        public Player () {
            hand = new int [52];
            numCards = 0;
        }
        public void addCard (Card c) {
            hand [c.map ()] ++;
            numCards++;
        }
        public Card playCard (Card c) {
            int [] possible = c.getMatch ();
            for (int i = possible.length - 1; i >= 0; i--)
                if (hand [possible [i]] > 0) {
                    hand [possible [i]]--;
                    numCards--;
                    return c.unmap (possible [i]);
                }
            return null;
        }
        public boolean win () {
            return numCards == 0;
        }
    }
    
    static class Card {
        public int rank;
        public char suit;
        public Card (int _rank, char _suit) {
            rank = _rank;
            suit = _suit; 
        }
        public Card unmap (int val) {
            int rank = (val / 4) + 1;
            int temp = val % 4;
            char suit = '\0';
            switch (temp) {
                case 0 : suit = 'C'; break;
                case 1 : suit = 'D'; break;
                case 2 : suit = 'H'; break;
                case 3 : suit = 'S'; break;
            }
            return new Card (rank, suit);
        }
        public int map () {
            int res = (rank - 1) * 4;
            switch (suit) {
                case 'S' : res += 1;
                case 'H' : res += 1;
                case 'D' : res += 1;
                case 'C' : break;
            }
            return res;
        }
        public int[] getMatch () {
            int[] matches = new int [16];
            Card temp = new Card (1, suit);
            int t = 0, i;
            for (i = 1; i <= 13; i++) {
                if (i == rank) continue;
                temp.setRank (i);
                matches [t++] = temp.map ();
            }
            temp.setRank (rank);
            temp.setSuit ('C');
            matches [t++] = temp.map ();
            temp.setSuit ('D');
            matches [t++] = temp.map ();
            temp.setSuit ('H');
            matches [t++] = temp.map ();
            temp.setSuit ('S');
            matches [t++] = temp.map ();
            Arrays.sort(matches);
            return matches;
        }
        public boolean match (Card other) {
            return other.suit == suit || other.rank == rank;
        }
        public void setRank (int _rank) {
            rank = _rank;
        }
        public void setSuit (char _suit) {
            suit = _suit;
        }
        public String toString () {
            return "" + rank + " " + suit;
        }
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    public Reader(){
        din=new DataInputStream(System.in);
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public Reader(String file_name) throws IOException{
        din=new DataInputStream(new FileInputStream(file_name));
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public String readLine() throws IOException{
        byte[] buf=new byte[64]; // line length
        int cnt=0,c;
        while((c=read())!=-1){
            if(c=='\n')break;
            buf[cnt++]=(byte)c;
        }
        return new String(buf,0,cnt);
    }

    public int nextInt() throws IOException{
        int ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    } 

    public long nextLong() throws IOException{
        long ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    }

    public double nextDouble() throws IOException{
        double ret=0,div=1;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c = read();
        do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')
            ret+=(c-'0')/(div*=10);
        if(neg)return -ret;
        return ret;
    }
    
    public char nextChar() throws IOException{
        byte c=read();
        while(c<=' ')c=read();
        return(char)c;
    }
    
    private void fillBuffer() throws IOException{
        bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
        if(bytesRead==-1)buffer[0]=-1;
    }
    
    private byte read() throws IOException{
        if(bufferPointer==bytesRead)fillBuffer();
        return buffer[bufferPointer++];
    }
    
    public void close() throws IOException{
        if(din==null) return;
        din.close();
    }
}