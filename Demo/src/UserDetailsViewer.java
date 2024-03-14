import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserDetailsViewer extends JFrame {

    private JComboBox<Integer> idComboBox;
    private JLabel nameLabel;
    private JLabel locationLabel;

    public UserDetailsViewer() {
        setTitle("User Details Viewer");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Select ID:"));
        idComboBox = new JComboBox<>();
        panel.add(idComboBox);

        panel.add(new JLabel("Name:"));
        nameLabel = new JLabel();
        panel.add(nameLabel);

        panel.add(new JLabel("Age:"));
        locationLabel = new JLabel();
        panel.add(locationLabel);

        add(panel);

        populateComboBox();

        // Add action listener to the combo box
        idComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedId = (int) idComboBox.getSelectedItem();
                displayUserDetails(selectedId);
            }
        });
    }

    private void populateComboBox() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Change this if you're using a different database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "1234"); // Update with your database URL, username, and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM student");

            while (rs.next()) {
                idComboBox.addItem(rs.getInt("id"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayUserDetails(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Change this if you're using a different database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "1234"); // Update with your database URL, username, and password
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM student WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String location= rs.getString("location");
                nameLabel.setText(name);
                locationLabel.setText(location);
            } else {
                nameLabel.setText("");
                locationLabel.setText("");
            }

            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserDetailsViewer frame = new UserDetailsViewer();
            frame.setVisible(true);
        });
    }
}
