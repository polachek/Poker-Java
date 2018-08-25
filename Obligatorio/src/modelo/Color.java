/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;

/**
 * @date 23-jun-2018
 * @time 6:25:44
 * @author Leonardo Pérez
 */
public class Color extends Figura {

    public Color() {
    }
    
    
    public Color(String nombre, int valor, int valorDes) {
        super(nombre, valor, valorDes);
    }
    
    
    @Override
    public boolean esFigura(Carta[] cartas) {
        HashMap<String, Integer> repetidas = this.calcularRepetidos(cartas);
        if (repetidas.isEmpty()) {
            return chequearColor(cartas);
        } else {
            return false;
        }
    }

    private boolean chequearColor(Carta[] cartas) {

        boolean color = true;
        int palo = cartas[0].getPalo();
        for (int i = 1; i < cartas.length; i++) {
            if (palo != cartas[i].getPalo()) {
                color = false;
            }
        }
        if (color) {
            String nombrePalo = cartas[0].getPaloString();
            int valorPalo =0;
            switch(nombrePalo){
                case "TREBOL":
                    valorPalo = 10000;
                    break;
                case "DIAMANTE":
                    valorPalo = 20000;
                    break;
                case "CORAZON":
                    valorPalo = 30000;
                    break;
            }
            Carta alta = this.getCartaAlta(cartas);
            this.setNombre(alta.getNumeroString());
            this.setValor(alta.getNumero() * 1000 + valorPalo);
            this.setValorDesempate(valorPalo);
        }
        return color;
    }

    @Override
    public Figura obtenerCopia() {
        Color copia = new Color(this.getNombre(), this.getValor(), this.getValorDesempate());
        return copia;
    }

}
