package com.esame;

import com.player.*;

public class Gestore {
    private final int MAX_NUMERO_GIOCATORI = 7;
    private final int MIN_NUMERO_GIOCATORI = 4;

    private UI user_interface;
    private int numero_giocatori;

    public Gestore(){
        user_interface = new UI();
    }

    public void start(){
        numero_giocatori = user_interface.getNumeroGiocatori(MIN_NUMERO_GIOCATORI, MAX_NUMERO_GIOCATORI);
    }

    private void creaTavolo(){

    }
}
