/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Persistencia.Persistencia;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapeadores.MapeadorJuego;
import mapeadores.MapeadorParticipante;
import utilidades.Observable;

/**
 *
 * @author leo
 */
public class Juego extends Observable implements Runnable {

    void addApuestaEnManoActual(Apuesta a) throws AplicationException {
        if (this.manoActual == null) {
            throw new AplicationException("La mano no ha comenzado aun!");
        }
        this.manoActual.addApuesta(a);
        //this.aumentarPozo(a.getValor());Lo saco de aca por que se estaba sumando dos veces, hay que revisar suma de pozos
        if (verificarSiTerminaronApuestas()) {//si terminan las apuestas define la mano en este metodo. 
            this.avisar(Eventos.TERMINARON_LAS_APUESTAS);
        } else {
            avisar(Eventos.APUESTA_REALIZADA);
            if (!hayCuentaRegresiva) {
                new Thread(this).start();
            }
        }
        avisarAlSistema();
    }

    boolean validarValorApuesta(int valorApuesta) {
        int saldoMenor = Integer.MAX_VALUE;
        for (Participante p : participantesActuales) {
            if (saldoMenor > p.getSaldoActual()) {
                saldoMenor = p.getSaldoActual();
            }
        }
        return saldoMenor > valorApuesta && valorApuesta > 0;
    }

//    void validarTurnoApuesta(Apuesta a) throws AplicationException {
//        this.manoActual.ValidarTurnoApuesta(a);
//    }
//    private void terminarManoActual() {
//       
//        this.manosJugadas.add(this.manoActual);
//        this.manoActual = null;
//        avisar(Eventos.NUEVA_MANO);
//    }
    private void comenzarMano(Pozo aux) throws AplicationException {
        guardaManoActualYAvisaQueComienzaMano();
        this.manoActual = new Mano(participantesActuales, this, aux);
        recolectarApuestasBase(apuestaBase);
        pedirQueRepartanCartas();

    }

    void verificarSiPasaronTodos() throws AplicationException {
        if (this.manoActual.pasaronTodos(this.cantidadJugadoresPorPartida)) {
            Pozo aux = this.manoActual.getPozo();
            int indiceUltimo = 0;
            ArrayList<Integer> temp = new ArrayList();
            for (Participante pa : participantesActuales) {
                try {
                    //pa.setEstado(Participante.Estado.SIN_APOSTAR);
                    revisarSiTieneSaldo(pa.getJugador());
                    indiceUltimo = participantesActuales.indexOf(pa);
                } catch (AplicationException a) {
                    temp.add(participantesActuales.indexOf(pa));
                }
            }

            if (temp.size() == this.cantidadJugadoresPorPartida - 1) {//Se termino el juego, queda solo 1 con saldo.
                this.aumentarPozo(aux.getValor());
                Participante ganador = participantesActuales.get(indiceUltimo);
                ganador.recibirPozo(aux);
                this.setUltimoGanador(ganador);
                eliminarLosQueNoPagaron(temp);
            } else {
                comenzarMano(aux);
                avisar(Juego.Eventos.PASARON_TODOS);

                avisarAlSistema();
            }
        }
    }

    boolean verificarSiTerminaronApuestas() throws AplicationException {
        if (this.manoActual.yaApostaronTodos()) {
            this.hayCuentaRegresiva = false;
            this.manoActual.definirMano();//Aumento el pozo del juego cuando se definela mano.
            guardarManoEnParticipantes();
            return true;
            // terminarManoActual();
//            comenzarMano();

        }

        return false;
    }

    public Participante getGanadorManoActual() {
        //return this.manoActual.getGanador();
//        Participante p = this.ultimaMano.getGanador();
//        if (p != null) {
//            this.aumentarPozo(this.ultimaMano.getValorDelPozo());
//        }
//        return p;
        return ultimoGanador;
    }

    private void guardarManoEnParticipantes() {
        for (Participante p : participantesActuales) {
            p.addMano(this.manoActual);
        }
    }

    public void confirmar(Participante p) throws AplicationException {
        this.confirmadosProximaMano++;
        //p.setEstado(Participante.Estado.SIN_APOSTAR);
        if (confirmadosProximaMano == this.getCantidadParticipantes()) {
            ArrayList<Integer> temp = new ArrayList();
            for (Participante pa : participantesActuales) {
                try {
                    pa.setEstado(Participante.Estado.SIN_APOSTAR);
                    revisarSiTieneSaldo(pa.getJugador());
                } catch (AplicationException a) {
                    temp.add(participantesActuales.indexOf(pa));
                }
            }
            if (temp.size() > 0) {
                eliminarLosQueNoPagaron(temp);
            }
            if (this.getCantidadParticipantes() != 1) {
                comenzarMano();
            } else {
                //verificarSiTerminoElJuego();
            }

        }
    }

