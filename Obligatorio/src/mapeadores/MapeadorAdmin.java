/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapeadores;

import modelo.Administrador;
import modelo.Usuario;

/**
 * @date 01-jul-2018
 * @time 14:56:07
 * @author Leonardo Pérez
 */
public class MapeadorAdmin extends MapeadorUsuario{
    
   
 @Override
    public String getSqlSelect() {
        return "SELECT u.oid, u.nombreUsuario, u.contrasena,u.nombreCompleto  FROM usuarios u WHERE oid NOT IN (SELECT oid FROM jugadores) ";
    }

    @Override
    public void crearNuevo() {
       this.setUser(new Administrador());
    }
    
    @Override
    public Object getObjeto() {
        return this.getUser();
    }
}
