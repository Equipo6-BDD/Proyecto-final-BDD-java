public class UserInterface extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserInterface.class.getName());
    IngresoUsuario ingreso = new IngresoUsuario();
    VentanaPrincipal principal = new VentanaPrincipal();

    public UserInterface() {
        initComponents();
        java.net.URL urlIcono = getClass().getResource("/Imagenes/icono.png");
        if (urlIcono != null) {
            setIconImage(new javax.swing.ImageIcon(urlIcono).getImage());
        }
        jMenuBar1.setVisible(false);
        panelPrincipal.add(ingreso, "inicio");
        panelPrincipal.add(principal, "menu");
        pack();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }
    
    public void mostrarPantalla(String nombre) {
        java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
        cl.show(panelPrincipal, nombre);

        // El menú superior se muestra cuando entramos al menú principal
        jMenuBar1.setVisible(!nombre.equals("inicio"));
        pack();
    }
    
    public IngresoUsuario getIngreso() {
        return ingreso;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuEmpleados = new javax.swing.JMenu();
        opcionAgregarEmpleado = new javax.swing.JMenuItem();
        consultasGeneralesEmpleados = new javax.swing.JMenuItem();
        menuDoctores = new javax.swing.JMenu();
        opcionAgregarDoctor = new javax.swing.JMenuItem();
        consultasGeneralesDoctores = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Núcleo de Diagnóstico");

        panelPrincipal.setLayout(new java.awt.CardLayout());

        menuEmpleados.setText("Empleados");

        opcionAgregarEmpleado.setText("Agregar");
        opcionAgregarEmpleado.addActionListener(this::opcionAgregarEmpleadoActionPerformed);
        menuEmpleados.add(opcionAgregarEmpleado);

        consultasGeneralesEmpleados.setText("Consultas Generales");
        consultasGeneralesEmpleados.addActionListener(this::consultasGeneralesEmpleadosActionPerformed);
        menuEmpleados.add(consultasGeneralesEmpleados);

        jMenuBar1.add(menuEmpleados);

        menuDoctores.setText("Doctores");

        opcionAgregarDoctor.setText("Insertar Doctor");
        opcionAgregarDoctor.addActionListener(this::opcionAgregarDoctorActionPerformed);
        menuDoctores.add(opcionAgregarDoctor);

        consultasGeneralesDoctores.setText("Consultas Generales");
        consultasGeneralesDoctores.addActionListener(this::consultasGeneralesDoctoresActionPerformed);
        menuDoctores.add(consultasGeneralesDoctores);

        jMenuBar1.add(menuDoctores);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAgregarEmpleadoActionPerformed
        ingresarEmpleado d = new ingresarEmpleado(this, true);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }//GEN-LAST:event_opcionAgregarEmpleadoActionPerformed

    private void consultasGeneralesEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultasGeneralesEmpleadosActionPerformed
        // PASO 4: Consulta general de la tabla Empleado en PostgreSQL
        String sql = "SELECT * FROM public.\"Empleado\"";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }//GEN-LAST:event_consultasGeneralesEmpleadosActionPerformed

    private void opcionAgregarDoctorActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        ingresarEmpleado d = new ingresarEmpleado(this, true);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }                                                   

    private void consultasGeneralesDoctoresActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        // PASO 8: Consulta general de la tabla Doctor en PostgreSQL
        String sql = "SELECT * FROM public.\"Doctor\" ORDER BY \"Codigo_Doc\" ASC";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }                                                          

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new UserInterface().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem consultasGeneralesDoctores;
    private javax.swing.JMenuItem consultasGeneralesEmpleados;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuDoctores;
    private javax.swing.JMenu menuEmpleados;
    private javax.swing.JMenuItem opcionAgregarDoctor;
    private javax.swing.JMenuItem opcionAgregarEmpleado;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}