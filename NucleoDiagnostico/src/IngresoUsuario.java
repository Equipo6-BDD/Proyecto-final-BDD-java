import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class IngresoUsuario extends javax.swing.JPanel {

    public IngresoUsuario() {
        initComponents();
    }

    public void limpiarCampos() {
        nombreUsuario.setText("");
        contraseñaUsuario.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelUsuario = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JTextField();
        labelContraseña = new javax.swing.JLabel();
        contraseñaUsuario = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        labelUsuario.setText("Usuario");

        labelContraseña.setText("Contraseña");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(evt -> limpiarCampos());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelUsuario)
                            .addComponent(labelContraseña))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(contraseñaUsuario)
                            .addComponent(nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario)
                    .addComponent(nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContraseña)
                    .addComponent(contraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(122, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String usuarioInput = nombreUsuario.getText().trim();
        String passwordInput = new String(contraseñaUsuario.getPassword()).trim();

        if (usuarioInput.isEmpty() || passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa usuario y contraseña.");
            return;
        }

        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        UserInterface ui = (UserInterface) ventana;

        // 1. Modo Administrador (ve todos los menús y opciones)
        if (usuarioInput.equalsIgnoreCase("admin") && passwordInput.equals("1234")) {
            try (Connection con = ConexionDB.conectar()) {
                if (con != null) {
                    ui.configurarPermisos(true);
                    ui.principal.setUsuario("admin");
                    ui.principal.ocultarTabla();
                    ui.mostrarPantalla("menu");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error SQL: " + e.getMessage());
            }
            return;
        }

        // 2. Modo Empleada (Beatriz: 1 / 7021972 | Janet: 2 / 31101987)
        String sql = "SELECT * FROM public.\"Empleado\"";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String codigoDB = rs.getString(1).trim();       // Columna 1: ID_Em
                String nombreCompleto = rs.getString(2).trim(); // Columna 2: Nombre
                String passDB = rs.getString(6).trim();         // Columna 6: Contraseña

                if (usuarioInput.equals(codigoDB) && passwordInput.equals(passDB)) {
                    String primerNombre = nombreCompleto.split(" ")[0].toUpperCase();
                    ui.configurarPermisos(false);
                    ui.principal.setUsuario("Hola, " + primerNombre);
                    ui.principal.ocultarTabla();
                    ui.mostrarPantalla("menu");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Código de empleado o contraseña incorrectos.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al autenticar empleado:\n" + e.getMessage());
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    public String getUsuario() {
        return nombreUsuario.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPasswordField contraseñaUsuario;
    private javax.swing.JLabel labelContraseña;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JTextField nombreUsuario;
    // End of variables declaration//GEN-END:variables
}