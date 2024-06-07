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
    private final int CARTE_PER_TURNO = 2;
    

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
    private boolean bang = false;
    private Logic logica;

    public Gestore(){
        user_interface = new UI();
        giocatori = new LinkedList<Giocatore>();
        ruoli = new ArrayList<Ruolo>();
        armi = new ArrayList<Arma>();
        carte = new ArrayList<Carta>();
        logica = new Logic(giocatori, mazzo, pilaScarti, user_interface);

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
        mazzo = new Mazzo();
        pilaScarti = new Scarti();
        mazzo.setCarte(carte);

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
        pesca(giocatori.get(turno), CARTE_PER_TURNO);

        do{
            sceltaTurno = user_interface.sceltaMenuTurno(giocatori.get(turno));
            casiTurno(sceltaTurno);
        }while(sceltaTurno != 0);
        
        if(!isGameEnded()){
            turno = turno >= giocatori.size()-1 ? 0 : turno+1;
            startTurno();
        }
    }

    private void casiTurno(int sceltaTurno) {
        switch(sceltaTurno){
            case 1:
                user_interface.guardaSceriffo(giocatori.get(0));
                break;
            case 2:
                user_interface.stampaLista(giocatori.get(turno).getMano());
                break;
            case 3:
                user_interface.stampaLista(giocatori.get(turno).getCarteEquipaggiate());
                break;
            case 4:
                distanzaGiocatore();
                break;
            case 5:
                user_interface.stampa(Integer.toString(giocatori.get(turno).getVita()));
                user_interface.waitUser();
                break;
            case 6:
                user_interface.stampaRuoloObiettivo(giocatori.get(turno));
                break;
            case 7:
                giocaCarta();
                break;
            case 0:
                break;
            
        }
    }

    private void distanzaGiocatore(){
        LinkedList<Giocatore> g = new LinkedList<Giocatore>(giocatori);
        g.remove(turno);
        int scelta = user_interface.sceltaGiocatore(g);
        user_interface.stampa(Integer.toString(tavolo.getDistanza(giocatori.get(turno), g.get(scelta))));
        user_interface.waitUser();
    }

    private void giocaCarta(){
        int scelta = user_interface.sceltaCarta(giocatori.get(turno).getMano());
        if(scelta != 0){
            logica.giocaCarta(giocatori.get(turno).getMano().get(scelta), turno, giocatori.get(turno));
        }
    }

    private boolean isGameEnded(){
        boolean sceriffoVivo = false;
        boolean fuorileggeVivi = false;
        boolean rinnegatoVivo = false;
        for(int i = 0; i<giocatori.size(); i++){
            if(giocatori.get(i).getRuolo().getNome().equals("Sceriffo")){
                sceriffoVivo = true;
            }
            else if(giocatori.get(i).getRuolo().getNome().equals("Fuorilegge")){
                fuorileggeVivi = true;
            }
            else if(giocatori.get(i).getRuolo().getNome().equals("Rinnegato")){
                rinnegatoVivo = true;
            }
        }
        if(!sceriffoVivo){
            if(giocatori.size() == 1 && rinnegatoVivo){
                user_interface.stampaWRinnegato();
            }
            else{
                user_interface.stampaWFuorilegge();
            }
        }
        else if(!fuorileggeVivi){
            user_interface.stampaWSceriffo();
        }

        return !sceriffoVivo || !fuorileggeVivi;
    }

}
