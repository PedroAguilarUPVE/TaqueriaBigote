package vistas;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controladores.CRegistro;

public class RegistroUsuario extends CompVista {

    private static final long serialVersionUID = 1L;

    private JTextField txtNombre;
    private JTextField txtPuesto;
    private JCheckBox chkActivo;

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JTextField txtRol;

    private JButton btnRegistrar;

    public RegistroUsuario() {
        super("Sistema", "Registro de Usuario");

        construirFormulario();
        eventos();
    }

    private void construirFormulario() {

        JPanel panel = getPanelContenido();
        panel.setLayout(new BorderLayout());

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));
        formulario.setBackground(new Color(245,247,250));

        /* ===== DATOS EMPLEADO ===== */
        formulario.add(new JLabel("Nombre Completo"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);

        formulario.add(Box.createVerticalStrut(10));

        formulario.add(new JLabel("Puesto"));
        txtPuesto = new JTextField();
        formulario.add(txtPuesto);

        formulario.add(Box.createVerticalStrut(10));

        chkActivo = new JCheckBox("Empleado Activo");
        chkActivo.setSelected(true);
        formulario.add(chkActivo);

        formulario.add(Box.createVerticalStrut(20));

        /* ===== DATOS USUARIO ===== */
        formulario.add(new JLabel("Usuario"));
        txtUsuario = new JTextField();
        formulario.add(txtUsuario);

        formulario.add(Box.createVerticalStrut(10));

        formulario.add(new JLabel("Contraseña"));
        txtPassword = new JPasswordField();
        formulario.add(txtPassword);

        formulario.add(Box.createVerticalStrut(10));

        formulario.add(new JLabel("Rol"));
        txtRol = new JTextField();
        formulario.add(txtRol);

        formulario.add(Box.createVerticalStrut(20));

        btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setBackground(rosaAcento);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        formulario.add(btnRegistrar);

        panel.add(formulario, BorderLayout.CENTER);
    }

    private void eventos() {

        btnRegistrar.addActionListener(e -> {

            String nombre = txtNombre.getText().trim();
            String puesto = txtPuesto.getText().trim();
            boolean activo = chkActivo.isSelected();

            String usuario = txtUsuario.getText().trim();
            String password = new String(txtPassword.getPassword());
            String rol = txtRol.getText().trim();

            if (nombre.isEmpty() || puesto.isEmpty() ||
                usuario.isEmpty() || password.isEmpty() || rol.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Complete todos los campos",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean registrado = CRegistro.registrarUsuario(
                    nombre, puesto, activo,
                    usuario, password, rol
            );

            if (registrado) {

                JOptionPane.showMessageDialog(this,
                        "Usuario registrado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                limpiarCampos();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Error al registrar usuario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPuesto.setText("");
        chkActivo.setSelected(true);
        txtUsuario.setText("");
        txtPassword.setText("");
        txtRol.setText("");
    }
}
