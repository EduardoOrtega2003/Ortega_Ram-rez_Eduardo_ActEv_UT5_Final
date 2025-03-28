import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Register extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    
    public Register() {
        setTitle("Registro");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(116, 116, 116));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Registro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        add(nameField, gbc);

        JLabel emailLabel = new JLabel("Correo electrónico:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        registerButton = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
    }
    
    private void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
        } else if (!Password.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres");
        } else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Correo electrónico inválido");
        } else {
            saveUser(email, password);
            JOptionPane.showMessageDialog(this, "Registro exitoso");
            new Login().setVisible(true);
            this.dispose(); // Cierra la ventana de registro
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
    
    private void saveUser(String email, String password) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios/users.txt", true));
            String hashedPassword = Password.hashPassword(password);
            writer.write(email + "," + hashedPassword);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
