package com.cards;

public class Giocabile extends Carta{
    private String descrizione;

    public Giocabile(String nome, String seme, String numero, String descrizione) {
        super(nome, seme, numero);
        this.descrizione = descrizione;
    }

    public String getDescrizione(){
        return descrizione;
    }
}
