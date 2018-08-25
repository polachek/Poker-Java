/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import modelo.Carta;
import modelo.Figura;
import modelo.Participante;

/**
 *
 * @author leo
 */
public interface IVistaJuego extends ISaludable{
     public void mostrarSaldo(int saldoActual);
    public void actualizarCartas(Carta[] cartas);
    public void mostrarPozo(int pozoManoActual);

    public void avisarQueHuboApuesta(int valor, String nombreCompletoDelJugador);

    public void mostrarGanador(Participante p);

    public void limpiar();

    public void avisarQuePase();

    public void mostrarLosQuePasaron(ArrayList<Participante> losQuepasan);

    public void mostrarLosQueDejanLamano(ArrayList<Participante> losQueSeFueron);

    public void seTerminoMiPartida(String mensaje, String titulo);

    public void felicitarGanadorPartida(Participante p);

    public void mostrarLomodeLasCartasYDesactivarBotones();

    public void actualizarContador(String mensaje);
    
    public void mostrarNombreFigura(Figura misCartas);
    
}
