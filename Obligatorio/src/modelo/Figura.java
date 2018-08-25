/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @date 11-jun-2018
 * @time 17:41:21
 * @author Leonardo Pérez
 */
public abstract class Figura {

    private String nombre;
    private int valor;
    private int valorDesempate;

    public Figura() {
        
    }
    
    public Figura(String nombre, int valor, int valorDesempate){
        this.nombre =nombre;
        this.valor = valor;
        this.valorDesempate = valorDesempate;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValorDesempate() {
        return valorDesempate;
    }

    public void setValorDesempate(int valorDesempate) {
        this.valorDesempate = valorDesempate;
    }
    
    //Se usa en CArtaAlta y en color.
    public Carta getCartaAlta(Carta[] cartas){
        Carta alta = cartas[0];
        for(int i=1; i < cartas.length;i++){
            if(!alta.isMayor(cartas[i])){
                alta = cartas[i];
            }
        }
        return alta;
    }
    public abstract boolean esFigura(Carta[] cartas);
    public abstract Figura obtenerCopia();
    public HashMap<String,Integer> calcularRepetidos(Carta[] cartas){
        
        //En string guardo el numero, y en el entero la cantidad de repeticiones.
        HashMap<String,Integer> repetidos =new HashMap<String, Integer>();
        for(int j=0; j<cartas.length;j++){
            int repetida =1;
            for(int i=j+1; i <= cartas.length-1; i++){
                if(cartas[j].getNumero() == cartas[i].getNumero()){
                    repetida++;
                    if(repetidos.containsKey(cartas[j].getNumeroString())){
                        repetidos.replace(cartas[j].getNumeroString(), repetida);
                    }else{
                        repetidos.put(cartas[j].getNumeroString(), repetida);
                    }
                    
                }
            }
        }
        return repetidos;
    }

}
