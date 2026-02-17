package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelos.MUsuario;

public class Main extends CompVista {

	private static final long serialVersionUID = 1L;

	private JButton btnRegistro;

	/* ======= ESTADO DE SESIÓN ======= */
	private boolean sesionIniciada = false;
	private String nombreUsuario = "";
	private String rolUsuario = "";

	/* ======= COMPONENTES DINÁMICOS ======= */
	private JButton btnSesion;

	public Main() {
		super("Sistema", "Panel Principal");

		agregarBotonesPropios();
		configurarSesionUI();
		configurarEventos();
		configurarContenidoInicial();

	}

	private void agregarBotonesPropios() {

		btnRegistro = crearBotonMenu("Registro");

		panelSidebar.add(Box.createVerticalStrut(20));
		panelSidebar.add(btnRegistro);

		panelSidebar.revalidate();
		panelSidebar.repaint();
	}

	/* ================= UI SESIÓN ================= */
	private void configurarSesionUI() {

		// Remover etiquetas heredadas
		panelSidebar.remove(lblUsuario);
		panelSidebar.remove(lblPuesto);

		btnSesion = new JButton("Iniciar Sesión");
		btnSesion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnSesion.setFocusPainted(false);
		btnSesion.setBackground(Color.WHITE);
		btnSesion.setForeground(azulPrincipal);
		btnSesion.setMaximumSize(new java.awt.Dimension(200, 40));

		panelSidebar.add(btnSesion);
		panelSidebar.revalidate();
		panelSidebar.repaint();
	}

	/* ================= EVENTOS ================= */
	private void configurarEventos() {

		/* ---- BOTÓN SESIÓN ---- */
		btnSesion.addActionListener(e -> {

			if (!sesionIniciada) {

				Login login = new Login(this);
				login.setVisible(true);

				if (login.getUsuarioAutenticado() != null) {

					MUsuario user = login.getUsuarioAutenticado();

					nombreUsuario = user.getUsuario();
					rolUsuario = user.getRol();
					sesionIniciada = true;

					btnSesion.setText(nombreUsuario + " - " + rolUsuario);
					btnSesion.setBackground(rosaAcento);
					btnSesion.setForeground(Color.WHITE);
				}

			} else {

				sesionIniciada = false;
				nombreUsuario = "";
				rolUsuario = "";

				btnSesion.setText("Iniciar Sesión");
				btnSesion.setBackground(Color.WHITE);
				btnSesion.setForeground(azulPrincipal);

				JOptionPane.showMessageDialog(this, "Sesión cerrada", "Logout", JOptionPane.INFORMATION_MESSAGE);
			}

		});

		/* ---- BOTONES DEL SISTEMA ---- */

		btnOrden.addActionListener(e -> {
			if (validarSesion("Orden")) {
				Venta NVenta = new Venta(0);
				NVenta.setVisible(true);
			}
		});
		btnInventario.addActionListener(e -> validarSesion("Inventario"));
		btnEmpleados.addActionListener(e -> validarSesion("Empleados"));
		btnReportes.addActionListener(e -> validarSesion("Reportes"));
		btnRegistro.addActionListener(e -> abrirRegistro());

	}

	private void abrirRegistro() {

		RegistroUsuario registro = new RegistroUsuario();
		registro.setLocationRelativeTo(this);
		registro.setVisible(true);
	}

	private void validarSesionRegistro() {

		if (!sesionIniciada) {

			JOptionPane.showMessageDialog(this, "Debe iniciar sesión para acceder al registro", "Acceso Denegado",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		RegistroUsuario registro = new RegistroUsuario();
		registro.setVisible(true);
	}

	/* ================= VALIDACIÓN ================= */
	private boolean validarSesion(String modulo) {

		if (!sesionIniciada) {

			JOptionPane.showMessageDialog(this, "Debe iniciar sesión para acceder a " + modulo, "Acceso Denegado",
					JOptionPane.WARNING_MESSAGE);

			return false;
		} else {
			return true;
		}

		// 🔹 Simulación de acceso panelContenido.removeAll();

//		JLabel lbl = new JLabel("Módulo: " + modulo);
//		lbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
//		lbl.setForeground(rosaAcento);
//
//		panelContenido.add(lbl, BorderLayout.CENTER);
//		panelContenido.revalidate();
//		panelContenido.repaint();
	}

	/* ================= CONTENIDO INICIAL ================= */
	private void configurarContenidoInicial() {

		JLabel bienvenida = new JLabel("Bienvenido al Sistema");
		bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
		bienvenida.setForeground(rosaAcento);

		JPanel panel = getPanelContenido();
		panel.add(bienvenida, BorderLayout.CENTER);
	}

	/* ================= MAIN ================= */
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(() -> {
			Main vista = new Main();
			vista.setVisible(true);
		});
	}
}
