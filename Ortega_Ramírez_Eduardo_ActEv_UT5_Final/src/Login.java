import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
;

public class Login extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    
    public Login() {
        setTitle("Inicio de sesión");
        setLayout(new FlowLayout());
        setSize(300, 150);
        
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Iniciar sesión");
        registerButton = new JButton("Registrarse");
        
        add(new JLabel("Correo electrónico:"));
        add(emailField);
        add(new JLabel("Contraseña:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openRegisterScreen();
            }
        });
    }
    
    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        if (checkLogin(email, password)) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido!");
            new Notas(email).setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }
    
    private boolean checkLogin(String email, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("usuarios/users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(email) && PasswordUtils.verifyPassword(password, data[1])) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    private void openRegisterScreen() {
        new Register().setVisible(true);
        this.setVisible(false);
    }
}
