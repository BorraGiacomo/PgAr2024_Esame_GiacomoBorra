package com.esame;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UI {
    private final String TXT_NUMERO_GIOCATORI = "Inserire il numero di giocatori, tra %d e %d";
    private final String TXT_NOME_SCERIFFO = "Inserire il nome dello sceriffo:";
    private final String TXT_NOME_GIOCATORE = "Inserire il nome del giocatore:";
    private final String ERRORE_NUMERO_GIOCATORI = "Numero di giocatori troppo alto";
    private final String NOME_NON_VALIDO = "Nome del giocatore già utilizzato\n";

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

    public void stampa(String s){
        System.out.println(s);
    }
}
