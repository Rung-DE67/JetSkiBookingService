import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class EnrollPage extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel titleLabel;

    private JComboBox<String> typeCombo;
    private JTextField nameTxt;
    private JComboBox<String> paxCombo;
    private JComboBox<String> durationCombo;
    private JTextField routeTxt;
    private JTextField minutesTxt;
    
    private JLabel durationLbl;
    private JLabel routeLbl;
    private JLabel minutesLbl;
    private JPanel stdPanel;

    private JTextArea bookingListTxtArea;
    private JButton addBookingBtn;
    private JButton backMainBtn;

    private void updateRouteByDuration() {
        String duration = (String) durationCombo.getSelectedItem();
        if ("4".equals(duration)) {
            routeTxt.setText("Khai Island - Coral Island - Racha Island");
        } else if ("6".equals(duration)) {
            routeTxt.setText("Khai Island - Coral Island - Racha Island - Phi Phi Island - Emerald Cave");
        } else if ("8".equals(duration)) {
            routeTxt.setText("Khai Island - Coral Island - Racha Island - Phi Phi Island - Emerald Cave - Sunset View");
        }
    }

    public EnrollPage() {
        super("Jet Ski Booking System");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(550, 600);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Jet Ski Booking Module", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        JLabel typeLbl = new JLabel("Booking Type");
        JLabel nameLbl = new JLabel("Customer Name");
        JLabel paxLbl = new JLabel("Passenger Count");
        durationLbl = new JLabel("Trip Duration (Hours)");
        routeLbl = new JLabel("Island Route");
        minutesLbl = new JLabel("Rental Minutes (1-480)");

        typeCombo = new JComboBox<>(new String[] {"Trip Booking", "Hourly Rental"});
        typeCombo.addActionListener(e -> toggleVisibillity());
        nameTxt = new JTextField(20);
        paxCombo = new JComboBox<>(new String[] { "1", "2" });
        durationCombo = new JComboBox<>(new String[] { "4", "6", "8" });
        routeTxt = new JTextField(20);
        updateRouteByDuration();
        durationCombo.addActionListener(e -> updateRouteByDuration());
        minutesTxt = new JTextField("30", 20);

        addBookingBtn = new JButton("Add Booking");
        addBookingBtn.addActionListener(this);
        backMainBtn = new JButton("Back to Main Page");
        backMainBtn.addActionListener(this);

        bookingListTxtArea = new JTextArea(10, 45);
        bookingListTxtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingListTxtArea);

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Enter booking details"));

        stdPanel = new JPanel();
        stdPanel.setLayout(new GridLayout(6, 2, 5, 5));
        stdPanel.add(typeLbl);
        stdPanel.add(typeCombo);
        stdPanel.add(nameLbl);
        stdPanel.add(nameTxt);
        stdPanel.add(paxLbl);
        stdPanel.add(paxCombo);
        stdPanel.add(durationLbl);
        stdPanel.add(durationCombo);
        stdPanel.add(routeLbl);
        stdPanel.add(routeTxt);
        stdPanel.add(minutesLbl);
        stdPanel.add(minutesTxt);
        toggleVisibillity();

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBookingBtn);
        btnPanel.add(backMainBtn);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titlePanel);
        panel.add(stdPanel);
        panel.add(btnPanel);

        bookingListTxtArea.append("Booking List:\n");
        for (Bookable b : Database.booking) {
            bookingListTxtArea.append(b.getBookingDetails() + "\n");
        }
        panel.add(scrollPane);

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void clearTextField() {
        nameTxt.setText("");
        durationCombo.setSelectedIndex(0);
        updateRouteByDuration();
        minutesTxt.setText("30");
    }

    private void toggleVisibillity() {
        boolean isTrip = typeCombo.getSelectedItem().toString().contains("Trip");

        durationLbl.setVisible(isTrip);
        durationCombo.setVisible(isTrip);
        routeLbl.setVisible(isTrip);
        routeTxt.setVisible(isTrip);

        minutesLbl.setVisible(!isTrip);
        minutesTxt.setVisible(!isTrip);

        stdPanel.revalidate();
        stdPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);

        if (actionCommand.equals("Add Booking")) {
            try {
                String name = nameTxt.getText().trim();
                int pax = Integer.parseInt((String) paxCombo.getSelectedItem());
                int id = Database.nextBookingId++;

                Bookable b;
                if (typeCombo.getSelectedItem().equals("Trip Booking")) {
                    int duration = Integer.parseInt((String) durationCombo.getSelectedItem());
                    String route = routeTxt.getText().trim();
                    b = new TripBooking(id, name, pax, duration, route);
                } else {
                    int minutes = Integer.parseInt(minutesTxt.getText().trim());
                    b = new HourlyRentalBooking(id, name, pax, minutes);
                }

                Database.booking.add(b);

                String msg = b.getBookingDetails() + "\n";
                bookingListTxtArea.append(msg);

                JOptionPane.showMessageDialog(this,
                        "Booking added successfully!\nCode: " + b.getBookingCode(), "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearTextField();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Wrong input. Please enter valid numbers. \nError: " + ex.getMessage(),
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Some error occurred. Please cantact admin.\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (actionCommand.equals("Back to Main Page")) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new EnrollPage();
    }
}