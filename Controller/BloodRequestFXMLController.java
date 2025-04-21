package Controller;

import dao.BloodRequestDAO;
import exception.BloodRequestException;
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
import model.BloodRequest;

import java.util.List;

public class BloodRequestFXMLController {

    @FXML private TableView<BloodRequest> bloodRequestTable;
    @FXML private TableColumn<BloodRequest, String> requestIDCol;
    @FXML private TableColumn<BloodRequest, String> hospitalIDCol;
    @FXML private TableColumn<BloodRequest, String> bloodTypeCol;
    @FXML private TableColumn<BloodRequest, Integer> quantityCol;

    @FXML private TextField requestIDField;
    @FXML private TextField hospitalIDField;
    @FXML private TextField bloodTypeField;
    @FXML private TextField quantityField;

    @FXML private VBox formBox;
    @FXML private Button submitRequestButton;
    @FXML private Button saveChangesButton;

    private final BloodRequestDAO bloodRequestDAO = new BloodRequestDAO();
    private ObservableList<BloodRequest> bloodRequests;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBloodRequests();
        
        // Initially hide the table and form
        bloodRequestTable.setVisible(false);  // Hide table
        formBox.setVisible(false);  // Hide form

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

    private void loadBloodRequests() {
        try {
            List<BloodRequest> list = bloodRequestDAO.getAllBloodRequests();
            bloodRequests = FXCollections.observableArrayList(list);
            bloodRequestTable.setItems(bloodRequests);
        } catch (BloodRequestException e) {
            showError("Error loading requests: " + e.getMessage());
        }
    }

    private void clearFormFields() {
        requestIDField.clear();
        hospitalIDField.clear();
        bloodTypeField.clear();
        quantityField.clear();
        requestIDField.setDisable(false);
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
        bloodRequestTable.setVisible(true);  // Hide table
        requestIDField.setDisable(false);
        submitRequestButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
    	 loadBloodRequests();
        bloodRequestTable.setVisible(true);
        try {
            String requestID = requestIDField.getText().trim();
            String hospitalID = hospitalIDField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());

            if (requestID.isEmpty() || hospitalID.isEmpty() || bloodType.isEmpty() || quantity < 0) {
                showError("Please fill in all fields with valid data.");
                return;
            }

            BloodRequest newRequest = new BloodRequest(requestID, hospitalID, bloodType, quantity);
            bloodRequestDAO.addBloodRequest(newRequest);

            BloodRequest inserted = bloodRequestDAO.getBloodRequestById(requestID);
            if (inserted == null) {
                showError("Failed to add blood request.");
                return;
            }

            bloodRequests.add(inserted);
            showInfo("Blood request added successfully!");
            clearFormFields();
            formBox.setVisible(false);  // Hide form
            refreshTable();
        } catch (Exception e) {
            showError("Error adding request: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        loadBloodRequests();
        bloodRequestTable.setVisible(true);  // Show table
        formBox.setVisible(false);  // Hide form
    }

    @FXML
    private void handleUpdate() {
    	 loadBloodRequests();
         bloodRequestTable.setVisible(true);
        BloodRequest selected = bloodRequestTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a request to update.");
            return;
        }

        requestIDField.setText(selected.getRequestID());
        requestIDField.setDisable(true);
        hospitalIDField.setText(selected.getHospitalID());
        bloodTypeField.setText(selected.getBloodType());
        quantityField.setText(String.valueOf(selected.getQuantity()));

        formBox.setVisible(true);  // Show form
        bloodRequestTable.setVisible(false);  // Hide table
        submitRequestButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String requestID = requestIDField.getText().trim();
            String hospitalID = hospitalIDField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());

            BloodRequest updated = bloodRequestDAO.getBloodRequestById(requestID);
            if (updated == null) {
                showError("Request ID not found.");
                return;
            }

            updated.setHospitalID(hospitalID);
            updated.setBloodType(bloodType);
            updated.setQuantity(quantity);

            bloodRequestDAO.updateBloodRequest(updated);

            for (int i = 0; i < bloodRequests.size(); i++) {
                if (bloodRequests.get(i).getRequestID().equals(requestID)) {
                    bloodRequests.set(i, updated);
                    break;
                }
            }

            showInfo("Request updated successfully.");
            clearFormFields();
            formBox.setVisible(false);  // Hide form
            refreshTable();
        } catch (Exception e) {
            showError("Error updating request: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
    	 loadBloodRequests();
         bloodRequestTable.setVisible(true);
        BloodRequest selected = bloodRequestTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                bloodRequestDAO.deleteBloodRequest(selected.getRequestID());
                bloodRequests.remove(selected);
                refreshTable();
            } catch (Exception e) {
                showError("Error deleting request: " + e.getMessage());
            }
        } else {
            showError("Please select a request to delete.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        requestIDField.setDisable(false);
        formBox.setVisible(false);  // Hide form
    }

    private void refreshTable() {
        try {
            loadBloodRequests();
        } catch (Exception e) {
            showError("Error refreshing request table: " + e.getMessage());
        }
    }
  
   
}
