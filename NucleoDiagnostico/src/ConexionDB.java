import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionDB {
    
    // Ajusta el nombre de tu base de datos y tu contraseña de PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/nucleo_diagnostico";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a PostgreSQL!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se encontró el driver:\n" + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a PostgreSQL:\n" + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        if (conectar() != null) {
            JOptionPane.showMessageDialog(null, "¡Conectado correctamente a PostgreSQL!");
        }
    }
}