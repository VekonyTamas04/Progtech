package com.example.amoeba;

import java.util.*;
import java.io.*;

public class Game {
    private final Scanner scanner = new Scanner(System.in);
    private Board board;
    private Player human;
    private Player aiPlayer;
    private final RandomAI ai = new RandomAI();
    private final Persistence persistence = new Persistence();

    public void start(){
        System.out.println("---- Amoba NxM ----");
        System.out.print("Adj meg egy sort N (4-25, alapból 10): ");
        int n = readIntOrDefault(10);
        System.out.print("Adj meg egy oszlopot M (4..N, alapból 10): ");
        int m = readIntOrDefault(10);
        board = new Board(n,m);
        System.out.print("Add meg a neved: ");
        String name = scanner.nextLine().trim();
        if(name.isEmpty()) name="Játékos";
        human = new Player(name,'x');
        aiPlayer = new Player("Gép",'o');

        int r = n/2;
        int c = m/2;
        board.place(new Move(r,c),'x');
        System.out.println("Kezdő pozíció (autómatikusan lehejez x-et középre):");
        board.print(System.out);

        loop();
    }

    private int readIntOrDefault(int d){
        String s = scanner.nextLine().trim();
        if(s.isEmpty()) return d;
        try{ return Integer.parseInt(s); }catch(Exception e){ return d; }
    }

    private void loop(){
        boolean humanTurn =
        humanTurn = true;
        while(true){
            if(humanTurn){
                System.out.println("Adj meg egy pozíciót (pl. a5), vagy 'save fname', 'load fname', 'high', 'exit':");
                String line = scanner.nextLine().trim();
                if(line.equalsIgnoreCase("exit")){ System.out.println("Szia"); break; }
                if(line.startsWith("save ")){
                    String f=line.substring(5).trim();
                    try{ board.saveToFile(new File(f)); System.out.println("elmentve ide " + f); }catch(Exception e){ e.printStackTrace(); }
                    continue;
                }else if(line.startsWith("load ")){
                    String f=line.substring(5).trim();
                    try{ board = Board.loadFromFile(new File(f)); System.out.println("betöltve innen " + f); board.print(System.out); }catch(Exception e){ e.printStackTrace(); }
                    continue;
                }else if(line.equalsIgnoreCase("high")){
                    persistence.printHighscores(); continue;
                }
                Move m = parseMove(line);
                if(m==null){ System.out.println("Nem megfelelő formátum"); continue; }
                if(!board.inBounds(m)){ System.out.println("Kereteken kívül"); continue; }
                if(!board.isEmpty(m.getRow(), m.getCol())){ System.out.println("A hely már foglalt"); continue; }
                if(board.anyPlaced() && !board.hasAdjacent(m)){ System.out.println("Csak kitöltött mező mellé lehet"); continue; }
                board.place(m, human.getMark());
                board.print(System.out);
                if(board.checkWin(human.getMark())){ System.out.println("Győztél!"); persistence.recordWin(human.getName()); break; }
            } else {
                Move m = ai.pickMove(board, aiPlayer.getMark());
                if(m==null){ System.out.println("Döntetlen."); break; }
                board.place(m, aiPlayer.getMark());
                System.out.println("A gép ide rakta: " + (char)('a'+m.getCol()) + (m.getRow()+1));
                board.print(System.out);
                if(board.checkWin(aiPlayer.getMark())){ System.out.println("A gép nyer!"); persistence.recordWin(aiPlayer.getName()); break; }
            }
            humanTurn = !humanTurn;
        }
    }

    private Move parseMove(String s){
        s = s.trim().toLowerCase();
        if(s.length()<2) return null;
        char colC = s.charAt(0);
        if(colC<'a' || colC>'z') return null;
        int col = colC - 'a';
        String rest = s.substring(1);
        try{
            int row = Integer.parseInt(rest) - 1;
            return new Move(row,col);
        }catch(Exception e){ return null; }
    }
}
