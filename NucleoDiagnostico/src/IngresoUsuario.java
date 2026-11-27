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
        campoContrasena.setText("");
    }

    public String getUsuario() {
        return nombreUsuario.getText();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JTextField();
        campoContrasena = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jLabel1.setText("Usuario");
        jLabel2.setText("Contraseña");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(evt -> limpiarCampos());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(btnAceptar))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombreUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(campoContrasena)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        String usuarioInput = nombreUsuario.getText().trim();
        String passwordInput = new String(campoContrasena.getPassword()).trim();

        javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        UserInterface ui = (UserInterface) ventana;

        // 1. Modo Administrador (admin / 1234 o tabla Usuario)
        if (usuarioInput.equalsIgnoreCase("admin") && passwordInput.equals("1234")) {
            ui.configurarPermisos(true);
            ui.principal.setUsuario("Hola, ADMIN");
            ui.principal.ocultarTabla();
            ui.mostrarPantalla("menu");
            return;
        }

        String sqlAdmin = "SELECT * FROM public.\"Usuario\"";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement pst = con.prepareStatement(sqlAdmin);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String userDB = rs.getString(1).trim();
                String passDB = rs.getString(2).trim();
                if (usuarioInput.equals(userDB) && passwordInput.equals(passDB)) {
                    ui.configurarPermisos(true);
                    ui.principal.setUsuario("Hola, " + userDB.toUpperCase());
                    ui.principal.ocultarTabla();
                    ui.mostrarPantalla("menu");
                    return;
                }
            }
        } catch (SQLException e) {
            // Si la tabla Usuario tiene otra estructura, continúa con Empleados y Doctores
        }

        // 2. Modo Empleada (Beatriz: 1 / 7021972 | Janet: 2 / 31101987)
        String sqlEmp = "SELECT * FROM public.\"Empleado\"";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement pst = con.prepareStatement(sqlEmp);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String codigoDB = rs.getString(1).trim();
                String nombreCompleto = rs.getString(2).trim();
                String passDB = rs.getString(6).trim();

                if (usuarioInput.equals(codigoDB) && passwordInput.equals(passDB)) {
                    String primerNombre = nombreCompleto.split(" ")[0].toUpperCase();
                    ui.configurarPermisos(false);
                    ui.principal.setUsuario("Hola, " + primerNombre);
                    ui.principal.ocultarTabla();
                    ui.mostrarPantalla("menu");
                    return;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al autenticar empleado:\n" + e.getMessage());
        }

        // 3. Modo Doctor (ej. Dr. Josué: 3 / 2031984)
        String sqlDoc = "SELECT \"Codigo_Doc\", \"Nombre_Doc\", \"Contraseña_Doc\" FROM public.\"Doctor\"";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement pst = con.prepareStatement(sqlDoc);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String codigoDoc = rs.getString(1).trim();
                String nombreDoc = rs.getString(2).trim();
                String passDoc = rs.getString(3).trim();

                if (usuarioInput.equals(codigoDoc) && passwordInput.equals(passDoc)) {
                    ui.configurarPermisosDoctor(nombreDoc);
                    ui.principal.setUsuario("Hola, DR. " + nombreDoc.split(" ")[0].toUpperCase());
                    ui.principal.ocultarTabla();
                    ui.mostrarPantalla("menu");
                    return;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al autenticar doctor:\n" + e.getMessage());
        }

        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
    }

    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPasswordField campoContrasena;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nombreUsuario;
}