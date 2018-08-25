/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author leo
 */
public class Mazo {
    
    //private Carta[] cartas = new Carta[52];
    private List<Carta> cartas= new ArrayList();
    public Mazo(){
        armarMazo();
        Collections.shuffle(cartas);
    }
    //Creado para prueba.
    public Mazo(boolean prueba){
        armarMazo();
    }
    private void armarMazo(){
        Carta.Palo[] palos = Carta.Palo.values();
        Carta.Numero[] numeros = Carta.Numero.values();
        //int numeroEnMazo =0;
        for(int i =0; i < palos.length; i++){
            for(int j =0; j < numeros.length; j++){
                Carta c = new Carta();
                c.setNumero(numeros[j]);
                c.setPalo(palos[i]);
                cartas.add(c);
                //numeroEnMazo++;
            }
        }
    }

    Carta[] repartirCinco() {
        Carta[] cartasARepartir = new Carta[5];
        for(int i =0; i< cartasARepartir.length; i++){
            cartasARepartir[i] = this.cartas.get(i);
            this.cartas.remove(i);
        }
        return cartasARepartir;
    }
    //metodo para prueba.
    public Carta getCarta(int indice){
        return cartas.get(indice);
    }
}
