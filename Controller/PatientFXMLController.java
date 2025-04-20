 package Controller;

import dao.PatientDAO;
import exception.PatientException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.patients;

import java.util.List;

public class PatientFXMLController {

    @FXML private TableColumn<patients, String> idCol;
    @FXML private TableColumn<patients, String> fnameCol;
    @FXML private TableColumn<patients, String> lnameCol;
    @FXML private TableColumn<patients, String> bloodCol;
    @FXML private TableColumn<patients, Integer> ageCol;
    @FXML private TableColumn<patients, String> genderCol;
    @FXML private TableColumn<patients, String> historyCol;
    @FXML private TableColumn<patients, String> hospitalCol;

    @FXML private TextField patientIDField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField bloodTypeField;
    @FXML private TextField ageField;
    @FXML private TextField genderField;
    @FXML private TextField historyField;
    @FXML private TextField hospitalIDField;

    @FXML private TableView<patients> patientTable;
    @FXML private VBox formBox;

    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initialize() {
        setupTableColumns();

        // Hide form and table initially
        patientTable.setVisible(false); // Initially hide the table
        formBox.setVisible(false); // Initially hide the form
    }

    private void setupTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        bloodCol.setCellValueFactory(new PropertyValueFactory<>("patientBloodType"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("patientAge"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("patientGender"));
        historyCol.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
        hospitalCol.setCellValueFactory(new PropertyValueFactory<>("hospitalID"));
    }

    private void loadPatientData() {
        try {
            List<patients> list = patientDAO.getAllPatients();
            System.out.println("Number of patients loaded: " + list.size());  // Debugging line to check size
            ObservableList<patients> data = FXCollections.observableArrayList(list);
            patientTable.setItems(data);
        } catch (PatientException e) {
            showError("Error loading patients: " + e.getMessage());
        }
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // CREATE
    @FXML
    private void handleCreate() {
        formBox.setVisible(true); // Show the form when Add new patient is clicked
        patientTable.setVisible(false); // Hide the table
        patientIDField.setVisible(true);
        firstNameField.setVisible(true);
        lastNameField.setVisible(true);
        bloodTypeField.setVisible(true);
        ageField.setVisible(true);
        genderField.setVisible(true);
        historyField.setVisible(true);
        hospitalIDField.setVisible(true);
    }


    // SEARCH by patient ID
    @FXML
    private void handleSearch() {
        String searchID = patientIDField.getText().trim();
        if (searchID.isEmpty()) {
            showError("Please enter a Patient ID to search.");
            return;
        }

        try {
            patients found = patientDAO.getPatientById(searchID);
            if (found != null) {
                ObservableList<patients> data = FXCollections.observableArrayList(found);
                patientTable.setItems(data);
                patientTable.setVisible(true);
            } else {
                showError("No patient found with ID: " + searchID);
            }
        } catch (PatientException e) {
            showError("Error during search: " + e.getMessage());
        }
    }

    // DISPLAY ALL
    @FXML
    private void handleDisplayAll() {
        patientTable.setVisible(true);
        loadPatientData();
    }

    // ADD NEW PATIENT
    @FXML
    private void handleAdd() {
        try {
            String patientId = patientIDField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String bloodType = bloodTypeField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();
            String medicalHistory = historyField.getText();
            String hospitalID = hospitalIDField.getText();

            // Create the new patient object
            patients newPatient = new patients(patientId, firstName, lastName, bloodType, age, gender, medicalHistory, hospitalID);

            // Add the new patient to the database
            patientDAO.addPatient(newPatient);
            loadPatientData(); // Refresh the list of patients

            // Clear the text fields after adding the patient
            clearFormFields();

        } catch (Exception e) {
            showError("Error creating patient: " + e.getMessage());
        }
    }

    // Helper method to clear the form fields
    private void clearFormFields() {
        patientIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        bloodTypeField.clear();
        ageField.clear();
        genderField.clear();
        historyField.clear();
        hospitalIDField.clear();
    }


    // UPDATE
    @FXML
    private void handleUpdate() {
        try {
            // Ensure the patient table is populated and visible
            if (patientTable.getItems().isEmpty()) {
                showError("No patients available to update.");
                return; // Exit if no data in the table
            }

            // Make the table visible and ready for update
            patientTable.setVisible(true); 

            // Get the selected patient from the table
            patients selectedPatient = patientTable.getSelectionModel().getSelectedItem();
            
            if (selectedPatient != null) {
                // Populate the form fields with the selected patient's details
                patientIDField.setText(selectedPatient.getPatientID());
                firstNameField.setText(selectedPatient.getFirstName());
                lastNameField.setText(selectedPatient.getLastName());
                bloodTypeField.setText(selectedPatient.getPatientBloodType());
                ageField.setText(String.valueOf(selectedPatient.getPatientAge()));
                genderField.setText(selectedPatient.getPatientGender());
                historyField.setText(selectedPatient.getMedicalHistory());
                hospitalIDField.setText(selectedPatient.getHospitalID());

                // Show the form for editing the selected patient
                formBox.setVisible(true); // Show the form fields for editing
                patientTable.setVisible(true); // Optionally hide the table while editing
            } else {
                showError("Please select a patient to update.");
            }
        } catch (Exception e) {
            showError("Error updating patient: " + e.getMessage());
        }
    }





    // DELETE
    @FXML
    private void handleDelete() {
        try {
            patients selectedPatient = patientTable.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                patientDAO.deletePatient(selectedPatient.getPatientID());
                loadPatientData(); // Refresh table
            } else {
                showError("Please select a patient to delete.");
            }
        } catch (Exception e) {
            showError("Error deleting patient: " + e.getMessage());
        }
    }
}
