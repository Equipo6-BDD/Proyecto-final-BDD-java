import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class UserInterface extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserInterface.class.getName());
    IngresoUsuario ingreso = new IngresoUsuario();
    VentanaPrincipal principal = new VentanaPrincipal();
    private String doctorActual = "";

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
        doctorActual = "";
        menuEmpleados.setVisible(esAdmin);
        menuDoctores.setVisible(true);
        opcionAgregarDoctor.setVisible(esAdmin);
        menuPacientes.setVisible(true);
        opcionAgregarPaciente.setVisible(true);
        menuCitas.setVisible(true);
        opcionAgregarCita.setVisible(true);
        opcionModificarCita.setVisible(true);
        menuMedicamentos.setVisible(esAdmin);
        menuConsultas.setVisible(false);
    }

    public void configurarPermisosDoctor(String nombreDoctor) {
        doctorActual = nombreDoctor;
        menuEmpleados.setVisible(false);
        menuDoctores.setVisible(false);
        menuPacientes.setVisible(true);
        opcionAgregarPaciente.setVisible(false);
        menuCitas.setVisible(true);
        opcionAgregarCita.setVisible(false);
        opcionModificarCita.setVisible(false);
        menuMedicamentos.setVisible(false);
        menuConsultas.setVisible(true);
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
        throw new IllegalArgumentException("Formato inválido. Usa DD-MM-AAAA (ej. 31-01-2027)");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();

        menuEmpleados = new javax.swing.JMenu("Empleados");
        opcionAgregarEmpleado = new javax.swing.JMenuItem("Insertar (Agregar)");
        consultasGeneralesEmpleados = new javax.swing.JMenuItem("Consultas Generales");

        menuDoctores = new javax.swing.JMenu("Doctores");
        opcionAgregarDoctor = new javax.swing.JMenuItem("Insertar Doctor");
        consultasGeneralesDoctores = new javax.swing.JMenuItem("Consultas Generales");

        menuPacientes = new javax.swing.JMenu("Pacientes");
        opcionAgregarPaciente = new javax.swing.JMenuItem("Insertar (Dar de alta)");
        consultasGeneralesPacientes = new javax.swing.JMenuItem("Ver Paciente / Consultas Generales");

        menuCitas = new javax.swing.JMenu("Citas");
        opcionAgregarCita = new javax.swing.JMenuItem("Registrar Cita (Dar de alta)");
        opcionModificarCita = new javax.swing.JMenuItem("Modificar / Actualizar Cita");
        consultasGeneralesCitas = new javax.swing.JMenuItem("Ver Citas");

        menuMedicamentos = new javax.swing.JMenu("Medicamentos");
        opcionAgregarMedicamento = new javax.swing.JMenuItem("Insertar (Dar de alta)");
        consultasGeneralesMedicamentos = new javax.swing.JMenuItem("Ver Medicamentos (Consultas Generales)");

        menuConsultas = new javax.swing.JMenu("Consultas");
        opcionRealizarConsulta = new javax.swing.JMenuItem("Realizar Consulta y Generar Receta");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Núcleo de Diagnóstico");
        panelPrincipal.setLayout(new java.awt.CardLayout());

        opcionAgregarEmpleado.addActionListener(this::opcionAgregarEmpleadoActionPerformed);
        consultasGeneralesEmpleados.addActionListener(this::consultasGeneralesEmpleadosActionPerformed);
        menuEmpleados.add(opcionAgregarEmpleado);
        menuEmpleados.add(consultasGeneralesEmpleados);
        jMenuBar1.add(menuEmpleados);

        opcionAgregarDoctor.addActionListener(this::opcionAgregarDoctorActionPerformed);
        consultasGeneralesDoctores.addActionListener(this::consultasGeneralesDoctoresActionPerformed);
        menuDoctores.add(opcionAgregarDoctor);
        menuDoctores.add(consultasGeneralesDoctores);
        jMenuBar1.add(menuDoctores);

        opcionAgregarPaciente.addActionListener(this::opcionAgregarPacienteActionPerformed);
        consultasGeneralesPacientes.addActionListener(this::consultasGeneralesPacientesActionPerformed);
        menuPacientes.add(opcionAgregarPaciente);
        menuPacientes.add(consultasGeneralesPacientes);
        jMenuBar1.add(menuPacientes);

        opcionAgregarCita.addActionListener(this::opcionAgregarCitaActionPerformed);
        opcionModificarCita.addActionListener(this::opcionModificarCitaActionPerformed);
        consultasGeneralesCitas.addActionListener(this::consultasGeneralesCitasActionPerformed);
        menuCitas.add(opcionAgregarCita);
        menuCitas.add(opcionModificarCita);
        menuCitas.add(consultasGeneralesCitas);
        jMenuBar1.add(menuCitas);

        opcionAgregarMedicamento.addActionListener(this::opcionAgregarMedicamentoActionPerformed);
        consultasGeneralesMedicamentos.addActionListener(this::consultasGeneralesMedicamentosActionPerformed);
        menuMedicamentos.add(opcionAgregarMedicamento);
        menuMedicamentos.add(consultasGeneralesMedicamentos);
        jMenuBar1.add(menuMedicamentos);

        opcionRealizarConsulta.addActionListener(this::opcionRealizarConsultaActionPerformed);
        menuConsultas.add(opcionRealizarConsulta);
        jMenuBar1.add(menuConsultas);

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
    }

    // ==================== EMPLEADOS / DOCTORES / PACIENTES ====================

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
        panelBotones.add(btnGuardar); panelBotones.add(btnCancelar);

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
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    private void consultasGeneralesEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        principal.cargarTablaDesdeSQL("SELECT * FROM public.\"Empleado\" ORDER BY 1 ASC");
        mostrarPantalla("menu");
    }

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
        panelBotones.add(btnGuardar); panelBotones.add(btnCancelar);

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
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    private void consultasGeneralesDoctoresActionPerformed(java.awt.event.ActionEvent evt) {
        principal.cargarTablaDesdeSQL("SELECT * FROM public.\"Doctor\" ORDER BY \"Codigo_Doc\" ASC");
        mostrarPantalla("menu");
    }

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
        panelBotones.add(btnGuardar); panelBotones.add(btnCancelar);

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
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    private void consultasGeneralesPacientesActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT * FROM public.\"Paciente\" ORDER BY \"ID_paciente\" ASC";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }

    // ==================== MÓDULO DE CITAS ====================

    private void cargarAgendaDoctor(String nombreDoctor, JTable tablaAgenda) {
        String[] columnas = {"Horario", "LUNES 23/NOV", "MARTES 24/NOV", "MIÉRCOLES 25/NOV", "JUEVES 26/NOV", "VIERNES 27/NOV"};
        String[] horarios = {"09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00"};
        String[] etiquetasHora = {"9:00 A 10:00", "10:00 A 11:00", "11:00 A 12:00", "12:00 A 1:00", "1:00 A 2:00", "2:00 A 3:00", "3:00 A 4:00", "4:00 A 5:00", "5:00 A 6:00", "6:00 A 7:00", "7:00 A 8:00"};
        String[] fechasSemana = {"2026-11-23", "2026-11-24", "2026-11-25", "2026-11-26", "2026-11-27"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        for (int i = 0; i < horarios.length; i++) {
            Object[] fila = new Object[6];
            fila[0] = etiquetasHora[i];
            for (int c = 1; c <= 5; c++) fila[c] = (i == 5 || i == 6) ? "COMIDA" : "";
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
                        int colIndex = -1, rowIndex = -1;
                        for (int c = 0; c < fechasSemana.length; c++) if (fechasSemana[c].equals(fechaStr)) colIndex = c + 1;
                        for (int r = 0; r < horarios.length; r++) if (horarios[r].equals(horaStr)) rowIndex = r;
                        if (colIndex != -1 && rowIndex != -1) modelo.setValueAt("OCUPADO: " + pac, rowIndex, colIndex);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar agenda:\n" + ex.getMessage());
            }
        }
        tablaAgenda.setModel(modelo);
    }

    private void opcionAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Registrar Cita y Consultar Agenda", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JComboBox<String> cbDoctor = new JComboBox<>();
        JComboBox<String> cbPaciente = new JComboBox<>();
        JTextField txtFecha = new JTextField("23-11-2026", 14);
        JComboBox<String> cbHora = new JComboBox<>(new String[]{"09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00"});

        try (Connection con = ConexionDB.conectar(); Statement st = con.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT \"Nombre_Doc\" FROM public.\"Doctor\" ORDER BY \"Codigo_Doc\" ASC")) {
                while (rs.next()) cbDoctor.addItem(rs.getString(1).trim());
            }
            try (ResultSet rs = st.executeQuery("SELECT \"Nombre_paciente\" FROM public.\"Paciente\" ORDER BY \"ID_paciente\" ASC")) {
                while (rs.next()) cbPaciente.addItem(rs.getString(1).trim());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialogo, "Error al cargar datos:\n" + ex.getMessage());
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

        if (cbDoctor.getItemCount() > 0) cargarAgendaDoctor((String) cbDoctor.getSelectedItem(), tablaAgenda);
        cbDoctor.addActionListener(e -> cargarAgendaDoctor((String) cbDoctor.getSelectedItem(), tablaAgenda));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Registrar Cita");
        JButton btnCancelar = new JButton("Cerrar");
        panelBotones.add(btnGuardar); panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            String medico = (String) cbDoctor.getSelectedItem();
            String paciente = (String) cbPaciente.getSelectedItem();
            String horaStr = (String) cbHora.getSelectedItem();
            try (Connection con = ConexionDB.conectar()) {
                Date fechaSQL = convertirFecha(txtFecha.getText());
                Time horaSQL = Time.valueOf(horaStr);

                String sqlCheckDoc = "SELECT \"Paciente\" FROM public.\"Cita\" WHERE \"Medico\" = ? AND \"Fecha\" = ? AND \"Hora\" = ?";
                try (PreparedStatement pst = con.prepareStatement(sqlCheckDoc)) {
                    pst.setString(1, medico); pst.setDate(2, fechaSQL); pst.setTime(3, horaSQL);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(dialogo, "NO se puede registrar la cita.\nEl doctor ya tiene cita con: " + rs.getString(1), "Horario Ocupado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }
                String sqlCheckPac = "SELECT \"Medico\" FROM public.\"Cita\" WHERE \"Paciente\" = ? AND \"Fecha\" = ? AND \"Hora\" = ?";
                try (PreparedStatement pst = con.prepareStatement(sqlCheckPac)) {
                    pst.setString(1, paciente); pst.setDate(2, fechaSQL); pst.setTime(3, horaSQL);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(dialogo, "NO se puede registrar la cita.\nEl paciente ya tiene cita con: " + rs.getString(1), "Paciente Ocupado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }
                String sqlInsert = "INSERT INTO public.\"Cita\" (\"Medico\", \"Paciente\", \"Hora\", \"Fecha\") VALUES (?, ?, ?, ?)";
                try (PreparedStatement pst = con.prepareStatement(sqlInsert)) {
                    pst.setString(1, medico); pst.setString(2, paciente); pst.setTime(3, horaSQL); pst.setDate(4, fechaSQL);
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
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    private void opcionModificarCitaActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Modificar / Actualizar Cita", true);
        dialogo.setLayout(new BorderLayout(10, 10));
        JComboBox<String> cbCitas = new JComboBox<>();
        Map<String, Integer> mapaIds = new HashMap<>();
        Map<String, String> mapaMedicos = new HashMap<>();
        Map<String, String> mapaPacientes = new HashMap<>();

        try (Connection con = ConexionDB.conectar(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT \"ID_Cita\", \"Fecha\", \"Hora\", \"Medico\", \"Paciente\" FROM public.\"Cita\" ORDER BY \"ID_Cita\" ASC")) {
            while (rs.next()) {
                int id = rs.getInt("ID_Cita");
                String med = rs.getString("Medico").trim();
                String pac = rs.getString("Paciente").trim();
                String etq = "ID " + id + " | " + pac + " con " + med + " (" + rs.getDate("Fecha") + " " + rs.getTime("Hora") + ")";
                cbCitas.addItem(etq); mapaIds.put(etq, id); mapaMedicos.put(etq, med); mapaPacientes.put(etq, pac);
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(dialogo, "Error: " + ex.getMessage()); }
        if (cbCitas.getItemCount() == 0) { JOptionPane.showMessageDialog(this, "No hay citas registradas."); return; }

        JTextField txtNuevaFecha = new JTextField("23-11-2026", 14);
        JComboBox<String> cbNuevaHora = new JComboBox<>(new String[]{"09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00"});
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));
        panelCampos.add(new JLabel("Seleccionar Cita:")); panelCampos.add(cbCitas);
        panelCampos.add(new JLabel("Nueva Fecha (DD-MM-AAAA):")); panelCampos.add(txtNuevaFecha);
        panelCampos.add(new JLabel("Nueva Hora (HH:MM:SS):")); panelCampos.add(cbNuevaHora);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnActualizar = new JButton("Actualizar Cita");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnActualizar); panelBotones.add(btnCancelar);

        btnActualizar.addActionListener(e -> {
            String sel = (String) cbCitas.getSelectedItem();
            int idCita = mapaIds.get(sel);
            try (Connection con = ConexionDB.conectar()) {
                Date fechaSQL = convertirFecha(txtNuevaFecha.getText());
                Time horaSQL = Time.valueOf((String) cbNuevaHora.getSelectedItem());
                String sqlUpdate = "UPDATE public.\"Cita\" SET \"Fecha\" = ?, \"Hora\" = ? WHERE \"ID_Cita\" = ?";
                try (PreparedStatement pst = con.prepareStatement(sqlUpdate)) {
                    pst.setDate(1, fechaSQL); pst.setTime(2, horaSQL); pst.setInt(3, idCita);
                    pst.executeUpdate();
                }
                JOptionPane.showMessageDialog(dialogo, "¡Cita actualizada correctamente en PostgreSQL!");
                dialogo.dispose();
            } catch (Exception ex) { JOptionPane.showMessageDialog(dialogo, "Error: " + ex.getMessage()); }
        });
        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER); dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    private void consultasGeneralesCitasActionPerformed(java.awt.event.ActionEvent evt) {
        String sql;
        if (doctorActual != null && !doctorActual.isEmpty()) {
            sql = "SELECT \"ID_Cita\", \"Fecha\", \"Hora\", \"Medico\", \"Paciente\" FROM public.\"Cita\" "
                + "WHERE \"Medico\" = '" + doctorActual.replace("'", "''") + "' ORDER BY \"ID_Cita\" ASC";
        } else {
            sql = "SELECT \"ID_Cita\", \"Fecha\", \"Hora\", \"Medico\", \"Paciente\" FROM public.\"Cita\" ORDER BY \"ID_Cita\" ASC";
        }
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }

    // ==================== MÓDULO DE MEDICAMENTOS (AVANCE 5) ====================

    private void opcionAgregarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Registrar Medicamento (Dar de alta)", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));

        JTextField txtCodigo = new JTextField(16);
        JTextField txtNombre = new JTextField(16);
        JTextField txtVia = new JTextField("ORAL", 16);
        JComboBox<String> cbPresentacion = new JComboBox<>(new String[]{"TABLETA", "CAPSULA", "JARABE", "INYECTABLE"});
        JTextField txtCaducidad = new JTextField("31-01-2027", 16);

        panelCampos.add(new JLabel("Código:")); panelCampos.add(txtCodigo);
        panelCampos.add(new JLabel("Nombre:")); panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Vía Administración:")); panelCampos.add(txtVia);
        panelCampos.add(new JLabel("Presentación:")); panelCampos.add(cbPresentacion);
        panelCampos.add(new JLabel("Fecha Caducidad (DD-MM-AAAA):")); panelCampos.add(txtCaducidad);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Insertar");
        JButton btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar); panelBotones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            String sql = "INSERT INTO public.\"Medicamento\" "
                       + "(\"ID_medicamento\", \"nombre\", \"via_adminis\", \"presentacion\", \"caducidad\") "
                       + "VALUES (?, ?, ?, ?, ?)";
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, Integer.parseInt(txtCodigo.getText().trim()));
                pst.setString(2, txtNombre.getText().trim());
                pst.setString(3, txtVia.getText().trim());
                pst.setString(4, cbPresentacion.getSelectedItem().toString());
                pst.setDate(5, convertirFecha(txtCaducidad.getText()));
                pst.executeUpdate();
                JOptionPane.showMessageDialog(dialogo, "¡Medicamento registrado correctamente en PostgreSQL!");
                dialogo.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al registrar medicamento:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    private void consultasGeneralesMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT * FROM public.\"Medicamento\" ORDER BY \"ID_medicamento\" ASC";
        principal.cargarTablaDesdeSQL(sql);
        mostrarPantalla("menu");
    }

    // ==================== MÓDULO DE CONSULTA Y RECETA PDF (AVANCE 5) ====================

    private void opcionRealizarConsultaActionPerformed(java.awt.event.ActionEvent evt) {
        JDialog dialogo = new JDialog(this, "Consulta Médica y Generación de Receta", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JComboBox<String> cbCitas = new JComboBox<>();
        Map<String, Integer> mapaIdCita = new HashMap<>();
        Map<String, String> mapaPaciente = new HashMap<>();

        JComboBox<String> cbMedicamentos = new JComboBox<>();
        Map<String, Integer> mapaIdMed = new HashMap<>();
        Map<String, String> mapaInfoMed = new HashMap<>();

        try (Connection con = ConexionDB.conectar()) {
            String sqlCitas = "SELECT \"ID_Cita\", \"Paciente\", \"Fecha\", \"Hora\" FROM public.\"Cita\" WHERE \"Medico\" = ? ORDER BY \"ID_Cita\" ASC";
            try (PreparedStatement pst = con.prepareStatement(sqlCitas)) {
                pst.setString(1, doctorActual);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("ID_Cita");
                        String pac = rs.getString("Paciente").trim();
                        String etq = "Cita #" + id + " | " + pac + " (" + rs.getDate("Fecha") + " " + rs.getTime("Hora") + ")";
                        cbCitas.addItem(etq);
                        mapaIdCita.put(etq, id);
                        mapaPaciente.put(etq, pac);
                    }
                }
            }
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT \"ID_medicamento\", \"nombre\", \"via_adminis\", \"presentacion\" FROM public.\"Medicamento\" ORDER BY \"ID_medicamento\" ASC")) {
                while (rs.next()) {
                    int idM = rs.getInt("ID_medicamento");
                    String nomM = rs.getString("nombre").trim();
                    String viaM = rs.getString("via_adminis").trim();
                    String presM = rs.getString("presentacion").trim();
                    String etqM = idM + " - " + nomM + " (" + presM + ", " + viaM + ")";
                    cbMedicamentos.addItem(etqM);
                    mapaIdMed.put(etqM, idM);
                    mapaInfoMed.put(etqM, nomM + " | Presentacion: " + presM + " | Via: " + viaM);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialogo, "Error al cargar citas/medicamentos:\n" + ex.getMessage());
        }

        if (cbCitas.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes citas registradas para consultar.");
            return;
        }
        if (cbMedicamentos.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay medicamentos registrados en el sistema.");
            return;
        }

        JTextArea txtDiagnostico = new JTextArea(4, 30);
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setWrapStyleWord(true);
        JTextField txtDias = new JTextField("7 dias", 20);
        JTextField txtHorario = new JTextField("Cada 8 horas", 20);

        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(15, 20, 10, 20));
        panelCampos.add(new JLabel("Cita / Paciente:")); panelCampos.add(cbCitas);
        panelCampos.add(new JLabel("Diagnóstico (Historial):")); panelCampos.add(new JScrollPane(txtDiagnostico));
        panelCampos.add(new JLabel("Medicamento:")); panelCampos.add(cbMedicamentos);
        panelCampos.add(new JLabel("Días de tratamiento:")); panelCampos.add(txtDias);
        panelCampos.add(new JLabel("Horario de suministro:")); panelCampos.add(txtHorario);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        JButton btnGuardarConsulta = new JButton("Guardar Consulta");
        JButton btnGenerarReceta = new JButton("GENERAR RECETA");
        JButton btnCerrar = new JButton("Cerrar");
        panelBotones.add(btnGuardarConsulta);
        panelBotones.add(btnGenerarReceta);
        panelBotones.add(btnCerrar);

        btnGuardarConsulta.addActionListener(e -> {
            String citaSel = (String) cbCitas.getSelectedItem();
            String medSel = (String) cbMedicamentos.getSelectedItem();
            int idCita = mapaIdCita.get(citaSel);
            int idMed = mapaIdMed.get(medSel);
            String historialCompleto = "Diagnostico: " + txtDiagnostico.getText().trim()
                    + " | Medicamento: " + medSel
                    + " | Tratamiento: " + txtDias.getText().trim()
                    + " | Horario: " + txtHorario.getText().trim();

            String sql = "INSERT INTO public.\"Consulta\" (\"ID_cita\", \"diagnostico\", \"ID_medicamento\") VALUES (?, ?, ?)";
            try (Connection con = ConexionDB.conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, idCita);
                pst.setString(2, historialCompleto);
                pst.setInt(3, idMed);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(dialogo, "¡Consulta y tratamiento guardados en PostgreSQL!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al guardar consulta:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGenerarReceta.addActionListener(e -> {
            String citaSel = (String) cbCitas.getSelectedItem();
            String medSel = (String) cbMedicamentos.getSelectedItem();
            String nombrePac = mapaPaciente.get(citaSel);
            String infoMed = mapaInfoMed.get(medSel);
            String diag = txtDiagnostico.getText().trim();
            String dias = txtDias.getText().trim();
            String horario = txtHorario.getText().trim();

            try {
                File archivo = new File("receta.pdf");
                generarRecetaPDF(archivo, nombrePac, doctorActual, diag, infoMed, dias, horario);
                JOptionPane.showMessageDialog(dialogo, "¡Receta generada exitosamente en:\n" + archivo.getAbsolutePath());
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(archivo);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al generar receta.pdf:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCerrar.addActionListener(e -> dialogo.dispose());
        dialogo.add(panelCampos, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack(); dialogo.setLocationRelativeTo(this); dialogo.setVisible(true);
    }

    // Generador nativo de receta.pdf (sin librerías externas)
    private void generarRecetaPDF(File destino, String paciente, String doctor, String diagnostico,
                                  String medicamento, String dias, String horario) throws Exception {
        byte[] jpegBytes = null;
        int imgW = 0, imgH = 0;
        java.net.URL urlLogo = getClass().getResource("/Imagenes/icono.png");
        if (urlLogo != null) {
            BufferedImage orig = ImageIO.read(urlLogo);
            BufferedImage rgb = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = rgb.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, rgb.getWidth(), rgb.getHeight());
            g.drawImage(orig, 0, 0, null);
            g.dispose();
            ByteArrayOutputStream imgOut = new ByteArrayOutputStream();
            ImageIO.write(rgb, "jpg", imgOut);
            jpegBytes = imgOut.toByteArray();
            imgW = rgb.getWidth();
            imgH = rgb.getHeight();
        }

        StringBuilder content = new StringBuilder();
        if (jpegBytes != null) {
            content.append("q 110 0 0 65 50 715 cm /Im1 Do Q\n");
        }
        content.append("BT\n");
        content.append("/F2 18 Tf 180 755 Td (NUCLEO DE DIAGNOSTICO) Tj\n");
        content.append("/F1 12 Tf 0 -22 Td (RECETA MEDICA) Tj\n");
        content.append("ET\n");
        content.append("50 700 m 560 700 l S\n");

        content.append("BT\n");
        content.append("/F2 12 Tf 50 670 Td (Fecha: ) Tj /F1 12 Tf (").append(escPdf(LocalDate.now().toString())).append(") Tj\n");
        content.append("/F2 12 Tf 0 -25 Td (Paciente: ) Tj /F1 12 Tf (").append(escPdf(paciente)).append(") Tj\n");
        content.append("/F2 12 Tf 0 -30 Td (Diagnostico: ) Tj /F1 12 Tf (").append(escPdf(diagnostico)).append(") Tj\n");
        content.append("/F2 12 Tf 0 -35 Td (Medicamento indicado:) Tj\n");
        content.append("/F1 12 Tf 0 -20 Td (  ").append(escPdf(medicamento)).append(") Tj\n");
        content.append("/F2 12 Tf 0 -25 Td (Duracion del tratamiento: ) Tj /F1 12 Tf (").append(escPdf(dias)).append(") Tj\n");
        content.append("/F2 12 Tf 0 -20 Td (Horario de suministro: ) Tj /F1 12 Tf (").append(escPdf(horario)).append(") Tj\n");
        content.append("/F2 12 Tf 0 -90 Td (Medico que realizo la consulta:) Tj\n");
        content.append("/F1 12 Tf 0 -20 Td (DR. ").append(escPdf(doctor)).append(") Tj\n");
        content.append("ET\n");

        byte[] contentBytes = content.toString().getBytes(StandardCharsets.ISO_8859_1);
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>();

        pdf.write("%PDF-1.4\n".getBytes(StandardCharsets.US_ASCII));

        // 1: Catalog
        offsets.add(pdf.size());
        pdf.write("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n".getBytes(StandardCharsets.US_ASCII));

        // 2: Pages
        offsets.add(pdf.size());
        pdf.write("2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n".getBytes(StandardCharsets.US_ASCII));

        // 3: Page
        offsets.add(pdf.size());
        String xobj = (jpegBytes != null) ? "/XObject << /Im1 7 0 R >>" : "";
        pdf.write(("3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 6 0 R "
                + "/Resources << /Font << /F1 4 0 R /F2 5 0 R >> " + xobj + " >> >> endobj\n").getBytes(StandardCharsets.US_ASCII));

        // 4: Font Helvetica
        offsets.add(pdf.size());
        pdf.write("4 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >> endobj\n".getBytes(StandardCharsets.US_ASCII));

        // 5: Font Helvetica-Bold
        offsets.add(pdf.size());
        pdf.write("5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >> endobj\n".getBytes(StandardCharsets.US_ASCII));

        // 6: Content stream
        offsets.add(pdf.size());
        pdf.write(("6 0 obj << /Length " + contentBytes.length + " >> stream\n").getBytes(StandardCharsets.US_ASCII));
        pdf.write(contentBytes);
        pdf.write("\nendstream endobj\n".getBytes(StandardCharsets.US_ASCII));

        // 7: Logo JPEG
        if (jpegBytes != null) {
            offsets.add(pdf.size());
            pdf.write(("7 0 obj << /Type /XObject /Subtype /Image /Width " + imgW + " /Height " + imgH
                    + " /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /DCTDecode /Length "
                    + jpegBytes.length + " >> stream\n").getBytes(StandardCharsets.US_ASCII));
            pdf.write(jpegBytes);
            pdf.write("\nendstream endobj\n".getBytes(StandardCharsets.US_ASCII));
        }

        int xrefPos = pdf.size();
        pdf.write(("xref\n0 " + (offsets.size() + 1) + "\n0000000000 65535 f \n").getBytes(StandardCharsets.US_ASCII));
        for (int off : offsets) {
            pdf.write(String.format("%010d 00000 n \n", off).getBytes(StandardCharsets.US_ASCII));
        }
        pdf.write(("trailer << /Size " + (offsets.size() + 1) + " /Root 1 0 R >>\nstartxref\n" + xrefPos + "\n%%EOF").getBytes(StandardCharsets.US_ASCII));

        try (FileOutputStream fos = new FileOutputStream(destino)) {
            fos.write(pdf.toByteArray());
        }
    }

    private String escPdf(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)")
                .replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u")
                .replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U")
                .replace("ñ", "n").replace("Ñ", "N");
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

    private javax.swing.JMenuItem consultasGeneralesCitas;
    private javax.swing.JMenuItem consultasGeneralesDoctores;
    private javax.swing.JMenuItem consultasGeneralesEmpleados;
    private javax.swing.JMenuItem consultasGeneralesMedicamentos;
    private javax.swing.JMenuItem consultasGeneralesPacientes;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuCitas;
    private javax.swing.JMenu menuConsultas;
    private javax.swing.JMenu menuDoctores;
    private javax.swing.JMenu menuEmpleados;
    private javax.swing.JMenu menuMedicamentos;
    private javax.swing.JMenu menuPacientes;
    private javax.swing.JMenuItem opcionAgregarCita;
    private javax.swing.JMenuItem opcionAgregarDoctor;
    private javax.swing.JMenuItem opcionAgregarEmpleado;
    private javax.swing.JMenuItem opcionAgregarMedicamento;
    private javax.swing.JMenuItem opcionAgregarPaciente;
    private javax.swing.JMenuItem opcionModificarCita;
    private javax.swing.JMenuItem opcionRealizarConsulta;
    private javax.swing.JPanel panelPrincipal;
}