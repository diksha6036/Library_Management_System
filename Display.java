import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Display {
    JFrame f;
    JTable j;

    Display() {
        f = new JFrame("Display Records");
        f.setSize(400, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        f.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));

        JLabel heading = new JLabel("Book Store");
        heading.setFont(new Font("Serif", Font.BOLD, 32));
        heading.setForeground(new Color(102, 0, 51));
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnNames = { "BOOK NAME", "BOOK ID", "PRICE", "AUTHOR NAME" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String user = "root";
            String password = "1234";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String query = "SELECT Bname, Bid, Price, Author FROM books";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("Bname");
                String id = resultSet.getString("Bid");
                String price = resultSet.getString("Price");
                String author = resultSet.getString("Author");
                model.addRow(new Object[] { name, id, price, author });
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        j = new JTable(model);
        j.setPreferredScrollableViewportSize(new Dimension(300, 100));
        j.setFillsViewportHeight(true);

        TableColumnModel columnModel = j.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(1);
        columnModel.getColumn(2).setPreferredWidth(1);
        columnModel.getColumn(3).setPreferredWidth(140);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < j.getColumnCount(); i++) {
            j.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane sp = new JScrollPane(j);

        JPanel bottomPanel = new JPanel();
        // bottomPanel.setBackground(new Color(102, 0, 51));

        JButton addButton = new JButton("Add");
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(63, 137, 255));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new AddBook();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(201,69,47));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new DelBook();
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(24, 111, 101));
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              f.dispose();
              new UpdateBook();
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(255,178,102));
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Login(); 
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(64,64,64));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(logoutButton);
        bottomPanel.add(exitButton);

        f.add(heading, BorderLayout.NORTH);
        f.add(sp, BorderLayout.CENTER);
        f.add(bottomPanel, BorderLayout.SOUTH);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Display();
            }
        });
    }
}
