import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConversorMonedasGUI extends JFrame {

    private JComboBox<String> cbDesde;
    private JComboBox<String> cbHacia;
    private JTextField txtCantidad;
    private JLabel lblResultado;
    private ConversorMonedas conversor;

    public ConversorMonedasGUI() {
        conversor = new ConversorMonedas();

        setTitle("Conversor de Monedas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Desde:"));
        cbDesde = new JComboBox<>();
        panel.add(cbDesde);

        panel.add(new JLabel("Hacia:"));
        cbHacia = new JComboBox<>();
        panel.add(cbHacia);

        panel.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panel.add(txtCantidad);

        JButton btnConvertir = new JButton("Convertir");
        btnConvertir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertirMoneda();
            }
        });
        panel.add(btnConvertir);

        lblResultado = new JLabel("");
        panel.add(lblResultado);

        add(panel);

        // Obtener las monedas disponibles
        try {
            String[] monedas = conversor.obtenerMonedas();
            for (String moneda : monedas) {
                cbDesde.addItem(moneda);
                cbHacia.addItem(moneda);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener las monedas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void convertirMoneda() {
        try {
            String monedaDesde = (String) cbDesde.getSelectedItem();
            String monedaHacia = (String) cbHacia.getSelectedItem();
            double cantidad = Double.parseDouble(txtCantidad.getText());

            double resultado = conversor.convertir(monedaDesde, monedaHacia, cantidad);

            lblResultado.setText(String.format("%.2f %s = %.2f %s", cantidad, monedaDesde, resultado, monedaHacia));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener la tasa de cambio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}