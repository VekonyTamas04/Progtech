package com.example.amoeba;

public final class Move {
    private final int row; private final int col;
    public Move(int row, int col){ this.row=row; this.col=col; }
    public int getRow(){ return row; } public int getCol(){ return col; }
    @Override public String toString(){ return "(" + row + "," + col + ")"; }
    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof Move)) return false;
        Move m=(Move)o; return row==m.row && col==m.col;
    }
    @Override public int hashCode(){ return row*31 + col; }
}
