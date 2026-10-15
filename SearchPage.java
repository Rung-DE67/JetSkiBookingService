import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class SearchPage extends JFrame implements ActionListener {
    private JTextArea searchResults;
    private JComboBox<String> criterionCombo;
    private JTextField searchTxt;
    private JButton searchBtn, backMainBtn;

    public SearchPage() {
        super("Jet Ski Booking System");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Jet Ski Search Module", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("", Font.BOLD, 20));

        JLabel criterionLbl = new JLabel("Search by:");
        JLabel valueLbl = new JLabel("Search value:");

        criterionCombo = new JComboBox<>(new String[] {
                "Customer Name", "Island Route", "Rental Minutes"
        });
        searchTxt = new JTextField(20);

        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);
        backMainBtn = new JButton("Back to Main Page");
        backMainBtn.addActionListener(this);

        searchResults = new JTextArea(10, 45);
        searchResults.setEditable(false);
        searchResults.append("Results:\n");
        JScrollPane scrollPane = new JScrollPane(searchResults);

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Search bookings"));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2, 5, 5));
        searchPanel.add(criterionLbl);
        searchPanel.add(criterionCombo);
        searchPanel.add(valueLbl);
        searchPanel.add(searchTxt);

        JPanel btnPanel = new JPanel();
        btnPanel.add(searchBtn);
        btnPanel.add(backMainBtn);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(titlePanel);
        panel.add(searchPanel);
        panel.add(btnPanel);
        panel.add(scrollPane);

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private List<Bookable> searchByName(List<Bookable> list, String name) {
        List<Bookable> results = new ArrayList<>();
        for (Bookable b : list) {
            if (b.getCustomerName().toLowerCase().contains(name.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }

    private List<Bookable> searchByRoute(List<Bookable> list, String route) {
        List<Bookable> results = new ArrayList<>();
        for (Bookable b : list) {
            if (b instanceof TripBooking) {
                TripBooking tb = (TripBooking) b;
                if (tb.getIslandRoute().toLowerCase().contains(route.toLowerCase())) {
                    results.add(tb);
                }
            }
        }
        return results;
    }

    private List<Bookable> searchByMinutes(List<Bookable> list, int minutes) {
        List<Bookable> results = new ArrayList<>();
        for (Bookable b : list) {
            if (b instanceof HourlyRentalBooking) {
                HourlyRentalBooking hb = (HourlyRentalBooking) b;
                if (hb.getRentalMinutes() == minutes) {
                    results.add(hb);
                }
            }
        }
        return results;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("Search")) {
            String value = searchTxt.getText().trim();
            if (value.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search value.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String criterion = (String) criterionCombo.getSelectedItem();
                List<Bookable> matched = new ArrayList<>();

                if (criterion.equals("Customer Name")) {
                    matched = searchByName(Database.booking, value);
                } else if (criterion.equals("Island Route")) {
                    matched = searchByRoute(Database.booking, value);
                } else if (criterion.equals("Rental Minutes")) {
                    int mins = Integer.parseInt(value);
                    matched = searchByMinutes(Database.booking, mins);
                }

                searchResults.setText("");
                searchResults.append("Results:\n");
                if (matched.isEmpty()) {
                    searchResults.append("Not Found\n");
                } else {
                    for (Bookable b : matched) {
                        searchResults.append(b.getBookingDetails() + "\n");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Rental minutes must be a whole number.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input for search field.\nERROR: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (actionCommand.equals("Back to Main Page")) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new SearchPage();
    }

}