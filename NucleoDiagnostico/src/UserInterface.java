import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
        menuCitas.setVisible(true);
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
        throw new IllegalArgumentException("Formato inválido. Usa DD-MM-AAAA o DD-MES-AAAA (ej. 23-NOV-2026)");
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
        menuCitas = new javax.swing.JMenu();
        opcionAgregarCita = new javax.swing.JMenuItem();
        opcionModificarCita = new javax.swing.JMenuItem();
        consultasGeneralesCitas = new javax.swing.JMenuItem();

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

        menuCitas.setText("Citas");

        opcionAgregarCita.setText("Registrar Cita (Dar de alta)");
        opcionAgregarCita.addActionListener(this::opcionAgregarCitaActionPerformed);
        menuCitas.add(opcionAgregarCita);

        opcionModificarCita.setText("Modificar / Actualizar Cita");
        opcionModificarCita.addActionListener(this::opcionModificarCitaActionPerformed);
        menuCitas.add(opcionModificarCita);

        consultasGeneralesCitas.setText("Consultas Generales");
        consultasGeneralesCitas.addActionListener(this::consultasGeneralesCitasActionPerformed);
        menuCitas.add(consultasGeneralesCitas);

        jMenuBar1.add(menuCitas);

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
    private void opcionAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {
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
    }

    private void consultasGeneralesEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT * FROM public.\"Empleado\" ORDER BY 1 ASC";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }

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

    // ==================== MÓDULO DE CITAS (AVANCE 4) ====================

    private void cargarAgendaDoctor(String nombreDoctor, JTable tablaAgenda) {
        String[] columnas = {"Horario", "LUNES 23/NOV", "MARTES 24/NOV", "MIÉRCOLES 25/NOV", "JUEVES 26/NOV", "VIERNES 27/NOV"};
        String[] horarios = {
            "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00",
            "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00"
        };
        String[] etiquetasHora = {
            "9:00 A 10:00", "10:00 A 11:00", "11:00 A 12:00", "12:00 A 1:00", "1:00 A 2:00",
            "2:00 A 3:00", "3:00 A 4:00", "4:00 A 5:00", "5:00 A 6:00", "6:00 A 7:00", "7:00 A 8:00"
        };
        String[] fechasSemana = {"2026-11-23", "2026-11-24", "2026-11-25", "2026-11-26", "2026-11-27"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < horarios.length; i++) {
            Object[] fila = new Object[6];
            fila[0] = etiquetasHora[i];
            if (i == 5 || i == 6) {
                for (int c = 1; c <= 5; c++) fila[c] = "COMIDA";
            } else {
                for (int c = 1; c <= 5; c++) fila[c] = "";
            }
            modelo.addRow(fila);
        }

        if (nombreDoctor != null && !nombreDoctor.isEmpty()) {
            String sql = "SELECT \"Fecha\", \"Hora\", \"Paciente\" FROM public.\"Cita\" WHERE \"Medico\" = ?";
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, nombreDoctor);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        String fechaStr = rs.getDate("Fecha").toString();
                        String horaStr = rs.getTime("Hora").toString();
                        String pac = rs.getString("Paciente");

                        int colIndex = -1;
                        for (int c = 0; c < fechasSemana.length; c++) {
                            if (fechasSemana[c].equals(fechaStr)) {
                                colIndex = c + 1;
                                break;
                            }
                        }
                        int rowIndex = -1;
                        for (int r = 0; r < horarios.length; r++) {
                            if (horarios[r].equals(horaStr)) {
                                rowIndex = r;
                                break;
                            }
                        }
                        if (colIndex != -1 && rowIndex != -1) {
                            modelo.setValueAt("OCUPADO: " + pac, rowIndex, colIndex);
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar agenda:\n" + ex.getMessage());
            }
        }
        tablaAgenda.setModel(modelo);
    }

    // Registrar Cita + Agenda Semanal del Doctor en vivo
    private void opcionAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Registrar Cita y Consultar Agenda", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JComboBox<String> cbDoctor = new JComboBox<>();
        JComboBox<String> cbPaciente = new JComboBox<>();
        JTextField txtFecha = new JTextField("23-11-2026", 14);
        JComboBox<String> cbHora = new JComboBox<>(new String[]{
            "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00",
            "16:00:00", "17:00:00", "18:00:00", "19:00:00"
        });

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT \"Nombre_Doc\" FROM public.\"Doctor\" ORDER BY \"Codigo_Doc\" ASC")) {
                while (rs.next()) cbDoctor.addItem(rs.getString(1).trim());
            }
            try (ResultSet rs = st.executeQuery("SELECT \"Nombre_paciente\" FROM public.\"Paciente\" ORDER BY \"ID_paciente\" ASC")) {
                while (rs.next()) cbPaciente.addItem(rs.getString(1).trim());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialogo, "Error al cargar doctores/pacientes:\n" + ex.getMessage());
        }

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(new EmptyBorder(15, 20, 10, 20));
        panelForm.add(new JLabel("Doctor:")); panelForm.add(cbDoctor);
        panelForm.add(new JLabel("Paciente:")); panelForm.add(cbPaciente);
        panelForm.add(new JLabel("Fecha (DD-MM-AAAA):")); panelForm.add(txtFecha);
        panelForm.add(new JLabel("Hora (HH:MM:SS):")); panelForm.add(cbHora);

        JTable tablaAgenda = new JTable();
        JScrollPane scrollAgenda = new JScrollPane(tablaAgenda);
        scrollAgenda.setPreferredSize(new Dimension(820, 230));
        scrollAgenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Agenda Semanal del Doctor Seleccionado"));

        if (cbDoctor.getItemCount() > 0) {
            cargarAgendaDoctor((String) cbDoctor.getSelectedItem(), tablaAgenda);
        }

        cbDoctor.addActionListener(e -> cargarAgendaDoctor((String) cbDoctor.getSelectedItem(), tablaAgenda));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Registrar Cita");
        JButton btnCancelar = new JButton("Cerrar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            String medico = (String) cbDoctor.getSelectedItem();
            String paciente = (String) cbPaciente.getSelectedItem();
            String horaStr = (String) cbHora.getSelectedItem();

            try (Connection con = ConexionDB.conectar()) {
                Date fechaSQL = convertirFecha(txtFecha.getText());
                Time horaSQL = Time.valueOf(horaStr);

                // 1. Verificar si el doctor ya tiene cita en esa fecha y hora
                String sqlCheckDoc = "SELECT \"Paciente\" FROM public.\"Cita\" WHERE \"Medico\" = ? AND \"Fecha\" = ? AND \"Hora\" = ?";
                try (PreparedStatement pst = con.prepareStatement(sqlCheckDoc)) {
                    pst.setString(1, medico);
                    pst.setDate(2, fechaSQL);
                    pst.setTime(3, horaSQL);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(dialogo,
                                "NO se puede registrar la cita.\nEl doctor " + medico + " ya tiene cita en ese horario con: " + rs.getString(1),
                                "Horario Ocupado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                // 2. Verificar si el paciente ya tiene cita en esa fecha y hora con otro doctor
                String sqlCheckPac = "SELECT \"Medico\" FROM public.\"Cita\" WHERE \"Paciente\" = ? AND \"Fecha\" = ? AND \"Hora\" = ?";
                try (PreparedStatement pst = con.prepareStatement(sqlCheckPac)) {
                    pst.setString(1, paciente);
                    pst.setDate(2, fechaSQL);
                    pst.setTime(3, horaSQL);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(dialogo,
                                "NO se puede registrar la cita.\nEl paciente " + paciente + " ya tiene cita a esa misma hora con: " + rs.getString(1),
                                "Paciente Ocupado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                // 3. Insertar cita en PostgreSQL
                String sqlInsert = "INSERT INTO public.\"Cita\" (\"Medico\", \"Paciente\", \"Hora\", \"Fecha\") VALUES (?, ?, ?, ?)";
                try (PreparedStatement pst = con.prepareStatement(sqlInsert)) {
                    pst.setString(1, medico);
                    pst.setString(2, paciente);
                    pst.setTime(3, horaSQL);
                    pst.setDate(4, fechaSQL);
                    pst.executeUpdate();
                }

                JOptionPane.showMessageDialog(dialogo, "¡Cita registrada correctamente en PostgreSQL!");
                cargarAgendaDoctor(medico, tablaAgenda);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al registrar cita:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        dialogo.add(panelForm, BorderLayout.NORTH);
        dialogo.add(scrollAgenda, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    // Modificar / Actualizar Cita (Paso 7)
    private void opcionModificarCitaActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Modificar / Actualizar Cita", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JComboBox<String> cbCitas = new JComboBox<>();
        Map<String, Integer> mapaIds = new HashMap<>();
        Map<String, String> mapaMedicos = new HashMap<>();
        Map<String, String> mapaPacientes = new HashMap<>();

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT \"ID_Cita\", \"Fecha\", \"Hora\", \"Medico\", \"Paciente\" FROM public.\"Cita\" ORDER BY \"ID_Cita\" ASC")) {
            while (rs.next()) {
                int id = rs.getInt("ID_Cita");
                String med = rs.getString("Medico").trim();
                String pac = rs.getString("Paciente").trim();
                String etiqueta = "ID " + id + " | " + pac + " con " + med + " (" + rs.getDate("Fecha") + " " + rs.getTime("Hora") + ")";
                cbCitas.addItem(etiqueta);
                mapaIds.put(etiqueta, id);
                mapaMedicos.put(etiqueta, med);
                mapaPacientes.put(etiqueta, pac);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialogo, "Error al cargar citas:\n" + ex.getMessage());
        }

        if (cbCitas.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay citas registradas para modificar.");
            return;
        }

        JTextField txtNuevaFecha = new JTextField("23-11-2026", 14);
        JComboBox<String> cbNuevaHora = new JComboBox<>(new String[]{
            "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00",
            "16:00:00", "17:00:00", "18:00:00", "19:00:00"
        });

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));
        panelCampos.add(new JLabel("Seleccionar Cita:")); panelCampos.add(cbCitas);
        panelCampos.add(new JLabel("Nueva Fecha (DD-MM-AAAA):")); panelCampos.add(txtNuevaFecha);
        panelCampos.add(new JLabel("Nueva Hora (HH:MM:SS):")); panelCampos.add(cbNuevaHora);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnActualizar = new JButton("Actualizar Cita");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);

        btnActualizar.addActionListener(e -> {
            String seleccion = (String) cbCitas.getSelectedItem();
            int idCita = mapaIds.get(seleccion);
            String medico = mapaMedicos.get(seleccion);
            String paciente = mapaPacientes.get(seleccion);
            String horaStr = (String) cbNuevaHora.getSelectedItem();

            try (Connection con = ConexionDB.conectar()) {
                Date fechaSQL = convertirFecha(txtNuevaFecha.getText());
                Time horaSQL = Time.valueOf(horaStr);

                // Verificar disponibilidad antes de actualizar
                String sqlCheck = "SELECT \"ID_Cita\" FROM public.\"Cita\" WHERE (\"Medico\" = ? OR \"Paciente\" = ?) AND \"Fecha\" = ? AND \"Hora\" = ? AND \"ID_Cita\" <> ?";
                try (PreparedStatement pst = con.prepareStatement(sqlCheck)) {
                    pst.setString(1, medico);
                    pst.setString(2, paciente);
                    pst.setDate(3, fechaSQL);
                    pst.setTime(4, horaSQL);
                    pst.setInt(5, idCita);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(dialogo, "NO se puede cambiar: ese horario ya está ocupado.", "Conflicto de Horario", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                String sqlUpdate = "UPDATE public.\"Cita\" SET \"Fecha\" = ?, \"Hora\" = ? WHERE \"ID_Cita\" = ?";
                try (PreparedStatement pst = con.prepareStatement(sqlUpdate)) {
                    pst.setDate(1, fechaSQL);
                    pst.setTime(2, horaSQL);
                    pst.setInt(3, idCita);
                    pst.executeUpdate();
                }

                JOptionPane.showMessageDialog(dialogo, "¡Cita actualizada correctamente en PostgreSQL!");
                dialogo.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al actualizar cita:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void consultasGeneralesCitasActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT \"ID_Cita\", \"Fecha\", \"Hora\", \"Medico\", \"Paciente\" FROM public.\"Cita\" ORDER BY \"ID_Cita\" ASC";
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
    private javax.swing.JMenuItem consultasGeneralesCitas;
    private javax.swing.JMenuItem consultasGeneralesDoctores;
    private javax.swing.JMenuItem consultasGeneralesEmpleados;
    private javax.swing.JMenuItem consultasGeneralesPacientes;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuCitas;
    private javax.swing.JMenu menuDoctores;
    private javax.swing.JMenu menuEmpleados;
    private javax.swing.JMenu menuPacientes;
    private javax.swing.JMenuItem opcionAgregarCita;
    private javax.swing.JMenuItem opcionAgregarDoctor;
    private javax.swing.JMenuItem opcionAgregarEmpleado;
    private javax.swing.JMenuItem opcionAgregarPaciente;
    private javax.swing.JMenuItem opcionModificarCita;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}