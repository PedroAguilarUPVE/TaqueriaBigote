package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDSQLServer;
import modelos.MDetallePedido;
import modelos.MProductos;

/**
 * Controlador del módulo de Ventas
 * Base de datos: TaqueriaBD
 * 
 * @author Milagros Guadalupe Camacho Camacho
 * @version 3.0
 */
public class CVentas {

    /* =========================================================
     * CARGAR PRODUCTOS
     * ========================================================= */
    public static List<MProductos> cargarProductos() {

        List<MProductos> lista = new ArrayList<>();

        String sql = "SELECT idProducto, nombre, precio, descripcion, urlFoto FROM Producto";

        try (Connection con = ConexionBDSQLServer.GetConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                MProductos p = new MProductos();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setUrlFoto(rs.getString("urlFoto"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
        }

        return lista;
    }

    /* =========================================================
     * REGISTRAR PEDIDO (CON SESIÓN)
     * ========================================================= */
    public static int registrarPedido(double total, int idEmpleado) {

        int idPedidoGenerado = 0;

        String sql = "INSERT INTO Pedido (total, idEmpleado, fecha) VALUES (?, ?, GETDATE())";

        try (Connection con = ConexionBDSQLServer.GetConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, total);
            ps.setInt(2, idEmpleado);

            int filas = ps.executeUpdate();

            if (filas > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPedidoGenerado = rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error al registrar pedido: " + e.getMessage());
        }

        return idPedidoGenerado;
    }

    /* =========================================================
     * REGISTRAR DETALLE PEDIDO
     * ========================================================= */
    public static void registrarDetalle(MDetallePedido detalle) {

        String sql = "INSERT INTO DetallePedido "
                + "(idPedido, idProducto, cantidad, subtotal) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBDSQLServer.GetConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdPedido());
            ps.setInt(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubtotal());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al registrar detalle: " + e.getMessage());
        }
    }
}
