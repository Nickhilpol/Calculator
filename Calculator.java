package Exgui;
import javax.swing.*;
import java.awt.*;

public class Calculator {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");

        /* Frame */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(360, 500);

        /* Screen */

        JTextField screen = new JTextField(10);

        screen.setFont(new Font("Monospace", Font.PLAIN, 26));
        screen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        screen.setEditable(false);
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setText("0");
        frame.add(screen, BorderLayout.NORTH);

        /*Keypad*/
        JPanel keypad = new JPanel();
        keypad.setLayout(new GridLayout(5, 4));
        String[] symbols = {"C", "^", "%", "/", "7", "8", "9", "x", "4", "5", "6", "-", "1", "2", "3", "+", "00", "0", ".", "="};

        for (int i = 0; i < symbols.length; i++) {
            JButton button = new JButton(symbols[i]);
            button.setFont(new Font("Monospace", Font.PLAIN, 20));
            int finalI = i;

            if (isNumber(symbols[i])) {
                button.addActionListener(e -> {
                    if (screen.getText().equals("0")) {
                        screen.setText(symbols[finalI]);
                    } else {
                        screen.setText(screen.getText() + symbols[finalI]);
                    }
                });
            }

            if (isOperator(symbols[i])) {
                button.addActionListener(e -> {
                    if (isOperator(screen.getText().substring(screen.getText().length() - 1))) {
                        screen.setText(screen.getText().substring(0, screen.getText().length() - 1) + symbols[finalI]);
                    } else {
                        screen.setText(screen.getText() + symbols[finalI]);
                    }
                });
            }

            if (symbols[i].equals("C")) {
                button.addActionListener(e -> {
                    screen.setText("0");
                });
            }

            if (symbols[i].equals("=")) {
                button.addActionListener(e -> {
                    screen.setText(calculate(screen.getText()));
                });
            }

            if (symbols[i].equals(".")) {
                button.addActionListener(e -> {
                    if (!screen.getText().contains(".")) {
                        screen.setText(screen.getText() + symbols[finalI]);
                    }
                });
            }

            if (symbols[i].equals("")) {
                button.setEnabled(false);
            }

            keypad.add(button);
        }

        frame.add(keypad, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static String calculate(String text) {

        String[] numbers = text.split("[+x/^%-]");
        String[] operators = text.split("[0-9.]+");
        double result = Double.parseDouble(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {

            switch (operators[i]) {
                case "+" -> result += Double.parseDouble(numbers[i]);
                case "-" -> result -= Double.parseDouble(numbers[i]);
                case "x" -> result *= Double.parseDouble(numbers[i]);
                case "/" -> result /= Double.parseDouble(numbers[i]);
                case "^" -> result = Math.pow(result, Double.parseDouble(numbers[i]));
                case "%" -> result %= Double.parseDouble(numbers[i]);
            }
        }
        return result % 1 == 0 ? String.valueOf((int) result) : String.valueOf(result);
    }

    private static boolean isNumber(String str) {
        return str.matches("[0-9]+");
    }

    private static boolean isOperator(String s) {
        return s.matches("[+x/%^-]");
    }
}