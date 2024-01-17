import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateBook {
    public UpdateBook() {
        JFrame frame = new JFrame("Update a Book");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));

        JPanel panel = new JPanel(null);
        frame.add(panel);

        JLabel heading = new JLabel("Update a Book");
        heading.setBounds(140, 30, 200, 50);
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        heading.setForeground(new Color(102, 0, 51));
        panel.add(heading);

        JLabel updateLabel = new JLabel("Enter Book ID or Name to Update:");
        updateLabel.setBounds(110, 100, 340, 30);
        updateLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(updateLabel);

        JTextField updateField = new JTextField();
        updateField.setBounds(130, 150, 220, 30);
        panel.add(updateField);

        JLabel bNameLabel = new JLabel("Book Name:");
        bNameLabel.setBounds(50, 200, 100, 30);
        bNameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(bNameLabel);

        JTextField bNameField = new JTextField();
        bNameField.setBounds(160, 200, 220, 30);
        panel.add(bNameField);

        JLabel bIdLabel = new JLabel("Book ID:");
        bIdLabel.setBounds(50, 250, 100, 30);
        bIdLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(bIdLabel);

        JTextField bIdField = new JTextField();
        bIdField.setBounds(160, 250, 220, 30);
        panel.add(bIdField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 300, 100, 30);
        priceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(160, 300, 220, 30);
        panel.add(priceField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 350, 100, 30);
        authorLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(160, 350, 220, 30);
        panel.add(authorField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(70, 420, 90, 30);
        updateButton.setBackground(new Color(24, 111, 101));
        updateButton.setForeground(Color.WHITE);
        panel.add(updateButton);

        JButton displayButton = new JButton("Display");
        displayButton.setBounds(180, 420, 90, 30);
        displayButton.setBackground(new Color(153, 0, 153));
        displayButton.setForeground(Color.WHITE);
        panel.add(displayButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(280, 420, 90, 30);
        cancelButton.setBackground(new Color(64, 64, 64));
        cancelButton.setForeground(Color.WHITE);
        panel.add(cancelButton);


        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = updateField.getText().trim();
                String bName = bNameField.getText().trim();
                String bId = bIdField.getText().trim();
                String price = priceField.getText().trim();
                String author = authorField.getText().trim();

                if (!input.isEmpty() && !bName.isEmpty() && !bId.isEmpty() && !price.isEmpty() && !author.isEmpty()) {
                    String url = "jdbc:mysql://localhost:3306/library";
                    String user = "root";
                    String pwd = "1234";
                    String query = "UPDATE books SET Bname=?, Bid=?, Price=?, Author=? WHERE Bid=? OR Bname=?";

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection(url, user, pwd);
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setString(1, bName);
                        ps.setString(2, bId);
                        ps.setString(3, price);
                        ps.setString(4, author);
                        ps.setString(5, input);
                        ps.setString(6, input);

                        int rows = ps.executeUpdate();

                        if (rows > 0) {
                            JOptionPane.showMessageDialog(null, "Book updated in Library");
                        } else {
                            JOptionPane.showMessageDialog(null, "No such book available");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Display();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UpdateBook();
            }
        });
    }
}
