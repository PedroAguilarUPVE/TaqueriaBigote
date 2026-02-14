package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import conexion.ConexionBDSQLServer;
import modelos.MInsumo;
import modelos.MInventario;

/**
 * CONTROLADOR INVENTARIO
 * Conectado a:
 * - Insumo
 * - Inventario
 */
public class CInventario {

    public static Connection Conexion = null;
    public static String sql;
    public static PreparedStatement sentencia;
    public static ResultSet rs;

    /* =====================================================
     * CARGAR INVENTARIO (JOIN CON INSUMO)
     * ===================================================== */
    public static List<MInventario> cargarInventario() {

        List<MInventario> lista = new ArrayList<>();

        try {

            Conexion = ConexionBDSQLServer.GetConexion();

            sql = """
                  SELECT i.idInventario,
                         ins.nombre,
                         ins.unidadMedida,
                         i.cantidadDisponible,
                         i.fechaActualizacion
                  FROM Inventario i
                  INNER JOIN Insumo ins ON i.idInsumo = ins.idInsumo
                  ORDER BY ins.nombre
                  """;

            sentencia = Conexion.prepareStatement(sql);
            rs = sentencia.executeQuery();

            while (rs.next()) {

                MInventario inv = new MInventario();

                inv.setIdInventario(rs.getInt("idInventario"));
                inv.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                inv.setFechaActualizacion(rs.getTimestamp("fechaActualizacion"));

                lista.add(inv);
            }


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "Error al cargar inventario\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }

    /* =====================================================
     * AGREGAR INSUMO AL INVENTARIO
     * ===================================================== */
    public static void agregarInventario(int idInsumo, double cantidad) {

        try {

            Conexion = ConexionBDSQLServer.GetConexion();

            // Verificar si ya existe en inventario
            sql = "SELECT COUNT(*) FROM Inventario WHERE idInsumo = ?";
            sentencia = Conexion.prepareStatement(sql);
            sentencia.setInt(1, idInsumo);

            rs = sentencia.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {

                JOptionPane.showMessageDialog(null,
                        "El insumo ya existe en inventario.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            sql = """
                  INSERT INTO Inventario (idInsumo, cantidadDisponible)
                  VALUES (?, ?)
                  """;

            sentencia = Conexion.prepareStatement(sql);
            sentencia.setInt(1, idInsumo);
            sentencia.setDouble(2, cantidad);

            sentencia.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Insumo agregado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "Error al agregar inventario\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /* =====================================================
     * ACTUALIZAR CANTIDAD
     * ===================================================== */
    public static void actualizarCantidad(int idInventario, double nuevaCantidad) {

        try {

            Conexion = ConexionBDSQLServer.GetConexion();

            sql = """
                  UPDATE Inventario
                  SET cantidadDisponible = ?,
                      fechaActualizacion = GETDATE()
                  WHERE idInventario = ?
                  """;

            sentencia = Conexion.prepareStatement(sql);
            sentencia.setDouble(1, nuevaCantidad);
            sentencia.setInt(2, idInventario);

            sentencia.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Cantidad actualizada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "Error al actualizar\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /* =====================================================
     * ELIMINAR REGISTRO INVENTARIO
     * ===================================================== */
    public static void eliminarInventario(int idInventario) {

        try {

            Conexion = ConexionBDSQLServer.GetConexion();

            sql = "DELETE FROM Inventario WHERE idInventario = ?";
            sentencia = Conexion.prepareStatement(sql);
            sentencia.setInt(1, idInventario);

            sentencia.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Registro eliminado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "No se puede eliminar este insumo.\nPuede estar relacionado con productos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
