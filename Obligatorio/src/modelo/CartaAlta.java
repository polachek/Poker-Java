/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;

/**
 * @date 11-jun-2018
 * @time 18:07:01
 * @author Leonardo Pérez
 */
public class CartaAlta extends Figura {

    public CartaAlta() {
    }
    
    
    private CartaAlta(String nombre, int valor, int valorDesempate){
        super(nombre, valor, valorDesempate);
    }
    @Override
    public boolean esFigura(Carta[] cartas) {
        HashMap<String, Integer> repetidos = this.calcularRepetidos(cartas);
        if (repetidos.isEmpty()) {
            Carta alta =this.getCartaAlta(cartas); //cartas[0];
//            for (int i = 1; i < cartas.length; i++) {
//                if (!alta.isMayor(cartas[i])) {
//                    alta = cartas[i];
//                }
//            }
            this.setNombre(alta.getNumeroString());
            this.setValor(alta.getNumero());
            this.setValorDesempate(alta.getPalo());//Deseempata por el palo.
            return true;
        }
        return false;
    }

    @Override
    public Figura obtenerCopia() {
        CartaAlta copia = new CartaAlta(this.getNombre(), this.getValor(), this.getValorDesempate());
        return copia;
    }

}
