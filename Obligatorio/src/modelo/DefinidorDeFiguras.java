/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;

/**
 * @date 16-jun-2018
 * @time 6:15:50
 * @author Leonardo Pérez
 */
public class DefinidorDeFiguras {//hacerlo singleton.
    private static DefinidorDeFiguras instancia = new DefinidorDeFiguras();
   
    //private ArrayList<Figura> figurasDisponibles = new ArrayList<>();
    private Figura[] figurasDisponibles;

    private DefinidorDeFiguras() {
        cargarFigurasPredefinidas();
    }
    
    
    private void cargarFigurasPredefinidas(){
        figurasDisponibles = new Figura[4];
        Figura f;
        f= new Color();
        figurasDisponibles[0] = f;
        f = new DoblePar();
        figurasDisponibles[1] = f;
        f=new Par();
        figurasDisponibles[2]=f;
        f = new CartaAlta();
        figurasDisponibles[3] =f;
        
    }

    public void setFigurasDisponibles(Figura[] figurasDisponibles) {
        this.figurasDisponibles = figurasDisponibles;
    }
    
    
    public static DefinidorDeFiguras getInstancia(){
        return instancia;
    }
//    public ArrayList<Figura> getFigurasDisponibles() {
//        return figurasDisponibles;
//    }
//
//    public void setFigurasDisponibles(ArrayList<Figura> figurasDisponibles) {
//        this.figurasDisponibles = figurasDisponibles;
//    }
//    
//    public void AddNuevaFigura(Figura f){
//        this.figurasDisponibles.add(f);
//    }
    
    public Figura devolverFigura(Carta[] cartas){
        Figura retorno = null;
        for(Figura f: figurasDisponibles){
            if(f.esFigura(cartas)){
                retorno = f.obtenerCopia();
                return retorno;
            }
        }
        return retorno;
    }
}
