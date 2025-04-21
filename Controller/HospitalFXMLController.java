package Controller;

import dao.HospitalDAO;
import exception.HospitalException;
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
import model.Hospital;

import java.util.List;

public class HospitalFXMLController {

    @FXML private TableColumn<Hospital, String> hospitalIDCol;
    @FXML private TableColumn<Hospital, String> hospitalNameCol;
    @FXML private TableColumn<Hospital, String> hospitalLocationCol;
    @FXML private TableColumn<Hospital, String> hospitalContactCol;
    @FXML private TableColumn<Hospital, String> bloodBankIDCol;  // Added BloodBankID Column

    @FXML private TextField hospitalIDField;
    @FXML private TextField hospitalNameField;
    @FXML private TextField hospitalLocationField;
    @FXML private TextField hospitalContactField;
    @FXML private TextField bloodBankIDField;  // Added BloodBankID Field

    @FXML private Button submitHospitalButton;    // For creation
    @FXML private Button saveChangesButton;        // For updating

    @FXML private TableView<Hospital> hospitalTable;
    @FXML private VBox formBox;

    private final HospitalDAO hospitalDAO = new HospitalDAO();
    private ObservableList<Hospital> hospitals;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadHospitalData();
        hospitalTable.setVisible(true);
        formBox.setVisible(false);

        hospitalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedHospital) -> {
            if (selectedHospital != null) {
                hospitalIDField.setText(selectedHospital.getHospitalID());
                hospitalNameField.setText(selectedHospital.getHospitalName());
                hospitalLocationField.setText(selectedHospital.getHospitalLocation());
                hospitalContactField.setText(selectedHospital.getHospitalContact());
                bloodBankIDField.setText(selectedHospital.getBloodBankID()); // Set BloodBankID field
            }
        });
    }

    private void setupTableColumns() {
        hospitalIDCol.setCellValueFactory(new PropertyValueFactory<>("hospitalID"));
        hospitalNameCol.setCellValueFactory(new PropertyValueFactory<>("hospitalName"));
        hospitalLocationCol.setCellValueFactory(new PropertyValueFactory<>("hospitalLocation"));
        hospitalContactCol.setCellValueFactory(new PropertyValueFactory<>("hospitalContact"));
        bloodBankIDCol.setCellValueFactory(new PropertyValueFactory<>("bloodBankID"));  // Added BloodBankID Column
    }

    private void loadHospitalData() {
        try {
            List<Hospital> list = hospitalDAO.getAllHospitals();
            hospitals = FXCollections.observableArrayList(list);
            hospitalTable.setItems(hospitals);
        } catch (HospitalException e) {
            showError("Error loading hospitals: " + e.getMessage());
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
        hospitalIDField.clear();
        hospitalNameField.clear();
        hospitalLocationField.clear();
        hospitalContactField.clear();
        bloodBankIDField.clear();  // Clear BloodBankID field
        hospitalIDField.setDisable(false);
    }

    // --- CREATE FLOW ---
    @FXML
    private void handleCreate() {
        clearFormFields();
        formBox.setVisible(true);
        hospitalIDField.setDisable(false);
        submitHospitalButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
        try {
            // Get user input from form fields
            String hospitalID = hospitalIDField.getText().trim();
            String hospitalName = hospitalNameField.getText().trim();
            String hospitalLocation = hospitalLocationField.getText().trim();
            String hospitalContact = hospitalContactField.getText().trim();
            String bloodBankID = bloodBankIDField.getText().trim();

            // Check if any field is empty
            if (hospitalID.isEmpty() || hospitalName.isEmpty() || hospitalLocation.isEmpty() || hospitalContact.isEmpty() || bloodBankID.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            // Create a new Hospital object with the user input
            Hospital newHospital = new Hospital(hospitalID, hospitalName, hospitalLocation, hospitalContact, bloodBankID);

            // Add the new hospital to the database
            hospitalDAO.addHospital(newHospital);  // Insert the hospital into the DB

            // After insertion, confirm if the hospital was added successfully
            Hospital insertedHospital = hospitalDAO.getHospitalById(hospitalID);  // Fetch the added hospital to confirm
            if (insertedHospital == null) {
                showError("Failed to add hospital. Please check your inputs.");
                return;
            }

            // Add the hospital to the list if it was successfully inserted
            hospitals.add(insertedHospital);
            showInfo("Hospital added successfully!");
            
            // Clear form fields and refresh the table
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging
            showError("Error adding hospital: " + e.getMessage());
        }
    }


    @FXML
    private void handleDisplayAll() {
        try {
            loadHospitalData();
            hospitalTable.setVisible(true);
            formBox.setVisible(false);
        } catch (Exception e) {
            showError("Error displaying all hospitals: " + e.getMessage());
        }
    }

    // --- UPDATE FLOW ---
    @FXML
    private void handleUpdate() {
        Hospital selectedHospital = hospitalTable.getSelectionModel().getSelectedItem();
        if (selectedHospital == null) {
            showError("Please select a hospital to update.");
            return;
        }

        hospitalIDField.setText(selectedHospital.getHospitalID());
        hospitalIDField.setDisable(true);
        hospitalNameField.setText(selectedHospital.getHospitalName());
        hospitalLocationField.setText(selectedHospital.getHospitalLocation());
        hospitalContactField.setText(selectedHospital.getHospitalContact());
        bloodBankIDField.setText(selectedHospital.getBloodBankID());  // Set BloodBankID field

        formBox.setVisible(true);
        submitHospitalButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String hospitalID = hospitalIDField.getText().trim();
            String hospitalName = hospitalNameField.getText().trim();
            String hospitalLocation = hospitalLocationField.getText().trim();
            String hospitalContact = hospitalContactField.getText().trim();
            String bloodBankID = bloodBankIDField.getText().trim();  // Get BloodBankID

            if (hospitalID.isEmpty()) {
                showError("Hospital ID cannot be empty.");
                return;
            }

            Hospital existingHospital = hospitalDAO.getHospitalById(hospitalID);
            if (existingHospital == null) {
                showError("No hospital found with ID: " + hospitalID);
                return;
            }

            existingHospital.setHospitalName(hospitalName);
            existingHospital.setHospitalLocation(hospitalLocation);
            existingHospital.setHospitalContact(hospitalContact);
            existingHospital.setBloodBankID(bloodBankID);  // Set BloodBankID

            hospitalDAO.updateHospital(existingHospital);

            for (int i = 0; i < hospitals.size(); i++) {
                if (hospitals.get(i).getHospitalID().equals(hospitalID)) {
                    hospitals.set(i, existingHospital);
                    break;
                }
            }

            showInfo("Hospital updated successfully.");
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error updating hospital: " + e.getMessage());
        }
    }

    // DELETE
    @FXML
    private void handleDelete() {
        try {
            Hospital selectedHospital = hospitalTable.getSelectionModel().getSelectedItem();
            if (selectedHospital != null) {
                hospitalDAO.deleteHospital(selectedHospital.getHospitalID());
                hospitals.remove(selectedHospital);
                refreshTable();
            } else {
                showError("Please select a hospital to delete.");
            }
        } catch (Exception e) {
            showError("Error deleting hospital: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        hospitalIDField.setDisable(false);
        formBox.setVisible(false);
    }

    private void refreshTable() {
        try {
            loadHospitalData();
            hospitalTable.setItems(hospitals);
        } catch (Exception e) {
            showError("Error refreshing hospital table: " + e.getMessage());
        }
    }
   
}
