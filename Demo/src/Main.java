import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {

    private JTable table;

    public Main() {
        setTitle("User Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // Add columns to the table model
        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Location");

        // Connect to the database and retrieve data
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Change this if you're using a different database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "1234"); // Update with your database URL, username, and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");

            // Add rows to the table model
            while (rs.next()) {
                Object[] row = {rs.getInt("id"), rs.getString("name"), rs.getString("location")};
                model.addRow(row);
            }

            // Close the connections
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }

        // Add the table to a scroll pane and add it to the frame
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
