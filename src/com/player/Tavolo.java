package com.player;

import java.util.LinkedList;

public class Tavolo {
    private LinkedList<Giocatore> giocatori;

    public Tavolo(LinkedList<Giocatore> giocatori){
        this.giocatori = giocatori;
    }

    public int getDistanza(Giocatore g1, Giocatore g2){
        int i1 = giocatori.indexOf(g1);
        int i2 = giocatori.indexOf(g2);

        return Math.min(Math.abs(i1-i2), Math.min(i1, i2) + giocatori.size()-Math.max(i1, i2));
    }
}
