package com.example.amoeba;

import java.sql.*;
import java.io.File;

public class Persistence {
    private static final String DB_URL = "jdbc:sqlite:database.db";
    public Persistence(){
        try(Connection c = DriverManager.getConnection(DB_URL)){
            try(Statement s = c.createStatement()){
                s.executeUpdate("CREATE TABLE IF NOT EXISTS players(name TEXT PRIMARY KEY, wins INTEGER)");
            }
        }catch(SQLException e){ e.printStackTrace(); }
    }
    public void recordWin(String playerName){
        try(Connection c = DriverManager.getConnection(DB_URL)){
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO players(name,wins) VALUES(?,?) ON CONFLICT(name) DO UPDATE SET wins = wins + 1")){
                ps.setString(1, playerName);
                ps.setInt(2, 1);
                ps.executeUpdate();
            }
        }catch(SQLException e){ e.printStackTrace(); }
    }
    public void printHighscores(){
        try(Connection c = DriverManager.getConnection(DB_URL)){
            try(Statement s = c.createStatement()){
                try(ResultSet rs = s.executeQuery("SELECT name,wins FROM players ORDER BY wins DESC")){
                    System.out.println("Highscores:");
                    System.out.println("-----------------");
                    while(rs.next()){
                        System.out.printf("%s : %d%n", rs.getString(1), rs.getInt(2));
                    }
                }
            }
        }catch(SQLException e){ e.printStackTrace(); }
    }
    public static void ensureDbFileDeletedForTests(){
        File f = new File("database.db");
        if(f.exists()) f.delete();
    }
}
