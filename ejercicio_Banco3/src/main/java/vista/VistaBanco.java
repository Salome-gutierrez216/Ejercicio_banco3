package vista;

import controlador.ControladorBanco;
import modelo.Fecha;
import javax.swing.*;
import java.awt.*;

public class VistaBanco extends JFrame {
    private ControladorBanco controlador;

    private JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Ahorros", "Corriente"});
    private JTextField txtNumero = new JTextField(10);
    private JTextField txtTitular = new JTextField(10);
    private JTextField txtDia = new JTextField(2);
    private JTextField txtMes = new JTextField(2);
    private JTextField txtAnio = new JTextField(4);
    private JTextField txtSaldo = new JTextField(10);
    private JTextField txtCupo = new JTextField(10);
    private JTextField txtValor = new JTextField(10);
    private JTextArea areaSalida = new JTextArea(8, 30);

    public VistaBanco(ControladorBanco controlador) {
        this.controlador = controlador;

        setTitle("Sistema Bancario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Panel de datos
        JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5));
        panelDatos.add(new JLabel("Tipo de cuenta:"));  panelDatos.add(cmbTipo);
        panelDatos.add(new JLabel("Número:"));          panelDatos.add(txtNumero);
        panelDatos.add(new JLabel("Titular:"));         panelDatos.add(txtTitular);
        panelDatos.add(new JLabel("Día:"));             panelDatos.add(txtDia);
        panelDatos.add(new JLabel("Mes:"));             panelDatos.add(txtMes);
        panelDatos.add(new JLabel("Año:"));             panelDatos.add(txtAnio);
        panelDatos.add(new JLabel("Saldo inicial:"));   panelDatos.add(txtSaldo);
        panelDatos.add(new JLabel("Cupo sobregiro:"));  panelDatos.add(txtCupo);
        panelDatos.add(new JLabel("Valor operación:")); panelDatos.add(txtValor);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCrear = new JButton("Crear cuenta");
        JButton btnConsignar = new JButton("Consignar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnSaldo = new JButton("Ver saldo");
        JButton btnDatos = new JButton("Ver todos los datos");
        panelBotones.add(btnCrear);
        panelBotones.add(btnConsignar);
        panelBotones.add(btnRetirar);
        panelBotones.add(btnSaldo);
        panelBotones.add(btnDatos);

        areaSalida.setEditable(false);

        add(panelDatos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(new JScrollPane(areaSalida), BorderLayout.SOUTH);

        // El cupo de sobregiro solo se usa en cuentas corrientes
        txtCupo.setEnabled(false);
        cmbTipo.addActionListener(e ->
            txtCupo.setEnabled(cmbTipo.getSelectedItem().equals("Corriente")));

        // Eventos de los botones
        btnCrear.addActionListener(e -> crearCuenta());
        btnConsignar.addActionListener(e -> consignar());
        btnRetirar.addActionListener(e -> retirar());
        btnSaldo.addActionListener(e -> consultar(true));
        btnDatos.addActionListener(e -> consultar(false));

        pack();
        setLocationRelativeTo(null);
    }

    private void crearCuenta() {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            String titular = txtTitular.getText().trim();
            Fecha fecha = new Fecha(Integer.parseInt(txtDia.getText().trim()),
                                    Integer.parseInt(txtMes.getText().trim()),
                                    Integer.parseInt(txtAnio.getText().trim()));
            double saldo = Double.parseDouble(txtSaldo.getText().trim());
            boolean creada;

            if (cmbTipo.getSelectedItem().equals("Ahorros")) {
                creada = controlador.crearCuentaAhorros(numero, titular, fecha, saldo);
            } else {
                double cupo = Double.parseDouble(txtCupo.getText().trim());
                creada = controlador.crearCuentaCorriente(numero, titular, fecha, saldo, cupo);
            }

            areaSalida.setText(creada ? "Cuenta creada correctamente."
                                      : "Ya existe una cuenta con ese número.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Revisa que los campos numéricos sean válidos.");
        }
    }

    private void consignar() {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            double valor = Double.parseDouble(txtValor.getText().trim());
            areaSalida.setText(controlador.consignar(numero, valor)
                    ? "Consignación exitosa."
                    : "No se pudo consignar (cuenta inexistente o valor inválido).");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de cuenta o valor inválido.");
        }
    }

    private void retirar() {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            double valor = Double.parseDouble(txtValor.getText().trim());
            areaSalida.setText(controlador.retirar(numero, valor)
                    ? "Retiro exitoso."
                    : "Retiro rechazado: fondos insuficientes o cuenta inexistente.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de cuenta o valor inválido.");
        }
    }

    private void consultar(boolean soloSaldo) {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            areaSalida.setText(controlador.consultar(numero, soloSaldo));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de cuenta inválido.");
        }
    }
}