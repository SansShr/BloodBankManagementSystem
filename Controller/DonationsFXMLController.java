package Controller;

import dao.DonationsDAO;
import exception.DonationException;
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
import model.Donation;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DonationsFXMLController {

    @FXML private TableView<Donation> donationTable;
    @FXML private TableColumn<Donation, String> donationIDCol;
    @FXML private TableColumn<Donation, String> donorIDCol;
    @FXML private TableColumn<Donation, String> donationDateCol;  // Change to String for donation date

    @FXML private TextField donationIDField;
    @FXML private TextField donorIDField;
    @FXML private TextField donationDateField; // Keep as TextField for date input

    @FXML private Button submitDonationButton;
    @FXML private Button saveChangesButton;

    @FXML private VBox donationFormBox;

    private final DonationsDAO donationsDAO = new DonationsDAO();
    private ObservableList<Donation> donations;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadDonationData();
        donationTable.setVisible(true);
        donationFormBox.setVisible(false);

        donationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedDonation) -> {
            if (selectedDonation != null) {
                donationIDField.setText(selectedDonation.getDonationID());
                donorIDField.setText(selectedDonation.getDonorID());
                donationDateField.setText(selectedDonation.getDonationDate());
            }
        });
    }

    private void setupTableColumns() {
        donationIDCol.setCellValueFactory(new PropertyValueFactory<>("donationID"));
        donorIDCol.setCellValueFactory(new PropertyValueFactory<>("donorID"));
        donationDateCol.setCellValueFactory(new PropertyValueFactory<>("donationDate"));
    }

    private void loadDonationData() {
        try {
            List<Donation> list = donationsDAO.getAllDonations();
            donations = FXCollections.observableArrayList(list);
            donationTable.setItems(donations);
        } catch (DonationException e) {
            showError("Error loading donations: " + e.getMessage());
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
        donationIDField.clear();
        donorIDField.clear();
        donationDateField.clear(); // clear text field
    }

    // --- CREATE FLOW --- 
    @FXML
    private void handleCreate() {
        clearFormFields();
        donationFormBox.setVisible(true);
        donationIDField.setDisable(false);
        submitDonationButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
        try {
            String donationID = donationIDField.getText().trim();
            String donorID = donorIDField.getText().trim();

            // Date parsing with error handling
            String donationDateString = donationDateField.getText().trim();
            if (donationDateString.isEmpty()) {
                showError("Donation date is required.");
                return;
            }

            // Validate date format
            LocalDate donationDate = null;
            try {
                donationDate = LocalDate.parse(donationDateString); // parse LocalDate from TextField
            } catch (DateTimeParseException e) {
                showError("Invalid date format. Please enter a date in yyyy-MM-dd format.");
                return;
            }

            if (donationID.isEmpty() || donorID.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            Donation newDonation = new Donation(donationID, donorID, donationDate.toString()); // Store as String
            donationsDAO.addDonation(newDonation); // Add donation to the database

            Donation insertedDonation = donationsDAO.getDonationById(donationID);
            if (insertedDonation == null) {
                showError("Failed to add donation. Please check your inputs.");
                return;
            }

            donations.add(insertedDonation);
            showInfo("Donation added successfully!");

            clearFormFields();
            donationFormBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error adding donation: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        try {
            loadDonationData();
            donationTable.setVisible(true);
            donationFormBox.setVisible(false);
        } catch (Exception e) {
            showError("Error displaying all donations: " + e.getMessage());
        }
    }

    // --- UPDATE FLOW --- 
    @FXML
    private void handleUpdate() {
        Donation selectedDonation = donationTable.getSelectionModel().getSelectedItem();
        if (selectedDonation == null) {
            showError("Please select a donation to update.");
            return;
        }

        donationIDField.setText(selectedDonation.getDonationID());
        donorIDField.setText(selectedDonation.getDonorID());
        donationDateField.setText(selectedDonation.getDonationDate()); // Use String for donation date

        donationIDField.setDisable(true);
        donationFormBox.setVisible(true);
        submitDonationButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String donationID = donationIDField.getText().trim();
            String donorID = donorIDField.getText().trim();

            // Date parsing with error handling
            String donationDateString = donationDateField.getText().trim();
            if (donationDateString.isEmpty()) {
                showError("Donation date is required.");
                return;
            }

            LocalDate donationDate = null;
            try {
                donationDate = LocalDate.parse(donationDateString); // parse LocalDate from TextField
            } catch (DateTimeParseException e) {
                showError("Invalid date format. Please enter a date in yyyy-MM-dd format.");
                return;
            }

            if (donationID.isEmpty()) {
                showError("Donation ID cannot be empty.");
                return;
            }

            Donation existingDonation = donationsDAO.getDonationById(donationID);
            if (existingDonation == null) {
                showError("No donation found with ID: " + donationID);
                return;
            }

            existingDonation.setDonorID(donorID);
            existingDonation.setDonationDate(donationDate.toString()); // Update donation date as String

            donationsDAO.updateDonation(existingDonation);

            for (int i = 0; i < donations.size(); i++) {
                if (donations.get(i).getDonationID().equals(donationID)) {
                    donations.set(i, existingDonation);
                    break;
                }
            }

            showInfo("Donation updated successfully.");
            clearFormFields();
            donationFormBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error updating donation: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            Donation selectedDonation = donationTable.getSelectionModel().getSelectedItem();
            if (selectedDonation != null) {
                donationsDAO.deleteDonation(selectedDonation.getDonationID());
                donations.remove(selectedDonation);
                refreshTable();
            } else {
                showError("Please select a donation to delete.");
            }
        } catch (Exception e) {
            showError("Error deleting donation: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        donationIDField.setDisable(false);
        donationFormBox.setVisible(false);
    }

    private void refreshTable() {
        try {
            loadDonationData();
            donationTable.setItems(donations);
        } catch (Exception e) {
            showError("Error refreshing donation table: " + e.getMessage());
        }
    }
   
   
}
