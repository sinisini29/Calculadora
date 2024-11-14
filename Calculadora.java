import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private JTextField pantalla;
    private double resultado;
    private String operador;
    private boolean nuevoNumero;

    public Calculadora() {
        resultado = 0;
        operador = "";
        nuevoNumero = true;

        // Configuración de la ventana
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Campo de texto para la pantalla
        pantalla = new JTextField("0");
        pantalla.setEditable(false);
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setFont(new Font("Arial", Font.BOLD, 24));
        add(pantalla, BorderLayout.NORTH);

        // Panel de botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        // Añadir botones
        String[] botones = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "√", "^2", "%", "C"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 18));
            boton.addActionListener(this);
            panel.add(boton);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            if ("0123456789.".contains(comando)) {
                if (nuevoNumero) {
                    pantalla.setText(comando);
                    nuevoNumero = false;
                } else {
                    pantalla.setText(pantalla.getText() + comando);
                }
            } else if (comando.equals("C")) {
                resultado = 0;
                operador = "";
                pantalla.setText("0");
                nuevoNumero = true;
            } else if (comando.equals("=")) {
                calcular(Double.parseDouble(pantalla.getText()));
                operador = "";
                pantalla.setText(String.valueOf(resultado));
                nuevoNumero = true;
            } else if (comando.equals("√")) {
                double valor = Double.parseDouble(pantalla.getText());
                resultado = Math.sqrt(valor);
                pantalla.setText(String.valueOf(resultado));
                nuevoNumero = true;
            } else if (comando.equals("^2")) {
                double valor = Double.parseDouble(pantalla.getText());
                resultado = Math.pow(valor, 2);
                pantalla.setText(String.valueOf(resultado));
                nuevoNumero = true;
            } else if (comando.equals("%")) {
                double valor = Double.parseDouble(pantalla.getText());
                resultado = valor / 100;
                pantalla.setText(String.valueOf(resultado));
                nuevoNumero = true;
            } else {
                if (!operador.isEmpty()) {
                    calcular(Double.parseDouble(pantalla.getText()));
                } else {
                    resultado = Double.parseDouble(pantalla.getText());
                }
                operador = comando;
                nuevoNumero = true;
            }
        } catch (NumberFormatException ex) {
            pantalla.setText("Error");
            nuevoNumero = true;
        }
    }

    private void calcular(double numero) {
        switch (operador) {
            case "+":
                resultado += numero;
                break;
            case "-":
                resultado -= numero;
                break;
            case "*":
                resultado *= numero;
                break;
            case "/":
                if (numero != 0) {
                    resultado /= numero;
                } else {
                    pantalla.setText("Error");
                }
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculadora calculadora = new Calculadora();
            calculadora.setVisible(true);
        });
    }
}
