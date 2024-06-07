package com.player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Graph<E> {//grafico
    private final int DEFAULT_CAPACITY = 5;
    private final int GROWTH_RATE = 1;
    private double[][] matrice;
    private HashMap<E, Integer> mappa;
    private int size;

    public Graph(){//inizializza una matrice vuota di n righe
        matrice = new double[DEFAULT_CAPACITY][];
        size = 0;
        mappa = new HashMap<E, Integer>();
    }

    public void grow(int s){// aumenta la dimensione della matrice di s
        if(s > 0){
            for(int i = 0; i<matrice.length; i++){
                if(matrice[i] != null){//se la riga della matrice non è null se ne crea una copia con s elementi in più (null)
                    matrice[i] = Arrays.copyOf(matrice[i], matrice[i].length+s);

                    for(int j = 0; j<s; j++){//inizializzo le nuove celle a infinito
                        if(i != matrice.length+j)
                            matrice[i][matrice.length+j] = Double.POSITIVE_INFINITY;
                    }
                }
            }
            matrice = Arrays.copyOf(matrice, matrice.length+s); //copia la matrice con s righe in più (null)
        }
    }

    public void add(E obj){
        mappa.put(obj, size);
        size++;
        //se la matrice ha raggiunto capienza massima si incrementa la sua dimensione
        if(size > matrice.length){
            grow(GROWTH_RATE);
        }

        matrice[size-1] = new double[matrice.length];

        initColonna(matrice[size-1], size-1);
    }

    public boolean remove(E obj){
        if(!mappa.containsKey(obj)){
            return false;
        }

        int i = mappa.get(obj);
        mappa.remove(obj);
        fastRemove(i);//i = posizione dell'oggetto da rimuovere nella matrice
        return true;
    }

    private void fastRemove(int i){
        int newSize = size-1; //dimensione finale della matrice

        if(newSize > i){//se l'oggetto da togliere NON si trova in ultima posizione 
            System.arraycopy(matrice, i+1, matrice, i, newSize-i);//copio le righe dopo quella da rimuovere e le muovo di una posizione indietro

            for(int j = 0; j<newSize; j++){
                System.arraycopy(matrice[j], i+1, matrice[j], i, newSize-i);//copio le colonne dopo quella da rimuovere e le sposto una colonna indietro
            }
            //ho rimosso quindi la riga e la colonna da rimuovere
        }

        //cancello l'ultima riga+1 della colonna, dato che è il duplicato dell'ultima, oppure è quella da rimuovere
        matrice[size = newSize] = null;

        for(int j = 0; j<size; j++){
            matrice[j][size] = Double.POSITIVE_INFINITY;
        }
    }

    public boolean setArco(E from, E to, double value){//dare valore alla freccia  direzione from-->to
        if((!mappa.containsKey(from) || !mappa.containsKey(to)) || from.equals(to))
            return false;

        int i = mappa.get(from);
        int j = mappa.get(to);

        //leggendo gli archi il valore della matrice si legge come da riga a colonna (dal primo [i] al secondo [j])
        matrice[i][j] = value;
        return true;
    }

    public double[] getAllArchi(E obj){//array di frecce che partono e che arrivano di un elemento
        if(!mappa.containsKey(obj))
            return null;
        
        int i = mappa.get(obj);
        return Arrays.copyOf(matrice[i], size);
    }

    public HashSet<E> getAllNodi(){//retituisce tutti gli oggetti presenti nel grafo
        HashSet<E> s = new HashSet<E>();

        for (Map.Entry<E, Integer> entry : mappa.entrySet()) {
            s.add(entry.getKey());
        }
        return s;
    }

    public double getArco(E from, E to){//valore freccia from-->to
        if(!mappa.containsKey(from) || !mappa.containsKey(to))
            return 0;
        
        int i = mappa.get(from);
        int j = mappa.get(to);

        return matrice[i][j];
    }

    private void initColonna(double[] arr, int j){
        for(int i = 0; i<arr.length; i++){
            if(i != j)
                arr[i] = Double.POSITIVE_INFINITY;
        }
    }

    public int getSize(){
        return size;
    }

    public void view(){
        for(int i = 0; i<matrice.length; i++){
            for(int j = 0; j<matrice.length; j++){
                if(i<size && j<size)
                {
                    if(matrice[i][j]>=0)
                    {
                        System.out.print(" ");
                        System.out.print(matrice[i][j]);
                        System.out.print("\t");
                    }else{
                        
                        System.out.print(matrice[i][j]);
                        System.out.print("\t");
                    } 
                }     
            }
            System.out.println();
        }   
    }
}

