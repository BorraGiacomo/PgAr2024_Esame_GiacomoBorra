package com.player;

public class Tavolo {
    private Graph<Giocatore> giocatori;

    public Tavolo(){
        giocatori = new Graph<Giocatore>();
    }

    public void addGiocatori(Giocatore[] giocatori){
        for(int i = 0; i<giocatori.length; i++){
            this.giocatori.add(giocatori[i]);
        }

        for(int i = 0; i<giocatori.length; i++){
            for(int j = i+1; j!=i; j++){
                
            }
        }
    }
}
