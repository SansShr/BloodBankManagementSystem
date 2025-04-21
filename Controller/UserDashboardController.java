package Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class UserDashboardController {

    // Define buttons
    @FXML private Button bloodRequestButton;
    @FXML private Button eligibilityCriteriaButton;
    @FXML private Button becomeADonorButton;
    @FXML private Button emergencyRequestButton;

    @FXML
    public void initialize() {
        bloodRequestButton.setOnAction(event -> loadScene("/views/bloodrequests.fxml"));
        eligibilityCriteriaButton.setOnAction(event -> loadScene("/views/eligibilitycriteria.fxml"));
        becomeADonorButton.setOnAction(event -> loadScene("/views/donor.fxml"));
        emergencyRequestButton.setOnAction(event -> loadScene("/views/emergencyservice.fxml"));
    }

    // Helper method to load a new scene
    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) bloodRequestButton.getScene().getWindow(); // Get current window
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
