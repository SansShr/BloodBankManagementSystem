package Controller;

import dao.EmergencyServicesDAO;
import exception.EmergencyServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.EmergencyService;

import java.util.List;

public class EmergencyServiceFXMLController {

    @FXML private TableView<EmergencyService> emergencyServiceTable;
    @FXML private TableColumn<EmergencyService, String> serviceIDCol;
    @FXML private TableColumn<EmergencyService, String> requestIDCol;
    @FXML private TableColumn<EmergencyService, String> serviceDetailsCol;

    @FXML private TextField serviceIDField;
    @FXML private TextField requestIDField;
    @FXML private TextField serviceDetailsField;

    @FXML private VBox formBox;
    @FXML private Button submitServiceButton;
    @FXML private Button saveChangesButton;

    private final EmergencyServicesDAO emergencyServiceDAO = new EmergencyServicesDAO();
    private ObservableList<EmergencyService> emergencyServices;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadEmergencyServices();

        // Initially hide the table and form
        emergencyServiceTable.setVisible(false);  // Hide table
        formBox.setVisible(false);  // Hide form

        emergencyServiceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedService) -> {
            if (selectedService != null) {
                serviceIDField.setText(selectedService.getServiceID());
                requestIDField.setText(selectedService.getRequestID());
                serviceDetailsField.setText(selectedService.getServiceDetails());
            }
        });
    }

    private void setupTableColumns() {
        serviceIDCol.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        requestIDCol.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        serviceDetailsCol.setCellValueFactory(new PropertyValueFactory<>("serviceDetails"));
    }

    private void loadEmergencyServices() {
        try {
            List<EmergencyService> list = emergencyServiceDAO.getAllEmergencyServices();
            emergencyServices = FXCollections.observableArrayList(list);
            emergencyServiceTable.setItems(emergencyServices);
        } catch (EmergencyServiceException e) {
            showError("Error loading services: " + e.getMessage());
        }
    }

    private void clearFormFields() {
        serviceIDField.clear();
        requestIDField.clear();
        serviceDetailsField.clear();
        serviceIDField.setDisable(false);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Completed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCreate() {
        clearFormFields();
        formBox.setVisible(true);  // Show form
        emergencyServiceTable.setVisible(true);  // Show table
        serviceIDField.setDisable(false);
        submitServiceButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
        loadEmergencyServices();
        emergencyServiceTable.setVisible(true);
        try {
            String serviceID = serviceIDField.getText().trim();
            String requestID = requestIDField.getText().trim();
            String serviceDetails = serviceDetailsField.getText().trim();

            if (serviceID.isEmpty() || requestID.isEmpty() || serviceDetails.isEmpty()) {
                showError("Please fill in all fields with valid data.");
                return;
            }

            EmergencyService newService = new EmergencyService(serviceID, requestID, serviceDetails);
            emergencyServiceDAO.addEmergencyService(newService);

            emergencyServices.add(newService);
            showInfo("Emergency service added successfully!");
            clearFormFields();
            formBox.setVisible(false);  // Hide form
            refreshTable();
        } catch (Exception e) {
            showError("Error adding service: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        loadEmergencyServices();
        emergencyServiceTable.setVisible(true);  // Show table
        formBox.setVisible(false);  // Hide form
    }

    @FXML
    private void handleUpdate() {
        loadEmergencyServices();
        emergencyServiceTable.setVisible(true);
        EmergencyService selected = emergencyServiceTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a service to update.");
            return;
        }

        serviceIDField.setText(selected.getServiceID());
        serviceIDField.setDisable(true);
        requestIDField.setText(selected.getRequestID());
        serviceDetailsField.setText(selected.getServiceDetails());

        formBox.setVisible(true);  // Show form
        emergencyServiceTable.setVisible(false);  // Hide table
        submitServiceButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String serviceID = serviceIDField.getText().trim();
            String requestID = requestIDField.getText().trim();
            String serviceDetails = serviceDetailsField.getText().trim();

            EmergencyService updated = new EmergencyService(serviceID, requestID, serviceDetails);

            emergencyServiceDAO.updateEmergencyService(updated);

            for (int i = 0; i < emergencyServices.size(); i++) {
                if (emergencyServices.get(i).getServiceID().equals(serviceID)) {
                    emergencyServices.set(i, updated);
                    break;
                }
            }

            showInfo("Service updated successfully.");
            clearFormFields();
            formBox.setVisible(false);  // Hide form
            refreshTable();
        } catch (Exception e) {
            showError("Error updating service: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        loadEmergencyServices();
        emergencyServiceTable.setVisible(true);
        EmergencyService selected = emergencyServiceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                emergencyServiceDAO.deleteEmergencyService(selected.getServiceID(), selected.getRequestID());
                emergencyServices.remove(selected);
                refreshTable();
            } catch (Exception e) {
                showError("Error deleting service: " + e.getMessage());
            }
        } else {
            showError("Please select a service to delete.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        serviceIDField.setDisable(false);
        formBox.setVisible(false);  // Hide form
    }

    private void refreshTable() {
        try {
            loadEmergencyServices();
        } catch (Exception e) {
            showError("Error refreshing service table: " + e.getMessage());
        }
    }
    @FXML
    private Button backToDashboardButton; // Button reference

    // This method handles the button click and redirects to the dashboard
    public void handleBackToDashboard() {
        try {
            // Load the dashboard FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
            Parent root = loader.load();

            // Get the stage (window) of the current scene
            Stage stage = (Stage) backToDashboardButton.getScene().getWindow();

            // Set the new scene (dashboard scene) and show the stage
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            // Handle the exception if the page fails to load
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load Dashboard");
            alert.setContentText("Could not load the Dashboard page.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
