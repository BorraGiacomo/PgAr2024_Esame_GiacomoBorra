package com.esame;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.cards.*;
import com.player.*;

public class XmlReader{
    private String nome_file;

    public XmlReader(String nome_file){
        this.nome_file = nome_file;
    }

    public void cambiaFile(String nomeFile){
        this.nome_file = nomeFile;
    }

    public void leggi(ArrayList<Ruolo> ruoli, ArrayList<Arma> armi, ArrayList<Carta> carte){
        
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        try (FileInputStream reader = new FileInputStream(nome_file)) {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, reader);

            while(xmlr.hasNext()){
                switch(xmlr.getEventType()){

                    case XMLStreamConstants.START_ELEMENT:
                        if(xmlr.getLocalName().equals("ruolo")){
                            ruoli.add(new Ruolo(xmlr.getElementText()));
                        }
                        else if(xmlr.getLocalName().equals("arma")){
                            creaArma(armi, xmlr);
                        }
                        else if(xmlr.getLocalName().equals("carta")){
                            creaCarta(carte, xmlr);
                        }
                        break;
                }

                xmlr.next();
            }
        }
        catch (FactoryConfigurationError | XMLStreamException | IOException e) {
            System.out.println("Errore nellinizializzazione del reader:");
            System.out.println(e.getMessage());
        }
    }

    private void creaArma(ArrayList<Arma> armi, XMLStreamReader xmlr) throws XMLStreamException {
        boolean controllo = true;
        String nome = "";
        int distanza = 0;
        while(controllo){
            if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT){
                if(xmlr.getLocalName().equals("nome")){
                    nome = xmlr.getElementText();
                }
                else if(xmlr.getLocalName().equals("distanza")){
                    distanza = Integer.parseInt(xmlr.getElementText());
                }
                else if(xmlr.getLocalName().equals("copie")){
                    creaCopie(armi, nome, distanza, xmlr);
                }
                xmlr.next();
            }
            else if(xmlr.getEventType() == XMLStreamConstants.END_ELEMENT){
                if(xmlr.getLocalName().equals("arma"))
                    controllo = false;
                else
                    xmlr.next();
            }
            else{
                xmlr.next();
            }
            
        }
        
    }

    private void creaCopie(ArrayList<Arma> armi, String nome, int distanza, XMLStreamReader xmlr) throws XMLStreamException {
        boolean controllo = true;
        String seme = "";
        String numero = "";

        while(controllo){
            if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT){
                if(xmlr.getLocalName().equals("valore")){
                    numero = xmlr.getElementText();
                }

                if(xmlr.getLocalName().equals("seme")){
                    seme = xmlr.getElementText();

                    armi.add(new Arma(nome, seme, numero, distanza));
                }
                xmlr.next();
            }
            else if(xmlr.getEventType() == XMLStreamConstants.END_ELEMENT){
                if(xmlr.getLocalName().equals("copie"))
                    controllo = false;
                else
                    xmlr.next();
            }
            else{
                xmlr.next();
            }
        }
    }

    private void creaCarta(ArrayList<Carta> carte, XMLStreamReader xmlr) throws XMLStreamException {
        boolean controllo = true;
        String nome = "";
        String descrizione = "";
        boolean equipaggiabile = xmlr.getAttributeValue(0).equals("true");

        while(controllo){
            if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT){
                if(xmlr.getLocalName().equals("nome")){
                    nome = xmlr.getElementText();
                }
                else if(xmlr.getLocalName().equals("descrizione")){
                    descrizione = xmlr.getElementText();
                }
                else if(xmlr.getLocalName().equals("copie")){
                    creaCopie(carte, nome, descrizione, equipaggiabile, xmlr);
                }
                xmlr.next();
            }
            else if(xmlr.getEventType() == XMLStreamConstants.END_ELEMENT){
                if(xmlr.getLocalName().equals("carta"))
                    controllo = false;
                else
                    xmlr.next();
            }
            else{
                xmlr.next();
            }
            
        }
    }

    private void creaCopie(ArrayList<Carta> carte, String nome, String descrizione, boolean equipaggiabile, XMLStreamReader xmlr) throws XMLStreamException {
        boolean controllo = true;
        String seme = "";
        String numero = "";

        while(controllo){
            if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT){
                if(xmlr.getLocalName().equals("valore")){
                    numero = xmlr.getElementText();
                }

                if(xmlr.getLocalName().equals("seme")){
                    seme = xmlr.getElementText();

                    if(equipaggiabile){
                        carte.add(new Equipaggiabile(nome, seme, numero, descrizione));
                    }
                    else{
                        carte.add(new Giocabile(nome, seme, numero, descrizione));
                    }
                }
                xmlr.next();
            }
            else if(xmlr.getEventType() == XMLStreamConstants.END_ELEMENT){
                if(xmlr.getLocalName().equals("copie"))
                    controllo = false;
                else
                    xmlr.next();
            }
            else{
                xmlr.next();
            }
        }
    }

    
    

}
