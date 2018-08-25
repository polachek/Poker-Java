/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import modelo.Juego;

/**
 *
 * @author Guillermo Polachek
 */
public interface IMonitorPartidas {
    public void noHayPartidasCurso();
    public void cargarPartidascurso(ArrayList<Juego> juegosCurso);
    public void crearVisorPartida(Juego miJuego);
    public void mostrarError(String mensaje);
}
