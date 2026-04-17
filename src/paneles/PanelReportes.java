package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import controladores.CReportes;

public class PanelReportes extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Color azulPrincipal = new Color(31, 42, 68);
    private final Color rosaAcento    = new Color(233, 30, 99);
    private final Color fondoClaro    = new Color(245, 247, 250);
    private final Color blanco        = Color.WHITE;

    private JLabel lblVentaC1, lblOrdenC1, lblPromedioC1, lblPorcentajeC1;
    private JLabel lblVentaC2, lblOrdenC2, lblPromedioC2, lblPorcentajeC2;

    public PanelReportes() {

        setLayout(new BorderLayout(25, 25));
        setBackground(fondoClaro);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel lblTitulo = new JLabel("Reportes de Ventas - Cortes del Día");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(azulPrincipal);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel contenedorTurnos = new JPanel(new GridLayout(1, 2, 30, 30));
        contenedorTurnos.setOpaque(false);

        lblVentaC1 = new JLabel("$0.00");
        lblOrdenC1 = new JLabel("0");
        lblPromedioC1 = new JLabel("$0.00");
        lblPorcentajeC1 = new JLabel("0% del total del día");

        lblVentaC2 = new JLabel("$0.00");
        lblOrdenC2 = new JLabel("0");
        lblPromedioC2 = new JLabel("$0.00");
        lblPorcentajeC2 = new JLabel("0% del total del día");

        Icon iconoSol  = new FlatSVGIcon("icons/sun.svg", 22, 22);
        Icon iconoLuna = new FlatSVGIcon("icons/moon.svg", 22, 22);

        JPanel turno1 = crearPanelTurno(
                "Corte 1 (Mañana)",
                "08:00 - 15:00 hrs",
                iconoSol,
                lblVentaC1, lblOrdenC1, lblPromedioC1, lblPorcentajeC1
        );

        JPanel turno2 = crearPanelTurno(
                "Corte 2 (Tarde)",
                "15:00 - 23:00 hrs",
                iconoLuna,
                lblVentaC2, lblOrdenC2, lblPromedioC2, lblPorcentajeC2
        );

        contenedorTurnos.add(turno1);
        contenedorTurnos.add(turno2);

        add(contenedorTurnos, BorderLayout.CENTER);
    }

    private JPanel crearPanelTurno(String titulo, String horario, Icon iconoTurno,
                                    JLabel lblVenta, JLabel lblOrden,
                                    JLabel lblPromedio, JLabel lblPorcentaje) {

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(blanco);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 30, 25, 30)
        ));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(blanco);

        JLabel lblTituloTurno = new JLabel(titulo);
        lblTituloTurno.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloTurno.setForeground(azulPrincipal);

        JLabel lblHorario = new JLabel(horario, iconoTurno, SwingConstants.RIGHT);
        lblHorario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblHorario.setForeground(Color.GRAY);
        lblHorario.setHorizontalTextPosition(SwingConstants.LEFT);

        header.add(lblTituloTurno, BorderLayout.WEST);
        header.add(lblHorario, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);

        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BoxLayout(metricsPanel, BoxLayout.Y_AXIS));
        metricsPanel.setBackground(blanco);

        JPanel pVenta = crearFilaMetrica(
                new FlatSVGIcon("icons/money.svg", 20, 20),
                "Ventas Totales",
                lblVenta
        );

        JPanel pOrden = crearFilaMetrica(
                new FlatSVGIcon("icons/receipt.svg", 20, 20),
                "Órdenes Procesadas",
                lblOrden
        );

        JPanel pProm = crearFilaMetrica(
                new FlatSVGIcon("icons/chart.svg", 20, 20),
                "Ticket Promedio",
                lblPromedio
        );

        metricsPanel.add(pVenta);
        metricsPanel.add(pOrden);
        metricsPanel.add(pProm);

        panel.add(metricsPanel, BorderLayout.CENTER);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));

        lblPorcentaje.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPorcentaje.setForeground(rosaAcento);
        lblPorcentaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pie = new JPanel();
        pie.setLayout(new BoxLayout(pie, BoxLayout.Y_AXIS));
        pie.setBackground(blanco);
        pie.add(sep);
        pie.add(Box.createVerticalStrut(15));
        pie.add(lblPorcentaje);

        panel.add(pie, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearFilaMetrica(Icon icono, String texto, JLabel valorLabel) {

        JPanel fila = new JPanel(new BorderLayout(15, 0));
        fila.setBackground(blanco);
        fila.setBorder(new EmptyBorder(12, 0, 12, 0));
        fila.setMaximumSize(new Dimension(450, 50));

        JLabel lblIcono = new JLabel(icono);

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTexto.setForeground(new Color(70, 70, 70));

        valorLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valorLabel.setForeground(azulPrincipal);
        valorLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        fila.add(lblIcono, BorderLayout.WEST);
        fila.add(lblTexto, BorderLayout.CENTER);
        fila.add(valorLabel, BorderLayout.EAST);

        return fila;
    }

    public void actualizar() {

        LocalTime inicioC1 = LocalTime.of(8, 0);
        LocalTime finC1    = LocalTime.of(15, 0);

        LocalTime inicioC2 = LocalTime.of(15, 0);
        LocalTime finC2    = LocalTime.of(23, 0);

        double[] datosC1 = CReportes.obtenerDatosCorte(inicioC1, finC1);
        double[] datosC2 = CReportes.obtenerDatosCorte(inicioC2, finC2);
        double totalDia  = CReportes.obtenerVentasDia();

        double pct1 = (totalDia > 0) ? (datosC1[0] / totalDia) * 100 : 0;

        lblVentaC1.setText(String.format("$%.2f", datosC1[0]));
        lblOrdenC1.setText(String.valueOf((int)datosC1[1]));
        lblPromedioC1.setText(String.format("$%.2f", datosC1[2]));
        lblPorcentajeC1.setText(String.format("%.1f%% del total del día", pct1));

        double pct2 = (totalDia > 0) ? (datosC2[0] / totalDia) * 100 : 0;

        lblVentaC2.setText(String.format("$%.2f", datosC2[0]));
        lblOrdenC2.setText(String.valueOf((int)datosC2[1]));
        lblPromedioC2.setText(String.format("$%.2f", datosC2[2]));
        lblPorcentajeC2.setText(String.format("%.1f%% del total del día", pct2));
    }
}