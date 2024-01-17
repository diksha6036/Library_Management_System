import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;

public class Register implements ActionListener {
    private static JFrame frame;
    private static JLabel headingLabel;
    private static JLabel[] labels;
    private static JTextField[] textFields;
    private static JLabel plabel, cplabel;
    private static JPasswordField pass;
    private static JPasswordField cpass;
    private static JButton registerButton;
    private static JButton exitButton;
    private static ImageIcon imageIcon;
    private static JLabel imageLabel;

    public Register() {
        frame = new JFrame("Registration Form");
        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2
        );
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));

       
        headingLabel = new JLabel("Registration");
        headingLabel.setBounds(60, 20, 380, 65);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 55));
        headingLabel.setForeground(new Color(102, 0, 51));
        frame.add(headingLabel);

        String[] labelNames = {"Full Name", "Username", "Date of Birth", "Email Id"};
        labels = new JLabel[labelNames.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(labelNames[i]);
            labels[i].setBounds(60, 100 + i * 35, 120, 20);
            frame.add(labels[i]);
        }

      
        textFields = new JTextField[labelNames.length];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField();
            textFields[i].setBounds(180, 100 + i * 35, 193, 28);
            frame.add(textFields[i]);
        }

        plabel = new JLabel("Password");
        plabel.setBounds(60, 240, 120, 20);
        frame.add(plabel);
        cplabel = new JLabel("Confirm Password");
        cplabel.setBounds(60, 275, 120, 20);
        frame.add(cplabel);

        pass = new JPasswordField();
        pass.setBounds(180, 240, 193, 28);
        frame.add(pass);
        cpass = new JPasswordField();
        cpass.setBounds(180, 275, 193, 28);
        frame.add(cpass);

        
        registerButton = new JButton("Register");
        registerButton.setBounds(130, 330, 100, 25);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 153, 153)); 
        registerButton.addActionListener(this);
        frame.add(registerButton);

        
        exitButton = new JButton("Exit");
        exitButton.setBounds(240, 330, 80, 25);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(64,64,64));
        exitButton.addActionListener(e -> System.exit(0));
        frame.add(exitButton);

        
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("/register.png"));
            int scaledWidth = 200;
            int scaledHeight = 300;
            BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaledImage.createGraphics();
            g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g.dispose();
            imageIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(400, 50, 200, 300);
        frame.add(imageLabel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (areFieldsValid()) {
            String url = "jdbc:mysql://localhost:3306/library";
            String mysqluser = "root";
            String mysqlpasswd = "1234";

            // Database insertion code
            String insertQuery = "INSERT INTO login (username, password) VALUES (?, ?)";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, mysqluser, mysqlpasswd);
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, textFields[1].getText()); // Username
                ps.setString(2, new String(pass.getPassword())); // Password
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registration Successful");
                con.close();
                frame.dispose();
                new Login();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private boolean areFieldsValid() {
        for (JTextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return false; 
            }
        }

       
        String email = textFields[3].getText().trim();
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid Email Address");
            return false;
        }

       
        char[] password = pass.getPassword();
        char[] confirmPassword = cpass.getPassword();
        if (!isPasswordValid(password, confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match");
            return false;
        }

        return true; 
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailPattern);
    }

    private boolean isPasswordValid(char[] password, char[] confirmPassword) {
        return new String(password).equals(new String(confirmPassword));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Register();
        });
    }
}