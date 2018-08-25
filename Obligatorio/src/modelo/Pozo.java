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
public class Pozo {
    private int valor;
    
    public Pozo(){
        this.valor=0;
    }
    public Pozo(int valor){
        this.valor=valor;
    }
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

     void sumarApuestas(int apuestaInicial) {
        this.valor += apuestaInicial;
    }
    
}
