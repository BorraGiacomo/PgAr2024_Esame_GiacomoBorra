package com.esame;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.cards.Carta;
import com.player.Giocatore;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UI {
    private final String TXT_NUMERO_GIOCATORI = "Inserire il numero di giocatori, tra %d e %d";
    private final String TXT_NOME_SCERIFFO = "Inserire il nome dello sceriffo:";
    private final String TXT_NOME_GIOCATORE = "Inserire il nome del giocatore:";
    private final String ERRORE_NUMERO_GIOCATORI = "Numero di giocatori troppo alto";
    private final String NOME_NON_VALIDO = "Nome del giocatore già utilizzato\n";
    private final String NEXT = "\n[ENTER]";
    private final String TITOLO_MENU_TURNO = "E' il turno di %s, premi exit per finire il turno";
    private final String[] SCELTE_MENU_TURNO = {"Guarda lo sceriffo", "Guarda la tua mano", "Guarda le tue carte equipaggiate", "Guarda la distanza da un altro giocatore", "Guarda vita", "Guarda chi sei", "Usa carta"};
    private final String GUARDA_SCERIFFO = "Lo sceriffo e' %s";
    private final String TITOLO_MENU_GIOCATORI = "Scegli il giocatore";
    private final String OBIETTIVO_SCERIFFO = "Elimina tutti i fuorilegge ed il rinnegato";
    private final String OBIETTIVO_RINNEGATO = "Rimani l'ultimo giocatore in vita";
    private final String OBIETTIVO_FUORILEGGE = "Elimina lo sceriffo";
    private final String OBIETTIVO_VICE = "Proteggi lo sceriffo eliminando i fuorilegge e il rinnegato";
    private final String TESTO_RUOLO = "Sei %s\n";
    private final String USO_CARTA = "%s usa %s";
    private final String SCARTA_CARTA = "%s viene scartata";
    private final String USO_CARTA_SU = "%s usa %s su %s";
    private final String TESTO_SCELTA_MANCATO = "Usi il mancato?";
    private final String TESTO_SCELTA_BARILE = "Usi il barile?";
    private final String TESTO_ESTRAI = "Pescata la carta %s di %s";
    private final String MANCATO = "Mancato!";
    private final String MORTE = "%s e' morto, era %s";
    private final String SCERIFFO_VICE = "Lo sceriffo uccide il vice, perde tutte le carte!";
    private final String MORTO_FUORILEGGE = "Era un fuorilegge, %s pesca 3 carte";
    private final String TITOLO_GIOCA_CARTA = "Scegli la carta da giocare";
    private final String VITTORIA_RINNEGATO = "Vince il rinnegato";
    private final String VITTORIA_SCERIFFO = "Vincono lo sceriffo e i vice";
    private final String VITTORIA_FUORILEGGE = "Vincono i fuorilegge"; 

    private MyMenu menu_turno;
    private MyMenu menu_scelta_giocatori;
    private MyMenu menu_carte;

    public UI(){

    }

    public int getNumeroGiocatori(int min, int max){
        int num;
        do{
            num = InputDati.leggiInteroConMinimo(String.format(TXT_NUMERO_GIOCATORI, min, max), min);
            if(num > max)
                stampa(ERRORE_NUMERO_GIOCATORI);
        }while(num > max);
        return num;
    }

    public String[] getNomiGiocatori(int numero_giocatori){
        String[] nomi = new String[numero_giocatori];
        nomi[0] = InputDati.leggiStringaNonVuota(TXT_NOME_SCERIFFO);
        
        for(int i = 1; i<numero_giocatori; i++){
            boolean controllo;
            String nomeTemp;
            do{
                controllo = false;
                nomeTemp = InputDati.leggiStringaNonVuota(TXT_NOME_GIOCATORE);

                for(int j = 0; j<i; j++){
                    if(nomi[j].equals(nomeTemp)){
                        stampa(NOME_NON_VALIDO);
                        controllo = true;
                    }
                }
            }while(controllo);

            nomi[i] = nomeTemp;
        }
        return nomi;
    }

    public int sceltaMenuTurno(Giocatore g){
        menu_turno = new MyMenu(String.format(TITOLO_MENU_TURNO, g.getNome()), SCELTE_MENU_TURNO);
        return menu_turno.scegli();
    }

    public void guardaSceriffo(Giocatore g){
        stampa(String.format(GUARDA_SCERIFFO, g.getNome()));
        waitUser();
    }

    public <E> void stampaLista(List<E> lista){
        for(E obj : lista){
            stampa(obj.toString());
            stampa("\n");
        }
        waitUser();
    }

    public void stampaRuoloObiettivo(Giocatore g){
        stampa(String.format(TESTO_RUOLO, g.getRuolo().getNome()));
        if(g.getRuolo().getNome().equals("Sceriffo")){
            stampa(OBIETTIVO_SCERIFFO);
        }
        else if(g.getRuolo().getNome().equals("Fuorilegge")){
            stampa(OBIETTIVO_FUORILEGGE);
        }
        else if(g.getRuolo().getNome().equals("Rinnegato")){
            stampa(OBIETTIVO_RINNEGATO);
        }
        else{
            stampa(OBIETTIVO_VICE);
        }
        waitUser();
    }

    public int sceltaGiocatore(LinkedList<Giocatore> giocatori){
        String[] nomi = new String[giocatori.size()];
        for(int i = 0; i<giocatori.size(); i++){
            nomi[i] = giocatori.get(i).getNome();
        }

        menu_scelta_giocatori = new MyMenu(TITOLO_MENU_GIOCATORI, nomi);
        return menu_scelta_giocatori.scegli();
    }

    public void stampaUsoCarta(Giocatore g, Carta c){
        stampa(String.format(USO_CARTA, g.getNome(), c.getNome()));
    }

    public void stampaUsoCarta(Giocatore g1, Carta c, Giocatore g2){
        stampa(String.format(USO_CARTA_SU, g1.getNome(), c.getNome(), g2.getNome()));
    }

    public void stampaScartaCarta(Carta c){
        stampa(String.format(SCARTA_CARTA, c.getNome()));
    }

    public boolean usaMancato(){
        return InputDati.yesOrNo(TESTO_SCELTA_MANCATO);
    }

    public boolean usaBarile(){
        return InputDati.yesOrNo(TESTO_SCELTA_BARILE);
    }

    public void stampaEstrai(Carta c){
        stampa(String.format(TESTO_ESTRAI, c.getNome(), c.getSeme()));
    }

    public void stampaMorte(Giocatore g){
        stampa(String.format(MORTE, g.getNome(), g.getRuolo().getNome()));
    }

    public void stampaSceriffoVice(){
        stampa(SCERIFFO_VICE);
        waitUser();
    }

    public void stampaMorteBandito(Giocatore g){
        stampa(String.format(MORTO_FUORILEGGE, g.getNome()));
        waitUser();
    }

    public int sceltaCarta(LinkedList<Carta> carte){
        String[] opz = new String[carte.size()];
        for(int i = 0; i<carte.size(); i++){
            opz[i] = carte.get(i).getNome();
        }

        menu_carte = new MyMenu(TITOLO_GIOCA_CARTA, opz);
        return menu_carte.scegli();
    }

    public void stampaWRinnegato(){
        stampa(VITTORIA_RINNEGATO);
    }

    public void stampaWFuorilegge(){
        stampa(VITTORIA_FUORILEGGE);
    }

    public void stampaWSceriffo(){
        stampa(VITTORIA_SCERIFFO);
    }

    public void stampa(String s){
        System.out.println(s);
    }   

    public void stampaMancato(){
        stampa(MANCATO);
    }

    public void waitUser(){
        stampa(NEXT);
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
