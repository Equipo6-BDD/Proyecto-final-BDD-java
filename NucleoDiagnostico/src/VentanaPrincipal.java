import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends javax.swing.JPanel {

    public VentanaPrincipal() {
        initComponents();
        panelDatos.setVisible(false);
    }

    public void setUsuario(String usuario) {
        LabelUsuario.setText(usuario);
    }

    public void ocultarTabla() {
        panelDatos.setVisible(false);
    }

    public void mostrarTabla(String[] columnas, Object[][] datos) {
        panelDatos.setVisible(true);
        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }

    public void cargarTablaDesdeSQL(String sql) {
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int numColumnas = meta.getColumnCount();

            DefaultTableModel modelo = new DefaultTableModel();
            for (int i = 1; i <= numColumnas; i++) {
                modelo.addColumn(meta.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[numColumnas];
                for (int i = 0; i < numColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }

            panelDatos.setVisible(true);
            tablaDatos.setModel(modelo);
            tablaDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar PostgreSQL:\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LabelUsuario = new javax.swing.JLabel();
        imagenEmpotrada = new javax.swing.JLabel();
        panelDatos = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        btnCerrarSesion = new javax.swing.JButton();

        LabelUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        LabelUsuario.setText("UsuarioLoggeado");

        imagenEmpotrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icono.png"))); // NOI18N

        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.addActionListener(evt -> {
            javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            UserInterface ui = (UserInterface) ventana;
            ui.getIngreso().limpiarCampos();
            ocultarTabla();
            ui.mostrarPantalla("inicio");
        });

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Title 1", "Title 2", "Title 3", "Title 4" }
        ));
        panelDatos.setViewportView(tablaDatos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imagenEmpotrada, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrarSesion)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(imagenEmpotrada, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelUsuario)
                            .addComponent(btnCerrarSesion))))
                .addGap(18, 18, 18)
                .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelUsuario;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JLabel imagenEmpotrada;
    private javax.swing.JScrollPane panelDatos;
    private javax.swing.JTable tablaDatos;
    // End of variables declaration//GEN-END:variables
}