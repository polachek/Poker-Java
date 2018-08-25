/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

//import java.util.Observable;
//import java.util.Observer;
import modelo.AplicationException;
import utilidades.Observador;
import modelo.Juego;
import modelo.Participante;
import utilidades.Observable;
import vista.IVistaInicioJugador;


/**
 *
 * @author leo
 */
public class ControladorInicioJugador implements Observador{
    private IVistaInicioJugador vista;
    private Participante participante;
    private Juego juego;

    public ControladorInicioJugador(IVistaInicioJugador vista, Participante participante) throws AplicationException {
        this.vista = vista;
        this.participante = participante;
        this.juego = this.participante.getJuego();
        //juego.addObserver(this);
        juego.agregar(this);
        vista.mostrarSaludo(participante.getNombreCompletoDelJugador());
        mostrarCuantosJugadoresFaltan();
       // revisaSiInicia();
       verificarEstadoDelJuego();
    }
    
    private void verificarEstadoDelJuego() {
        if(this.juego.tieneTodosLosParticipantes()){
            iniciarJuego();
        }else{
            mostrarCuantosJugadoresFaltan();
        }
    }
    public void revisaSiInicia() throws AplicationException{
        juego.revisarSiCorrespondeiniciarJuego(); 
    }


    private void iniciarJuego() {
        vista.iniciarJuego(participante);
        //juego.deleteObserverthis);
        juego.quitar(this);
    }

    private void mostrarCuantosJugadoresFaltan() {
        vista.actualizarCuantosFaltan(juego.getCuantosJugadoresFaltan());
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
       if(evento.equals(Juego.Eventos.JUGADOR_AGREGADO)){
              mostrarCuantosJugadoresFaltan();
             // verificarEstadoDelJuego();
        }
        if(evento.equals(Juego.Eventos.COMIENZA_JUEGO)){
            iniciarJuego();
        }
        if(evento.equals(Juego.Eventos.CAMBIOCANTJUG)){
              mostrarCuantosJugadoresFaltan();
             // verificarEstadoDelJuego();
        }
        if(evento.equals(Juego.Eventos.PART_ABANDONA_ESPERA)){
             mostrarCuantosJugadoresFaltan();
        }
    }

    public void abandonarEspera() {
        juego.participanteAbandonaEspera(participante);
        juego.quitar(this);
    }
    
    
    
    
}
