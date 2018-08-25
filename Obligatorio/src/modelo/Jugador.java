/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Persistencia.Persistencia;
import mapeadores.MapeadorJugador;

/**
 *
 * @author leo
 */
public class Jugador extends Usuario{
   
    private int saldo;
    
    public Jugador() {
        super();
    }
    
    
    public Jugador(int saldo, String nombreUsuario, String contrasena, String nombreCompleto) {
        super(nombreUsuario, contrasena, nombreCompleto);
        this.saldo = saldo;
       
    }
    
    

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
      
    }



    protected boolean tieneSaldoSuficiente(int valorApuesta) {
        return this.saldo > valorApuesta;
    }

    protected void descontarApuesta(int valorApuesta) {
        this.saldo-= valorApuesta;
        actualizarDatos();
    }

    void cobrarPozo(Pozo pozo) {
        this.saldo += pozo.getValor();
        actualizarDatos();
    }

    private void actualizarDatos() {
        MapeadorJugador mj = new MapeadorJugador(this);
        Persistencia.getInstancia().guardar(mj);
    }
        
}
