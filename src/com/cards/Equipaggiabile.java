package com.cards;

public class Equipaggiabile extends Carta{
    private String descrizione;
    
    public Equipaggiabile(String nome, String seme, String numero, String descrizione) {
        super(nome, seme, numero);
        this.descrizione = descrizione;
    }

    public String getDescrizione(){
        return descrizione;
    }
    
    public String toString(){
        return super.toString()+"\nDescrizione: "+descrizione;
    }
}
