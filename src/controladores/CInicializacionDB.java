package controladores;

import java.sql.*;

import conexion.ConexionBDSQLServer;

public class CInicializacionBD {

    public static boolean existeBD() {
        try (Connection con = ConexionBDSQLServer.GetConexionMaster();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT name FROM sys.databases WHERE name = 'TaqueriaBD'"
             );
             ResultSet rs = ps.executeQuery()) {

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error verificando BD: " + e.getMessage());
            return false;
        }
    }

    public static void inicializarSiNoExiste() {

        if (!existeBD()) {
            System.out.println("Inicializando base de datos...");
            ejecutarScript();
        }
    }

    private static void ejecutarScript() {

        try (Connection con = ConexionBDSQLServer.GetConexion();
             Statement st = con.createStatement()) {

            // Aquí metes tus CREATE TABLE uno por uno
            st.executeUpdate("CREATE TABLE Producto (...)");
            st.executeUpdate("CREATE TABLE Empleado (...)");

            // etc...

            st.executeUpdate("INSERT INTO Empleado (nombre, puesto, activo) VALUES ('Administrador General','Administrador',1)");
            st.executeUpdate("INSERT INTO Usuario (usuario,password,rol,id_empleado) VALUES ('admin','admin','ADMIN',1)");

        } catch (Exception e) {
            System.out.println("Error inicializando BD: " + e.getMessage());
        }
    }
}