    public void dejaPartida(Participante p) throws AplicationException {
        this.participantesActuales.remove(p);
        this.manoActual.retirar(p);
        this.cantidadJugadoresPorPartida--;
        this.confirmadosProximaMano--;
        if (verificarSiTerminaronApuestas()) {
            avisar(Eventos.TERMINARON_LAS_APUESTAS);
            //comenzarMano();
        }
        confirmar(null);
//        if(verificarSiTerminaronApuestas() ){
//            avisar(Eventos.TERMINARON_LAS_APUESTAS);
//        }
        verificarSiTerminoElJuego();
        this.avisar(Juego.Eventos.JUGADOR_RETIRADO_PARTIDA);
        avisarAlSistema();
    }

    public ArrayList<Participante> getParticipantesQuePasanManoActual() {
        return this.manoActual.getParticipantesQuePasan();
    }

    public void retirar(Participante participante) throws AplicationException {
        participante.setEstado(Participante.Estado.RETIRADO_MANO);
        this.manoActual.retirar(participante);//Aca avisa que se retira.
        if (verificarSiTerminaronApuestas()) {
            avisar(Eventos.TERMINARON_LAS_APUESTAS);
        }
//        if (manoActual.getCantidadParticipantes() == 1) {//Por si se retiran todos menos el que aposto;
//            manoActual.definirMano();
//            //terminarManoActual();
//
//            avisar(Juego.Eventos.TERMINARON_LAS_APUESTAS);
//            avisarAlSistema();
//            comenzarMano();
//        } else {
//            if (verificarSiTerminaronApuestas()) {
//                avisar(Juego.Eventos.TERMINARON_LAS_APUESTAS);
//                avisarAlSistema();
//                comenzarMano();
//            }
//
//        }
    }

    public ArrayList<Participante> mostrarLosQueDejanLaManoActual() throws AplicationException {
        ArrayList<Participante> losQueSeFueron = new ArrayList<>();
        for (Participante p : participantesActuales) {
            if (!this.manoActual.sigueJugando(p)) {
                losQueSeFueron.add(p);
            }
        }
        verificarSiTerminaronApuestas();
        return losQueSeFueron;
    }

    void eliminarLosQueNoPagaron(ArrayList<Integer> temp) {
        Participante p;
        ArrayList<Participante> aux = new ArrayList();
        for (Integer i : temp) {
            p = participantesActuales.get(i);
            aux.add(p);

        }
        if (aux.size() > 0) {
            for (Participante pa : aux) {
                participantesActuales.remove(pa);
                cantidadJugadoresPorPartida--;
            }
            avisar(Juego.Eventos.JUGADOR_RETIRADO_PARTIDA);
            avisarAlSistema();
        }
        verificarSiTerminoElJuego();
    }

    private void verificarSiTerminoElJuego() {
        if (cantidadJugadoresPorPartida == 1) {
            this.enCurso = false;
            Persistencia p = Persistencia.getInstancia();
            MapeadorJuego mj = new MapeadorJuego(this);
            MapeadorParticipante mp;
            p.guardar(mj);
            for (Participante part : participantesQueJugaron) {
                mp = new MapeadorParticipante(part);
                p.guardar(mp);
            }
            avisar(Juego.Eventos.TERMINO_PARTIDA);
            avisarAlSistema();

        }
    }

    public Participante getGanadorPartida() {
        if (cantidadJugadoresPorPartida == 1) {
            return participantesActuales.get(0);
        }
        return null;
    }

    public boolean sigueEnPartida(Participante participante) {
        return participantesActuales.contains(participante);
    }

    protected void avisarAlSistema() {
        sistema.avisar(Sistema.Eventos.CAMBIO_LISTA_JUEGOS);
    }

