package Controller;

import dao.BloodRequestDAO;
import exception.BloodRequestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.BloodRequest;

import java.util.List;

public class BloodRequestFXMLController {

    @FXML private TableColumn<BloodRequest, String> requestIDCol;
    @FXML private TableColumn<BloodRequest, String> hospitalIDCol;
    @FXML private TableColumn<BloodRequest, String> bloodTypeCol;
    @FXML private TableColumn<BloodRequest, Integer> quantityCol;

    @FXML private TextField requestIDField;
    @FXML private TextField hospitalIDField;
    @FXML private TextField bloodTypeField;
    @FXML private TextField quantityField;

    // Two separate buttons in the form: one for adding and one for updating
    @FXML private Button submitRequestButton;    // For creation
    @FXML private Button saveChangesButton;        // For updating

    @FXML private TableView<BloodRequest> bloodRequestTable;
    @FXML private VBox formBox;

    private final BloodRequestDAO bloodRequestDAO = new BloodRequestDAO();
    private ObservableList<BloodRequest> bloodRequests;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBloodRequestData();
        bloodRequestTable.setVisible(true);
        formBox.setVisible(false);

        // Auto-fill fields when a row is selected (this is only used for update)
        bloodRequestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedRequest) -> {
            if (selectedRequest != null) {
                requestIDField.setText(selectedRequest.getRequestID());
                hospitalIDField.setText(selectedRequest.getHospitalID());
                bloodTypeField.setText(selectedRequest.getBloodType());
                quantityField.setText(String.valueOf(selectedRequest.getQuantity()));
            }
        });
    }

    private void setupTableColumns() {
        requestIDCol.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        hospitalIDCol.setCellValueFactory(new PropertyValueFactory<>("hospitalID"));
        bloodTypeCol.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadBloodRequestData() {
        try {
            List<BloodRequest> list = bloodRequestDAO.getAllBloodRequests();
            bloodRequests = FXCollections.observableArrayList(list);
            bloodRequestTable.setItems(bloodRequests);
        } catch (BloodRequestException e) {
            showError("Error loading blood requests: " + e.getMessage());
        }
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

    private void clearFormFields() {
        requestIDField.clear();
        hospitalIDField.clear();
        bloodTypeField.clear();
        quantityField.clear();
        // Re-enable Request ID for creation mode
        requestIDField.setDisable(false);
    }

    // --- CREATE FLOW ---
    // Called when the "Add New Request" button is clicked.
    @FXML
    private void handleCreate() {
        clearFormFields();
        formBox.setVisible(true);
        // In creation mode, enable the Request ID field
        requestIDField.setDisable(false);
        // Show only the Submit Request button and hide the Save Changes button.
        submitRequestButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }
    
    // Called when the "Submit Request" button is clicked in create mode.
    @FXML
    private void handleAdd() {
        try {
            String requestID = requestIDField.getText().trim();
            String hospitalID = hospitalIDField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            String quantityStr = quantityField.getText().trim();

            if (requestID.isEmpty() || hospitalID.isEmpty() || bloodType.isEmpty() || quantityStr.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            int quantity = Integer.parseInt(quantityStr);

            // Check if the Request ID already exists
            BloodRequest existingRequest = bloodRequestDAO.getBloodRequestById(requestID);
            if (existingRequest != null) {
                showError("A blood request with this ID already exists.");
                return;
            }

            BloodRequest newRequest = new BloodRequest(requestID, hospitalID, bloodType, quantity);
            bloodRequestDAO.addBloodRequest(newRequest);
            bloodRequests.add(newRequest);

            showInfo("Blood request added successfully!");
            clearFormFields();
            formBox.setVisible(false);
        } catch (NumberFormatException e) {
            showError("Quantity must be a valid number.");
        } catch (Exception e) {
            showError("Error adding blood request: " + e.getMessage());
        }
    }
    @FXML
    private void handleDisplayAll() {
        try {
            loadBloodRequestData(); // Re-fetch data from the database
            bloodRequestTable.setVisible(true);
            formBox.setVisible(false); // Hide the form
        } catch (Exception e) {
            showError("Error displaying all blood requests: " + e.getMessage());
        }
    }


    // --- UPDATE FLOW ---
    // Called when the "Update" button is clicked (from the action buttons outside the form).
    @FXML
    private void handleUpdate() {
        BloodRequest selectedRequest = bloodRequestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest == null) {
            showError("Please select a blood request to update.");
            return;
        }

        // Pre-fill the form with the selected record and disable Request ID (primary key) editing.
        requestIDField.setText(selectedRequest.getRequestID());
        requestIDField.setDisable(true);
        hospitalIDField.setText(selectedRequest.getHospitalID());
        bloodTypeField.setText(selectedRequest.getBloodType());
        quantityField.setText(String.valueOf(selectedRequest.getQuantity()));

        formBox.setVisible(true);
        // In update mode, show only the Save Changes button.
        submitRequestButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    // Called when the "Save Changes" button is clicked in update mode.
    @FXML
    private void handleSaveChanges() {
        try {
            String requestID = requestIDField.getText().trim();
            String hospitalID = hospitalIDField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());

            if (requestID.isEmpty()) {
                showError("Request ID cannot be empty.");
                return;
            }
            
            // Retrieve the existing record from the database to update
            BloodRequest existingRequest = bloodRequestDAO.getBloodRequestById(requestID);
            if (existingRequest == null) {
                showError("No blood request found with ID: " + requestID);
                return;
            }
            
            existingRequest.setHospitalID(hospitalID);
            existingRequest.setBloodType(bloodType);
            existingRequest.setQuantity(quantity);

            // Perform the update in the database
            bloodRequestDAO.updateBloodRequest(existingRequest);

            // Update the ObservableList
            for (int i = 0; i < bloodRequests.size(); i++) {
                if (bloodRequests.get(i).getRequestID().equals(requestID)) {
                    bloodRequests.set(i, existingRequest);
                    break;
                }
            }
            showInfo("Blood request updated successfully.");
            clearFormFields();
            formBox.setVisible(false);
        } catch (Exception e) {
            showError("Error updating blood request: " + e.getMessage());
        }
    }

    // DELETE
    @FXML
    private void handleDelete() {
        try {
            BloodRequest selectedRequest = bloodRequestTable.getSelectionModel().getSelectedItem();
            if (selectedRequest != null) {
                bloodRequestDAO.deleteBloodRequest(selectedRequest.getRequestID());
                bloodRequests.remove(selectedRequest);
            } else {
                showError("Please select a request to delete.");
            }
        } catch (Exception e) {
            showError("Error deleting blood request: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleCancel() {
        clearFormFields();
        // Re-enable Request ID (for create mode)
        requestIDField.setDisable(false);
        formBox.setVisible(false);
    }
}
