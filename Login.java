import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

class Login implements ActionListener {
    private static JFrame frame;
    private static JLabel label;
    private static JTextField user;
    private static JButton loginButton;
    private static JButton registerButton;
    private static JButton exitButton;
    private static JPasswordField password;

    public Login() {
        frame = new JFrame("LOGIN PAGE");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a background panel with the path to your background image
        BackgroundPanel backgroundPanel = new BackgroundPanel("loginBg.jpg");
        backgroundPanel.setLayout(null);

        frame.setContentPane(backgroundPanel);

        frame.setLocation(
            (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2
        );
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));

        JLabel headingLabel = new JLabel("Login Account");
        headingLabel.setBounds(60, 20, 380, 65);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 55));
        headingLabel.setForeground(new Color(102, 0, 51));
        frame.add(headingLabel);

        label = new JLabel("Username");
        label.setBounds(130, 105, 70, 20);
        frame.add(label);

        user = new JTextField();
        user.setBounds(130, 140, 193, 28);
        frame.add(user);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(130, 190, 70, 20);
        frame.add(passwordLabel);

        password = new JPasswordField();
        password.setBounds(130, 225, 193, 28);
        frame.add(password);

        loginButton = new JButton("Login");
        loginButton.setBounds(130, 300, 80, 25);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(255,178,102));
        loginButton.addActionListener(this);
        frame.add(loginButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(230, 300, 80, 25);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(64,64,64));
        exitButton.addActionListener(e -> System.exit(0));
        frame.add(exitButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(180, 340, 100, 25);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 153, 153)); 
        registerButton.addActionListener(this);
        frame.add(registerButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String url = "jdbc:mysql://localhost:3306/library";
            String mysqluser = "root";
            String mysqlpasswd = "1234";
            String pswrd = new String(password.getPassword());
            String username = user.getText();
            String query = "SELECT password FROM login WHERE username = ?";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, mysqluser, mysqlpasswd);
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String realpasswrd = rs.getString("password");
                    if (realpasswrd.equals(pswrd)) {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        frame.dispose();
                        new BookStore();
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or Password mismatch");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Username");
                }
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
       
            
            
        if (e.getSource() == registerButton) {
            frame.dispose();
            new Register();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login();
        });
    }
}
