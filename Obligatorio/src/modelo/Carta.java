/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author leo
 */
public class Carta {

    private Numero numero;
    private Palo palo;

    public void setNumero(Numero numero) {
        this.numero = numero;
    }

    public void setPalo(Palo palo) {
        this.palo = palo;
    }

    boolean isMayor(Carta c) {
        if (this.getNumero() == c.getNumero()) {
            return this.getPalo() > c.getPalo();

        }

        return this.getNumero() > c.getNumero();
    }

    public enum Numero {
        DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, J, Q, K, A
    };

    public enum Palo {
        PIQUE, TREBOL, DIAMANTE, CORAZON
    }

    public int getNumero() {
        return numero.ordinal();
    }

    public int getPalo() {
        return palo.ordinal();
    }
    
    public String getPaloString() {
        return palo.toString();
    }
    
    public String getNumeroString(){
        return numero.toString();
    }
    @Override//va para pruebas
    public String toString() {
        return "Numero" + this.numero + " Palo " + this.palo;
    }

}
