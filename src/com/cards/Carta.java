package com.cards;

public abstract class Carta {
    private String nome;
    private String seme;
    private String numero;

    public Carta(String nome, String seme, String numero){
        this.nome = nome;
        this.seme = seme;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public String getSeme() {
        return seme;
    }

    public String getNumero() {
        return numero;
    }

    public String toString(){
        return "Nome: "+nome+", seme: "+seme+", numero: "+numero;
    }
}
