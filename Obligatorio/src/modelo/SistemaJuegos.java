/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Persistencia.Persistencia;
import java.util.ArrayList;
import mapeadores.MapeadorJuego;
import mapeadores.MapeadorParticipante;

/**
 *
 * @author leo
 */
public class SistemaJuegos {

    private ArrayList<Juego> juegos = new ArrayList<>();
    //private ArrayList<Juego> juegos; //= cargarJuegos();
    private Juego proximoJuego;
    private int cantidadJugadoresPorPartida;
    private int apuestaBase;
    private int timeOut;
    private Sistema sistema;
    
    protected SistemaJuegos(){
        //juegos = cargarJuegos();
        
    }
    //Preguntar si esto va asi.
    public Juego getProximoJuego() {
        return proximoJuego;
    }

    protected int getTimeOut() {
        return timeOut;
    }

    protected void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
    
    public void setProximoJuego(Juego proximoJuego) {
        this.proximoJuego = proximoJuego;
    }

    public int getCantidadJugadoresPorPartida() {
        return cantidadJugadoresPorPartida;
    }

    public void setCantidadJugadoresPorPartida(int cantidadJugadoresPorPartida) {
        this.cantidadJugadoresPorPartida = cantidadJugadoresPorPartida;
        if (proximoJuego != null) {
            proximoJuego.cambioCantJug(cantidadJugadoresPorPartida);
        }
        Sistema.getInstancia().avisar(Sistema.Eventos.CAMBIO_CANTJUGMESA);
    }

    public int getApuestaBase() {
        return apuestaBase;
    }

    public void setApuestaBase(int apuestaBase) {
        this.apuestaBase = apuestaBase;
        if (proximoJuego != null) {
            proximoJuego.cambiarLuz(apuestaBase);
        }
        Sistema.getInstancia().avisar(Sistema.Eventos.CAMBIO_LUZ);
    }

    protected Participante buscarParticipacion(Jugador jugador) throws AplicationException {

        Participante p = proximoJuego.iniciar(jugador);
        if (proximoJuego.tieneTodosLosParticipantes()) {

            juegos.add(proximoJuego);
            proximoJuego = crearJuego();
            Sistema.getInstancia().avisar(Sistema.Eventos.CAMBIO_LISTA_JUEGOS);
        }
        return p;
    }

    protected Juego crearJuego() {
        return new Juego(apuestaBase, cantidadJugadoresPorPartida, timeOut);
    }

    protected ArrayList<Juego> juegosActivos() {
       ArrayList<Juego> lista = new ArrayList<>();
       for(Juego j: juegos){
           if(j.isEnCurso()){
              lista.add(j);
           }
        }
       
       return lista;
        //return juegos; // Para retornar Lista completa
    }

    private ArrayList<Juego> cargarJuegos() {

        //Sistema sis = Sistema.getInstancia();

        Persistencia p = Persistencia.getInstancia();
        MapeadorJuego mj = new MapeadorJuego();
        MapeadorParticipante mp = new MapeadorParticipante();
        //Cargo los juegos
        juegos = p.buscar(mj, null);
        if (juegos != null) {
            ArrayList<Participante> participantes;
            //Cargo los participantes
            Jugador jugador;
            for (Juego j : juegos) {
                j.setEnCurso(false);
                participantes = p.buscar(mp, null);
                j.setParticipantesActuales(participantes);//lo cargo en actuales para no modificar la vista.
                for (Participante part : j.getParticipantesActuales()) {
                    jugador =sistema.buscarJugador(part.getOid_Jugador());
                    part.setJugador(jugador);//Cargo el jugador corespondiente a cada participante.
                    part.setJuego(j);
                }
            }
        }else{
            juegos = new ArrayList<>();
        }

        return juegos;
    }
    
    protected void cargarJuegos2(){
        Persistencia p = Persistencia.getInstancia();
        MapeadorJuego mj = new MapeadorJuego();
        MapeadorParticipante mp = new MapeadorParticipante();
        Sistema sis = Sistema.getInstancia();
        //Cargo los juegos
        juegos = p.buscar(mj, null);
        if (juegos != null) {
            ArrayList<Participante> participantes;
            //Cargo los participantes
            Jugador jugador;
            for (Juego j : juegos) {
                j.setEnCurso(false);
                participantes = p.buscar(mp, "oid_juego=" + j.getOid());
                j.setParticipantesActuales(participantes);//lo cargo en actuales para no modificar la vista.
                for (Participante part : j.getParticipantesActuales()) {
                    jugador =sis.buscarJugador(part.getOid_Jugador());
                    part.setJugador(jugador);//Cargo el jugador corespondiente a cada participante.
                    part.setJuego(j);
                }
            }
        }else{
            juegos = new ArrayList<>();
        }

    }
}
