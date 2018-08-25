/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControladorJuego;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Carta;
import modelo.Figura;
import modelo.Participante;
import utilidades.MiLogger;

/**
 *
 * @author leo
 */
public class VistaJuego extends javax.swing.JFrame implements IVistaJuego {

    /**
     * Creates new form VistaJuego
     */
    ControladorJuego controlador;
    ArrayList<String> listaAvisos = new ArrayList<>();

    public VistaJuego(Participante p) {
        initComponents();
        controlador = new ControladorJuego(p, this);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSaldo = new javax.swing.JLabel();
        lblPozo = new javax.swing.JLabel();
        btnApostar = new javax.swing.JButton();
        txtValorApuesta = new javax.swing.JTextField();
        lblAvisoDeApuesta = new javax.swing.JLabel();
        btnPasar = new javax.swing.JButton();
        carta1 = new javax.swing.JLabel();
        carta2 = new javax.swing.JLabel();
        carta3 = new javax.swing.JLabel();
        carta4 = new javax.swing.JLabel();
        carta5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstAvisos = new javax.swing.JList();
        jbtnSalirJuego = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(lblSaldo);
        lblSaldo.setBounds(10, 22, 117, 20);

        lblPozo.setText("jLabel1");
        getContentPane().add(lblPozo);
        lblPozo.setBounds(145, 28, 198, 16);

        btnApostar.setText("Apostar");
        btnApostar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApostarActionPerformed(evt);
            }
        });
        getContentPane().add(btnApostar);
        btnApostar.setBounds(80, 380, 110, 32);
        getContentPane().add(txtValorApuesta);
        txtValorApuesta.setBounds(210, 380, 110, 24);

        lblAvisoDeApuesta.setText("jLabel1");
        getContentPane().add(lblAvisoDeApuesta);
        lblAvisoDeApuesta.setBounds(50, 49, 580, 40);

        btnPasar.setText("Pasar");
        btnPasar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarActionPerformed(evt);
            }
        });
        getContentPane().add(btnPasar);
        btnPasar.setBounds(330, 380, 130, 32);

        carta1.setText("Carta1");
        getContentPane().add(carta1);
        carta1.setBounds(20, 80, 130, 170);

        carta2.setText("Carta2");
        getContentPane().add(carta2);
        carta2.setBounds(160, 80, 130, 170);

        carta3.setText("Carta3");
        getContentPane().add(carta3);
        carta3.setBounds(300, 80, 130, 170);

        carta4.setText("Carta4");
        getContentPane().add(carta4);
        carta4.setBounds(440, 80, 130, 170);

        carta5.setText("Carta5");
        getContentPane().add(carta5);
        carta5.setBounds(570, 80, 130, 170);

        jlstAvisos.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(jlstAvisos);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(50, 277, 550, 90);

        jbtnSalirJuego.setText("Salir del Juego");
        jbtnSalirJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirJuegoActionPerformed(evt);
            }
        });
        getContentPane().add(jbtnSalirJuego);
        jbtnSalirJuego.setBounds(480, 380, 150, 32);

        setBounds(0, 0, 725, 472);
    }// </editor-fold>//GEN-END:initComponents

    private void btnApostarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApostarActionPerformed
        if (evt.getActionCommand().equals("Apostar")) {
            apostar();
        } else if (evt.getActionCommand().equals("Pagar")) {
            pagarApuesta();
        }

    }//GEN-LAST:event_btnApostarActionPerformed

    private void btnPasarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarActionPerformed
        if (evt.getActionCommand().equals("Pasar")) {
            pasar();
        } else if (evt.getActionCommand().equals("Retirar")) {
            retirar();
        }
    }//GEN-LAST:event_btnPasarActionPerformed

    private void jbtnSalirJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirJuegoActionPerformed
        dejarPartida();
    }//GEN-LAST:event_jbtnSalirJuegoActionPerformed

    public void mostrarSaludo(String nombreCompletoDelJugador) {
        this.setTitle(nombreCompletoDelJugador + " esta sentado en la mesa");
    }

    public void mostrarSaldo(int saldoActual) {
        lblSaldo.setText("Saldo: $" + saldoActual);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApostar;
    private javax.swing.JButton btnPasar;
    private javax.swing.JLabel carta1;
    private javax.swing.JLabel carta2;
    private javax.swing.JLabel carta3;
    private javax.swing.JLabel carta4;
    private javax.swing.JLabel carta5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnSalirJuego;
    private javax.swing.JList jlstAvisos;
    private javax.swing.JLabel lblAvisoDeApuesta;
    private javax.swing.JLabel lblPozo;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JTextField txtValorApuesta;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actualizarCartas(Carta[] cartas) {
//        jlListacartas.removeAll();
//        jlListacartas.setListData(cartas);
        String strCarta = "";
        for (int i = 0; i < cartas.length; i++) {
            strCarta = "" + ((cartas[i].getNumero()) + 2) + cartas[i].getPaloString().substring(0, 1);

            strCarta = "assets/images/cartas/" + strCarta.toLowerCase() + ".gif";

        }
        String direccion ="";
        String direccionWeb ="";
        for (int i = 0; i < cartas.length; i++) {
            strCarta = "" + ((cartas[i].getNumero()) + 2) + cartas[i].getPaloString().substring(0, 1);
            direccion = "/vista/cartas/" + strCarta.toLowerCase() + ".gif";
            direccionWeb ="http://localhost:8080/Aplicacion_Web/" + "assets/images/cartas/" + strCarta.toLowerCase() + ".gif";
            switch (i) {
                case 0:
                    try {
                        
                        ImageIcon card1 = new ImageIcon(getClass().getResource(direccion));
                        carta1.setIcon(card1);

                    } catch (Exception e) {
                      
                       ImageIcon card1 = new ImageIcon( direccionWeb);
                       carta1.setIcon(card1);

                    }

                case 1:
                    try {
                        
                        ImageIcon card2 = new ImageIcon(getClass().getResource(direccion));
                        carta2.setIcon(card2);

                    } catch (Exception e) {
                      
                       ImageIcon card2 = new ImageIcon( direccionWeb);
                       carta2.setIcon(card2);

                    }
                    
                case 2:
                    
                    try {
                        
                        ImageIcon card3 = new ImageIcon(getClass().getResource(direccion));
                        carta3.setIcon(card3);

                    } catch (Exception e) {
                      
                       ImageIcon card3 = new ImageIcon( direccionWeb);
                       carta3.setIcon(card3);

                    }
                case 3:
                    try {
                        
                        ImageIcon card4 = new ImageIcon(getClass().getResource(direccion));
                        carta4.setIcon(card4);

                    } catch (Exception e) {
                      
                       ImageIcon card4 = new ImageIcon( direccionWeb);
                       carta4.setIcon(card4);

                    }
                case 4:
                    try {
                        
                        ImageIcon card5 = new ImageIcon(getClass().getResource(direccion));
                        carta5.setIcon(card5);

                    } catch (Exception e) {
                      
                       ImageIcon card5 = new ImageIcon( direccionWeb);
                       carta5.setIcon(card5);

                    }
                    break;
                default:
                    break;
            }
        }

        actualizarListaAvisos("Comienza una nueva mano");
    }

    @Override
    public void mostrarPozo(int pozoManoActual) {
        lblPozo.setText("Pozo de la mano: " + String.valueOf(pozoManoActual));
    }

    private void apostar() {
        try {
            int valorApuesta = Integer.parseInt(txtValorApuesta.getText());
            this.controlador.apostar(valorApuesta);
        } catch (Exception e) {
            UtilidadesVista u = new UtilidadesVista();
            u.mostrarError(this, e.getMessage());
        }
    }

    @Override
    public void avisarQueHuboApuesta(int valor, String nombreCompletoDelJugador) {
        //this.lblAvisoDeApuesta.setText(nombreCompletoDelJugador + " ha apostado $" + valor);
        String aviso = nombreCompletoDelJugador + " ha apostado $" + valor;
        actualizarListaAvisos(aviso);

        this.btnApostar.setText("Pagar apuesta");
        this.btnApostar.setActionCommand("Pagar");
        this.btnPasar.setText("Retirarse de la mano");
        this.btnPasar.setActionCommand("Retirar");
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        UtilidadesVista u = new UtilidadesVista();
        u.mostrarError(this, mensaje);
    }

    private void pagarApuesta() {
        try {
            this.controlador.pagarApuesta();
        } catch (Exception e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void pasar() {
        this.controlador.pasar();
    }

    @Override
    public void mostrarGanador(Participante p) {
        String ganador = p.getNombreCompletoDelJugador();
        String pozo = String.valueOf(p.getUltimaMano().getPozo().getValor());
        String mensaje = "Pozo acumulado: $" + pozo + " Desea jugar otra mano?";
        //String titulo = ganador + " ha ganado la mano con carta alta" + p.getCartaAlta().getNumeroString()+" de "+ p.getCartaAlta().getPaloString() ;
        String titulo = ganador + " ha ganado la mano con " + p.getMisCartas().getClass().getName() + " de " + p.getMisCartas().getNombre();

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.OK_CANCEL_OPTION);
        actualizarListaAvisos("mano terminada");
        actualizarListaAvisos(titulo);
        actualizarListaAvisos("Se ha llevado un pozo de $" + pozo);
        try {
            if (opcion == JOptionPane.OK_OPTION) {

                this.controlador.confirmar();
            } else {
                this.controlador.dejarPartida();
                dispose();
            }
        } catch (Exception e) {
            mostrarMensajeError(e.getMessage());
            MiLogger.loggearError(e);
        }
    }

    @Override
    public void limpiar() {
        //this.lblAvisoDeApuesta.setText("Ha terminado la mano");
        //actualizarListaAvisos("Ha terminado la mano");
        this.btnApostar.setActionCommand("Apostar");
        this.btnApostar.setText("Apostar");
        this.btnApostar.setEnabled(true);
        this.btnPasar.setActionCommand("Pasar");
        this.btnPasar.setText("Pasar");
        this.btnPasar.setEnabled(true);
        this.txtValorApuesta.setText("");
        this.lblAvisoDeApuesta.setText("");
    }

    @Override
    public void avisarQuePase() {
        //this.lblAvisoDeApuesta.setText("Has pasado la apuesta");
        actualizarListaAvisos("Has pasado la apuesta");
    }

    @Override
    public void mostrarLosQuePasaron(ArrayList<Participante> losQuepasan) {
        String pasaron = "Pasa/n la apuesta";
        mostrarAvisoDeParticipantes(losQuepasan, pasaron);
    }

    private void retirar() {
        this.controlador.retirar();
    }

    @Override
    public void mostrarLosQueDejanLamano(ArrayList<Participante> losQueSeFueron) {
        String pasaron = "Deja/n la mano ";
        mostrarAvisoDeParticipantes(losQueSeFueron, pasaron);
    }

    private void mostrarAvisoDeParticipantes(ArrayList<Participante> losQuepasan, String pasaron) {
        for (Participante p : losQuepasan) {
            pasaron += " " + p.getNombreCompletoDelJugador();
        }
        //pasaron += " ";
        //this.lblAvisoDeApuesta.setText(pasaron);
        actualizarListaAvisos(pasaron);
    }

    private void actualizarListaAvisos(String aviso) {
        jlstAvisos.removeAll();
        listaAvisos.add(aviso);
        jlstAvisos.setListData(listaAvisos.toArray());
        jlstAvisos.ensureIndexIsVisible(jlstAvisos.getModel().getSize() - 1);

    }

    @Override
    public void seTerminoMiPartida(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, "No tienes saldo suficiente para continuar", "Se te termino la partida", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    @Override
    public void felicitarGanadorPartida(Participante p) {
        String ganador = p.getNombreCompletoDelJugador();
        String pozo = String.valueOf(p.getJuego().getPozo().getValor());
        String mensaje = "Pozo acumulado: $" + pozo + " Desea jugar una nueva partida?";
        String titulo = ganador + " ha ganado la Partida";
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.OK_CANCEL_OPTION);
        dispose();
        try {
            if (opcion == JOptionPane.OK_OPTION) {

            } else {

            }
        } catch (Exception e) {
            mostrarMensajeError(e.getMessage());
            MiLogger.loggearError(e);
        }
    }

    @Override
    public void mostrarLomodeLasCartasYDesactivarBotones() {

        String strCarta = "/vista/cartas/Invertida.gif";
        ImageIcon card = new ImageIcon(getClass().getResource(strCarta));
        carta1.setIcon(card);
        carta2.setIcon(card);
        carta3.setIcon(card);
        carta4.setIcon(card);
        carta5.setIcon(card);
        this.btnApostar.setEnabled(false);
        this.btnPasar.setEnabled(false);
    }

    private void dejarPartida() {
        mostrarMensajeError("Te retiras del juego");

        controlador.salirJuego();
        dispose();

    }

    @Override
    public void actualizarContador(String mensaje) {
        this.lblAvisoDeApuesta.setText(mensaje);
    }

    @Override
    public void mostrarNombreFigura(Figura misCartas) {
        String aviso = formatearNombreFigura(misCartas);
        actualizarListaAvisos(aviso);
    }

    private String formatearNombreFigura(Figura misCartas) {
        String nombreFig = misCartas.getClass().getName();
        String[] nomDiv = nombreFig.split("[.]");
        String nombre = nomDiv[1];
        String aviso = "Tienes :" + nombre + " de " + misCartas.getNombre();
        return aviso;
    }

}