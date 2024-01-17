import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BookStore implements ActionListener {
    private static JFrame f;
    private static ImageIcon imageIcon;
    private static JLabel imageLabel;

    public BookStore() {
        f = new JFrame("Book Store");
        f.setSize(600, 500);
        f.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation((int) (screenSize.getWidth() - f.getWidth()) / 2, (int) (screenSize.getHeight() - f.getHeight()) / 2);
        f.setUndecorated(true);
        f.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));

        JLabel heading = new JLabel("Book Store");
        heading.setBounds(170, 50, 400, 50);
        heading.setFont(new Font("Serif", Font.BOLD, 55));
        heading.setForeground(new Color(102, 0, 51));
        f.add(heading);

        JButton addbtn = new JButton("Add");
        addbtn.setForeground(Color.white);
        addbtn.setBackground(new Color(63, 137, 255));
        addbtn.setBounds(150, 180, 110, 50);
        f.add(addbtn);
        addbtn.addActionListener(this);

        JButton deletebtn = new JButton("Delete");
        deletebtn.setForeground(Color.white);
        deletebtn.setBackground(new Color(201,69,47));
        deletebtn.setBounds(340, 180, 110, 50);
        f.add(deletebtn);

        deletebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new DelBook();
            }
        });

        JButton updatebtn = new JButton("Update");
        updatebtn.setForeground(Color.white);
        updatebtn.setBackground(new Color(24, 111, 101));
        updatebtn.setBounds(150, 280, 110, 50);
        f.add(updatebtn);

        updatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new UpdateBook();
            }
        });

        JButton displaybtn = new JButton("Display");
        displaybtn.setBackground(new Color(153, 0, 153));
        displaybtn.setForeground(Color.white);
        displaybtn.setBounds(340, 280, 110, 50);
        f.add(displaybtn);

        JButton exit = new JButton("Exit");
        exit.setForeground(Color.white);
        exit.setBackground(new Color(64,64,64));
        exit.setBounds(150, 380, 110, 50);
        f.add(exit);

        JButton login = new JButton("Logout");
        login.setForeground(Color.white);
        login.setBackground(new Color(255,178,102));
        login.setBounds(340, 380, 110, 50);
        f.add(login);
        exit.addActionListener(e -> System.exit(0));

        displaybtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Display();
            }
        });

        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 0, 800, 600);
        panel2.setLayout(new BorderLayout());
        f.add(panel2);

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("./loginBg.jpg"));
            int scaledWidth = 800;
            int scaledHeight = 600;
            BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaledImage.createGraphics();
            g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g.dispose();
            imageIcon = new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(400, 50, 200, 300);
        panel2.add(imageLabel);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Login();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        f.dispose();
        new AddBook();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BookStore();
            }
        });
    }
}
