package Controller;

import dao.DonorDAO;
import exception.DonorException;
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
import model.Donor;

import java.util.List;

public class DonorFXMLController {

    @FXML private TableColumn<Donor, String> donorIDCol;
    @FXML private TableColumn<Donor, String> donorFirstNameCol;
    @FXML private TableColumn<Donor, String> donorLastNameCol;
    @FXML private TableColumn<Donor, String> donorPhoneCol;
    @FXML private TableColumn<Donor, String> donorBloodTypeCol;
    @FXML private TableColumn<Donor, Integer> donorAgeCol;

    @FXML private TextField donorIDField;
    @FXML private TextField donorFirstNameField;
    @FXML private TextField donorLastNameField;
    @FXML private TextField donorPhoneField;
    @FXML private TextField donorBloodTypeField;
    @FXML private TextField donorAgeField;

    @FXML private Button submitDonorButton;
    @FXML private Button saveChangesButton;

    @FXML private TableView<Donor> donorTable;
    @FXML private VBox formBox;

    private final DonorDAO donorDAO = new DonorDAO();
    private ObservableList<Donor> donors;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadDonorData();
        donorTable.setVisible(true);
        formBox.setVisible(false);

        donorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedDonor) -> {
            if (selectedDonor != null) {
                donorIDField.setText(selectedDonor.getDonorID());
                donorFirstNameField.setText(selectedDonor.getFirstName());
                donorLastNameField.setText(selectedDonor.getLastName());
                donorPhoneField.setText(selectedDonor.getDonorPhone());
                donorBloodTypeField.setText(selectedDonor.getDonorBloodType());
                donorAgeField.setText(String.valueOf(selectedDonor.getAge()));
            }
        });
    }

    private void setupTableColumns() {
        donorIDCol.setCellValueFactory(new PropertyValueFactory<>("donorID"));
        donorFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        donorLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        donorPhoneCol.setCellValueFactory(new PropertyValueFactory<>("donorPhone"));
        donorBloodTypeCol.setCellValueFactory(new PropertyValueFactory<>("donorBloodType"));
        donorAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
    }

    private void loadDonorData() {
        try {
            List<Donor> list = donorDAO.getAllDonors();
            donors = FXCollections.observableArrayList(list);
            donorTable.setItems(donors);
        } catch (DonorException e) {
            showError("Error loading donors: " + e.getMessage());
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
        donorIDField.clear();
        donorFirstNameField.clear();
        donorLastNameField.clear();
        donorPhoneField.clear();
        donorBloodTypeField.clear();
        donorAgeField.clear();
    }

    // --- CREATE FLOW ---
    @FXML
    private void handleCreate() {
        clearFormFields();
        formBox.setVisible(true);
        donorIDField.setDisable(false);
        submitDonorButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
        try {
            // Get user input from form fields
            String donorID = donorIDField.getText().trim();
            String firstName = donorFirstNameField.getText().trim();
            String lastName = donorLastNameField.getText().trim();
            String donorPhone = donorPhoneField.getText().trim();
            String donorBloodType = donorBloodTypeField.getText().trim();
            int age = Integer.parseInt(donorAgeField.getText().trim());

            // Check if any field is empty
            if (donorID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || donorPhone.isEmpty() || donorBloodType.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            // Create a new Donor object with the user input
            Donor newDonor = new Donor(donorID, firstName, lastName, donorPhone, donorBloodType, age);

            // Add the new donor to the database
            donorDAO.addDonor(newDonor);

            // After insertion, confirm if the donor was added successfully
            Donor insertedDonor = donorDAO.getDonorById(donorID);  // Fetch the added donor to confirm
            if (insertedDonor == null) {
                showError("Failed to add donor. Please check your inputs.");
                return;
            }

            // Add the donor to the list if it was successfully inserted
            donors.add(insertedDonor);
            showInfo("Donor added successfully!");

            // Clear form fields and refresh the table
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging
            showError("Error adding donor: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        try {
            loadDonorData();
            donorTable.setVisible(true);
            formBox.setVisible(false);
        } catch (Exception e) {
            showError("Error displaying all donors: " + e.getMessage());
        }
    }

    // --- UPDATE FLOW ---
    @FXML
    private void handleUpdate() {
        Donor selectedDonor = donorTable.getSelectionModel().getSelectedItem();
        if (selectedDonor == null) {
            showError("Please select a donor to update.");
            return;
        }

        donorIDField.setText(selectedDonor.getDonorID());
        donorIDField.setDisable(true);
        donorFirstNameField.setText(selectedDonor.getFirstName());
        donorLastNameField.setText(selectedDonor.getLastName());
        donorPhoneField.setText(selectedDonor.getDonorPhone());
        donorBloodTypeField.setText(selectedDonor.getDonorBloodType());
        donorAgeField.setText(String.valueOf(selectedDonor.getAge()));

        formBox.setVisible(true);
        submitDonorButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String donorID = donorIDField.getText().trim();
            String firstName = donorFirstNameField.getText().trim();
            String lastName = donorLastNameField.getText().trim();
            String donorPhone = donorPhoneField.getText().trim();
            String donorBloodType = donorBloodTypeField.getText().trim();
            int age = Integer.parseInt(donorAgeField.getText().trim());

            if (donorID.isEmpty()) {
                showError("Donor ID cannot be empty.");
                return;
            }

            Donor existingDonor = donorDAO.getDonorById(donorID);
            if (existingDonor == null) {
                showError("No donor found with ID: " + donorID);
                return;
            }

            existingDonor.setFirstName(firstName);
            existingDonor.setLastName(lastName);
            existingDonor.setDonorPhone(donorPhone);
            existingDonor.setDonorBloodType(donorBloodType);
            existingDonor.setAge(age);

            donorDAO.updateDonor(existingDonor);

            for (int i = 0; i < donors.size(); i++) {
                if (donors.get(i).getDonorID().equals(donorID)) {
                    donors.set(i, existingDonor);
                    break;
                }
            }

            showInfo("Donor updated successfully.");
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error updating donor: " + e.getMessage());
        }
    }

    // DELETE
    @FXML
    private void handleDelete() {
        try {
            Donor selectedDonor = donorTable.getSelectionModel().getSelectedItem();
            if (selectedDonor != null) {
                donorDAO.deleteDonor(selectedDonor.getDonorID());
                donors.remove(selectedDonor);
                refreshTable();
            } else {
                showError("Please select a donor to delete.");
            }
        } catch (Exception e) {
            showError("Error deleting donor: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        donorIDField.setDisable(false);
        formBox.setVisible(false);
    }

    private void refreshTable() {
        try {
            loadDonorData();
            donorTable.setItems(donors);
        } catch (Exception e) {
            showError("Error refreshing donor table: " + e.getMessage());
        }
    }
  
}
