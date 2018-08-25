/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.HashMap;

/**
 * @date 16-jun-2018
 * @time 5:35:05
 * @author Leonardo Pérez
 */
public class Par extends Figura{

    public Par() {
    }
    
    public Par(String nombre, int valor, int valorDes) {
        super(nombre, valor, valorDes);
    }
    
    
    @Override
    public boolean esFigura(Carta[] cartas) {
        HashMap<String, Integer> repetidas = this.calcularRepetidos(cartas);
        for(Carta c: cartas){
            if(repetidas.containsKey(c.getNumeroString()) && repetidas.get(c.getNumeroString())==2){
                //se le suma 1 al numero por que el 2 vale cero.
                int valor =( c.getNumero() + 1)*20;//Multiplico por 20 para que el valor de los pares bajos no sea menor al del As(12)
                this.setValor(valor);
                this.setNombre(c.getNumeroString());
                return true;
            }
        } 
        return false;
    }

    @Override
    public Figura obtenerCopia() {
        Par copia = new Par(this.getNombre(), this.getValor(), this.getValorDesempate());
        return copia;
    }

}
