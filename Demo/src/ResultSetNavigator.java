import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResultSetNavigator extends JFrame {

    private JButton previousButton;
    private JButton nextButton;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel ageLabel;

    private Connection connection;
    private ResultSet resultSet;

    public ResultSetNavigator() {
        setTitle("Result Set Navigator");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("ID:"));
        idLabel = new JLabel();
        panel.add(idLabel);

        panel.add(new JLabel("Name:"));
        nameLabel = new JLabel();
        panel.add(nameLabel);

        panel.add(new JLabel("Age:"));
        ageLabel = new JLabel();
        panel.add(ageLabel);

        previousButton = new JButton("Previous");
        panel.add(previousButton);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (resultSet.previous()) {
                        displayResultSetRow();
                    } else {
                        JOptionPane.showMessageDialog(null, "Already at first row.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        nextButton = new JButton("Next");
        panel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (resultSet.next()) {
                        displayResultSetRow();
                    } else {
                        JOptionPane.showMessageDialog(null, "Already at last row.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        add(panel);

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Change this if you're using a different database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "1234"); // Update with your database URL, root, and root
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("SELECT * FROM student");
            resultSet.next(); // Move cursor to the first row initially
            displayResultSetRow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayResultSetRow() {
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String location = resultSet.getString("location");
            idLabel.setText(Integer.toString(id));
            nameLabel.setText(name);
            ageLabel.setText(location);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResultSetNavigator frame = new ResultSetNavigator();
            frame.setVisible(true);
        });
    }
}
