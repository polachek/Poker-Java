/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Apuesta;
import modelo.Juego;
import modelo.Participante;
import modelo.Sistema;
import utilidades.Observable;
import utilidades.Observador;
import vista.IVisorPartida;

/**
 *
 * @author Guillermo Polachek
 */
public class CntlrVisorPartida implements Observador{
    private Sistema sistema = Sistema.getInstancia();
    private IVisorPartida vista;
    private Juego miJuego;
    
    public CntlrVisorPartida(IVisorPartida v, Juego juego) {
        this.vista=v;
        miJuego = juego;
        miJuego.agregar(this);
    }
    
    public void cargarDatosJuego(Juego miJuego) {
        String FechaIni = miJuego.getFechaIncio().toString();
        int juegoCantJug = miJuego.getCantidadParticipantes();
        int juegoCantManos = miJuego.getCantManosJugadas();
        int juegoTotalApostado = miJuego.getPozo().getValor();
        int juegoApuestaLuz = miJuego.getApuestaBase();
        vista.cargarDatosJuego(FechaIni,juegoCantJug,juegoCantManos,juegoTotalApostado,juegoApuestaLuz);
    }
    
    public void limpiaTabJug(){
        vista.limpiTabJug();
    }

    public void cargarJugadores() {
        for (Participante p : miJuego.getParticipantesActuales()) {
            vista.cargarJugador(p.getNombreCompletoDelJugador(),p.getSaldoInicial(),p.getSaldoFinal(),p.getTotalApostado());
        }
    }
    
    public void updateCantManos() {
        vista.updateCantManos(miJuego.getCantManosJugadas());
    }
    
    private void updateCantJugadores() {
        vista.updateCantJug(miJuego.getCantidadParticipantes());
    }
    
    private void updatePozo() {
        vista.updatePozo(miJuego.getPozo().getValor());
    }
    
    private void updatePartida() {
        if(!sistema.cargarPartidasCurso().contains(miJuego))
            vista.partidaFinalizada();
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Juego.Eventos.NUEVA_MANO)) {
            updateCantManos();
        }
        if (evento.equals(Juego.Eventos.JUGADOR_RETIRADO_PARTIDA)) {
            updateCantJugadores();
        }        
        if (evento.equals(Juego.Eventos.APUESTA_REALIZADA)||evento.equals(Juego.Eventos.TERMINARON_LAS_APUESTAS)) {
            limpiaTabJug();
            cargarJugadores();
        }
        if (evento.equals(Juego.Eventos.CAMBIO_POZO)) {
            updatePozo();
        }
        if (evento.equals(Juego.Eventos.TERMINO_PARTIDA)) {
            updatePartida();
        }      
  
    }
}
