package com.esame;

import java.util.LinkedList;

import com.cards.*;
import com.cards.Equipaggiabile;

public class Giocatore {
    private String nome;
    private LinkedList<Carta> mano;
    private LinkedList<Equipaggiabile> carteEquipaggiate; 
    private Arma arma;
    private Ruolo ruolo;
    private int vita;

    public Giocatore(String nome){
        this.nome = nome;
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

    public Carta getArma() {
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

    public void addVita(int vita){
        this.vita += vita;
    }

    public boolean isDead(){
        return vita <= 0;
    }
}
