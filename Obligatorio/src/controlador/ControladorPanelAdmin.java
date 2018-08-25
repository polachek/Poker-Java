/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Administrador;
import modelo.Sistema;
import utilidades.Observable;
import utilidades.Observador;
import vista.IInicioAdministrador;

/**
 *
 * @author Polachek
 */
public class ControladorPanelAdmin implements Observador{
    private Sistema sistema = Sistema.getInstancia();
    private IInicioAdministrador vista;
    private Administrador admin;
    public ControladorPanelAdmin(IInicioAdministrador v, Administrador a) {
        this.vista=v;
        vista.cargarLuz(sistema.getApuestaBase());
        vista.cargarJugMesa(sistema.getJugPartida());
        sistema.agregar(this);
        admin =a;
    }
    
    public void cambiarCantJugMesa(Integer cantJugMesa){
        sistema.setCantidadJugadoresPorPartida(cantJugMesa);
    }
    
    public void cambiarLuz(Integer nuevaLuz){
        sistema.setApuestaBase(nuevaLuz);
    }
    
    protected Sistema getSistema(){
        return sistema;
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Sistema.Eventos.CAMBIO_LUZ)) {
            vista.cargarLuz(sistema.getApuestaBase());
        }
        if (evento.equals(Sistema.Eventos.CAMBIO_CANTJUGMESA)) {
            vista.cargarJugMesa(sistema.getJugPartida());
        }
    }
    
    public void logout(){
        sistema.logoutAdmin(admin);
    }
}
