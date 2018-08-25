/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author leo
 */
public class UtilidadesVista {
    
    
    public void mostrarError(JFrame padre, String mensaje){
        JOptionPane.showMessageDialog(padre, mensaje);
    }
}
