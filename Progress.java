import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Progress {
    private static JFrame frame;
    private JProgressBar progressBar;
    private Timer timer;

    public Progress() {
        frame = new JFrame("Progress Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(102, 0, 51), 3));

        // Create a JLabel with the background image
        JLabel background = new JLabel(new ImageIcon("loginBg.jpg")); // Replace "background.jpg" with your image file
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(background);

        JLabel headingLabel = new JLabel("HD1.0");
        headingLabel.setBounds(100, 28, 380, 60);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 55));
        headingLabel.setForeground(new Color(102, 0, 51));
        background.add(headingLabel); // Add to the background JLabel

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(60, 108, 250, 30);
        progressBar.setStringPainted(true);
        background.add(progressBar); // Add to the background JLabel

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        timer = new Timer(50, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(progress);
                if (progress == 100) {
                    timer.stop();
                    frame.dispose(); // Close the progress frame
                    SwingUtilities.invokeLater(() -> {
                        new Login(); // Open the Login page
                    });
                }
                progress++;
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Progress();
        });
    }
}
