/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import modelo.Juego;
import modelo.Sistema;
import utilidades.Observable;
import utilidades.Observador;
import vista.IMonitorPartidas;

/**
 *
 * @author Polachek
 */
public class CntlrMonitorPartidas implements Observador{
    private Sistema sistema = Sistema.getInstancia();
    private IMonitorPartidas vista;
    ArrayList<Juego> misJuegosCurso;
    
    public CntlrMonitorPartidas(IMonitorPartidas v) {
        this.vista=v;
        cargarPartidascurso(sistema.cargarPartidasCurso());
        sistema.agregar(this);
    }
    
    public void cargarPartidascurso(ArrayList<Juego> juegosCurso){
        if(juegosCurso.isEmpty()){
            vista.noHayPartidasCurso();
        }else{
            misJuegosCurso = juegosCurso;
            vista.cargarPartidascurso(misJuegosCurso);
        }
    }
    
    public void verPartida(int indexJuego){
        Juego miJuego = misJuegosCurso.get(indexJuego);
        vista.crearVisorPartida(miJuego);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento.equals(Sistema.Eventos.CAMBIO_LISTA_JUEGOS)) {
            cargarPartidascurso(sistema.cargarPartidasCurso());
        }
    }
}
