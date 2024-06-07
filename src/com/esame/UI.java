package com.esame;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UI {
    private final String TXT_NUMERO_GIOCATORI = "Inserire il numero di giocatori, tra %d e %d";
    private final String TXT_NOME_SCERIFFO = "Inserire il nome dello sceriffo:";
    private final String TXT_NOME_GIOCATORE = "Inserire il nome del giocatore:";
    private final String ERRORE_NUMERO_GIOCATORI = "Numero di giocatori troppo alto";

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

    public String getSceriffo(){
        return InputDati.leggiStringaNonVuota(TXT_NOME_SCERIFFO);
    }

    public String getNomeGiocatore(){
        return InputDati.leggiStringaNonVuota(TXT_NOME_GIOCATORE);
    }

    public void stampa(String s){
        System.out.println(s);
    }
}
