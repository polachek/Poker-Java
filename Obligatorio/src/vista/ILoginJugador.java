/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import modelo.Participante;

/**
 *
 * @author Polachek
 */
public interface ILoginJugador {
    public void mostrarError(String mensaje);
    public void ingresar(Participante p);
}
