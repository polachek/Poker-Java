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
import modelo.Usuario;

/**
 * @date 24-jun-2018
 * @time 12:48:07
 * @author Leonardo Pérez
 */
public class MapeadorUsuario implements Mapeador {

    private Usuario user;

    public MapeadorUsuario() {
    }

    public MapeadorUsuario(Usuario user) {
        this.user = user;
    }

    public Usuario getUser() {
        return this.user;
    }

    public void setUser(Usuario u) {
        this.user = u;
    }

    @Override
    public int getOid() {
        return user.getOid();
    }

    @Override
    public void setOid(int oid) {
        user.setOid(oid);
    }

    @Override
    public ArrayList<String> getSqlInsertar() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add("INSERT INTO usuarios values (" + getOid() + ",'"
                + user.getNombreUsuario() + "','" + user.getContrasena() + "', '" + user.getNombreCompleto() + "')");
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlModificar() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add("UPDATE usuarios set nombreUsuario='" + user.getNombreUsuario()
                + "',contrasena='" + user.getContrasena()
                + "', nombreCompleto ='" + user.getNombreCompleto()
                + "' where oid=" + getOid());
        return sqls;

    }

    @Override
    public ArrayList<String> getSqlBorrar() {
        ArrayList<String> sqls = new ArrayList();

        sqls.add("DELETE FROM usuarios WHERE oid =" + user.getOid());
        return sqls;
    }

    @Override
    public String getSqlSelect() {
        return "SELECT u.oid, u.nombreUsuario, u.contrasena,u.nombreCompleto  FROM usuarios u ";
    }

    @Override
    public void crearNuevo() {
        user = new Usuario();
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {
        user.setOid(rs.getInt("oid"));
        user.setNombreUsuario(rs.getString("nombreUsuario"));
        user.setContrasena(rs.getString("contrasena"));
        user.setNombreCompleto(rs.getString("nombreCompleto"));
    }

    @Override
    public Object getObjeto() {
        return user;
    }
}
