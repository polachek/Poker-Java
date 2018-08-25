/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.Observable;
import modelo.Participante;
import modelo.Sistema;
import vista.ILoginJugador;

/**
 *
 * @author leo
 */
public class ControladorLoginJugador{
    
    private Sistema sistema = Sistema.getInstancia();
    private ILoginJugador vista;
    
    public ControladorLoginJugador(ILoginJugador v) {
        this.vista=v;
    }

    protected Sistema getSistema(){
        return sistema;
    }

    public void login(String nombre, String pass) {
       try{
           Participante p =this.getSistema().login(nombre, pass);
           this.vista.ingresar(p);
       }catch(Exception e){
           vista.mostrarError(e.getMessage());
       }
    }

   
}
