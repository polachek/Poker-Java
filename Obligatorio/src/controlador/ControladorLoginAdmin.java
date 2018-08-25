/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Administrador;
import modelo.Sistema;
import vista.LoginAdmin;

/**
 *
 * @author Polachek
 */
public class ControladorLoginAdmin {
    private Sistema sistema = Sistema.getInstancia();
    private LoginAdmin vista;
    
    
    public ControladorLoginAdmin(LoginAdmin v) {
        this.vista=v;
    }
    
    protected Sistema getSistema(){
        return sistema;
    }
    
    public void login(String nombre, String pass) {
       try{
           Administrador a =this.getSistema().loginAdmin(nombre, pass);
           this.vista.ingresar(a);
       }catch(Exception e){
           vista.mostrarError(e.getMessage());
       }
    }
}
