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
    private final Arma ARMA_BASE = new Arma("Colt .45", "none", "0", 1);

    private UI user_interface;
    private int numero_giocatori;
    private LinkedList<Giocatore> giocatori;
    private ArrayList<Ruolo> ruoli;
    private ArrayList<Arma> armi;
    private ArrayList<Carta> carte;
    private Tavolo tavolo;
    private Mazzo mazzo;
    private Scarti pilaScarti;
    private int turno;

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

        setupPartita();
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
        giocatori.get(0).setArma(ARMA_BASE);

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
            giocatori.get(i).setArma(ARMA_BASE);
        }
    }


    private void setupPartita(){
        mazzo = new Mazzo(carte);
        pilaScarti = new Scarti();

        mazzo.mescola();

        pescaPrimeMani();

        turno = 0;
        startTurno();
    }

    private void pescaPrimeMani(){
        for(int i = 0; i<giocatori.size(); i++){
            pesca(giocatori.get(i), giocatori.get(i).getPuntiFerita());
        }
    }

    private void pesca(Giocatore giocatore, int numeroCarte) {
        for(int i = 0; i<numeroCarte; i++){
            Carta c = mazzo.pesca();

            if(c == null){
                mazzo.setCarte(pilaScarti.getCarte());
                pilaScarti.clear();
                mazzo.mescola();
                c = mazzo.pesca();
            }

            giocatore.getMano().add(c);
        }
    }

    public void startTurno(){
        int sceltaTurno;
        do{
            sceltaTurno = user_interface.sceltaMenuTurno(giocatori.get(turno));
            casiTurno(sceltaTurno);
        }while(sceltaTurno != 0);
        
    }

    private void casiTurno(int sceltaTurno) {
        switch(sceltaTurno){
            case 1:
                user_interface.guardaSceriffo(giocatori.get(0));
            case 2:
                user_interface.stampaLista(giocatori.get(turno).getMano());
            case 3:
                user_interface.stampaLista(giocatori.get(turno).getCarteEquipaggiate());
        }
    }


}
