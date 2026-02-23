package paneles;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import util.ImagenUtil;
import controladores.CVentas;
import modelos.MDetallePedido;
import modelos.MProductos;

/**
 * Panel de Orden/Venta.
 *
 * CAMBIOS respecto a VentanaVenta:
 *  - Extiende JPanel en lugar de JFrame
 *  - Header y sidebar eliminados (ya los tiene VentanaPrincipal)
 *  - cargarProductos() se llama internamente en el constructor
 *    (ya no es responsabilidad del Login)
 */
public class PanelOrden extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Color azulPrincipal  = new Color(31, 42, 68);
    private final Color rosaAcento     = new Color(233, 30, 99);
    private final Color amarilloAcento = new Color(255, 193, 7);
    private final Color fondoClaro     = new Color(245, 247, 250);

    private final int    idEmpleadoSesion;
    private final String rolSesion;
    private final String nombreSesion;

    private JPanel            panelGridProductos;
    private JTable            tablaResumen;
    private DefaultTableModel modeloResumen;
    private JLabel            lblTotal;
    private double            total = 0.0;

    // ─────────────────────────────────────────────────────────────────────────
    public PanelOrden(int idEmpleado, String rol, String nombre) {
        this.idEmpleadoSesion = idEmpleado;
        this.rolSesion        = rol;
        this.nombreSesion     = nombre;

        setLayout(new BorderLayout());
        setBackground(fondoClaro);

        crearZonaProductos();
        crearResumen();

        // ── Carga los productos desde BD al construirse ───────────────────
        // Antes esto lo hacía el Login con:
        //   List<MProductos> lista = CVentas.cargarProductos();
        //   vistaVenta.mostrarProductos(lista);
        // Ahora el panel se autoabastece.
        cargarProductos();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Zona de productos (centro)
    // ─────────────────────────────────────────────────────────────────────────
    private void crearZonaProductos() {
        JPanel panelProductos = new JPanel(new BorderLayout());
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
        panelProductos.add(scroll,  BorderLayout.CENTER);

        add(panelProductos, BorderLayout.CENTER);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Panel de resumen (derecha)
    // ─────────────────────────────────────────────────────────────────────────
    private void crearResumen() {
        JPanel panelResumen = new JPanel(new BorderLayout());
        panelResumen.setPreferredSize(new Dimension(360, 0));
        panelResumen.setBackground(Color.WHITE);
        panelResumen.setBorder(new EmptyBorder(20, 20, 20, 20));

        modeloResumen = new DefaultTableModel(
            new Object[]{"ID", "Producto", "Cant.", "Subtotal"}, 0);

        tablaResumen = new JTable(modeloResumen);
        tablaResumen.removeColumn(tablaResumen.getColumnModel().getColumn(0));
        tablaResumen.setEnabled(false);

        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotal.setForeground(rosaAcento);

        JButton btnConfirmar = new JButton("Confirmar Orden");
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

        add(panelResumen, BorderLayout.EAST);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Carga de productos desde BD
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Consulta los productos en BD y los pinta en el grid.
     * Se llama automáticamente en el constructor.
     * Puedes llamarlo también desde VentanaPrincipal si agregas
     * un método actualizar() y quieres refrescar el catálogo.
     */
    // Llamado por VentanaPrincipal al navegar a este panel
    // Refresca el catálogo de productos desde BD
    public void actualizar() {
        cargarProductos();
    }

    public void cargarProductos() {
        List<MProductos> lista = CVentas.cargarProductos();
        mostrarProductos(lista);
    }

    private void mostrarProductos(List<MProductos> lista) {
        panelGridProductos.removeAll();
        for (MProductos p : lista) {
        	if (p.isActivo()) {        		
            panelGridProductos.add(crearTarjetaProducto(p));
        	}
        }
        panelGridProductos.revalidate();
        panelGridProductos.repaint();
    }

    private JPanel crearTarjetaProducto(MProductos p) {
    	if (p.isActivo()) {
    		JPanel card = new JPanel(new BorderLayout());
    		card.setBorder(new EmptyBorder(15, 15, 15, 15));
    		card.setBackground(Color.WHITE);
    		card.setPreferredSize(new Dimension(200, 160));
    		card.putClientProperty("FlatLaf.style", "arc:20");
    		
    		JLabel lblImagen = new JLabel();
    		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
    		Image img = ImagenUtil.cargar(p.getUrlFoto(), 150, 130);
    		if (img != null) lblImagen.setIcon(new ImageIcon(img));
    		
    		JLabel lblNombre = new JLabel(p.getNombre(), SwingConstants.CENTER);
    		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
    		lblNombre.setForeground(azulPrincipal);
    		
    		JLabel lblPrecio = new JLabel("$" + p.getPrecio(), SwingConstants.CENTER);
    		lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 15));
    		lblPrecio.setForeground(amarilloAcento);
    		
    		JPanel panelInfo = new JPanel(new GridLayout(2, 1));
    		panelInfo.setBackground(Color.WHITE);
    		panelInfo.add(lblNombre);
    		panelInfo.add(lblPrecio);
    		
    		card.add(lblImagen, BorderLayout.NORTH);
    		card.add(panelInfo, BorderLayout.CENTER);
    		
    		card.addMouseListener(new MouseAdapter() {
    			@Override public void mouseEntered(MouseEvent e) { card.setBackground(new Color(255, 240, 245)); }
    			@Override public void mouseExited(MouseEvent e)  { card.setBackground(Color.WHITE); }
    			@Override public void mouseClicked(MouseEvent e) {
    				agregarProducto(p.getIdProducto(), p.getNombre(), p.getPrecio());
    			}
    		});
    		
    		return card;    		
    	}
    	else return null;
    	
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Lógica de orden
    // ─────────────────────────────────────────────────────────────────────────
    public void agregarProducto(int id, String nombre, double precio) {
        for (int i = 0; i < modeloResumen.getRowCount(); i++) {
            if ((int) modeloResumen.getValueAt(i, 0) == id) {
                int cantidad = (int) modeloResumen.getValueAt(i, 2) + 1;
                modeloResumen.setValueAt(cantidad, i, 2);
                modeloResumen.setValueAt(cantidad * precio, i, 3);
                recalcularTotal();
                return;
            }
        }
        modeloResumen.addRow(new Object[]{id, nombre, 1, precio});
        recalcularTotal();
    }

    private void recalcularTotal() {
        total = 0;
        for (int i = 0; i < modeloResumen.getRowCount(); i++) {
            total += (double) modeloResumen.getValueAt(i, 3);
        }
        lblTotal.setText("TOTAL: $" + String.format("%.2f", total));
    }

    private void completarOrden() {
        if (modeloResumen.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la orden.", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int idPedido = CVentas.registrarPedido(total, idEmpleadoSesion);
            if (idPedido == 0) {
                JOptionPane.showMessageDialog(this, "No se pudo generar el pedido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < modeloResumen.getRowCount(); i++) {
                MDetallePedido detalle = new MDetallePedido();
                detalle.setIdPedido(idPedido);
                detalle.setIdProducto((int)   modeloResumen.getValueAt(i, 0));
                detalle.setCantidad((int)     modeloResumen.getValueAt(i, 2));
                detalle.setSubtotal((double)  modeloResumen.getValueAt(i, 3));
                CVentas.registrarDetalle(detalle);
            }
            JOptionPane.showMessageDialog(this, "Orden completada correctamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            limpiarOrden();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void limpiarOrden() {
        modeloResumen.setRowCount(0);
        total = 0;
        lblTotal.setText("TOTAL: $0.00");
    }

    public double getTotal() { return total; }

    public List<MDetallePedido> getDetallePedido(int idPedido) {
        List<MDetallePedido> lista = new ArrayList<>();
        for (int i = 0; i < modeloResumen.getRowCount(); i++) {
            MDetallePedido d = new MDetallePedido();
            d.setIdPedido(idPedido);
            d.setIdProducto((int)   modeloResumen.getValueAt(i, 0));
            d.setCantidad((int)     modeloResumen.getValueAt(i, 2));
            d.setSubtotal((double)  modeloResumen.getValueAt(i, 3));
            lista.add(d);
        }
        return lista;
    }
}
