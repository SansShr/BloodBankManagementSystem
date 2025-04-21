package Controller;

import dao.BloodStockDAO;
import exception.BloodStockException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.BloodStock;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BloodStockFXMLController {

    @FXML private TableColumn<BloodStock, String> stockIDCol;
    @FXML private TableColumn<BloodStock, String> bloodBankIDCol;
    @FXML private TableColumn<BloodStock, String> bloodTypeCol;
    @FXML private TableColumn<BloodStock, Integer> quantityCol;
    @FXML private TableColumn<BloodStock, String> expirationDateCol;

    @FXML private TextField stockIDField;
    @FXML private TextField bloodBankIDField;
    @FXML private TextField bloodTypeField;
    @FXML private TextField quantityField;
    @FXML private TextField expirationDateField;

    @FXML private Button submitBloodStockButton;  
    @FXML private Button saveChangesButton;       

    @FXML private TableView<BloodStock> bloodStockTable;
    @FXML private VBox formBox;

    private final BloodStockDAO bloodStockDAO = new BloodStockDAO();
    private ObservableList<BloodStock> bloodStocks;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBloodStockData();
        bloodStockTable.setVisible(true);
        formBox.setVisible(false);

        bloodStockTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedBloodStock) -> {
            if (selectedBloodStock != null) {
                stockIDField.setText(selectedBloodStock.getId());
                bloodBankIDField.setText(selectedBloodStock.getBloodBankId());
                bloodTypeField.setText(selectedBloodStock.getBloodType());
                quantityField.setText(String.valueOf(selectedBloodStock.getQuantity()));
                expirationDateField.setText(selectedBloodStock.getExpirationDate().toString());
            }
        });
    }

    private void setupTableColumns() {
        stockIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bloodBankIDCol.setCellValueFactory(new PropertyValueFactory<>("bloodBankId"));
        bloodTypeCol.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        expirationDateCol.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
    }

    private void loadBloodStockData() {
        try {
            List<BloodStock> list = bloodStockDAO.getAllBloodStocks();
            bloodStocks = FXCollections.observableArrayList(list);
            bloodStockTable.setItems(bloodStocks);
        } catch (BloodStockException e) {
            showError("Error loading blood stock data: " + e.getMessage());
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
        stockIDField.clear();
        bloodBankIDField.clear();
        bloodTypeField.clear();
        quantityField.clear();
        expirationDateField.clear();
        stockIDField.setDisable(false);
    }

    // --- CREATE FLOW ---
    @FXML
    private void handleCreate() {
    	 loadBloodStockData();
         bloodStockTable.setVisible(true);
    	
        clearFormFields();
        formBox.setVisible(true);
        stockIDField.setDisable(false);
        submitBloodStockButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void handleAdd() {
   
        try {
            // Get user input from form fields
            String stockID = stockIDField.getText().trim();
            String bloodBankID = bloodBankIDField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String expirationDateString = expirationDateField.getText().trim();

            if (stockID.isEmpty() || bloodBankID.isEmpty() || bloodType.isEmpty() || expirationDateString.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            // Parse expiration date from String to Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date expirationDate = null;

            try {
                expirationDate = new Date(sdf.parse(expirationDateString).getTime());
            } catch (ParseException e) {
                showError("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                return;
            }

            // Create a new BloodStock object
            BloodStock newBloodStock = new BloodStock(stockID, bloodBankID, bloodType, quantity, expirationDate);

            // Add the blood stock to the database
            bloodStockDAO.addBloodStock(newBloodStock);

            // After insertion, confirm if the blood stock was added
            BloodStock insertedBloodStock = bloodStockDAO.getBloodStockById(stockID);
            if (insertedBloodStock == null) {
                showError("Failed to add blood stock.");
                return;
            }

            // Add to list and refresh
            bloodStocks.add(insertedBloodStock);
            showInfo("Blood stock added successfully!");

            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error adding blood stock: " + e.getMessage());
        }
    }

    @FXML
    private void handleDisplayAll() {
        try {
            loadBloodStockData();
            bloodStockTable.setVisible(true);
            formBox.setVisible(false);
        } catch (Exception e) {
            showError("Error displaying all blood stocks: " + e.getMessage());
        }
    }
    // --- UPDATE FLOW ---
    @FXML
    private void handleUpdate() {
   
        BloodStock selectedBloodStock = bloodStockTable.getSelectionModel().getSelectedItem();
        if (selectedBloodStock != null) {
            stockIDField.setText(selectedBloodStock.getId());
            bloodBankIDField.setText(selectedBloodStock.getBloodBankId());
            bloodTypeField.setText(selectedBloodStock.getBloodType());
            quantityField.setText(String.valueOf(selectedBloodStock.getQuantity()));
            expirationDateField.setText(selectedBloodStock.getExpirationDate().toString());
            stockIDField.setDisable(false);
            formBox.setVisible(true);
            submitBloodStockButton.setVisible(false);  // Hide the 'Submit' button (Add new)
            saveChangesButton.setVisible(true);
        } else {
            showError("Please select a blood stock to update.");
        }

    }

    @FXML
    private void handleSaveChanges() {
        try {
            String stockID = stockIDField.getText().trim();
            String bloodBankID = bloodBankIDField.getText().trim();
            String bloodType = bloodTypeField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            String expirationDateString = expirationDateField.getText().trim();

            if (stockID.isEmpty() || bloodBankID.isEmpty() || bloodType.isEmpty() || expirationDateString.isEmpty()) {
                showError("Please fill in all fields.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date expirationDate = null;

            try {
                expirationDate = new Date(sdf.parse(expirationDateString).getTime());
            } catch (ParseException e) {
                showError("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                return;
            }

            // Update the BloodStock object
            BloodStock updatedBloodStock = new BloodStock(stockID, bloodBankID, bloodType, quantity, expirationDate);
            bloodStockDAO.updateBloodStock(updatedBloodStock);

            // Refresh the list and the table
            loadBloodStockData();
            showInfo("Blood stock updated successfully!");

            clearFormFields();
            formBox.setVisible(false);
            refreshTable();
        } catch (Exception e) {
            showError("Error saving changes: " + e.getMessage());
        }
    }

    // --- DELETE FLOW ---
    @FXML
    private void handleDelete() {
    
        BloodStock selectedBloodStock = bloodStockTable.getSelectionModel().getSelectedItem();
        if (selectedBloodStock != null) {
            String stockID = selectedBloodStock.getId();

            // Ask for confirmation before deleting
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this blood stock?");
            alert.setContentText("Stock ID: " + stockID);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        bloodStockDAO.deleteBloodStock(stockID);
                        showInfo("Blood stock deleted successfully!");
                        loadBloodStockData();
                    } catch (BloodStockException e) {
                        showError("Error deleting blood stock: " + e.getMessage());
                    }
                }
            });
        } else {
            showError("Please select a blood stock to delete.");
        }
    }

    // --- REFRESH TABLE ---
    private void refreshTable() {
        loadBloodStockData();
        bloodStockTable.refresh(); // This will update the table view
    }

    @FXML
    private void handleCancel() {
        clearFormFields();
        stockIDField.setDisable(false);
        formBox.setVisible(false);
    }

  
}
