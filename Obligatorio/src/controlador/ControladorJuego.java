/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.AplicationException;
import modelo.Apuesta;
//import java.util.Observer;
import utilidades.Observador;
import modelo.Juego;
import modelo.Participante;
import utilidades.MiLogger;
import vista.IVistaJuego;

/**
 *
 * @author leo
 */
public class ControladorJuego implements Observador {

    private Participante participante;
    private Juego juego;
    private IVistaJuego vista;

    public ControladorJuego(Participante participante, IVistaJuego vista) {
        this.participante = participante;
        this.vista = vista;
        this.juego = participante.getJuego();
        //juego.addObserver(this);
        juego.agregar(this);
        vista.mostrarSaludo(this.participante.getNombreCompletoDelJugador());
        actualizarCartas();
        actualizarSaldoYPozo();
    }

    private void actualizarCartas() {
        if (participante.getCartas() != null) {
            vista.actualizarCartas(participante.getCartas());
            vista.mostrarNombreFigura(participante.getMisCartas());
        }
    }

    @Override
    public void actualizar(Object evento, utilidades.Observable origen) {
        if (evento.equals(Juego.Eventos.CARTAS_REPARTIDAS)) {
            actualizarCartas();
            //mandarLimpiarCampos();
            limpiarVista();
            actualizarSaldoYPozo();

        }
        if (evento.equals(Juego.Eventos.APUESTA_REALIZADA)) {
            avisarQueHuboApuesta();
        }
        if (evento.equals(Juego.Eventos.PASARON_TODOS)) {
            vista.mostrarMensajeError("Todos los jugadores pasaron, comienza una nueva mano");
            actualizarSaldoYPozo();
        }
        if (evento.equals(Juego.Eventos.TERMINARON_LAS_APUESTAS)) {
            //vista.mostrarMensajeError("llega evento");
            actualizarSaldoYPozo();
            //limpiarVista();
            solicitarGanador();
        }
        if (evento.equals(Juego.Eventos.JUGADOR_PASA)) {

                ArrayList<Participante> losQuepasan = this.juego.getParticipantesQuePasanManoActual();

                vista.mostrarLosQuePasaron(losQuepasan);

           
        }
        if (evento.equals(Juego.Eventos.JUGADOR_RETIRADO_MANO)) {
            try {
                ArrayList<Participante> losQueSeFueron = this.juego.mostrarLosQueDejanLaManoActual();
                vista.mostrarLosQueDejanLamano(losQueSeFueron);
            } catch (Exception e) {
                vista.mostrarMensajeError(e.getMessage());
                MiLogger.loggearError(e);

            }
        }
        if (evento.equals(Juego.Eventos.JUGADOR_RETIRADO_PARTIDA)) {
            //vista.mostrarMensajeError("RetiradoPartida");
            actualizarJugadoresPartida();
        }
        if (evento.equals(Juego.Eventos.TERMINO_PARTIDA)) {
            if (juego.getGanadorPartida() != null && juego.getGanadorPartida().equals(participante)) {
                vista.felicitarGanadorPartida(participante);
                //vista.mostrarMensajeError("Ganaste");
                juego.quitar(this);
            }
        }
        if(evento.equals(Juego.Eventos.PASO_UN_SEGUNDO)){
            int segundos =this.juego.getContadorSegundos();
            String mensaje;
            if(this.participante.getEstado().equals(Participante.Estado.YA_APOSTO)){//El primero en apostar.
                mensaje ="Realizaste una apuesta, los demas tienen " + segundos  + " segundos para pagar";
                vista.actualizarContador(mensaje );
            }else{
                mensaje ="Realizaron una apuesta, tienes :" + segundos + " para decidir";
                vista.actualizarContador(mensaje);
            }
        }
    }

    public void apostar(int valorApuesta) {

        try {

            participante.apostar(valorApuesta);
        } catch (Exception ex) {
            vista.mostrarMensajeError(ex.getMessage());
            MiLogger.loggearError(ex);
        }
    }

    private void avisarQueHuboApuesta() {
        Apuesta ultima = this.juego.getManoActual().getUltimaApuesta();
        if (!ultima.getParticipante().equals(this.participante)) {
            vista.avisarQueHuboApuesta(ultima.getValor(), ultima.getParticipante().getNombreCompletoDelJugador());

        } else {
            vista.avisarQueHuboApuesta(ultima.getValor(), "");
        }
        actualizarSaldoYPozo();
    }

    private void actualizarSaldoYPozo() {
        vista.mostrarSaldo(this.participante.getSaldoActual());
        vista.mostrarPozo(juego.getPozoManoActual());
    }

    public void pagarApuesta() {
        try {
            this.participante.pagar();
        } catch (AplicationException ex) {
            vista.mostrarMensajeError(ex.getMessage());
            MiLogger.loggearError(ex);
        }
    }

    public void pasar() {
        try {
            this.participante.participantePasa();
        } catch (Exception ex) {
            vista.mostrarMensajeError(ex.getMessage());
            MiLogger.loggearError(ex);
        }
    }

    private void solicitarGanador() {
        //MiLogger.loggearError("Entra evento");
        try {
            Participante p;
            if (juego.sigueEnPartida(participante)) {
                p = juego.getGanadorManoActual();
                if(p!=null){
                    vista.mostrarGanador(p);
                    //Estoy en el controlador, acá debería llamar a
                    // vista.mostrarLomodeLasCartasYDesactivarBotones() ??
                }
                
            }else{
                
                vista.seTerminoMiPartida("no sigues", "Se termino tu partida");
            }

        } catch (Exception e) {
            vista.mostrarMensajeError(e.getMessage());
            MiLogger.loggearError(e);
        }

    }

    public void confirmar() throws AplicationException {
        actualizarSaldoYPozo();//va aca??
        this.juego.confirmar(participante);

    }

    private void limpiarVista() {
        vista.limpiar();
    }

    public void dejarPartida() {
        try {
            this.juego.dejaPartida(participante);
            this.juego.quitar(this);
            vista.mostrarMensajeError("Has dejado la partida");
        } catch (AplicationException ex) {
            vista.mostrarMensajeError(ex.getMessage());
            
        }
    }

    public void retirar() {
        try {
            vista.mostrarLomodeLasCartasYDesactivarBotones();
            this.juego.retirar(this.participante);
        } catch (AplicationException ex) {
            vista.mostrarMensajeError(ex.getMessage());
            MiLogger.loggearError(ex);
        }
        
    }

//    private void mandarLimpiarCampos() {
//        
//    }
    private void actualizarJugadoresPartida() {
        if (!this.juego.getParticipantesActuales().contains(participante)) {
            vista.seTerminoMiPartida("No tienes saldo suficiente", "Se termino tu partida");
        }
    }

    public void salirJuego() {
        try {
            juego.dejaPartida(participante);
            
            juego.quitar(this);
        } catch (AplicationException ex) {
            
            vista.mostrarMensajeError(ex.getMessage());
        }
    }

}
