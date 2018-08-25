/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.ArrayList;

/**
 *
 * @author alumnoFI
 */
public class Observable {
    
    private ArrayList<Observador> lista = new ArrayList();
    
    public void agregar(Observador es){
        if(!lista.contains(es)){
            lista.add(es);
        }
    }
    public void quitar(Observador es){
        lista.remove(es);
    }
    public void avisar(Object evento) {
        ArrayList<Observador> tmp = new ArrayList(lista);
        for(Observador obs:tmp){
            obs.actualizar(evento, this);
        }
    }
    
}
