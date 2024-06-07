package com.player;

public class Ruolo {
    private String nome;
    private String obiettivo;

    public Ruolo(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setObiettivo(String obiettivo){
        this.obiettivo = obiettivo;
    }

    public String getObiettivo(){
        return obiettivo;
    }
}
