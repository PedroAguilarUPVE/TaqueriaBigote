package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import controladores.CLogin;
import controladores.CVentas;
import modelos.MProductos;

/**
 * Clase que representa la interfaz gráfica para el login de empleados y administrativos.
 * <p>
 * Esta clase extiende JFrame y proporciona una ventana a pantalla completa para la autenticación de usuarios.
 * Incluye campos para usuario y contraseña, con fondo azul y validación contra la base de datos.
 * </p>
 * 
 * @author [Tu Nombre o Equipo]
 * @version 1.0
 * @since [Fecha]
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Variable que determina el color principal azul
	 */
	private Color azulPrincipal;
	/**
	 * Variable que determina el color de acento rosa
	 */
	private Color rosaAcento;
	/**
	 * Variable que determina el color de fondo claro
	 */
	
	/* ================= COMPONENTES ================= */
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JButton btnLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FlatLightLaf.setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(() -> {
			Login login = new Login();
			login.setVisible(true);
		});
	}

	/**
	 * Constructor de componentes de la interfaz visual.
	 */
	public Login() {
		/* ================= PALETA MODERNA ================= */
		azulPrincipal = new Color(31, 42, 68);     // Azul profundo
		rosaAcento = new Color(233, 30, 99);       // Rosa moderno
		
		setTitle("Login - Sistema de Taquería");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(azulPrincipal); // Fondo azul
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50)); // Margen para centrar
		setContentPane(contentPane);
		
		crearPanelLogin();
	}

	/**
	 * Crea y configura el panel de login.
	 */
	private void crearPanelLogin() {
		JPanel panelLogin = new JPanel(new GridBagLayout());
		panelLogin.setBackground(Color.WHITE);
		panelLogin.setBorder(new EmptyBorder(40, 40, 40, 40)); // Padding interno
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 15, 15, 15);
		
		JLabel lblTitulo = new JLabel("Iniciar Sesión");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Fuente más grande para pantalla completa
		lblTitulo.setForeground(rosaAcento);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelLogin.add(lblTitulo, gbc);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Fuente más grande
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		panelLogin.add(lblUsuario, gbc);
		
		txtUsuario = new JTextField(25); // Más ancho
		txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelLogin.add(txtUsuario, gbc);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelLogin.add(lblPassword, gbc);
		
		txtPassword = new JPasswordField(25); // Más ancho, oculta con puntos
		txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 2;
		panelLogin.add(txtPassword, gbc);
		
		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Fuente más grande
		btnLogin.setBackground(rosaAcento);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.putClientProperty("JButton.buttonType", "roundRect");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validarLogin();
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panelLogin.add(btnLogin, gbc);
		
		contentPane.add(panelLogin, BorderLayout.CENTER);
	}

	/**
	 * Valida el login del usuario.
	 */
	private void validarLogin() {
		String usuario = txtUsuario.getText().trim();
		String password = new String(txtPassword.getPassword());
		
		if (usuario.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, ingrese usuario y contraseña.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		Object[] resultado = CLogin.validarLogin(usuario, password);
		
		if (resultado != null) {
		    int idEmpleado = (int) resultado[0];
		    String rol = (String) resultado[1];
		    String nombre = (String) resultado[2];
		    
		    JOptionPane.showMessageDialog(this, "Bienvenido, " + nombre + " (" + rol + ")", "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
		    
		    // Abrir la ventana principal (Punto de Venta)
		    Venta vistaVenta = new Venta(idEmpleado, rol, nombre); // Agregar nombre
		    List<MProductos> lista = CVentas.cargarProductos();
		    vistaVenta.mostrarProductos(lista);
		    vistaVenta.setVisible(true);
		    
		    // Cerrar la ventana de login
		    this.dispose();
		} else {
		    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de login", JOptionPane.ERROR_MESSAGE);
		}
	}
}
