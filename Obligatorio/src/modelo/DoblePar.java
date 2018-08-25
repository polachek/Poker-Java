/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.HashMap;

/**
 * @date 18-jun-2018
 * @time 19:27:43
 * @author Leonardo Pérez
 */
public class DoblePar extends Figura{

    public DoblePar() {
    }
    
    
    private DoblePar(String nombre, int valor, int valorDes) {
        super(nombre,valor, valorDes);
    }

    @Override
    public boolean esFigura(Carta[] cartas) {
         HashMap<String, Integer> repetidas = this.calcularRepetidos(cartas);
         int suma =0;
         String nombre ="";
         int contador =0;
        for(Carta c: cartas){
            if(repetidas.containsKey(c.getNumeroString()) && repetidas.get(c.getNumeroString())==2){
                //se le suma 1 al numero por que el 2 vale cero.
                suma +=( c.getNumero() + 1)*40;//
                
                nombre+=c.getNumeroString();
                 contador++;       
            }else{
                this.setValorDesempate(c.getNumero());
            }
            
        } 
        if(contador==4){
            this.setValor(suma);
            this.setNombre(nombre);
            return true;
        }
        return false;
    }

    @Override
    public Figura obtenerCopia() {
        DoblePar copia = new DoblePar(this.getNombre(), this.getValor(), this.getValorDesempate());
        return copia;
    }

}
