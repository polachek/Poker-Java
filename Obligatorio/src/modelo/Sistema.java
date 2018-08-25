/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import utilidades.Observable;

/**
 *
 * @author leo
 */
public class Sistema extends Observable{

    private static Sistema instancia;
    
    private SistemaUsuarios sistemaUsuarios = new SistemaUsuarios();
    private SistemaJuegos sistemaJuegos = new SistemaJuegos();
    
    private Sistema(){
        sistemaUsuarios = new SistemaUsuarios();
        sistemaJuegos = new SistemaJuegos();
    }
    public static Sistema getInstancia(){
        if(instancia == null){
            instancia = new Sistema();
        }
        return instancia;
    }

    public void setTimeOut(int timeOut) {
        sistemaJuegos.setTimeOut(timeOut);
    }

    
    public void logoutAdmin(Administrador admin) {
        sistemaUsuarios.logout(admin);
    }

    public void cargarDatos() {
        sistemaUsuarios.cargarJugadores3();
        sistemaJuegos.cargarJuegos2();
        
    }
    
    public enum Eventos {
        CAMBIO_LUZ, CAMBIO_CANTJUGMESA, CAMBIO_LISTA_JUEGOS;
    }

    public Participante login(String nombre, String pass) throws AplicationException {
        return sistemaUsuarios.loginJugador(nombre, pass);
    }
    
    public Administrador loginAdmin(String nombre, String pass) throws AplicationException {
        return sistemaUsuarios.loginAdmin(nombre, pass);
    }

    protected Participante buscarParticipacion(Jugador j) throws AplicationException {
        return sistemaJuegos.buscarParticipacion(j);
    }

    public void addJugador(Jugador j) {
        sistemaUsuarios.addJugador(j);
    }
    
    public void addAdmin(Administrador a) {
        sistemaUsuarios.addAdmin(a);
    }
    
    public int getApuestaBase(){
        return sistemaJuegos.getApuestaBase();
    }
    
    public int getJugPartida(){
        return sistemaJuegos.getCantidadJugadoresPorPartida();
    }
    
/*Preguntar si esto va*/
    public void setProximoJuego(Juego proximoJuego) {
        sistemaJuegos.setProximoJuego(proximoJuego);
    }

    public void setCantidadJugadoresPorPartida(int cantidadJugadoresPorPartida) {
        sistemaJuegos.setCantidadJugadoresPorPartida(cantidadJugadoresPorPartida);
    }

    public void setApuestaBase(int apuestaBase) {
        sistemaJuegos.setApuestaBase(apuestaBase);
    }
    
    
    /**/
    
    public ArrayList<Juego> cargarPartidasCurso(){
        return sistemaJuegos.juegosActivos();
    }

    public Juego crearJuego() {
        return sistemaJuegos.crearJuego();
    }

    protected ArrayList<Jugador> getJugadores() {
        return sistemaUsuarios.getJugadores();
    }

    protected Jugador buscarJugador(int oid) {
        return sistemaUsuarios.buscarJugador(oid);
    }
    
    
}
