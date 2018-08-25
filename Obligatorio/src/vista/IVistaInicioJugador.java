/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import modelo.Participante;

/**
 *
 * @author leo
 */
public interface IVistaInicioJugador extends ISaludable {
    public void actualizarCuantosFaltan(int cuantosJugadoresFaltan);
    public void iniciarJuego(Participante participante);
    public void abandonarEspera();
}
