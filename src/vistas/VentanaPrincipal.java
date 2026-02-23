package vistas;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;
import paneles.*;

/*
 * Ventana base principal.
 * Header + Sidebar + CardLayout central.
 * Botones de Empleados y Agregar Empleado solo visibles para Administrador.
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private final Color azulPrincipal  = new Color(31, 42, 68);
    private final Color rosaAcento     = new Color(233, 30, 99);
    private final Color amarilloAcento = new Color(255, 193, 7);
    private final Color fondoClaro     = new Color(245, 247, 250);

    private final int    idEmpleadoSesion;
    private final String rolSesion;
    private final String nombreSesion;

    private JPanel     contentPane;
    private JPanel     panelCentral;
    private CardLayout cardLayout;

    private PanelOrden           panelOrden;
    private PanelInventario      panelInventario;
    private PanelOrdenesDia      panelOrdenesDia;
    private PanelDetallesOrden   panelDetallesOrden;
    private PanelProductos       panelProductos;
    private PanelEmpleados       panelEmpleados;
    private PanelAgregarEmpleado panelAgregarEmpleado;

    public static final String VISTA_ORDEN            = "orden";
    public static final String VISTA_INVENTARIO       = "inventario";
    public static final String VISTA_ORDENES          = "verOrdenes";
    public static final String VISTA_DETALLES         = "detalles";
    public static final String VISTA_PRODUCTOS        = "productos";
    public static final String VISTA_EMPLEADOS        = "empleados";
    public static final String VISTA_AGREGAR_EMPLEADO = "agregarEmpleado";
    public static final String VISTA_REPORTES         = "reportes";

    public VentanaPrincipal(int idEmpleado, String rol, String nombre) {
        this.idEmpleadoSesion = idEmpleado;
        this.rolSesion        = rol;
        this.nombreSesion     = nombre;

        setTitle("Taquería El Bigotes - Punto de Venta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(fondoClaro);
        setContentPane(contentPane);

        crearHeader();
        crearSidebar();
        crearPanelCentral();
        registrarPaneles();

        mostrarPanel(VISTA_ORDEN);
    }

    private void crearHeader() {
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(new EmptyBorder(15, 25, 15, 25));

        JLabel lblTitulo = new JLabel("Taquería El Bigotes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(rosaAcento);

        JLabel lblFecha = new JLabel(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy HH:mm")));
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFecha.setForeground(Color.GRAY);

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(lblFecha,  BorderLayout.EAST);
        contentPane.add(panelHeader, BorderLayout.NORTH);
    }

    private void crearSidebar() {
        JPanel panelSidebar = new JPanel();
        panelSidebar.setPreferredSize(new Dimension(230, 0));
        panelSidebar.setBackground(azulPrincipal);
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBorder(new EmptyBorder(50, 20, 30, 20));

        // Logo
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/logo.jpg"));
            Image img = icon.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Logo no encontrado.");
        }
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSidebar.add(lblLogo);
        panelSidebar.add(Box.createVerticalStrut(20));

        // Botones visibles para todos los roles
        agregarBoton(panelSidebar, "Orden",               () -> mostrarPanel(VISTA_ORDEN));
        agregarBoton(panelSidebar, "Inventario",          () -> mostrarPanel(VISTA_INVENTARIO));
        agregarBoton(panelSidebar, "Productos",           () -> mostrarPanel(VISTA_PRODUCTOS));
        agregarBoton(panelSidebar, "Ver Órdenes del Día", () -> mostrarPanel(VISTA_ORDENES));
        agregarBoton(panelSidebar, "Reportes",            () -> mostrarPanel(VISTA_REPORTES));

        // Botones exclusivos para Administrador — no se agregan si el rol es otro
        if ("Administrador".equals(rolSesion)) {
            agregarBoton(panelSidebar, "Empleados",        () -> mostrarPanel(VISTA_EMPLEADOS));
            agregarBoton(panelSidebar, "Agregar Empleado", () -> mostrarPanel(VISTA_AGREGAR_EMPLEADO));
        }

        // Cerrar sesión
        panelSidebar.add(Box.createVerticalStrut(30));
        JButton btnCerrarSesion = crearBotonMenu("Cerrar Sesión");
        btnCerrarSesion.setBackground(amarilloAcento);
        btnCerrarSesion.setForeground(Color.BLACK);
        btnCerrarSesion.setMaximumSize(new Dimension(150, 42));
        btnCerrarSesion.setPreferredSize(new Dimension(150, 42));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelSidebar.add(btnCerrarSesion);

        // Info de usuario al fondo
        panelSidebar.add(Box.createVerticalGlue());
        panelSidebar.add(Box.createVerticalStrut(15));

        JPanel panelInfoUsuario = new JPanel();
        panelInfoUsuario.setLayout(new BoxLayout(panelInfoUsuario, BoxLayout.Y_AXIS));
        panelInfoUsuario.setBackground(new Color(245, 245, 220));
        panelInfoUsuario.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInfoUsuario.setMaximumSize(new Dimension(200, 60));
        panelInfoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUsuario = new JLabel(nombreSesion);
        lblUsuario.setForeground(Color.BLACK);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPuesto = new JLabel(rolSesion);
        lblPuesto.setForeground(new Color(0, 51, 102));
        lblPuesto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPuesto.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelInfoUsuario.add(lblUsuario);
        panelInfoUsuario.add(Box.createVerticalStrut(5));
        panelInfoUsuario.add(lblPuesto);
        panelSidebar.add(panelInfoUsuario);

        contentPane.add(panelSidebar, BorderLayout.WEST);
    }

    // Agrega botón al sidebar sin repetir código
    private void agregarBoton(JPanel sidebar, String texto, Runnable accion) {
        JButton btn = crearBotonMenu(texto);
        btn.addActionListener(e -> accion.run());
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(15));
    }

    private void crearPanelCentral() {
        cardLayout   = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(fondoClaro);
        contentPane.add(panelCentral, BorderLayout.CENTER);
    }

    private void registrarPaneles() {
        panelOrden = new PanelOrden(idEmpleadoSesion, rolSesion, nombreSesion);
        registrarPanel(VISTA_ORDEN, panelOrden);

        panelInventario = new PanelInventario(rolSesion, nombreSesion);
        registrarPanel(VISTA_INVENTARIO, panelInventario);

        panelOrdenesDia = new PanelOrdenesDia(this);
        registrarPanel(VISTA_ORDENES, panelOrdenesDia);

        panelDetallesOrden = new PanelDetallesOrden(this);
        registrarPanel(VISTA_DETALLES, panelDetallesOrden);

        panelProductos = new PanelProductos();
        registrarPanel(VISTA_PRODUCTOS, panelProductos);

        // Solo se instancian y registran para administrador
        if ("Administrador".equals(rolSesion)) {
            panelEmpleados = new PanelEmpleados();
            registrarPanel(VISTA_EMPLEADOS, panelEmpleados);

            panelAgregarEmpleado = new PanelAgregarEmpleado();
            registrarPanel(VISTA_AGREGAR_EMPLEADO, panelAgregarEmpleado);
        }

        registrarPanel(VISTA_REPORTES, crearPlaceholder("Reportes - Próximamente"));
    }

    public void registrarPanel(String clave, JPanel panel) {
        panelCentral.add(panel, clave);
    }

    public void mostrarPanel(String clave) {
        if (VISTA_ORDEN.equals(clave))      panelOrden.actualizar();
        if (VISTA_INVENTARIO.equals(clave)) panelInventario.actualizar();
        if (VISTA_ORDENES.equals(clave))    panelOrdenesDia.actualizar();
        if (VISTA_PRODUCTOS.equals(clave))  panelProductos.actualizar();
        if (VISTA_EMPLEADOS.equals(clave) && panelEmpleados != null) panelEmpleados.actualizar();
        cardLayout.show(panelCentral, clave);
    }

    public void mostrarDetalle(int idPedido) {
        panelDetallesOrden.setIdPedido(idPedido);
        cardLayout.show(panelCentral, VISTA_DETALLES);
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(rosaAcento);
        btn.setForeground(Color.WHITE);
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setPreferredSize(new Dimension(200, 42));
        btn.setMaximumSize(new Dimension(200, 42));
        return btn;
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que quieres cerrar sesión?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
            new Login().setVisible(true);
        }
    }

    private JPanel crearPlaceholder(String texto) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(fondoClaro);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(azulPrincipal);
        panel.add(lbl);
        return panel;
    }

    public static void main(String[] args) {
        try { FlatLightLaf.setup(); } catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
