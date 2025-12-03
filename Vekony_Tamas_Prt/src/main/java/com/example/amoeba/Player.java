package com.example.amoeba;

public final class Player {
    private final String name;
    private final char mark; // 'x' or 'o'
    public Player(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }
    public String getName() { return name; }
    public char getMark() { return mark; }
    @Override public String toString(){ return name + " ("+mark+")"; }
    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof Player)) return false;
        Player p=(Player)o;
        return name.equals(p.name) && mark==p.mark;
    }
    @Override public int hashCode(){ return name.hashCode()*31 + (int)mark; }
}
