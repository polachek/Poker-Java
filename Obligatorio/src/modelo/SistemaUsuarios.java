/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Persistencia.BaseDatos;
import Persistencia.Persistencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mapeadores.MapeadorAdmin;
import mapeadores.MapeadorJugador;


/**
 *
 * @author leo
 */
public class SistemaUsuarios {

    private ArrayList<Jugador> jugadores;// = cargarJugadores2();
    //private ArrayList<Jugador> jugadores = new ArrayList<>();
    //private ArrayList<Administrador> administradores = new ArrayList();
    private ArrayList<Administrador> administradores = cargarAdmins();
    private ArrayList<Administrador> loggueadosAdmin = new ArrayList();

    protected SistemaUsuarios(){
        //jugadores =cargarJugadores2();
        
    }
    public Participante loginJugador(String nombre, String pass) throws AplicationException {
        Sistema sistema = Sistema.getInstancia();
        for (Jugador j : jugadores) {
            if (j.getNombreUsuario().equals(nombre) && j.getContrasena().equals(pass)) {
                Participante p = sistema.buscarParticipacion(j);
                return p;
            }
        }
        throw new AplicationException("Usuario no encontrado o password incorrecto");
    }

    public Administrador loginAdmin(String nombre, String pass) throws AplicationException {
        Sistema sistema = Sistema.getInstancia();
        Administrador admin = null;
        for (Administrador ad : administradores) {
            if (ad.getNombreUsuario().equals(nombre) && ad.getContrasena().equals(pass)) {
                admin = ad;
            }
        }
        if (admin == null) {
            throw new AplicationException("Usuario no encontrado o password incorrecto");
        }
        for (Administrador ads : loggueadosAdmin) {
            if (ads.getNombreUsuario().equals(nombre) && ads.getContrasena().equals(pass)) {
                throw new AplicationException("El usuario ya se encuentra Logueado");
            }
        }
        loggueadosAdmin.add(admin);
        return admin;
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
    }

    public void addAdmin(Administrador a) {
        administradores.add(a);
    }

    void logout(Administrador admin) {
        int index = 0;
        for (Administrador a : loggueadosAdmin) {
            if (a == admin) {
                break;
            }
            index++;
        }

        loggueadosAdmin.remove(index);
    }

//    private ArrayList<Jugador> cargarJugadores() {
////        MapeadorJugador mj = new MapeadorJugador();
////        //String consulta =mj.getSqlSelect();
////        Persistencia p = Persistencia.getInstancia();
////        ArrayList<Jugador> lista =(ArrayList<Jugador>)p.buscar(mj, null);
//        ArrayList<Jugador> lista = new ArrayList<>();
////        Persistencia p = Persistencia.getInstancia();
//        BaseDatos b = BaseDatos.getInstancia();
//        try {
//            Statement st = b.conexion.createStatement();
//            ResultSet rs = st.executeQuery("SELECT u.oid, u.nombreUsuario, u.contrasena,u.nombreCompleto, j.saldo  FROM usuarios u, jugadores j WHERE u.oid=j.oid");
//            while (rs.next()) {
//                Jugador j = new Jugador();
//                j.setOid(Integer.parseInt(rs.getString("oid")));
//                j.setNombreUsuario(rs.getString("nombreUsuario"));
//                j.setContrasena(rs.getString("contrasena"));
//                j.setNombreCompleto(rs.getString("nombreCompleto"));
//                j.setSaldo(Integer.parseInt(rs.getString("saldo")));
//                lista.add(j);
//            }
//        } catch (SQLException ex) {
//            //Logger.getLogger(SistemaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println(ex.getMessage());
//        }
//        finally{
//            return lista;
//        }
//    }
    protected void cargarJugadores3(){
        Persistencia p = Persistencia.getInstancia();
        
        MapeadorJugador mj = new MapeadorJugador();
       
        
        jugadores = p.buscar(mj, null);

    }
    private ArrayList<Jugador> cargarJugadores2(){
        Persistencia p = Persistencia.getInstancia();
        
        MapeadorJugador mj = new MapeadorJugador();
        ArrayList<Jugador> jugadores;
        
        jugadores = p.buscar(mj, null);

        return jugadores;
    }
    private ArrayList<Administrador> cargarAdmins() {
        Persistencia p = Persistencia.getInstancia();
        
        MapeadorAdmin ma = new MapeadorAdmin();
        ArrayList<Administrador> admins;
       
        admins = p.buscar(ma, null);

        return admins;
    }

    protected ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
   protected Jugador buscarJugador(int oid){
       for(Jugador j: jugadores){
           if(j.getOid() == oid){
               return j;
           }
       }
       
       return null;
   }
}
