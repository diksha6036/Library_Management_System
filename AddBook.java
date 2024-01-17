import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddBook {
    private static JLabel[] labels;
    private static JTextField[] textFields;

    public AddBook() {
        JFrame f = new JFrame("Add Book");
        f.setSize(600, 500);
        f.setLayout(null);
        f.setUndecorated(true);
        f.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));
        f.setLocationRelativeTo(null);

        // Create a JLabel with a background image
        ImageIcon background = new ImageIcon("loginBg.jpg");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 600, 500);
        f.add(backgroundLabel);

        JLabel heading = new JLabel("Add Book");
        heading.setBounds(200, 40, 250, 50);
        heading.setFont(new Font("Serif", Font.BOLD, 52));
        heading.setForeground(new Color(102, 0, 51));
        backgroundLabel.add(heading);

        String[] labelNames = { "BName", "Bid", "Price", "Author" };
        labels = new JLabel[labelNames.length];
        textFields = new JTextField[labelNames.length];

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(labelNames[i] + ":");
            labels[i].setFont(new Font("Serif", Font.BOLD, 20));
            labels[i].setBounds(100, 115 + i * 60, 120, 30);
            backgroundLabel.add(labels[i]);

            textFields[i] = new JTextField();
            textFields[i].setBounds(230, 115 + i * 60, 260, 30);
            textFields[i].setFont(new Font("Serif", Font.PLAIN, 18));
            backgroundLabel.add(textFields[i]);
        }

        JButton btn = new JButton("Add Book");
        btn.setBounds(190, 350, 120, 50);
        btn.setFont(new Font("Serif", Font.BOLD, 15));
        btn.setBackground(new Color(63, 137, 255));
        btn.setForeground(Color.white);
        backgroundLabel.add(btn);

        JButton back = new JButton("Back");
        back.setBounds(190, 420, 120, 50);
        back.setFont(new Font("Serif", Font.BOLD, 15));
        backgroundLabel.add(back);
        back.setBackground(new Color(201, 69, 47));
        back.setForeground(Color.white);

        JButton displaybtn = new JButton("Display");
        displaybtn.setFont(new Font("Serif", Font.BOLD, 15));
        displaybtn.setBackground(new Color(153, 0, 153));
        displaybtn.setForeground(Color.white);
        displaybtn.setBounds(320, 350, 120, 50);
        backgroundLabel.add(displaybtn);

        JButton exit = new JButton("Exit");
        exit.setBounds(320, 420, 120, 50);
        exit.setFont(new Font("Serif", Font.BOLD, 15));
        backgroundLabel.add(exit);
        exit.addActionListener(e -> System.exit(0));
        exit.setBackground(new Color(64, 64, 64));
        exit.setForeground(Color.white);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookName = textFields[0].getText().trim();
                String bookId = textFields[1].getText().trim();
                String price = textFields[2].getText().trim();
                String author = textFields[3].getText().trim();

                if (bookName.isEmpty() || bookId.isEmpty() || price.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Text field is required!");
                } else if (!price.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Price must be in digits only.");
                } else if (!author.matches("^[A-Za-z\\s]+$")) {
                    JOptionPane.showMessageDialog(null, "Author should contain only letters.");
                } else if (!bookName.matches("^[A-Za-z\\s]+$")) {
                    JOptionPane.showMessageDialog(null, "Book Name should contain only letters.");
                } else {
                    try {
                        String url = "jdbc:mysql://localhost:3306/library";
                        String user = "root";
                        String password = "1234";
                        Connection connection = DriverManager.getConnection(url, user, password);

                        String query = "INSERT INTO books (Bid, Bname, Price, Author) VALUES (?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, bookId);
                        preparedStatement.setString(2, bookName);
                        preparedStatement.setString(3, price);
                        preparedStatement.setString(4, author);

                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            f.dispose();
                            new RecAdded();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add the book.");
                        }

                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        displaybtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Display();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new BookStore();
            }
        });

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddBook();
            }
        });
    }
}
