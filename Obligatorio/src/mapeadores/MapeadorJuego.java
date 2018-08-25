/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeadores;

import Persistencia.Mapeador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Juego;
import modelo.Jugador;
import modelo.Pozo;

/**
 * @date 25-jun-2018
 * @time 5:55:11
 * @author Leonardo Pérez
 */
public class MapeadorJuego implements Mapeador {

    private Juego juego;

    public MapeadorJuego() {
    }

    public MapeadorJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public int getOid() {
        return juego.getOid();
    }

    @Override
    public void setOid(int oid) {
        juego.setOid(oid);
    }

    @Override
    public ArrayList<String> getSqlInsertar() {
        ArrayList<String> sqls = new ArrayList();
        Jugador ganador = juego.getGanadorPartida().getJugador();
        java.sql.Timestamp fecha = new java.sql.Timestamp(juego.getFechaIncio().getTime());
        System.out.println(fecha);
        sqls.add("INSERT INTO juegos values (" + getOid() + ",'"
                + fecha + "'," + juego.getPozo().getValor() + ", "
                + juego.getGanadorPartida().getJugador().getOid() + ", " +juego.getCantManosJugadas()+
                ", "+juego.getApuestaBase() +")");
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlModificar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getSqlBorrar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSqlSelect() {
        return "SELECT * FROM juegos";
    }

    @Override
    public void crearNuevo() {
        juego =new Juego();
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {
       juego.setOid(rs.getInt("oid"));
       juego.setFechaHoraInicio(new java.util.Date(rs.getTimestamp("fechaHoraInicio").getTime()));
       juego.setPozo(new Pozo(rs.getInt("pozo")));
       juego.setOid_Ganador(rs.getInt("oid_Ganador"));
       juego.setApuestaBase(rs.getInt("apuestaBase"));
       juego.setCantidadManos(rs.getInt("cantidadManosJugadas"));
       
    }

    @Override
    public Object getObjeto() {
        return juego;
    }

}
