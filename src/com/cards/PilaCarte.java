package com.cards;

import java.util.ArrayList;

public abstract class PilaCarte {
    private ArrayList<Carta> carte;

    public PilaCarte(){
        carte = new ArrayList<Carta>();
    }

    public PilaCarte(ArrayList<Carta> carte){
        carte = new ArrayList<Carta>(carte);
    }

    public ArrayList<Carta> getCarte() {
        return carte;
    }

    public int getSize(){
        return carte.size();
    }

    public void setCarte(ArrayList<Carta> carte){
        this.carte = new ArrayList<Carta>(carte);
    }

    public void clear(){
        carte.clear();
    }
}
