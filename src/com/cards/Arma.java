package com.cards;

public class Arma extends Carta{
    private int distanza;

    public Arma(String nome, String seme, String numero, int distanza) {
        super(nome, seme, numero);
        this.distanza = distanza;
    }
    
    public int getDistanza(){
        return distanza;
    }
}
