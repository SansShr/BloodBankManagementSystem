package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Label errorLabel;

    // This method is triggered when the login button is clicked
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || role == null) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        // Validate the user credentials from DB
        if (validateUser(username, password, role)) {
            // If credentials are correct, proceed with navigation
            loadRespectiveTableScene(role);
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    // Method to validate user credentials using the database
    private boolean validateUser(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();

            // If user is found in the database
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in production
        }

        return false; // No user found
    }

    // Navigate to respective table scene based on the role
    private void loadRespectiveTableScene(String role) {
        try {
            // Implement navigation logic here based on role
            // For example, if role is Admin, load Admin Table, if User, load User Table
        } catch (Exception e) {
            errorLabel.setText("Error loading table scene: " + e.getMessage());
        }
    }

    // This method is triggered when the signup button is clicked
    @FXML
    public void handleSignup() {
        // Handle sign-up logic here (e.g., show sign-up form or open new window)
        System.out.println("Sign-up logic here.");
    }

    // This method is triggered when the scene is initialized
    @FXML
    public void initialize() {
        // Add roles to the ComboBox
        roleComboBox.getItems().addAll("Admin", "User");
    }
}
