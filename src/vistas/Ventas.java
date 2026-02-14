/*package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JScrollPane;

public class Ventas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel total;
	private JPanel barraBotones;
	private JButton ventaboton;
	private JButton empleadosboton;
	private JButton reportesboton;
	private JLabel lblTaqueria;
	private JLabel lblEl;
	private JLabel lblBigote;
	private JPanel tarjeta1;
	private JPanel panel;
	private JPanel panel_2;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventas frame = new Ventas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Ventas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1266, 635);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		barraBotones = new JPanel();
		barraBotones.setBackground(new Color(220, 20, 60));
		barraBotones.setForeground(new Color(0, 0, 0));
		barraBotones.setBounds(0, 0, 230, 598);
		contentPane.add(barraBotones);
		barraBotones.setLayout(null);
		
		ventaboton = new JButton("Ventas");
		ventaboton.setForeground(new Color(0, 0, 0));
		ventaboton.setBackground(new Color(255, 255, 0));
		ventaboton.setBounds(33, 194, 175, 32);
		barraBotones.add(ventaboton);
		
		JButton inventarioboton = new JButton("Inventario");
		inventarioboton.setBackground(new Color(255, 255, 0));
		inventarioboton.setBounds(33, 251, 175, 32);
		barraBotones.add(inventarioboton);
		
		empleadosboton = new JButton("Empleados");
		empleadosboton.setBackground(new Color(255, 255, 0));
		empleadosboton.setBounds(33, 307, 175, 32);
		barraBotones.add(empleadosboton);
		
		reportesboton = new JButton("Reportes");
		reportesboton.setBackground(new Color(255, 255, 0));
		reportesboton.setBounds(33, 368, 175, 32);
		barraBotones.add(reportesboton);
		
		lblTaqueria = new JLabel("Taqueria ");
		lblTaqueria.setForeground(new Color(255, 255, 0));
		lblTaqueria.setHorizontalAlignment(SwingConstants.CENTER);
		lblTaqueria.setFont(new Font("Showcard Gothic", Font.PLAIN, 25));
		lblTaqueria.setBounds(10, 21, 186, 48);
		barraBotones.add(lblTaqueria);
		
		lblEl = new JLabel("El");
		lblEl.setForeground(new Color(255, 255, 0));
		lblEl.setHorizontalAlignment(SwingConstants.CENTER);
		lblEl.setFont(new Font("Showcard Gothic", Font.PLAIN, 25));
		lblEl.setBounds(33, 56, 138, 38);
		barraBotones.add(lblEl);
		
		lblBigote = new JLabel("Bigote");
		lblBigote.setForeground(new Color(255, 255, 0));
		lblBigote.setHorizontalAlignment(SwingConstants.CENTER);
		lblBigote.setFont(new Font("Showcard Gothic", Font.PLAIN, 25));
		lblBigote.setBounds(33, 80, 138, 38);
		barraBotones.add(lblBigote);
		
		total = new JPanel();
		total.setBackground(new Color(255, 99, 71));
		total.setBounds(970, 0, 282, 598);
		contentPane.add(total);
		total.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(tarjeta1);
		scrollPane.setBounds(229, 0, 742, 598);
		contentPane.add(scrollPane);
		
		tarjeta1 = new JPanel();
		tarjeta1.setBackground(new Color(255, 255, 0));
		scrollPane.setViewportView(tarjeta1);
		tarjeta1.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(46, 119, 157, 168);
		tarjeta1.add(panel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(266, 119, 157, 168);
		tarjeta1.add(panel_1_1);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(495, 119, 157, 168);
		tarjeta1.add(panel_1_2);
		
		JPanel panel_1_3 = new JPanel();
		panel_1_3.setBounds(46, 354, 157, 168);
		tarjeta1.add(panel_1_3);
		
		panel = new JPanel();
		panel.setBounds(266, 354, 157, 168);
		tarjeta1.add(panel);
		
		panel_2 = new JPanel();
		panel_2.setBounds(495, 354, 157, 168);
		tarjeta1.add(panel_2);
	}
}*/
