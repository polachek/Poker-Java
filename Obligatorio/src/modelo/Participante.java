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
public class Participante {

    private void verificarEstadoParaApuestas() throws AplicationException {
        if (this.estado.equals(Estado.YA_PAGO) || this.estado.equals(Estado.YA_APOSTO)) {
            throw new AplicationException("Ya realizo la cantidad de apuestas permitidas");
        }
    }

    private void descontarSaldoFinal(Apuesta a) {
        this.saldoFinal -= a.getValor();
    }

    void recibirPozo(Pozo pozo) {
        this.saldoFinal += pozo.getValor();
        this.jugador.cobrarPozo(pozo);
    }

    void addMano(Mano manoActual) {
        this.ultimaMano = manoActual;
        this.manos.add(manoActual);
    }

    public enum Estado {
        SIN_APOSTAR, APOSTO_LUZ, YA_APOSTO, YA_PAGO, PASO, RETIRADO_MANO, RETIRADO_PARTIDA;
    }

    private Jugador jugador;
    private Juego juego;
    private Carta[] cartas;
    private ArrayList<Apuesta> pagadas = new ArrayList<>();
    private ArrayList<Apuesta> realizadas = new ArrayList<>();
    private ArrayList<Mano> manos = new ArrayList<>();
    private int saldoInicial;
    private int saldoFinal;
    private Estado estado;
    private Mano ultimaMano;
    //para levantar datos de bd.
    private int oid;
    private int oid_Juego;
    private int oid_Jugador;
    private int totalApostado;

    public Participante() {
    }

    public Participante(Jugador jugador, Juego juego) {
        this.jugador = jugador;
        this.juego = juego;
        this.saldoInicial = jugador.getSaldo();
        this.estado = Estado.SIN_APOSTAR;

    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
    
    
    public void setTotalApostado(int totalApostado) {
        this.totalApostado = totalApostado;
    }
    
    
    public int getOid_Juego() {
        return oid_Juego;
    }

    public void setOid_Juego(int oid_Juego) {
        this.oid_Juego = oid_Juego;
    }

    public int getOid_Jugador() {
        return oid_Jugador;
    }

    public void setOid_Jugador(int oid_Jugador) {
        this.oid_Jugador = oid_Jugador;
    }

    public Mano getUltimaMano() {
        return ultimaMano;
    }

    public ArrayList<Apuesta> Getpagadas() {
        return pagadas;
    }

//    public void setUltimaMano(Mano ultimaMano) {
//        this.ultimaMano = ultimaMano;
//    }
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Carta[] getCartas() {
        return cartas;
    }

    public void setCartas(Carta[] cartas) {
        this.cartas = cartas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(int saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public int getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(int saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public int getSaldoActual() {
        return this.jugador.getSaldo();
    }

    public String getNombreCompletoDelJugador() {
        return this.getJugador().getNombreCompleto();
    }

    public int getTotalApostado() {
        int total = 0;
        if (realizadas.isEmpty() && pagadas.isEmpty()) {
           total = totalApostado;
        }else{
             for (Apuesta a : pagadas) {
                total += a.getValor();
            }
            for (Apuesta a : realizadas) {
                total += a.getValor();
            }
        }
        return total;
    }

    public Apuesta descontarApuesta(int valorApuesta) throws AplicationException {
        if (this.jugador.tieneSaldoSuficiente(valorApuesta)) {
            //Creo una apuesta 
            Apuesta a = crearApuesta(valorApuesta);
            //this.juego.validarTurnoApuesta(a);//si ya aposto la excepcion
            verificarEstadoParaApuestas();
            //descuento el valor del saldo del jugador.
            this.jugador.descontarApuesta(valorApuesta);
            this.descontarSaldoFinal(a);
            this.setEstado(Estado.YA_PAGO);
            this.pagadas.add(a);
            return a;
        }
        return null;
    }

    protected Apuesta crearApuesta(int valorApuesta) {

        Apuesta a = new Apuesta();
        a.setValor(valorApuesta);
        a.setParticipante(this);
        return a;
    }

    public void apostar(int valorApuesta) throws AplicationException {
        if (this.jugador.tieneSaldoSuficiente(valorApuesta)) {

            if (this.juego.validarValorApuesta(valorApuesta)) {
                Apuesta a = crearApuesta(valorApuesta);
                //this.juego.validarTurnoApuesta(a);//si no se valida lanza AplicationException
                this.verificarEstadoParaApuestas();
                this.jugador.descontarApuesta(valorApuesta);

                this.realizadas.add(a);
                this.descontarSaldoFinal(a);//Descuenta el saldo final de la partida.
                this.setEstado(Estado.YA_APOSTO);
                this.juego.addApuestaEnManoActual(a);

            } else {
                throw new AplicationException("El valor de la apuesta no puede ser mayor que el menor saldo ni un valor negativo");
            }
        } else {
            throw new AplicationException("No tienes saldo suficiente!!");
        }

    }

    public void pagar() throws AplicationException {
        Apuesta aux = juego.getManoActual().getUltimaApuesta();
        Apuesta a = this.descontarApuesta(aux.getValor());
        if (a != null) {
            juego.addApuestaEnManoActual(a);
            //juego.verificarSiTerminaronApuestas();
        } else {
            throw new AplicationException("Error no se pudo pagar la apuesta");
        }

    }

    public void participantePasa() throws AplicationException {
        juego.getManoActual().participantePasa(this);
        juego.verificarSiPasaronTodos();

    }

    public Carta getCartaAlta() {//metodo de la primer parte del obligatorio.
        Carta alta = this.cartas[0];
        for (int i = 1; i < cartas.length; i++) {
            if (!alta.isMayor(cartas[i])) {
                alta = cartas[i];
            }
        }
        return alta;
    }

    public Figura getMisCartas() {
        DefinidorDeFiguras definidor = DefinidorDeFiguras.getInstancia();

        return definidor.devolverFigura(cartas);
    }
}
