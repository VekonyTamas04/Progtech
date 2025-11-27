package com.example.amoeba;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void testHorizontalWin(){
        Board b = new Board(10,10);
        for(int c=2;c<7;c++) b.place(new Move(4,c),'x');
        assertTrue(b.checkWin('x'));
    }
    @Test
    void testVerticalWin(){
        Board b = new Board(10,10);
        for(int r=1;r<=5;r++) b.place(new Move(r,3),'o');
        assertTrue(b.checkWin('o'));
    }
    @Test
    void testDiagonalWin(){
        Board b = new Board(10,10);
        for(int i=0;i<5;i++) b.place(new Move(2+i,2+i),'x');
        assertTrue(b.checkWin('x'));
    }
    @Test
    void testAdjacency(){
        Board b = new Board(6,6);
        b.place(new Move(3,3),'x');
        assertTrue(b.hasAdjacent(new Move(2,2)));
        assertFalse(b.hasAdjacent(new Move(0,0)));
    }
    @Test
    void testSaveLoad() throws Exception {
        Board b = new Board(6,6);
        b.place(new Move(0,0),'x');
        java.io.File f = new java.io.File(\"/tmp/test_board.txt\");
        b.saveToFile(f);
        Board b2 = Board.loadFromFile(f);
        assertEquals(b.getRows(), b2.getRows());
        assertEquals(b.getCols(), b2.getCols());
        assertEquals(b.at(0,0), b2.at(0,0));
        f.delete();
    }
}
