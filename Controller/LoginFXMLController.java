package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.User;
import db.DBConnection;
import java.sql.*;
import java.io.IOException;

public class LoginFXMLController {

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
            // If credentials are correct, navigate to respective tables
            loadRespectiveTableScene(role);
        } else {
            errorLabel.setText("Invalid credentials. Please try again.");
        }
    }

    // This method validates the user credentials from the database
    private boolean validateUser(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();

            // Return true if user is found
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Database error
        }
    }

    // Navigate to respective table scene based on the role
    private void loadRespectiveTableScene(String role) {
        try {
            FXMLLoader loader;
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = null;

            // If role is Admin, load admin dashboard, else load user dashboard
            if (role.equals("Admin")) {
                loader = new FXMLLoader(getClass().getResource("/views/admin_dashboard.fxml"));
                scene = new Scene(loader.load());
            } else if (role.equals("User")) {
                loader = new FXMLLoader(getClass().getResource("/views/user_dashboard.fxml"));
                scene = new Scene(loader.load());
            } else {
                errorLabel.setText("Role not supported.");
                return;
            }

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Error loading table scene: " + e.getMessage());
        }
    }

    // This method is triggered when the signup button is clicked
    @FXML
    public void handleSignup() {
        try {
            // Load the signup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signup.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene (signup screen)
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Error loading signup screen: " + e.getMessage());
        }
    }

    // This method is triggered when the scene is initialized
    @FXML
    public void initialize() {
        // Add roles to the ComboBox
        roleComboBox.getItems().addAll("Admin", "User");
    }
}
