/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeadores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Jugador;
import modelo.Usuario;

/**
 * @date 25-jun-2018
 * @time 4:50:22
 * @author Leonardo Pérez
 */
public class MapeadorJugador extends MapeadorUsuario {

    //private Jugador jugador;
    public MapeadorJugador() {
        super();
    }

    public MapeadorJugador(Jugador jugador) {
        super(jugador);
    }

    @Override
    public ArrayList<String> getSqlInsertar() {
        ArrayList<String> sqls = super.getSqlInsertar();
        Jugador j = (Jugador) getUser();
        sqls.add("INSERT INTO jugadores values (" + getOid() + ",'"
                + j.getNombreUsuario() + "','" + j.getSaldo() + "')");
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlModificar() {
        ArrayList<String> sqls = super.getSqlModificar();
        Jugador j = (Jugador) getUser();
        sqls.add("UPDATE jugadores set nombreUsuario='" + j.getNombreUsuario()
                + "',saldo='" + j.getSaldo() + "' where oid=" + getOid());
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlBorrar() {
        ArrayList<String> sqls = super.getSqlBorrar();
        Jugador j = (Jugador) getUser();

        sqls.add("DELETE FROM jugadores WHERE oid =" + j.getOid());
        //sqls.addAll(super.getSqlBorrar());
        return sqls;
    }

    @Override
    public String getSqlSelect() {
        return "SELECT u.oid, u.nombreUsuario, u.contrasena,u.nombreCompleto, j.saldo  FROM usuarios u, jugadores j WHERE u.oid=j.oid";
    }

    @Override
    public void crearNuevo() {
        this.setUser(new Jugador());
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {
        Jugador j = (Jugador) getUser();
        j.setNombreUsuario(rs.getString("nombreUsuario"));
        j.setContrasena(rs.getString("contrasena"));
        j.setNombreCompleto(rs.getString("nombreCompleto"));
        j.setSaldo(Integer.parseInt(rs.getString("saldo")));
//        super.leer(rs);
//        j.setSaldo(Integer.parseInt(rs.getString("saldo")));

    }

    @Override
    public Object getObjeto() {
        return getUser();
    }
}
