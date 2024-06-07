package com.player;

import java.util.LinkedList;

import com.cards.*;
import com.cards.Equipaggiabile;

public class Giocatore {
    private String nome;
    private int pf;
    private LinkedList<Carta> mano;
    private LinkedList<Equipaggiabile> carteEquipaggiate; 
    private Arma arma;
    private Ruolo ruolo;
    private int vita;

    public Giocatore(String nome, Ruolo ruolo, int pf){
        this.nome = nome;
        this.ruolo = ruolo;
        this.pf = pf;
        vita = pf;
        mano = new LinkedList<Carta>();
        carteEquipaggiate = new LinkedList<Equipaggiabile>();
    }

    public String getNome() {
        return nome;
    }

    public LinkedList<Carta> getMano() {
        return mano;
    }

    public LinkedList<Equipaggiabile> getCarteEquipaggiate() {
        return carteEquipaggiate;
    }

    public Arma getArma() {
        return arma;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setArma(Arma arma){
        this.arma = arma;
    }

    public int getVita(){
        return vita;
    }

    public boolean addVita(int vita){
        if(this.vita+vita <= pf){
            this.vita += vita;
            return true;
        }
        else
            return false;
    }

    public boolean isDead(){
        return vita <= 0;
    }

    public int getPuntiFerita(){
        return this.pf;
    }

    public boolean equals(Giocatore g){
        return (nome.equals(g.getNome()));
    }
}
