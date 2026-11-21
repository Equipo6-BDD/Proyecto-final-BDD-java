import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
        setLocationRelativeTo(null);
    }
    
    public void mostrarPantalla(String nombre) {
        java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
        cl.show(panelPrincipal, nombre);
        jMenuBar1.setVisible(!nombre.equals("inicio"));
        pack();
        setLocationRelativeTo(null);
    }

    public void configurarPermisos(boolean esAdmin) {
        menuEmpleados.setVisible(esAdmin);
        menuDoctores.setVisible(true);
        opcionAgregarDoctor.setVisible(esAdmin);
        menuPacientes.setVisible(true);
        opcionAgregarPaciente.setVisible(true);
    }
    
    public IngresoUsuario getIngreso() {
        return ingreso;
    }

    private Date convertirFecha(String textoFecha) {
        String limpio = textoFecha.trim().toUpperCase().replace("/", "-");
        String[] partes = limpio.split("-");
        if (partes.length == 3) {
            if (partes[0].length() == 4) {
                return Date.valueOf(limpio);
            }
            Map<String, Integer> meses = new HashMap<>();
            meses.put("ENE", 1); meses.put("FEB", 2); meses.put("MAR", 3); meses.put("ABR", 4);
            meses.put("MAY", 5); meses.put("JUN", 6); meses.put("JUL", 7); meses.put("AGO", 8);
            meses.put("SEP", 9); meses.put("OCT", 10); meses.put("NOV", 11); meses.put("DIC", 12);

            int dia = Integer.parseInt(partes[0]);
            int mes = meses.containsKey(partes[1]) ? meses.get(partes[1]) : Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            return Date.valueOf(LocalDate.of(anio, mes, dia));
        }
        throw new IllegalArgumentException("Formato inválido. Usa DD-MM-AAAA o DD-MES-AAAA (ej. 14-AGO-1967)");
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
        menuPacientes = new javax.swing.JMenu();
        opcionAgregarPaciente = new javax.swing.JMenuItem();
        consultasGeneralesPacientes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Núcleo de Diagnóstico");

        panelPrincipal.setLayout(new java.awt.CardLayout());

        menuEmpleados.setText("Empleados");

        opcionAgregarEmpleado.setText("Insertar (Agregar)");
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

        menuPacientes.setText("Pacientes");

        opcionAgregarPaciente.setText("Insertar (Dar de alta)");
        opcionAgregarPaciente.addActionListener(this::opcionAgregarPacienteActionPerformed);
        menuPacientes.add(opcionAgregarPaciente);

        consultasGeneralesPacientes.setText("Consultas Generales");
        consultasGeneralesPacientes.addActionListener(this::consultasGeneralesPacientesActionPerformed);
        menuPacientes.add(consultasGeneralesPacientes);

        jMenuBar1.add(menuPacientes);

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

    // Formulario VERTICAL para Insertar Empleado
    private void opcionAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAgregarEmpleadoActionPerformed
        JDialog dialogo = new JDialog(this, "Insertar Empleado", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(9, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));

        JTextField txtCodigo = new JTextField(16);
        JTextField txtNombre = new JTextField(16);
        JTextField txtDireccion = new JTextField(16);
        JTextField txtTelefono = new JTextField(16);
        JTextField txtFechaNac = new JTextField(16);
        JComboBox<String> cbSexo = new JComboBox<>(new String[]{"FEMENINO", "MASCULINO"});
        JTextField txtSalario = new JTextField(16);
        JComboBox<String> cbTurno = new JComboBox<>(new String[]{"MATUTINO", "VESPERTINO"});
        JTextField txtPass = new JTextField(16);

        panelCampos.add(new JLabel("Código:")); panelCampos.add(txtCodigo);
        panelCampos.add(new JLabel("Nombre:")); panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Dirección:")); panelCampos.add(txtDireccion);
        panelCampos.add(new JLabel("Teléfono:")); panelCampos.add(txtTelefono);
        panelCampos.add(new JLabel("Fecha Nac (DD-MM-AAAA):")); panelCampos.add(txtFechaNac);
        panelCampos.add(new JLabel("Sexo:")); panelCampos.add(cbSexo);
        panelCampos.add(new JLabel("Salario:")); panelCampos.add(txtSalario);
        panelCampos.add(new JLabel("Turno:")); panelCampos.add(cbTurno);
        panelCampos.add(new JLabel("Contraseña:")); panelCampos.add(txtPass);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Insertar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try (Connection con = ConexionDB.conectar();
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM public.\"Empleado\" LIMIT 1")) {

                ResultSetMetaData meta = rs.getMetaData();
                StringBuilder cols = new StringBuilder();
                for (int i = 1; i <= 9; i++) {
                    if (i > 1) cols.append(", ");
                    cols.append("\"").append(meta.getColumnName(i)).append("\"");
                }

                String sql = "INSERT INTO public.\"Empleado\" (" + cols + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setInt(1, Integer.parseInt(txtCodigo.getText().trim()));
                    pst.setString(2, txtNombre.getText().trim());
                    pst.setString(3, cbSexo.getSelectedItem().toString().substring(0, 1));
                    pst.setString(4, cbTurno.getSelectedItem().toString().substring(0, 1));
                    pst.setBigDecimal(5, new java.math.BigDecimal(txtSalario.getText().trim()));
                    pst.setString(6, txtPass.getText().trim());
                    pst.setString(7, txtDireccion.getText().trim());
                    pst.setString(8, txtTelefono.getText().trim());
                    pst.setDate(9, convertirFecha(txtFechaNac.getText()));

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(dialogo, "¡Empleado insertado correctamente!");
                    dialogo.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al insertar empleado:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }//GEN-LAST:event_opcionAgregarEmpleadoActionPerformed

    private void consultasGeneralesEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultasGeneralesEmpleadosActionPerformed
        String sql = "SELECT * FROM public.\"Empleado\" ORDER BY 1 ASC";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }//GEN-LAST:event_consultasGeneralesEmpleadosActionPerformed

    // Formulario VERTICAL para Insertar Doctor
    private void opcionAgregarDoctorActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        JDialog dialogo = new JDialog(this, "Insertar Doctor", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(8, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));

        JTextField txtCodigo = new JTextField(16);
        JTextField txtNombre = new JTextField(16);
        JTextField txtDireccion = new JTextField(16);
        JTextField txtTelefono = new JTextField(16);
        JTextField txtFechaNac = new JTextField(16);
        JComboBox<String> cbSexo = new JComboBox<>(new String[]{"MASCULINO", "FEMENINO"});
        JTextField txtEspecialidad = new JTextField(16);
        JTextField txtPass = new JTextField(16);

        panelCampos.add(new JLabel("Código:")); panelCampos.add(txtCodigo);
        panelCampos.add(new JLabel("Nombre:")); panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Dirección:")); panelCampos.add(txtDireccion);
        panelCampos.add(new JLabel("Teléfono:")); panelCampos.add(txtTelefono);
        panelCampos.add(new JLabel("Fecha Nac (DD-MM-AAAA):")); panelCampos.add(txtFechaNac);
        panelCampos.add(new JLabel("Sexo:")); panelCampos.add(cbSexo);
        panelCampos.add(new JLabel("Especialidad:")); panelCampos.add(txtEspecialidad);
        panelCampos.add(new JLabel("Contraseña:")); panelCampos.add(txtPass);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Insertar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            String sql = "INSERT INTO public.\"Doctor\" "
                       + "(\"Nombre_Doc\", \"DireccDoc\", \"Num_Doc\", \"Fecha_NacDoc\", \"SexoDoc\", \"Especial\", \"Contraseña_Doc\", \"Codigo_Doc\") "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setString(1, txtNombre.getText().trim());
                pst.setString(2, txtDireccion.getText().trim());
                pst.setString(3, txtTelefono.getText().trim());
                pst.setDate(4, convertirFecha(txtFechaNac.getText()));
                pst.setString(5, cbSexo.getSelectedItem().toString().substring(0, 1));
                pst.setString(6, txtEspecialidad.getText().trim());
                pst.setString(7, txtPass.getText().trim());
                pst.setInt(8, Integer.parseInt(txtCodigo.getText().trim()));

                pst.executeUpdate();
                JOptionPane.showMessageDialog(dialogo, "¡Doctor insertado correctamente!");
                dialogo.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al insertar doctor:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }                                                   

    private void consultasGeneralesDoctoresActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        String sql = "SELECT * FROM public.\"Doctor\" ORDER BY \"Codigo_Doc\" ASC";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }                                                          

    // Formulario VERTICAL para Insertar Paciente
    private void opcionAgregarPacienteActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Dar de Alta Paciente", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(8, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));

        JTextField txtCodigo = new JTextField(16);
        JTextField txtNombre = new JTextField(16);
        JTextField txtDireccion = new JTextField(16);
        JTextField txtTelefono = new JTextField(16);
        JTextField txtFechaNac = new JTextField(16);
        JComboBox<String> cbSexo = new JComboBox<>(new String[]{"MASCULINO", "FEMENINO"});
        JTextField txtEdad = new JTextField(16);
        JTextField txtEstatura = new JTextField(16);

        panelCampos.add(new JLabel("Código:")); panelCampos.add(txtCodigo);
        panelCampos.add(new JLabel("Nombre:")); panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Dirección:")); panelCampos.add(txtDireccion);
        panelCampos.add(new JLabel("Teléfono:")); panelCampos.add(txtTelefono);
        panelCampos.add(new JLabel("Fecha Nac (ej. 14-AGO-1967):")); panelCampos.add(txtFechaNac);
        panelCampos.add(new JLabel("Sexo:")); panelCampos.add(cbSexo);
        panelCampos.add(new JLabel("Edad:")); panelCampos.add(txtEdad);
        panelCampos.add(new JLabel("Estatura (ej. 1.77):")); panelCampos.add(txtEstatura);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Insertar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            String sql = "INSERT INTO public.\"Paciente\" "
                       + "(\"ID_paciente\", \"Nombre_paciente\", \"Direccion_paciente\", \"Telefono_paciente\", \"Sexo_paciente\", \"Edad_paciente\", \"Estatura_paciente\", \"Fecha_nac_paciente\") "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = ConexionDB.conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {

                pst.setInt(1, Integer.parseInt(txtCodigo.getText().trim()));
                pst.setString(2, txtNombre.getText().trim());
                pst.setString(3, txtDireccion.getText().trim());
                pst.setString(4, txtTelefono.getText().trim());
                pst.setString(5, cbSexo.getSelectedItem().toString().substring(0, 1));
                pst.setInt(6, Integer.parseInt(txtEdad.getText().trim()));
                pst.setDouble(7, Double.parseDouble(txtEstatura.getText().trim()));
                pst.setDate(8, convertirFecha(txtFechaNac.getText()));

                pst.executeUpdate();
                JOptionPane.showMessageDialog(dialogo, "¡Paciente registrado correctamente en PostgreSQL!");
                dialogo.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al insertar paciente:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void consultasGeneralesPacientesActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT * FROM public.\"Paciente\" ORDER BY \"ID_paciente\" ASC";
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
    private javax.swing.JMenuItem consultasGeneralesPacientes;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuDoctores;
    private javax.swing.JMenu menuEmpleados;
    private javax.swing.JMenu menuPacientes;
    private javax.swing.JMenuItem opcionAgregarDoctor;
    private javax.swing.JMenuItem opcionAgregarEmpleado;
    private javax.swing.JMenuItem opcionAgregarPaciente;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}