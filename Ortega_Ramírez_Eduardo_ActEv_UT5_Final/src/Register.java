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
        setLayout(new FlowLayout());
        setSize(300, 200);
        
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Registrar");
        
        add(new JLabel("Nombre:"));
        add(nameField);
        add(new JLabel("Correo electrónico:"));
        add(emailField);
        add(new JLabel("Contraseña:"));
        add(passwordField);
        add(registerButton);
        
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
            this.setVisible(false);
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
