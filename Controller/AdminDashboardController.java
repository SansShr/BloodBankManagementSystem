package Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AdminDashboardController {

    // Define buttons
    @FXML private Button bloodBanksButton;
    @FXML private Button bloodStockButton;
    @FXML private Button donorsButton;
    @FXML private Button donationsButton;
    @FXML private Button patientsButton;
    @FXML private Button emergencyServicesButton;
    @FXML private Button eligibilityCriteriaButton;
    @FXML private Button bloodRequestsButton;
    @FXML private Button hospitalsButton;

    @FXML
    public void initialize() {
        bloodBanksButton.setOnAction(event -> loadScene("/views/bloodbank.fxml"));
        bloodStockButton.setOnAction(event -> loadScene("/views/bloodstock.fxml"));
        donorsButton.setOnAction(event -> loadScene("/views/donor.fxml"));
        donationsButton.setOnAction(event -> loadScene("/views/donations.fxml"));
        patientsButton.setOnAction(event -> loadScene("/views/patient.fxml"));
        emergencyServicesButton.setOnAction(event -> loadScene("/views/emergencyservice.fxml"));
        eligibilityCriteriaButton.setOnAction(event -> loadScene("/views/eligibilitycriteria.fxml"));
        bloodRequestsButton.setOnAction(event -> loadScene("/views/bloodrequests.fxml"));
        hospitalsButton.setOnAction(event -> loadScene("/views/hospital.fxml"));
    }

    // Helper method to load a new scene
    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) bloodBanksButton.getScene().getWindow(); // Get current window
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