    @Override
    public void run() {
        this.contadorSegundos = timeOut;
        this.hayCuentaRegresiva = true;
        for (int x = 1; x <= timeOut && hayCuentaRegresiva; x++) {
            try {
                contadorSegundos--;
                Thread.sleep(1000);
                avisar(Eventos.PASO_UN_SEGUNDO);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

        }
        if (contadorSegundos == 0) {
            try {
                if (verificarSiTerminaronApuestas()) {
                    this.avisar(Eventos.TERMINARON_LAS_APUESTAS);
                } else {
                    //hay gente que no pago
                    ArrayList<Integer> temp = new ArrayList<>();
                    boolean yaAposto;
                    for (Participante p : participantesActuales) {
                        yaAposto = false;
                        Participante.Estado estadoActual = p.getEstado();
                        switch (estadoActual) {
                            case YA_APOSTO:
                            case YA_PAGO:
                                yaAposto = true;
                                break;
                            default:
                                break;
                        }
                        if (!yaAposto) {
                            temp.add(participantesActuales.indexOf(p));
                        }
                    }
                    this.manoActual.eliminarLosQueNoPagaron(temp);
                    this.avisar(Eventos.JUGADOR_RETIRADO_MANO);
                    if (this.manoActual.getCantidadParticipantes() == 1 && this.cantidadJugadoresPorPartida != 1) {
                        //Si queda uno en la mano pero no en el juego lo hago ganador de la mano.
                        this.manoActual.definirMano();
                        temp.clear();
                        for (Participante p : this.participantesActuales) {
                            try {
                                revisarSiTieneSaldo(p.getJugador());
                            } catch (AplicationException ap) {
                                temp.add(participantesActuales.indexOf(p));
                            }
                        }
                        if (temp.size() > 0) {
                            eliminarLosQueNoPagaron(temp);//aca verifica si termino el juego y si termino avisa el ganador.
                        }
                        if (cantidadJugadoresPorPartida != 1) {
                            resetearEstadoParticipantes();
                            comenzarMano();
                        }
                    } else {
                        if (this.manoActual.getCantidadParticipantes() != 1) {
                            this.manoActual.definirMano();
                            this.avisar(Eventos.TERMINARON_LAS_APUESTAS);
                            resetearEstadoParticipantes();
                        }
                        verificarSiTerminoElJuego();
                    }
                }
            } catch (AplicationException ex) {
                Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void resetearEstadoParticipantes() {
        for(Participante p: participantesActuales){
            p.setEstado(Participante.Estado.SIN_APOSTAR);
        }
    }

    public enum Eventos {
        COMIENZA_JUEGO, JUGADOR_AGREGADO, JUGADOR_RETIRADO_MANO, JUGADOR_RETIRADO_PARTIDA, JUGADOR_PASA, CARTAS_REPARTIDAS, APUESTA_REALIZADA,
        PASARON_TODOS, TERMINARON_LAS_APUESTAS, TERMINO_PARTIDA, NUEVA_MANO, CAMBIO_POZO, CAMBIOCANTJUG, PART_ABANDONA_ESPERA, PASO_UN_SEGUNDO;
    }
    private int oid;
    private Date fechaHoraInicio;
    private int apuestaBase;
    private int cantidadJugadoresPorPartida;
    private Pozo pozo;
    private ArrayList<Participante> participantesActuales = new ArrayList<>();
    private ArrayList<Participante> participantesQueJugaron;
    private ArrayList<Mano> manosJugadas = new ArrayList<>();
    private Mano manoActual;
    //private Mano ultimaMano;
    private Participante ultimoGanador;
    private boolean enCurso;
    int confirmadosProximaMano;
    private Sistema sistema = Sistema.getInstancia();
    private int cantidadManos = 0;
    private int oid_Ganador;
    private int contadorSegundos;
    private boolean hayCuentaRegresiva;
    private int timeOut;

    public Juego() {
    }

    public Juego(int apuestaBase, int cantidadJugadoresPorPartida, int timeOut) {
        this.apuestaBase = apuestaBase;
        this.cantidadJugadoresPorPartida = cantidadJugadoresPorPartida;
        this.pozo = new Pozo();
        this.timeOut = timeOut;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
    
    public int getContadorSegundos() {
        return contadorSegundos;
    }

    public void setContadorSegundos(int contadorSegundos) {
        this.contadorSegundos = contadorSegundos;
    }

    public boolean isHayCuentaRegresiva() {
        return hayCuentaRegresiva;
    }

    public void setHayCuentaRegresiva(boolean hayCuentaRegresiva) {
        this.hayCuentaRegresiva = hayCuentaRegresiva;
    }

    public int getOid_Ganador() {
        return oid_Ganador;
    }

    public void setOid_Ganador(int oid_Ganador) {
        this.oid_Ganador = oid_Ganador;
    }

    public void setParticipantesActuales(ArrayList<Participante> participantesActuales) {
        this.participantesActuales = participantesActuales;
    }

    public void setEnCurso(boolean enCurso) {
        this.enCurso = enCurso;
    }

    public void setCantidadManos(int cantidadManos) {
        this.cantidadManos = cantidadManos;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public void setUltimoGanador(Participante ultimoGanador) {
        this.ultimoGanador = ultimoGanador;
    }

    public Mano getManoActual() {
        return manoActual;
    }

    public Date getFechaIncio() {
        return fechaHoraInicio;
    }

    public int getCantManosJugadas() {
        if (manosJugadas.isEmpty()) {
            return cantidadManos;//Para cuando se lee desde la bd.
        }
        return manosJugadas.size();
    }

    public int getCantidadParticipantes() {
        return participantesActuales.size();
    }

    public ArrayList<Participante> getParticipantesActuales() {
        return participantesActuales;
    }

    public ArrayList<Participante> getParticipantesJugaron() {
        return participantesQueJugaron;
    }

    public int getApuestaBase() {
        return apuestaBase;
    }

    public void setApuestaBase(int apuestaBase) {
        this.apuestaBase = apuestaBase;
    }

    void cambiarLuz(int nuevaApuestaBase) {
        if (!enCurso) {
            if (participantesActuales.isEmpty()) {
                this.apuestaBase = nuevaApuestaBase;
            }
        }
    }

    void cambioCantJug(int nuevoCantJug) {
        if (!enCurso) {
            if (participantesActuales.size() <= nuevoCantJug) {
                this.cantidadJugadoresPorPartida = nuevoCantJug;
                avisar(Eventos.CAMBIOCANTJUG);
                try {
                    this.revisarSiCorrespondeiniciarJuego();
                } catch (AplicationException ex) {
                }
            }
        }
    }

    public int getCantidadJugadoresPorPartida() {
        return cantidadJugadoresPorPartida;
    }

    public void setCantidadJugadoresPorPartida(int cantidadJugadoresPorPartida) {
        this.cantidadJugadoresPorPartida = cantidadJugadoresPorPartida;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public int getPozoManoActual() {
        if (this.manoActual == null) {
            return 0;
        }
        return this.manoActual.getValorDelPozo();
    }

    private boolean esJugadorRepetido(Jugador j) {
        //Preguntar si hay que revisar los retirados.
        for (Participante p : participantesActuales) {
            if (p.getJugador().equals(j)) {
                return true;
            }
        }
        return false;
    }

    public boolean tieneTodosLosParticipantes() {
        return this.cantidadJugadoresPorPartida == this.participantesActuales.size();
    }

    public int getCuantosJugadoresFaltan() {
        return this.cantidadJugadoresPorPartida - this.participantesActuales.size();
    }

    private void agregarParticipante(Participante p) {
        participantesActuales.add(p);
        avisar(Eventos.JUGADOR_AGREGADO);
        avisarAlSistema();
    }

    protected Participante iniciar(Jugador jugador) throws AplicationException {
        revisarSiElJugadorYaIngresoAEsteJuego(jugador);
        revisarSiTieneSaldo(jugador);
        Participante p = new Participante(jugador, this);

        this.agregarParticipante(p);
        revisarSiCorrespondeiniciarJuego();

        return p;
    }

    public void revisarSiCorrespondeiniciarJuego() throws AplicationException {
        if (this.tieneTodosLosParticipantes()) {
            participantesQueJugaron = new ArrayList<>(participantesActuales);
            this.fechaHoraInicio = new Date();
            this.enCurso = true;
            comenzarMano();
            avisar(Eventos.COMIENZA_JUEGO);
            avisarAlSistema();
        }

    }

    private void comenzarMano() throws AplicationException {
        //Creo una nueva mano actual.
        this.confirmadosProximaMano = 0;

        guardaManoActualYAvisaQueComienzaMano();
        this.manoActual = new Mano(participantesActuales, this);
        //descuenta a los jugadores las apuestas base.
        recolectarApuestasBase(apuestaBase);
        pedirQueRepartanCartas();

    }

    private void guardaManoActualYAvisaQueComienzaMano() {
        if (this.manoActual != null) {
            this.manosJugadas.add(manoActual);
            avisar(Eventos.NUEVA_MANO);
        }
    }

    private void pedirQueRepartanCartas() {

        manoActual.pedirCartasAlMazo();
        avisar(Eventos.CARTAS_REPARTIDAS);
        avisarAlSistema();
    }

    /**
     * recorre la lista de participantes y llama al metodo aumentar pozo de la
     * clase mano. si el pozo no se pudo aumentar es por que el jugador no tiene
     * saldo, el jugador es removido de la lista de jugadores actuales.
     *
     * @param apuesta
     */
    protected void recolectarApuestasBase(int apuesta) throws AplicationException {
        int apuestas = this.manoActual.descontarApuestasBase(apuestaBase);
    }

    void aumentarPozo(int valor) {
        this.pozo.sumarApuestas(valor);
        avisar(Eventos.CAMBIO_POZO);
    }

    private void revisarSiElJugadorYaIngresoAEsteJuego(Jugador jugador) throws AplicationException {
        if (this.esJugadorRepetido(jugador)) {
            throw new AplicationException("El jugador ya esta registrado en el proximo juego a comenzar.");
        }
    }

    private void revisarSiTieneSaldo(Jugador jugador) throws AplicationException {
        int valorARevisar = this.apuestaBase;
        if (!jugador.tieneSaldoSuficiente(valorARevisar)) {
            throw new AplicationException("El jugador no tiene saldo suficiente para ingresar a la partida.");
        }
    }

    public void participanteAbandonaEspera(Participante miParticipante) {
        participantesActuales.remove(miParticipante);
        avisar(Eventos.PART_ABANDONA_ESPERA);
    }

}
