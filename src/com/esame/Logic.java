package com.esame;

import java.util.LinkedList;

import com.cards.*;
import com.player.*;
public class Logic {
    private final Arma ARMA_BASE = new Arma("Colt .45", "none", "0", 1);

    private LinkedList<Giocatore> giocatori;
    private Mazzo mazzo;
    private Scarti pilaScarti;
    private UI user_interface;

    public Logic(LinkedList<Giocatore> giocatori, Mazzo mazzo, Scarti pilaScarti, UI user_interface) {
        this.giocatori = giocatori;
        this.mazzo = mazzo;
        this.pilaScarti = pilaScarti;
        this.user_interface = user_interface;
    }

    public boolean giocaCarta(Carta carta, int turno, Giocatore giocante){
        if(carta instanceof Arma){
            equipArma(giocante, (Arma) carta);
        }
        if(carta instanceof Giocabile){
            if(giocante.equals(giocatori.get(turno)) && !carta.getNome().equals("Mancato!")){
                usaGiocabile((Giocabile) carta, turno);
            }
            else{
                return false;
            }
        }
        return true;
    }

    private void equipArma(Giocatore giocatore, Arma arma){
        if(!giocatore.getArma().equals(ARMA_BASE)){
            pilaScarti.scarta(giocatore.getArma());
        }
        giocatore.setArma(arma);
    }

    private void usaGiocabile(Giocabile carta, int turno){
        switch(carta.getNome()){
            case "BANG!":
                Giocatore morto = useBang(turno);
                if(morto != null){
                    controllaMorte(giocatori.get(turno), morto);
                }
                break;

        }
    }

    private void controllaMorte(Giocatore giocatore, Giocatore morto) {
        if(giocatore.getRuolo().getNome().equals("Sceriffo") && morto.getRuolo().getNome().equals("Vice")){
            for(int i = 0; i<giocatore.getMano().size(); i++){
                pilaScarti.scarta(giocatore.getMano().remove(i));
            }

            for(int i = 0; i<giocatore.getCarteEquipaggiate().size(); i++){
                pilaScarti.scarta(giocatore.getCarteEquipaggiate().remove(i));
            }
            user_interface.stampaSceriffoVice();
        }
        else if(morto.getRuolo().getNome().equals("Fuorilegge")){
            for(int i = 0; i<3; i++){
                giocatore.getMano().add(pesca());
            }
            user_interface.stampaMorteBandito(giocatore);
        }
    }

    private Giocatore useBang(int turno){
        LinkedList<Giocatore> gTemp = new LinkedList<Giocatore>(giocatori);
        gTemp.remove(turno);
        int scelta = user_interface.sceltaGiocatore(gTemp);
        boolean colpito = minaccia(gTemp.get(scelta));
        
        if(colpito){
            infliggiDanno(gTemp.get(scelta));
            if(isMorto(gTemp.get(scelta))){
                Giocatore morto = giocatori.remove(giocatori.indexOf(gTemp.get(scelta)));
                user_interface.stampaMorte(morto);
                return morto;
            }
        }
        else{
            user_interface.stampaMancato();
        }
        return null;
    }

    private boolean minaccia(Giocatore g){
        boolean colpito = true;
        if(hasCarta(g.getCarteEquipaggiate(), "Barile")){
            if(user_interface.usaBarile()){
                colpito = !estrai();
            }
        }
        if(colpito && hasCarta(g.getMano(), "Mancato!")){
            if(user_interface.usaMancato()){
                scartaMancato(g);
                colpito = false;
            }
        }

        return colpito;
    }

    private void scartaMancato(Giocatore g){
        boolean contr = true;
        for(int i = 0; i<g.getMano().size() && contr; i++){
            if(g.getMano().get(i).getNome().equals("Mancato!")){
                pilaScarti.scarta(g.getMano().get(i));
                contr = false;
            }
        }
    }

    private <E extends Carta> boolean hasCarta(LinkedList<E> lista, String nome){
        for(int i = 0; i<lista.size(); i++){
            if(lista.get(i).getNome().equals(nome)){
                return true;
            }
        }
        return false;
    }

    private void infliggiDanno(Giocatore g){
        g.addVita(-1);
    }

    private boolean estrai(){
        Carta c = pesca();
        pilaScarti.scarta(c);
        user_interface.stampaEstrai(c);

        return c.getSeme().equals("CUORI");
    }

    private Carta pesca(){
        if(mazzo.getSize() <= 0){
            mazzo.setCarte(pilaScarti.getCarte());
            mazzo.mescola();
            pilaScarti.clear();
        }
        return mazzo.pesca();
    }

    private boolean isMorto(Giocatore g){
        return g.isDead();
    }
}
