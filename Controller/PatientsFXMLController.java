package Controller;

import dao.PatientDAO;
import exception.HospitalException;
import exception.PatientException;
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
import model.Patient;

import java.util.List;

public class PatientsFXMLController {

    @FXML private TableColumn<Patient, String> patientIDCol;
    @FXML private TableColumn<Patient, String> firstNameCol;
    @FXML private TableColumn<Patient, String> lastNameCol;
    @FXML private TableColumn<Patient, String> bloodTypeCol;
    @FXML private TableColumn<Patient, Integer> ageCol;
    @FXML private TableColumn<Patient, String> genderCol;
    @FXML private TableColumn<Patient, String> medicalHistoryCol;
    @FXML private TableColumn<Patient, String> hospitalIDCol;

    @FXML private TextField patientIDField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField bloodTypeField;
    @FXML private TextField ageField;
    @FXML private TextField genderField;
    @FXML private TextField medicalHistoryField;
    @FXML private TextField hospitalIDField;

    @FXML private Button submitPatientButton;
    @FXML private Button saveChangesButton;

    @FXML private TableView<Patient> patientTable;
    @FXML private VBox formBox;

    private final PatientDAO patientDAO = new PatientDAO();
    private ObservableList<Patient> patients;

    @FXML
    public void initialize() throws PatientException {
        setupTableColumns();
        loadPatientData();
        patientTable.setVisible(true);
        formBox.setVisible(true);

        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedPatient) -> {
            if (selectedPatient != null) {
                patientIDField.setText(selectedPatient.getPatientID());
                firstNameField.setText(selectedPatient.getFirstName());
                lastNameField.setText(selectedPatient.getLastName());
                bloodTypeField.setText(selectedPatient.getPatientBloodType());
                ageField.setText(String.valueOf(selectedPatient.getPatientAge()));
                genderField.setText(selectedPatient.getPatientGender());
                medicalHistoryField.setText(selectedPatient.getMedicalHistory());
                hospitalIDField.setText(selectedPatient.getHospitalID());
            }
        });
    }

    private void setupTableColumns() {
        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        bloodTypeCol.setCellValueFactory(new PropertyValueFactory<>("patientBloodType"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("patientAge"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("patientGender"));
        medicalHistoryCol.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
        hospitalIDCol.setCellValueFactory(new PropertyValueFactory<>("hospitalID"));
    }

    private void loadPatientData() throws PatientException {
        try {
            List<Patient> list = patientDAO.getAllPatients();
            patients = FXCollections.observableArrayList(list);
            patientTable.setItems(patients);
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

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Completed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFormFields() {
        patientIDField.clear();
        firstNameField.clear();
        lastNameField.clear();
        bloodTypeField.clear();
        ageField.clear();
        genderField.clear();
        medicalHistoryField.clear();
        hospitalIDField.clear();
        patientIDField.setDisable(false);
    }

    // --- CREATE FLOW ---
    @FXML
    private void handleCreate() {
        clearFormFields();
        formBox.setVisible(true);
        patientIDField.setDisable(false);
        submitPatientButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
        try {
            // Get user input from form fields
            String patientID = patientIDField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            String medicalHistory = medicalHistoryField.getText().trim();
            String hospitalID = hospitalIDField.getText().trim();

            // Check if any field is empty
            if (patientID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || bloodType.isEmpty() || gender.isEmpty() || medicalHistory.isEmpty() || hospitalID.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            // Create a new Patient object with the user input
            Patient newPatient = new Patient(patientID, firstName, lastName, bloodType, age, gender, medicalHistory, hospitalID);

            // Add the new patient to the database
            patientDAO.addPatient(newPatient);

            // After insertion, confirm if the patient was added successfully
            Patient insertedPatient = patientDAO.getPatientById(patientID);
            if (insertedPatient == null) {
                showError("Failed to add patient. Please check your inputs.");
                return;
            }

            // Add the patient to the list if it was successfully inserted
            patients.add(insertedPatient);
            showInfo("Patient added successfully!");
            
            // Clear form fields and refresh the table
            clearFormFields();
            formBox.setVisible(false);
            submitPatientButton.setVisible(true);
            saveChangesButton.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging
            showError("Error adding patient: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        try {
            loadPatientData();
            patientTable.setVisible(true);
            formBox.setVisible(false);
        } catch (Exception e) {
            showError("Error displaying all patients: " + e.getMessage());
        }
    }

    // --- UPDATE FLOW ---
    @FXML
    private void handleUpdate() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showError("Please select a patient to update.");
            return;
        }

        patientIDField.setText(selectedPatient.getPatientID());
        patientIDField.setDisable(true);
        firstNameField.setText(selectedPatient.getFirstName());
        lastNameField.setText(selectedPatient.getLastName());
        bloodTypeField.setText(selectedPatient.getPatientBloodType());
        ageField.setText(String.valueOf(selectedPatient.getPatientAge()));
        genderField.setText(selectedPatient.getPatientGender());
        medicalHistoryField.setText(selectedPatient.getMedicalHistory());
        hospitalIDField.setText(selectedPatient.getHospitalID());

        formBox.setVisible(true);
        submitPatientButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String patientID = patientIDField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String gender = genderField.getText().trim();
            String medicalHistory = medicalHistoryField.getText().trim();
            String hospitalID = hospitalIDField.getText().trim();

            if (patientID.isEmpty()) {
                showError("Patient ID cannot be empty.");
                return;
            }

            Patient existingPatient = patientDAO.getPatientById(patientID);
            if (existingPatient == null) {
                showError("No patient found with ID: " + patientID);
                return;
            }

            existingPatient.setFirstName(firstName);
            existingPatient.setLastName(lastName);
            existingPatient.setPatientBloodType(bloodType);
            existingPatient.setPatientAge(age);
            existingPatient.setPatientGender(gender);
            existingPatient.setMedicalHistory(medicalHistory);
            existingPatient.setHospitalID(hospitalID);

            patientDAO.updatePatient(existingPatient);

            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getPatientID().equals(patientID)) {
                    patients.set(i, existingPatient);
                    break;
                }
            }

            showInfo("Patient updated successfully.");
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error updating patient: " + e.getMessage());
        }
    }

    // DELETE
    @FXML
    private void handleDelete() {
        try {
            Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                patientDAO.deletePatient(selectedPatient.getPatientID());
                patients.remove(selectedPatient);
                refreshTable();
            } else {
                showError("Please select a patient to delete.");
            }
        } catch (Exception e) {
            showError("Error deleting patient: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        patientIDField.setDisable(false);
        formBox.setVisible(false);
    }

    private void refreshTable() {
        try {
            loadPatientData();
            patientTable.setItems(patients);
        } catch (Exception e) {
            showError("Error refreshing patient table: " + e.getMessage());
        }
    }
  
}
