package com.example.amoeba;

import java.util.*;

public class RandomAI {
    private final Random rnd = new Random();
    public Move pickMove(Board board, char mark){
        List<Move> moves = board.possibleMoves(mark);
        if(moves.isEmpty()) return null;
        return moves.get(rnd.nextInt(moves.size()));
    }
}
