/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author leo
 */
public class InicioPrototipo extends javax.swing.JFrame {

    /**
     * Creates new form InicioPrototipo
     */
    public InicioPrototipo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnJugador = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Programador");
        getContentPane().setLayout(null);

        btnJugador.setText("Jugador");
        btnJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJugadorActionPerformed(evt);
            }
        });
        getContentPane().add(btnJugador);
        btnJugador.setBounds(570, 160, 140, 50);

        btnAdmin.setText("Admin");
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdmin);
        btnAdmin.setBounds(570, 280, 140, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/diseno/panelprogramador.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 930, 460);

        setBounds(0, 0, 951, 515);
    }// </editor-fold>//GEN-END:initComponents

    private void btnJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugadorActionPerformed
        crearLoginJugador();
    }//GEN-LAST:event_btnJugadorActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        crearLoginAdmin();
    }//GEN-LAST:event_btnAdminActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnJugador;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    private void crearLoginJugador() {
        //dispose();
        new LoginJugador().setVisible(true);
    }
    
    private void crearLoginAdmin() {
        //dispose();
        new LoginAdmin().setVisible(true);
    }
}
