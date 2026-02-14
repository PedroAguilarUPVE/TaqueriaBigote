package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import controladores.CVentas;
import modelos.MDetallePedido;
import modelos.MProductos;

public class Venta extends JFrame {

    private static final long serialVersionUID = 1L;

    /* ================= PALETA MODERNA ================= */
    private Color azulPrincipal = new Color(31, 42, 68);     // Azul profundo
    private Color rosaAcento = new Color(233, 30, 99);       // Rosa moderno
    private Color amarilloAcento = new Color(255, 193, 7);   // Amarillo elegante
    private Color fondoClaro = new Color(245, 247, 250);     // Fondo suave

    /* ================= CONTENEDORES ================= */
    private JPanel contentPane;
    private JPanel panelSidebar;
    private JPanel panelHeader;
    private JPanel panelProductos;
    private JPanel panelResumen;
    private JPanel panelGridProductos;

    /* ================= SIDEBAR ================= */
    private JButton btnOrden;
    private JButton btnInventario;
    private JButton btnEmpleados;
    private JButton btnReportes;

    private JLabel lblUsuario;
    private JLabel lblPuesto;

    /* ================= RESUMEN ================= */
    private JTable tablaResumen;
    private DefaultTableModel modeloResumen;
    private JLabel lblTotal;
    private JButton btnConfirmar;

    private double total = 0.0;
    private int idEmpleadoSesion;


