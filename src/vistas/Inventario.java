package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import controladores.CInventario;
import modelos.MInventario;

/**
 * VISTA INVENTARIO
 * Diseño moderno igual a Venta.java
 */
public class Inventario extends JFrame {

    private static final long serialVersionUID = 1L;

    /* ================= PALETA (IGUAL QUE VENTA) ================= */
    private Color azulPrincipal = new Color(31, 42, 68);
    private Color rosaAcento = new Color(233, 30, 99);
    private Color amarilloAcento = new Color(255, 193, 7);
    private Color fondoClaro = new Color(245, 247, 250);

    /* ================= CONTENEDORES ================= */
    private JPanel contentPane;
    private JPanel panelSidebar;
    private JPanel panelHeader;
    private JPanel panelTabla;

    /* ================= COMPONENTES ================= */
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnAgregar;

    /* ================= MAIN ================= */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new Inventario().setVisible(true));
    }

    /* ================= CONSTRUCTOR ================= */
    public Inventario() {

        setTitle("Gestión de Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(fondoClaro);
        setContentPane(contentPane);

        crearSidebar();
        crearHeader();
        crearTabla();
        cargarInventarioDesdeBD();  // 🔥 ESTA LÍNEA FALTABA

    }

    /* ================= SIDEBAR (IGUAL A VENTA) ================= */
    private void crearSidebar() {

        panelSidebar = new JPanel();
        panelSidebar.setPreferredSize(new Dimension(230, 0));
        panelSidebar.setBackground(azulPrincipal);
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JButton btnOrden = crearBotonMenu("Orden");
        JButton btnInventario = crearBotonMenu("Inventario");
        JButton btnEmpleados = crearBotonMenu("Empleados");
        JButton btnReportes = crearBotonMenu("Reportes");

        panelSidebar.add(btnOrden);
        panelSidebar.add(Box.createVerticalStrut(20));
        panelSidebar.add(btnInventario);
        panelSidebar.add(Box.createVerticalStrut(20));
        panelSidebar.add(btnEmpleados);
        panelSidebar.add(Box.createVerticalStrut(20));
        panelSidebar.add(btnReportes);
        panelSidebar.add(Box.createVerticalGlue());

        JLabel lblUsuario = new JLabel("Sesión Activa");
        lblUsuario.setForeground(Color.WHITE);

        panelSidebar.add(lblUsuario);

        contentPane.add(panelSidebar, BorderLayout.WEST);
    }

    private JButton crearBotonMenu(String texto) {

        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(200, 42));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setBackground(rosaAcento);
        btn.setForeground(Color.WHITE);
        btn.putClientProperty("JButton.buttonType", "roundRect");

        return btn;
    }

    /* ================= HEADER ================= */
    private void crearHeader() {

        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Gestión de Inventario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(rosaAcento);

        btnAgregar = new JButton("➕ Agregar Producto");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(amarilloAcento);
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.putClientProperty("JButton.buttonType", "roundRect");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(btnAgregar, BorderLayout.EAST);

        contentPane.add(panelHeader, BorderLayout.NORTH);
    }

    /* ================= TABLA ================= */
    private void crearTabla() {

        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(fondoClaro);
        panelTabla.setBorder(new EmptyBorder(30, 30, 30, 30));

        modelo = new DefaultTableModel(
                new Object[]{"Producto", "Cantidad", "Última Actualización", "Editar", "Eliminar"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(45);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(rosaAcento);
        tabla.getTableHeader().setForeground(Color.WHITE);

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int fila = tabla.rowAtPoint(e.getPoint());
                int columna = tabla.columnAtPoint(e.getPoint());

                if (columna == 3) {
                    editarProducto(fila);
                }

                if (columna == 4) {
                    eliminarProducto(fila);
                }
            }
        });

        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);
        contentPane.add(panelTabla, BorderLayout.CENTER);
    }
    
    
    /* ================= CARGAR DATOS DESDE BD ================= */
    private void cargarInventarioDesdeBD() {

        modelo.setRowCount(0); // limpiar tabla

        List<MInventario> lista = CInventario.cargarInventario();

        for (MInventario inv : lista
        		MInsumo ins : lista) {

            modelo.addRow(new Object[]{
                    inv.getNombreInsumo(),
                    inv.getCantidadDisponible() + " " + inv.getUnidadMedida(),
                    inv.getFechaActualizacion(),
                    "✏️",
                    "🗑"
            });
        }
    }

    

    /* ================= FORMULARIO AGREGAR ================= */
    private void mostrarFormularioAgregar() {

        JTextField txtProducto = new JTextField();
        JTextField txtCantidad = new JTextField();

        Object[] mensaje = {
                "Producto:", txtProducto,
                "Cantidad:", txtCantidad
        };

        int opcion = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Nuevo Producto",
                JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {

            String producto = txtProducto.getText();
            String cantidad = txtCantidad.getText();

            modelo.addRow(new Object[]{
                    producto,
                    cantidad,
                    java.time.LocalDateTime.now(),
                    "✏️",
                    "🗑"
            });
        }
    }

    /* ================= EDITAR ================= */
    private void editarProducto(int fila) {

        String productoActual = modelo.getValueAt(fila, 0).toString();
        String cantidadActual = modelo.getValueAt(fila, 1).toString();

        JTextField txtProducto = new JTextField(productoActual);
        JTextField txtCantidad = new JTextField(cantidadActual);

        Object[] mensaje = {
                "Producto:", txtProducto,
                "Cantidad:", txtCantidad
        };

        int opcion = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Editar Producto",
                JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {

            modelo.setValueAt(txtProducto.getText(), fila, 0);
            modelo.setValueAt(txtCantidad.getText(), fila, 1);
            modelo.setValueAt(java.time.LocalDateTime.now(), fila, 2);
        }
    }

    /* ================= ELIMINAR ================= */
    private void eliminarProducto(int fila) {

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar este producto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            modelo.removeRow(fila);
        }
    }
}
