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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Correo electrónico:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        loginButton = new JButton("Iniciar sesión");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(loginButton, gbc);

        registerButton = new JButton("Registrarse");
        gbc.gridx = 1;
        add(registerButton, gbc);

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
                if (data[0].equals(email) && Password.verifyPassword(password, data[1])) {
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
