import java.io.*;
import java.util.*;

public class nqueen
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        Queens q;
        for ( ; ; ) {
            int t = in.nextInt ();
            if (t == -Integer.MAX_VALUE) break;
            int [] initial = new int [t];
            for (int i = 0; i < t; i++) initial [i] = in.nextInt ();
            q = new Queens (t, initial);
            int [] solved = q.solve ();
            if (solved != null) {
                for (int i = 0; i < t; i++) {
                    if (i != 0) out.print (" ");
                    out.print (solved [i]);
                }
                out.println ();
            } else System.out.println ("IMPOSSIBLE");
        }
    }
}
class Queens {
    private int size;
    private DancingLinks data;
    private boolean consistent;
    public Queens (int _size, int [] initial) {
        size = _size;
        if (size != initial.length) return;
        ArrayList <Node> constraints = new ArrayList <Node> ();
        data = new DancingLinks (size);
        int [] rowData = new int [4];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                rowData [0] = 1 + i;
                rowData [1] = 1 + size + j;
                rowData [2] = 2 * size + i + j;
                rowData [3] = 1 + 4 * size + i - j;
                Node newRow = data.addInitialRow (rowData);
                if (initial [i] == j + 1) 
                    constraints.add (newRow);
            }
        consistent = data.removeInitialSolutions (constraints);
    }
    public int [] solve () {
        if (consistent) return handleSolution (data.solve ());
        else return null;
    }
    private int [] handleSolution (int [] sol) {
        if (sol == null) return null;
        int [] solution = new int [sol.length];
        for (int i = 0; i < sol.length; i++) {
            int val = sol [i], row, col;
            col = val % size; val /= size;
            row = val % size;
            solution [row] = col + 1;
        }
        return solution;
    }
}

class DancingLinks {
    public ColumnNode firstColumn, columns [];
    public int rowCount, solutionIndex, startingCount;
    public Node solution [], traveller;
    public DancingLinks (int size) {
        int length = 6 * size - 2;
        columns = new ColumnNode [length];
        for (int i = 0; i < length; i++) {
            columns [i] = new ColumnNode (i + 1, i >= 2 * size);
            columns [i].right = null;
            if (i > 0) {
                columns [i].left = columns [i - 1];
                columns [i - 1].right = columns [i];
            }
        }
        firstColumn = new ColumnNode (0, false);
        columns [0].left = firstColumn;
        firstColumn.right = columns [0];
        columns [length - 1].right = firstColumn;
        firstColumn.left = columns [length - 1];
        solution = new Node [length];
        solutionIndex = 0;
    }
    protected void removeColumn (ColumnNode columnHead) {
        for (Node colScan = columnHead.down; colScan != columnHead; colScan = colScan.down)
            for (Node rowScan = colScan.right; rowScan != colScan; rowScan = rowScan.right) {
                rowScan.up.down = rowScan.down;
                rowScan.down.up = rowScan.up;
                rowScan.column.size--;
            }
        columnHead.left.right = columnHead.right;
        columnHead.right.left = columnHead.left;
    }
    protected void insertColumn (ColumnNode columnNode) {
        for (Node colScan = columnNode.up; colScan != columnNode; colScan = colScan.up) 
            for (Node rowScan = colScan.left; rowScan != colScan; rowScan = rowScan.left) {
                rowScan.up.down = rowScan;
                rowScan.down.up = rowScan;
                rowScan.column.size++;
            }
        columnNode.left.right = columnNode;
        columnNode.right.left = columnNode;
    }
    protected void removeRow (Node rowHead) {
        Node scanner = rowHead;
        do {
            removeColumn (scanner.column);
            scanner = scanner.right;
        } while (scanner != rowHead);
    }
    protected void insertRow (Node rowHead) {
        Node scanner = rowHead;
        do {
            scanner = scanner.left;
            insertColumn (scanner.column);
        } while (scanner != rowHead);
    }
    protected Node addInitialRow (int [] labels) {
        if (labels.length == 0) return null;
        Node prev = null, first = null; rowCount++;
        for (int i = 0; i < labels.length; i++) {
            ColumnNode theColumn = columns [labels [i] - 1];
            Node node = new Node (rowCount, labels [i], theColumn);
            theColumn.addAtEnd (node);
            node.left = prev;
            node.right = null;
            if (prev != null) prev.right = node;
            else first = node;
            prev = node;
        }
        first.left = prev;
        prev.right = first;
        return first;
    }
    private final ColumnNode nextNonOptionalColumn () {
        ColumnNode nextColumn = firstColumn, bestColumn = firstColumn;
        int bestCount = Integer.MAX_VALUE; // infinity;
        do {
            nextColumn = nextColumn.right.column;
            if (nextColumn.optional) continue;
            if (nextColumn == firstColumn) break;
            if (nextColumn.size < bestCount) {
                bestColumn = nextColumn;
                bestCount = nextColumn.size;
            }
        } while (bestCount > 2);
        if (bestColumn.optional) System.exit (1);
        return bestColumn;
    }
    private int [] returnArray () {
        int [] res = new int [solutionIndex];
        for (int i = 0; i < solutionIndex; i++)
            res [i] = solution [i].rowNumber - 1;
        return res;
    }
    public int [] solve () {
        int [] res = null;
        if (traveller == null) {
            traveller = nextNonOptionalColumn().down;
            startingCount = solutionIndex;
        }
        while (res == null) {
            ColumnNode thisColumn = traveller.column;
            if ((thisColumn == firstColumn) || (thisColumn == traveller)) {
                if (thisColumn == firstColumn) res = returnArray ();
                if (solutionIndex == startingCount) return res;
                else {
                    traveller = solution [--solutionIndex];
                    insertRow (traveller);
                    traveller = traveller.down;
                }
            }
            else {
                removeRow (traveller);
                solution [solutionIndex++] = traveller;
                traveller = nextNonOptionalColumn ().down;
            }
        }
        return res;
    }
    public boolean removeInitialSolutions (ArrayList <Node> solutions) {
        Iterator listIterator = solutions.iterator ();
        while (listIterator.hasNext ()) {
            Node row = (Node) listIterator.next ();
            if (row.down.up != row) return false;
            removeRow (row);
            solution [solutionIndex++] = row;
        }
        return true;
    }
}
class Node {
    public Node left, right, up, down;
    public ColumnNode column;
    public int rowNumber, label;
    public Node (int _rowNumber, int _label, ColumnNode _column) {
        left = this; right = this; up = this; down = this;
        column = _column; rowNumber = _rowNumber; label = _label;
    }
}
class ColumnNode extends Node {
    public int size; 
    public boolean optional;
    
    public ColumnNode (int _label, boolean _optional) {
        super (0, _label, null);column = this;
        optional = _optional;size = 0;
    }
    
    public void addAtEnd (Node node) {
        Node end = this.up;node.up = end;
        node.down = this;end.down = node;
        this.up = node;size++;
    }
}


/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine() throws IOException{byte[] buf=new byte[64];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt() throws IOException{int ret=0;byte c=read();if(c==-1)return-Integer.MAX_VALUE;while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    public boolean available () throws IOException{return buffer[bufferPointer]!=-1;}
    private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close() throws IOException{if(din==null) return;din.close();}
}