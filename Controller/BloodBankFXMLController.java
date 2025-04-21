package Controller;

import dao.BloodBankDAO;
import exception.BloodBankException;
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
import model.BloodBank;

import java.util.List;

public class BloodBankFXMLController {

    @FXML private TableView<BloodBank> bloodBankTable;
    @FXML private TableColumn<BloodBank, String> bloodBankIDCol;
    @FXML private TableColumn<BloodBank, String> bloodBankNameCol;
    @FXML private TableColumn<BloodBank, String> bloodBankLocationCol;
    @FXML private TableColumn<BloodBank, String> bloodBankContactCol;
    @FXML private TableColumn<BloodBank, String> operatingHoursCol;

    @FXML private TextField bloodBankIDField;
    @FXML private TextField bloodBankNameField;
    @FXML private TextField bloodBankLocationField;
    @FXML private TextField bloodBankContactField;
    @FXML private TextField operatingHoursField;

    @FXML private Button submitBloodBankButton;
    @FXML private Button saveChangesButton;
    @FXML private Button backToDashboardButton;

    @FXML private VBox formBox;

    private final BloodBankDAO bloodBankDAO = new BloodBankDAO();
    private ObservableList<BloodBank> bloodBanks;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBloodBankData();
        bloodBankTable.setVisible(true);
        formBox.setVisible(false);

        bloodBankTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedBank) -> {
            if (selectedBank != null) {
                bloodBankIDField.setText(selectedBank.getBloodBankID());
                bloodBankNameField.setText(selectedBank.getBloodBankName());
                bloodBankLocationField.setText(selectedBank.getBloodBankLocation());
                bloodBankContactField.setText(selectedBank.getBloodBankContact());
                operatingHoursField.setText(selectedBank.getOperatingHours());
            }
        });
    }

    private void setupTableColumns() {
        bloodBankIDCol.setCellValueFactory(new PropertyValueFactory<>("bloodBankID"));
        bloodBankNameCol.setCellValueFactory(new PropertyValueFactory<>("bloodBankName"));
        bloodBankLocationCol.setCellValueFactory(new PropertyValueFactory<>("bloodBankLocation"));
        bloodBankContactCol.setCellValueFactory(new PropertyValueFactory<>("bloodBankContact"));
        operatingHoursCol.setCellValueFactory(new PropertyValueFactory<>("operatingHours"));
    }

    private void loadBloodBankData() {
        try {
            List<BloodBank> list = bloodBankDAO.getAllBloodBanks();
            bloodBanks = FXCollections.observableArrayList(list);
            bloodBankTable.setItems(bloodBanks);
        } catch (BloodBankException e) {
            showError("Error loading blood banks: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Completed");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearFormFields() {
        bloodBankIDField.clear();
        bloodBankNameField.clear();
        bloodBankLocationField.clear();
        bloodBankContactField.clear();
        operatingHoursField.clear();
        bloodBankIDField.setDisable(false);
    }

    // --- CREATE ---
    @FXML
    private void handleCreate() {
        clearFormFields();
        formBox.setVisible(true);
        submitBloodBankButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
        try {
            String id = bloodBankIDField.getText().trim();
            String name = bloodBankNameField.getText().trim();
            String location = bloodBankLocationField.getText().trim();
            String contact = bloodBankContactField.getText().trim();
            String hours = operatingHoursField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || location.isEmpty() || contact.isEmpty() || hours.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            BloodBank newBank = new BloodBank(id, name, location, contact, hours);
            bloodBankDAO.addBloodBank(newBank);

            BloodBank insertedBank = bloodBankDAO.getBloodBankById(id);
            if (insertedBank == null) {
                showError("Failed to add blood bank.");
                return;
            }

            bloodBanks.add(insertedBank);
            showInfo("Blood Bank added successfully!");
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error adding blood bank: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        loadBloodBankData();
        bloodBankTable.setVisible(true);
        formBox.setVisible(false);
    }

    // --- UPDATE ---
    @FXML
    private void handleUpdate() {
        BloodBank selected = bloodBankTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a blood bank to update.");
            return;
        }

        bloodBankIDField.setText(selected.getBloodBankID());
        bloodBankIDField.setDisable(true);
        bloodBankNameField.setText(selected.getBloodBankName());
        bloodBankLocationField.setText(selected.getBloodBankLocation());
        bloodBankContactField.setText(selected.getBloodBankContact());
        operatingHoursField.setText(selected.getOperatingHours());

        formBox.setVisible(true);
        submitBloodBankButton.setVisible(false);
        saveChangesButton.setVisible(true);
    }

    @FXML
    private void handleSaveChanges() {
        try {
            String id = bloodBankIDField.getText().trim();
            String name = bloodBankNameField.getText().trim();
            String location = bloodBankLocationField.getText().trim();
            String contact = bloodBankContactField.getText().trim();
            String hours = operatingHoursField.getText().trim();

            BloodBank existing = bloodBankDAO.getBloodBankById(id);
            if (existing == null) {
                showError("Blood Bank not found.");
                return;
            }

            existing.setBloodBankName(name);
            existing.setBloodBankLocation(location);
            existing.setBloodBankContact(contact);
            existing.setOperatingHours(hours);

            bloodBankDAO.updateBloodBank(existing);

            for (int i = 0; i < bloodBanks.size(); i++) {
                if (bloodBanks.get(i).getBloodBankID().equals(id)) {
                    bloodBanks.set(i, existing);
                    break;
                }
            }

            showInfo("Blood Bank updated successfully.");
            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error updating blood bank: " + e.getMessage());
        }
    }

    // --- DELETE ---
    @FXML
    private void handleDelete() {
        BloodBank selected = bloodBankTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a blood bank to delete.");
            return;
        }

        try {
            bloodBankDAO.deleteBloodBank(selected.getBloodBankID());
            bloodBanks.remove(selected);
            refreshTable();
        } catch (Exception e) {
            showError("Error deleting blood bank: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        bloodBankIDField.setDisable(false);
        formBox.setVisible(false);
    }

    private void refreshTable() {
        try {
            loadBloodBankData();
            bloodBankTable.setItems(bloodBanks);
        } catch (Exception e) {
            showError("Error refreshing table: " + e.getMessage());
        }
    }

  
}
