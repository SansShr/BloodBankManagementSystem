package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.Initializable;
import db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleChoiceBox.getItems().addAll("user", "staff");
        roleChoiceBox.setValue("user"); // Default selection
    }

    @FXML
    private void handleSignUp() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String selectedRole = roleChoiceBox.getValue();

        if (password.equals(confirmPassword)) {
            try {
                Connection conn = DBConnection.getConnection();
                String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, selectedRole);  // Use selected role

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("User registered successfully!");

                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
                    Parent root = loader.load();
                    Stage loginStage = new Stage();
                    loginStage.setTitle("Login");
                    loginStage.setScene(new Scene(root));
                    loginStage.show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                errorLabel.setText("Error: " + e.getMessage());
            }
        } else {
            errorLabel.setText("Passwords do not match.");
        }
    }
}
