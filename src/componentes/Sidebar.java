package componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel {

    /* ================= PALETA MODERNA ================= */
    protected Color azulPrincipal = new Color(31, 42, 68);
    protected Color rosaAcento = new Color(233, 30, 99);
    protected Color amarilloAcento = new Color(255, 193, 7);
    protected Color fondoClaro = new Color(245, 247, 250);
    
    protected JPanel panelSidebar;
    
	private static final long serialVersionUID = 1L;

    /* ================= SIDEBAR ================= */
    protected JButton btnOrden;
    protected JButton btnInventario;
    protected JButton btnEmpleados;
    protected JButton btnReportes;

    protected JLabel lblUsuario;
    protected JLabel lblPuesto;
	private Container contentPane;

	
	/**
	 * Create the panel.
	 */
	public Sidebar() {
		crearSidebar();
	}
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

        lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);

        lblPuesto = new JLabel("Rol");
        lblPuesto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPuesto.setForeground(Color.LIGHT_GRAY);

        panelSidebar.add(lblUsuario);
        panelSidebar.add(lblPuesto);

        contentPane.add(panelSidebar, BorderLayout.WEST);
    }
    
    /* ================= BOTÓN MENÚ ================= */
    protected JButton crearBotonMenu(String texto) {

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

}
