import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel titleLabel;
    private JButton enrollButton;
    private JButton searchButton;
    private JButton exitButton;

    public MainPage() {
        setTitle("Jet Ski Booking Service System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        titleLabel = new JLabel("Jet Ski Service Menu", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        enrollButton = new JButton("Data Entry");
        enrollButton.addActionListener(this);
        searchButton = new JButton("Search Booking");
        searchButton.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        enrollButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(enrollButton);
        panel.add(searchButton);
        panel.add(exitButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);

        if (actionCommand.equals("Data Entry")) {
            new EnrollPage();
        } else if (actionCommand.equals("Search Booking")) {
            new SearchPage();
        } else if (actionCommand.equals("Exit")) {
            int chosenOption = JOptionPane.showConfirmDialog(this, "Do you want to exit the program?",
                    "Jet Ski Booking System", JOptionPane.OK_CANCEL_OPTION);
            if (chosenOption == JOptionPane.OK_OPTION)
                System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}