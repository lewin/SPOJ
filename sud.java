import java.io.*;
import java.util.*;

public class sud
{
    private static Reader in;
    private static PrintWriter out;

    public static void main (String [] args) throws IOException {
        in = new Reader ();
        out = new PrintWriter (System.out, true);
        int t = in.nextInt (), count = 0;
        long totalTime = System.currentTimeMillis ();
        for (int d = 1; d <= t; d++) {
            int [][] puzzle = new int [9][9]; int b = 0;
            char [] line = in.readLine ().toCharArray ();
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    puzzle [i][j] = line [b++] - '0';
            Sudoku s = new Sudoku (puzzle);
            puzzle = s.solve ();
            if (puzzle == null) System.out.println ("N");
            else {
                System.out.println ("Y");
                for (int i = 0; i < 9; i++)
                    for (int j = 0; j < 9; j++)
                        System.out.print (puzzle [i][j]);
                System.out.println ();
            }
        }
        System.out.printf ("Solved %d/%d puzzles in %.3fs", count, t, (System.currentTimeMillis () - totalTime) / 1000.0);
    }
}

class Sudoku
{
    public DancingLinks data;
    public boolean consistent;
    
    public Sudoku (int [][] puzzle) {
        
        int [] labels, rowData;
        ArrayList <Node> constraints = new ArrayList <Node> ();
        
        data = new DancingLinks ();
        rowData = new int [4];
        
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                for (int num = 0; num < 9; num++) {
                    int box_x, box_y;
                    
                    rowData [0] = 1 + (row * 9 + col);
                    rowData [1] = 1 + 81 + (row * 9 + num);
                    rowData [2] = 1 + 81 + 81 + (col * 9 + num);
                    box_x = row / 3; box_y = col / 3;
                    rowData [3] = 1 + 81 + 81 + 81 + 
                        ((box_x * 3 + box_y) * 9 + num);
                        
                    Node newRow = data.addInitialRow (rowData);
                    
                    if (puzzle [row][col] == (num + 1))
                        constraints.add (newRow);
                }
                
        consistent = data.removeInitialSolutions (constraints);
    }
    
    public int [][] solve () {
        if (consistent) // can solve
            return handleSolution (data.solve ());
        else return null;
    }
    
    private int [][] handleSolution (int [] sol) {
        if (sol == null) return null;
        
        int [][] solution = new int [9][9];
        
        for (int i = 0; i < 81; i++) {
            int val = sol [i], num, row, col;
            num = val % 9; val /= 9;
            col = val % 9; val /= 9;
            row = val % 9; 
            
            solution [row][col] = num + 1;
        }
        
        return solution;
    }
}

class DancingLinks 
{
    public ColumnNode firstColumn, columns [];
    public int rowCount, solutionIndex, startingCount;
    public Node solution [], traveller;
    
    public DancingLinks () {
        columns = new ColumnNode [324];
        
        for (int i = 0; i < 324; i++) {
            columns [i] = new ColumnNode (i + 1);
            columns [i].right = null;
            
            if (i > 0) {
                columns [i].left = columns [i - 1];
                columns [i - 1].right = columns [i];
            }
        }
        
        firstColumn = new ColumnNode (0);
        columns [0].left = firstColumn;
        firstColumn.right = columns [0];
        columns [323].right = firstColumn;
        firstColumn.left = columns [323];
        solution = new Node [81];
        solutionIndex = 0;
    }
    
    protected ColumnNode getFirstColumn () {
        return (ColumnNode) firstColumn.right;
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
            
            if (theColumn == null) {
                System.err.println ("Couldn't find column labeled " + labels [i]);
                System.exit (1);
            }
            
            Node node = new Node (rowCount, labels [i], theColumn);
            theColumn.addAtEnd (node);
            
            node.left = prev;
            node.right = null;
            
            if (prev != null) prev.right = node;
            else first = node;
            
            prev = node;
        }
        // complete the circle
        first.left = prev;
        prev.right = first;
        
        return first;
    }
    
    private final ColumnNode nextColumn () {
        ColumnNode nextColumn = firstColumn, bestColumn = firstColumn;
        int bestCount = Integer.MAX_VALUE; // infinity;
        
        do {
            nextColumn = nextColumn.right.column;
            if (nextColumn == firstColumn) break;
            
            if (nextColumn.size < bestCount) {
                bestColumn = nextColumn;
                bestCount = nextColumn.size;
            }
        } while (bestCount > 0);
        
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
            traveller = nextColumn().down;
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
                traveller = nextColumn ().down;
            }
        }
        
        return res;
    }
    
    public boolean removeInitialSolutions (ArrayList <Node> solutions) {
        Iterator listIterator = solutions.iterator ();
        
        while (listIterator.hasNext ()) {
            Node row = (Node) listIterator.next ();
            
            // contradiction
            if (row.down.up != row) return false;
            
            removeRow (row);
            solution [solutionIndex++] = row;
        }
        
        return true;
    }
}

class ColumnNode extends Node {
    public int size; 
    
    public ColumnNode (int _label) {
        super (0, _label, null);
        column = this;
        size = 0;
    }
    
    public void addAtEnd (Node node) {
        Node end = this.up;
        node.up = end;
        node.down = this;
        end.down = node;
        this.up = node;
        size++;
    }
}

class Node {
    public Node left, right, up, down;
    public ColumnNode column;
    public int rowNumber, label;
    
    // references self
    public Node (int _rowNumber, int _label, ColumnNode _column) {
        left = this; right = this; up = this; down = this;
        column = _column; rowNumber = _rowNumber; label = _label;
    }
}

/** Faster input **/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;private DataInputStream din;private byte[] buffer;private int bufferPointer, bytesRead;
    public Reader(){din=new DataInputStream(System.in);buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public Reader(String file_name) throws IOException{din=new DataInputStream(new FileInputStream(file_name));buffer=new byte[BUFFER_SIZE];bufferPointer=bytesRead=0;}
    public String readLine() throws IOException{byte[] buf=new byte[128];int cnt=0,c;while((c=read())!=-1){if(c=='\n')break;buf[cnt++]=(byte)c;}return new String(buf,0,cnt);}
    public int nextInt() throws IOException{int ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;} 
    public long nextLong() throws IOException{long ret=0;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c=read();do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');if(neg)return -ret;return ret;}
    public double nextDouble() throws IOException{double ret=0,div=1;byte c=read();while(c<=' ')c=read();boolean neg=(c=='-');if(neg)c = read();do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')ret+=(c-'0')/(div*=10);if(neg)return -ret;return ret;}
    private void fillBuffer() throws IOException{bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);if(bytesRead==-1)buffer[0]=-1;}
    private byte read() throws IOException{if(bufferPointer==bytesRead)fillBuffer();return buffer[bufferPointer++];}
    public void close() throws IOException{if(din==null) return;din.close();}
}