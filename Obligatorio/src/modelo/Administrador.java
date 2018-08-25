/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author leo
 */
public class Administrador extends Usuario{

    public Administrador() {
        super();
    }
    
    
    public Administrador(String nombreUsuario, String contrasena, String nombreCompleto) {
        super(nombreUsuario, contrasena, nombreCompleto);
    }

    public Administrador(Usuario u) {
        super(u.getNombreUsuario(),u.getContrasena(),u.getNombreCompleto());
    }
   
    
}
