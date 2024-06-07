package com.esame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.player.*;
import com.cards.*;

public class Gestore {
    private final int MAX_NUMERO_GIOCATORI = 7;
    private final int MIN_NUMERO_GIOCATORI = 4;
    private final int PUNTI_FERITA = 4;
    private final String PATH_CARTE = "src\\data\\listaCarte.xml";

    private UI user_interface;
    private int numero_giocatori;
    private LinkedList<Giocatore> giocatori;
    private ArrayList<Ruolo> ruoli;
    private ArrayList<Arma> armi;
    private ArrayList<Carta> carte;
    private Tavolo tavolo;

    public Gestore(){
        user_interface = new UI();
        giocatori = new LinkedList<Giocatore>();
        ruoli = new ArrayList<Ruolo>();
        armi = new ArrayList<Arma>();
        carte = new ArrayList<Carta>();
    }

    public void start(){
        leggiFile();
        creaGiocatori();
        creaTavolo();

        
    }

    private void creaTavolo(){
        tavolo = new Tavolo(giocatori);
    }

    private void leggiFile(){
        XmlReader r = new XmlReader(PATH_CARTE);
        r.leggi(ruoli, armi, carte);
    }

    private void creaGiocatori(){
        Random r = new Random();
        numero_giocatori = user_interface.getNumeroGiocatori(MIN_NUMERO_GIOCATORI, MAX_NUMERO_GIOCATORI);
        String[] nomi = user_interface.getNomiGiocatori(numero_giocatori);

        giocatori.add(new Giocatore(nomi[0], ruoli.remove(0), PUNTI_FERITA+1));

        ArrayList<Ruolo> ruoliTemp = new ArrayList<Ruolo>();
        ruoliTemp.add(ruoli.get(2)); //Rinnegato
        ruoliTemp.add(ruoli.get(0)); //Fuorilegge
        ruoliTemp.add(ruoli.get(0));

        if(numero_giocatori > 4)
            ruoliTemp.add(ruoli.get(1));//vice
        if(numero_giocatori > 5)
            ruoliTemp.add(ruoli.get(0));
        if(numero_giocatori > 6)
            ruoliTemp.add(ruoli.get(1));

        for(int i = 1; i<nomi.length; i++){
            giocatori.add(new Giocatore(nomi[i], ruoliTemp.remove(r.nextInt(ruoliTemp.size())), PUNTI_FERITA));
        }
    }
}
