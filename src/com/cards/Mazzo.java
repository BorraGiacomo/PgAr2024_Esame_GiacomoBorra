package com.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Mazzo extends PilaCarte{
    
    public Mazzo(){
        super();
    }

    public Mazzo(ArrayList<Carta> carte){
        super(carte);
    }

    public void riempi(ArrayList<Carta> carte){
        setCarte(carte);
    }

    public Carta pesca(){
        return getCarte().removeFirst();
    }

    public void mescola(){
        Collections.shuffle(getCarte());
    }
}
