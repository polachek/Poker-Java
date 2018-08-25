/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author Guillermo Polachek
 */
public interface IVisorPartida {
    public void cargarDatosJuego(String FchI,int CantJug,int CantManos,int TotalApostado, int juegoApuestaLuz);
    public void cargarJugador(String nombreCompletoDelJugador, int saldoInicial, int saldoFinal, int totalApostado);
    public void updateCantManos(int CantManos);
    public void updateCantJug(int cantJug);
    public void limpiTabJug();
    public void updatePozo(int nuevoPozo);
    public void partidaFinalizada();
    public void mostrarMensaje(String mensaje);
}
