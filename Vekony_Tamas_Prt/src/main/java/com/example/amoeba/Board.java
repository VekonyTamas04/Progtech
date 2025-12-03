package com.example.amoeba;

import java.util.*;
import java.io.*;

public class Board {
    private final int rows;
    private final int cols;
    private final char[][] grid;
    public Board(int rows, int cols){
        if(cols<4 || rows<cols || rows>25 || cols<4) throw new IllegalArgumentException("Invalid size");
        this.rows=rows; this.cols=cols;
        grid = new char[rows][cols];
        for(int r=0;r<rows;r++) for(int c=0;c<cols;c++) grid[r][c]='.';
    }
    public int getRows(){ return rows; }
    public int getCols(){ return cols; }
    public char at(int r,int c){ return grid[r][c]; }
    public boolean isEmpty(int r,int c){ return grid[r][c]=='.'; }
    public boolean place(Move m, char mark){
        if(!inBounds(m)) return false;
        if(!isEmpty(m.getRow(), m.getCol())) return false;
        grid[m.getRow()][m.getCol()]=mark;
        return true;
    }
    public boolean inBounds(Move m){ return m.getRow()>=0 && m.getRow()<rows && m.getCol()>=0 && m.getCol()<cols; }
    public boolean hasAdjacent(Move m){
        for(int dr=-1; dr<=1; dr++) for(int dc=-1; dc<=1; dc++){
            if(dr==0 && dc==0) continue;
            int r=m.getRow()+dr, c=m.getCol()+dc;
            if(r>=0 && r<rows && c>=0 && c<cols && grid[r][c]!='.') return true;
        }
        return false;
    }
    public List<Move> possibleMoves(char mark){
        List<Move> res = new ArrayList<>();
        for(int r=0;r<rows;r++) for(int c=0;c<cols;c++){
            if(grid[r][c]=='.'){
                Move m=new Move(r,c);
                if(anyPlaced() ? hasAdjacent(m) : true) res.add(m);
            }
        }
        return res;
    }
    public boolean anyPlaced(){
        for(int r=0;r<rows;r++) for(int c=0;c<cols;c++) if(grid[r][c]!='.') return true;
        return false;
    }
    public boolean checkWin(char mark){
        int need=5;
        for(int r=0;r<rows;r++) for(int c=0;c<cols;c++) if(grid[r][c]==mark){
            if(countDir(r,c,0,1,mark)>=need) return true;
            if(countDir(r,c,1,0,mark)>=need) return true;
            if(countDir(r,c,1,1,mark)>=need) return true;
            if(countDir(r,c,1,-1,mark)>=need) return true;
        }
        return false;
    }
    private int countDir(int r,int c,int dr,int dc,char mark){
        int cnt=0;
        int rr=r, cc=c;
        while(rr>=0 && rr<rows && cc>=0 && cc<cols && grid[rr][cc]==mark){ cnt++; rr+=dr; cc+=dc; }
        return cnt;
    }
    public void print(PrintStream out){
        out.print("   ");
        for(int c=0;c<cols;c++) out.print((char)('a'+c)+" ");
        out.println();
        for(int r=0;r<rows;r++){
            out.printf("%2d ", r+1);
            for(int c=0;c<cols;c++) out.print(grid[r][c]+" ");
            out.println();
        }
    }
    public void saveToFile(File f) throws IOException{
        try(PrintWriter pw=new PrintWriter(f)){
            pw.println(rows + " " + cols);
            for(int r=0;r<rows;r++){
                pw.println(new String(grid[r]));
            }
        }
    }
    public static Board loadFromFile(File f) throws IOException{
        try(BufferedReader br=new BufferedReader(new FileReader(f))){
            String[] parts = br.readLine().split("\\s+");
            int rows=Integer.parseInt(parts[0]), cols=Integer.parseInt(parts[1]);
            Board b = new Board(rows, cols);
            for(int r=0;r<rows;r++){
                String line = br.readLine();
                for(int c=0;c<cols && c<line.length();c++) b.grid[r][c]=line.charAt(c);
            }
            return b;
        }
    }
}
