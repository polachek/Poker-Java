/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Persistencia.Persistencia;
import java.util.ArrayList;
import mapeadores.MapeadorJugador;
import mapeadores.MapeadorUsuario;
import modelo.Administrador;
import modelo.Jugador;
import modelo.Sistema;
import modelo.Usuario;

/**
 *
 * @author leo
 */
public class Test {

    public static void main(String[] args) {
        new InicioPrototipo().setVisible(true);
        cargarDatosPrueba();

    }

    private static void cargarDatosPrueba() {

        Persistencia p = Persistencia.getInstancia();
        Sistema s = Sistema.getInstancia();
        s.cargarDatos();
//        ArrayList<Usuario> admins = new ArrayList<>();
//        ArrayList<Jugador> jugadores = new ArrayList();
//        MapeadorUsuario mu = new MapeadorUsuario();
        //MapeadorJugador mj = new MapeadorJugador();
//        jugadores = p.buscar(mj, null);
//        for(Jugador j: jugadores){
//            s.addJugador(j);
//        }
//        admins = p.buscar(mu, null);
//        for(Usuario u: admins){
//            s.addAdmin(new Administrador(u));
//        }
//        
//        Jugador j = new Jugador(1000, "Pepe", "1", "Pepe Gavilan");
//        s.addJugador(j);
//        MapeadorJugador mj = new MapeadorJugador(j);
//        p.guardar(mj);
//        j = new Jugador(1500, "eldani", "1", "Daniel Negreanu");
//        s.addJugador(j);
//        mj = new MapeadorJugador(j);
//        p.guardar(mj);
//        j = new Jugador(1500, "beto", "1", "Alberto Font");
//        s.addJugador(j);
//        mj = new MapeadorJugador(j);
//        p.guardar(mj);
//        j = new Jugador(1500, "mary", "1", "Maria Lampropulos");
//        s.addJugador(j);
//        mj = new MapeadorJugador(j);
//        p.guardar(mj);
//        j = new Jugador(1500, "cele", "1", "Celeste Orona");
//        s.addJugador(j);
//        mj = new MapeadorJugador(j);
//        p.guardar(mj);
//
//        j = new Jugador(3500, "Tony", "1", "Antonio Pacheco");
//        s.addJugador(j);
//        mj = new MapeadorJugador(j);
//        p.guardar(mj);
//        
//        j = new Jugador(1500, "1", "1", "Rigoberta Menchu");
//        s.addJugador(j);
//        mj =new MapeadorJugador(j);
//        p.guardar(mj);
//        
//        j = new Jugador(1500, "2", "1", "Apolodoro de Puntaleste");
//        s.addJugador(j);
//        mj =new MapeadorJugador(j);
//        p.guardar(mj);
//        
//        j = new Jugador(1500, "3", "1", "Sara Sarasa");
//        s.addJugador(j);
//        mj =new MapeadorJugador(j);
//        p.guardar(mj);
//        
//        j = new Jugador(10, "4", "1", "Don Sinplata");
//        s.addJugador(j);
//        mj =new MapeadorJugador(j);
//        p.guardar(mj);
//
//        Administrador ad = new Administrador("11", "1", "Guillermo Polachek");
//        s.addAdmin(ad);
//        MapeadorUsuario mu = new MapeadorUsuario(ad);
//        p.guardar(mu);
//        ad = new Administrador("Leo", "1", "Leonardo");
//        s.addAdmin(ad);
//        mu = new MapeadorUsuario(ad);
//        p.guardar(mu);
        s.setApuestaBase(100);
        s.setCantidadJugadoresPorPartida(2);
        s.setTimeOut(30);
        s.setProximoJuego(s.crearJuego());
    }
}
