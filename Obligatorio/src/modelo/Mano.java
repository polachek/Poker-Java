/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author leo
 */
public class Mano {

    private ArrayList<Participante> participantes;
    private Pozo pozo;
    private Mazo elMazo;
    private Juego elJuego;
    private ArrayList<Apuesta> apuestas = new ArrayList<>();
    private Apuesta ultimaApuesta;
    private int contadorDeLosQuePasan;
    private Participante ganador;

    public Mano(ArrayList<Participante> participantes, Juego juego) {
        this.participantes = new ArrayList<Participante>(participantes);
        this.pozo = new Pozo();
        this.elMazo = new Mazo();
        this.elJuego = juego;
        this.contadorDeLosQuePasan = 0;
    }

    public Mano(ArrayList<Participante> participantesActuales, Juego juego, Pozo p) {
        this(participantesActuales, juego);
        this.pozo.sumarApuestas(p.getValor());
    }

//    public Mano(){
//        this.pozo = new Pozo();
//        this.elMazo = new Mazo();
//    }
    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    public void addApuesta(Apuesta a) {
        this.apuestas.add(a);
        this.ultimaApuesta = a;
        //this.elJuego.aumentarPozo(a.getValor());
        this.pozo.sumarApuestas(a.getValor());
    }

    public Apuesta getUltimaApuesta() {
        return ultimaApuesta;
    }

    public int getCantidadParticipantes() {
        return participantes.size();
    }

    /**
     * Descuenta la apuesta base de cada jugador de la mano si no tiene saldo,
     * lo saca de la lista de jugadores de la mano.
     *
     * @param apuestaBase
     * @return el total de haber descontado la apuesta base a cada jugador.
     * @throws AplicationException
     */
    int descontarApuestasBase(int apuestaBase) throws AplicationException {
        int apuestaTotalActual = 0;
        ArrayList<Integer> temp = new ArrayList();//Guarda los indices de los que no pagan la apuesta.
        for (Participante p : participantes) {
            Apuesta apuesta = p.descontarApuesta(apuestaBase);
            if (apuesta == null) {//Si no puede pagar la apuesta
                temp.add(participantes.indexOf(p));//guardo el indice para borrarlo de esta mano.
            } else {
                apuestaTotalActual += apuesta.getValor();
                addApuesta(apuesta);//guardo la apuesta en la lista de apuestas y aumenta el pozo de la mano.
                p.setEstado(Participante.Estado.APOSTO_LUZ);
            }
        }
        if (temp.size() > 0) {
            eliminarLosQueNoPagaron(temp);
        }
        //Aumenta el pozo general de la mano.
        //this.pozo.sumarApuestas(apuestaTotalActual);
        return apuestaTotalActual;
    }

    void pedirCartasAlMazo() {
        for (Participante p : participantes) {
            p.setCartas(this.elMazo.repartirCinco());
        }
    }

    int getValorDelPozo() {
        return pozo.getValor();
    }

    protected void eliminarLosQueNoPagaron(ArrayList<Integer> temp) {
        ArrayList<Participante> aux = new ArrayList();
        Participante p;
        for (Integer i : temp) {
            p = participantes.get(i);
            aux.add(p);

        }
        if (aux.size() > 0) {
            for (Participante pa : aux) {
                participantes.remove(pa);

            }

        }
        //elJuego.eliminarLosQueNoPagaron(temp);

    }

    void ValidarTurnoApuesta(Apuesta a) throws AplicationException {
        int contador = 0;
        for (Apuesta apuesta : apuestas) {
            if (apuesta.getParticipante().equals(a.getParticipante())) {
                contador++;
            }
            if (contador == 2) {
                throw new AplicationException("Ya cumplio su turno de apuestas");
            }
        }

    }

    void participantePasa(Participante participante) throws AplicationException {
        verificarSiYaHuboApuesta();
        if (!participante.getEstado().equals(Participante.Estado.PASO)) {//esto va en participante.
            participante.setEstado(Participante.Estado.PASO);
            this.contadorDeLosQuePasan++;
            this.elJuego.avisar(Juego.Eventos.JUGADOR_PASA);
            this.elJuego.avisarAlSistema();
        } else {
            throw new AplicationException("Ya ha pasado una apuesta");
        }

    }

    boolean pasaronTodos(int cantidadJugadoresPorPartida) {
        return this.contadorDeLosQuePasan == cantidadJugadoresPorPartida;
    }

    boolean yaApostaronTodos() {
        boolean yaApostaron = true;
        for (Participante p : participantes) {
            Participante.Estado estadoActual = p.getEstado();
            switch (estadoActual) {
                case APOSTO_LUZ:
                case PASO:
                case SIN_APOSTAR:
                    yaApostaron = false;
                    break;
                default:
                    break;
            }
        }
        return yaApostaron;
    }

    Participante getGanador() {

        return ganador;
    }

    //agregar atributo ganador y calcularlo cuando termina la mano.
    void definirMano() {
        if (this.ganador == null) {
            Participante ganador = participantes.get(0);
            for (int i = 1; i < participantes.size(); i++) {
                Participante competidor = participantes.get(i);
                Figura fc = competidor.getMisCartas();
                Figura fg = ganador.getMisCartas();
                if (fc.getValor() == fg.getValor()) {
                    if (fc.getValorDesempate() > fg.getValorDesempate()) {
                        ganador = participantes.get(i);
                    }
                } else if (fc.getValor() > fg.getValor()) {
                    ganador = participantes.get(i);
                }

            }
            this.ganador = ganador;
            this.ganador.recibirPozo(this.pozo);
            this.elJuego.aumentarPozo(this.pozo.getValor());
            this.elJuego.setUltimoGanador(ganador);

        }
    }

    private void verificarSiYaHuboApuesta() throws AplicationException {
        for (Participante p : participantes) {
            if (p.getEstado().equals(Participante.Estado.YA_APOSTO)) {//esto va en  participante
                throw new AplicationException("Ya se realizo una apuesta debe pagar o retirarse");
            }
        }
    }

    ArrayList<Participante> getParticipantesQuePasan() {
        ArrayList<Participante> losQuePasan = new ArrayList();
        for (Participante p : participantes) {
            if (p.getEstado().equals(Participante.Estado.PASO)) {
                losQuePasan.add(p);
            }
        }
        return losQuePasan;
    }

    void retirar(Participante participante) {
        this.participantes.remove(participante);
        if (this.getCantidadParticipantes() == 1) {
            definirMano();//para aumentarle el pozo al ultimo
            // elJuego.avisar(Juego.Eventos.TERMINARON_LAS_APUESTAS);
        } else {
            //this.elJuego.avisar(Juego.Eventos.JUGADOR_RETIRADO_MANO);
        }

        this.elJuego.avisarAlSistema();
    }

    boolean sigueJugando(Participante p) {
        return participantes.contains(p);
    }
}
