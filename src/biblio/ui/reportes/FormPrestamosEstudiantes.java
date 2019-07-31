/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.ui.reportes;

import biblio.Files.ObjectRead;
import biblio.Models.Prestamo;
import biblio.ui.FormPrincipal;
import static biblio.ui.reportes.FormPrestamoLibros.addDatatoJTable;
import static biblio.ui.reportes.FormPrestamoLibros.eliminar;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gamcas
 */
public class FormPrestamosEstudiantes extends javax.swing.JFrame {

    private static LocalDate todayDate = LocalDate.now();
    private static DefaultTableModel model;

    /**
     * Creates new form FormPrestamoLibros
     */
    public FormPrestamosEstudiantes() {
        initComponents();
        this.contenedor.setSize(650, 550);
        this.setSize(660, 580);
        this.setLocationRelativeTo(null);
        model = (DefaultTableModel) jTable1.getModel();
        llenarEstudiante();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new javax.swing.JPanel();
        titulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbEst = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        contenedor.setLayout(null);

        titulo.setBackground(new java.awt.Color(255, 255, 255));
        titulo.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.black));
        titulo.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Prestamos hechos por estudiantes");
        titulo.add(jLabel1);
        jLabel1.setBounds(180, 10, 330, 30);

        contenedor.add(titulo);
        titulo.setBounds(0, 0, 680, 50);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray));
        jPanel1.setLayout(null);

        jTable1.setBackground(new java.awt.Color(0, 153, 153));
        jTable1.setForeground(new java.awt.Color(204, 204, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Carnet", "Estudiante", "Libro", "Fecha de prestamo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 110, 640, 220);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/biblio/images/regresar.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2);
        jLabel2.setBounds(290, 410, 80, 60);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setText("Regresar");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(290, 470, 90, 24);

        jLabel3.setFont(new java.awt.Font("Gill Sans MT", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 255));
        jLabel3.setText("Seleccione un estudiante:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(220, 10, 200, 30);

        cmbEst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione:" }));
        jPanel1.add(cmbEst);
        cmbEst.setBounds(200, 40, 150, 40);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(360, 40, 80, 40);

        contenedor.add(jPanel1);
        jPanel1.setBounds(0, 50, 680, 510);

        getContentPane().add(contenedor, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.dispose();
        FormPrincipal f = new FormPrincipal();
        f.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        eliminar(model);
        llenarTab();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void addDatatoJTable(Prestamo prestamo, DefaultTableModel model) {

        try {
            Object rowData[] = new Object[4];

            rowData[0] = prestamo.getCarnetEstudiante();
            rowData[1] = ObjectRead.readEstudiante(prestamo.getCarnetEstudiante()).getNombre();
            rowData[2] = ObjectRead.readLibro(prestamo.getCodigoLibro()).getTitulo();
            rowData[3] = prestamo.getFechaPrestamo();
            model.addRow(rowData);
        } catch (IOException ex) {
            Logger.getLogger(FormPrestamosEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//limpiamos la tabla de cualquier dato no deseado

    public static void eliminar(DefaultTableModel tb) {
        int a = tb.getRowCount() - 1;
        for (int i = a; i >= 0; i--) {
            tb.removeRow(tb.getRowCount() - 1);
        }
    }
//llenamos nuestro combo box con los carnets que tienen prestamos
    private static void llenarEstudiante() {

        File f = new File("DB\\prestamos");

        for (String s : f.list()) {
            cmbEst.addItem(s);

        }
    }
//llenamos nuestra tabla con los datos del estudiante seleccionado en el combo box
    private static void llenarTab() {
        if (!(cmbEst.getSelectedIndex() == 0)) {

            File f = new File("DB\\prestamos\\" + cmbEst.getSelectedItem());
            for (String s : f.list()) {

                String comm = s.substring(0, s.indexOf("."));

                try {
                    Prestamo p = ObjectRead.readPrestamo(Integer.valueOf((String) cmbEst.getSelectedItem()), comm);
                    addDatatoJTable(p, model);
                } catch (IOException ex) {
                    Logger.getLogger(FormPrestamosEstudiantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPrestamosEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPrestamosEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPrestamosEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrestamosEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPrestamosEstudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cmbEst;
    private javax.swing.JPanel contenedor;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel titulo;
    // End of variables declaration//GEN-END:variables
}