    /* ================= MAIN ================= */
    public static void main(String[] args) {

        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            int idEmpleadoLogueado = 1; // cambiar cuando tengas login real

            Venta vista = new Venta(idEmpleadoLogueado);
            List<MProductos> lista = CVentas.cargarProductos();
            vista.mostrarProductos(lista);
            vista.setVisible(true);
        });

    }

    /* ================= CONSTRUCTOR ================= */
    public Venta(int idEmpleado) {

        this.idEmpleadoSesion = idEmpleado;

        setTitle("Punto de Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(fondoClaro);
        setContentPane(contentPane);

        crearHeader();
        crearSidebar();
        crearZonaProductos();
        crearResumen();
    }


    /* ================= HEADER ================= */
    private void crearHeader() {

        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new EmptyBorder(15, 25, 15, 25));

        JLabel lblTitulo = new JLabel("Punto de Venta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(rosaAcento);

        JLabel lblFecha = new JLabel(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy HH:mm")));
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFecha.setForeground(Color.GRAY);

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(lblFecha, BorderLayout.EAST);

        contentPane.add(panelHeader, BorderLayout.NORTH);
    }

    /* ================= SIDEBAR ================= */
    private void crearSidebar() {

        panelSidebar = new JPanel();
        panelSidebar.setPreferredSize(new Dimension(230, 0));
        panelSidebar.setBackground(azulPrincipal);
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        btnOrden = crearBotonMenu("Orden");
        btnInventario = crearBotonMenu("Inventario");
        btnEmpleados = crearBotonMenu("Empleados");
        btnReportes = crearBotonMenu("Reportes");

        panelSidebar.add(btnOrden);
        panelSidebar.add(Box.createVerticalStrut(20));
        panelSidebar.add(btnInventario);
        panelSidebar.add(Box.createVerticalStrut(20));
        panelSidebar.add(btnEmpleados);
        panelSidebar.add(Box.createVerticalStrut(20));
        panelSidebar.add(btnReportes);
        panelSidebar.add(Box.createVerticalGlue());

        lblUsuario = new JLabel("Empleado");
        lblUsuario.setForeground(Color.WHITE);

        lblPuesto = new JLabel("Caja");
        lblPuesto.setForeground(Color.LIGHT_GRAY);

        panelSidebar.add(lblUsuario);
        panelSidebar.add(lblPuesto);

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

    /* ================= PRODUCTOS ================= */
    private void crearZonaProductos() {

        panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(fondoClaro);
        panelProductos.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblMenu = new JLabel("Menú de Productos");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMenu.setForeground(azulPrincipal);

        panelGridProductos = new JPanel(new GridLayout(0, 3, 25, 25));
        panelGridProductos.setBackground(fondoClaro);

        JScrollPane scroll = new JScrollPane(
                panelGridProductos,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setBorder(null);

        panelProductos.add(lblMenu, BorderLayout.NORTH);
        panelProductos.add(scroll, BorderLayout.CENTER);

        contentPane.add(panelProductos, BorderLayout.CENTER);
    }

    private JPanel crearTarjetaProducto(MProductos p) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200, 160));
        card.putClientProperty("FlatLaf.style", "arc:20");

        /* ===== IMAGEN ===== */
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        try {

            String ruta = p.getUrlFoto();

            if (ruta != null && !ruta.isEmpty()) {

                // 🔥 Quitar "src/" si viene así desde la BD
                if (ruta.startsWith("src/")) {
                    ruta = ruta.substring(4);
                }

                java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);

                if (imgURL != null) {

                    ImageIcon icon = new ImageIcon(imgURL);
                    Image imgEscalada = icon.getImage()
                            .getScaledInstance(150, 130, Image.SCALE_SMOOTH);

                    lblImagen.setIcon(new ImageIcon(imgEscalada));
                }
            }

        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen: " + e.getMessage());
        }

        /* ===== NOMBRE ===== */
        JLabel lblNombre = new JLabel(p.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setForeground(azulPrincipal);

        /* ===== PRECIO ===== */
        JLabel lblPrecio = new JLabel("$" + p.getPrecio(), SwingConstants.CENTER);
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPrecio.setForeground(amarilloAcento);

        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.add(lblNombre);
        panelInfo.add(lblPrecio);

        card.add(lblImagen, BorderLayout.NORTH);
        card.add(panelInfo, BorderLayout.CENTER);

        /* ===== EVENTOS ===== */
        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(255, 240, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                agregarProducto(p.getIdProducto(), p.getNombre(), p.getPrecio());
            }
        });

        return card;
    }


    public void mostrarProductos(List<MProductos> lista) {

        panelGridProductos.removeAll();

        for (MProductos p : lista) {
            panelGridProductos.add(crearTarjetaProducto(p));
        }

        panelGridProductos.revalidate();
        panelGridProductos.repaint();
    }

    /* ================= RESUMEN ================= */
    private void crearResumen() {

        panelResumen = new JPanel(new BorderLayout());
        panelResumen.setPreferredSize(new Dimension(360, 0));
        panelResumen.setBackground(Color.WHITE);
        panelResumen.setBorder(new EmptyBorder(20, 20, 20, 20));

        modeloResumen = new DefaultTableModel(
                new Object[] { "ID", "Producto", "Cant.", "Subtotal" }, 0);

        tablaResumen = new JTable(modeloResumen);
        tablaResumen.removeColumn(tablaResumen.getColumnModel().getColumn(0));

        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotal.setForeground(rosaAcento);

        btnConfirmar = new JButton("Confirmar Orden");
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConfirmar.setBackground(amarilloAcento);
        btnConfirmar.setForeground(Color.BLACK);
        btnConfirmar.putClientProperty("JButton.buttonType", "roundRect");
        btnConfirmar.addActionListener(e -> completarOrden());


        JPanel bottom = new JPanel(new GridLayout(2, 1, 15, 15));
        bottom.setBackground(Color.WHITE);
        bottom.add(lblTotal);
        bottom.add(btnConfirmar);

        panelResumen.add(new JScrollPane(tablaResumen), BorderLayout.CENTER);
        panelResumen.add(bottom, BorderLayout.SOUTH);

        contentPane.add(panelResumen, BorderLayout.EAST);
    }

    private void completarOrden() {

        if (modeloResumen.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay productos en la orden.",
                    "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            // 🔥 idEmpleado viene de tu sesión
            int idPedido = CVentas.registrarPedido(getTotal(), idEmpleadoSesion);

            if (idPedido == 0) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se pudo generar el pedido.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 🔥 Registrar cada detalle
            for (int i = 0; i < modeloResumen.getRowCount(); i++) {

                MDetallePedido detalle = new MDetallePedido();

                detalle.setIdPedido(idPedido);
                detalle.setIdProducto((int) modeloResumen.getValueAt(i, 0));
                detalle.setCantidad((int) modeloResumen.getValueAt(i, 2));
                detalle.setSubtotal((double) modeloResumen.getValueAt(i, 3));

                CVentas.registrarDetalle(detalle);
            }

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Orden completada correctamente.",
                    "Éxito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

            limpiarOrden();

        } catch (Exception ex) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error al completar la orden: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);

            ex.printStackTrace();
        }


         
    }


	/* ================= LÓGICA ================= */
    public void agregarProducto(int id, String nombre, double precio) {

        boolean encontrado = false;

        for (int i = 0; i < modeloResumen.getRowCount(); i++) {

            int idTabla = (int) modeloResumen.getValueAt(i, 0);

            if (idTabla == id) {

                int cantidad = (int) modeloResumen.getValueAt(i, 2);
                cantidad++;

                double nuevoSubtotal = cantidad * precio;

                modeloResumen.setValueAt(cantidad, i, 2);
                modeloResumen.setValueAt(nuevoSubtotal, i, 3);

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            modeloResumen.addRow(new Object[] { id, nombre, 1, precio });
        }

        recalcularTotal();
    }

    private void recalcularTotal() {

        total = 0;

        for (int i = 0; i < modeloResumen.getRowCount(); i++) {
            total += (double) modeloResumen.getValueAt(i, 3);
        }

        lblTotal.setText("TOTAL: $" + String.format("%.2f", total));
    }

    public double getTotal() {
        return total;
    }

    public List<MDetallePedido> getDetallePedido(int idPedido) {

        List<MDetallePedido> lista = new ArrayList<>();

        for (int i = 0; i < modeloResumen.getRowCount(); i++) {

            MDetallePedido d = new MDetallePedido();
            d.setIdPedido(idPedido);
            d.setIdProducto((int) modeloResumen.getValueAt(i, 0));
            d.setCantidad((int) modeloResumen.getValueAt(i, 2));
            d.setSubtotal((double) modeloResumen.getValueAt(i, 3));

            lista.add(d);
        }

        return lista;
    }

    public void limpiarOrden() {
        modeloResumen.setRowCount(0);
        total = 0;
        lblTotal.setText("TOTAL: $0.00");
    }
}
