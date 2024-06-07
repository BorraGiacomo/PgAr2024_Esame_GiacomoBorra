package com.esame;

public class Ruolo {
    private String nome;
    private String obiettivo;

    public Ruolo(String nome, String obiettivo){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public String getObiettivo(){
        return obiettivo;
    }
}
