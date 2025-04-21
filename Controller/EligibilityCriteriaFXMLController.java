package Controller;

import dao.EligibilityCriteriaDAO;
import exception.EligibilityCriteriaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.EligibilityCriteria;

import java.util.List;

public class EligibilityCriteriaFXMLController {

    @FXML private TableView<EligibilityCriteria> eligibilityTable;
    @FXML private TableColumn<EligibilityCriteria, String> bloodTypeCol;
    @FXML private TableColumn<EligibilityCriteria, String> criteriaCol;

    private final EligibilityCriteriaDAO dao = new EligibilityCriteriaDAO();
    private ObservableList<EligibilityCriteria> data;

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
    }

    private void setupColumns() {
        bloodTypeCol.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        criteriaCol.setCellValueFactory(new PropertyValueFactory<>("criteria"));
    }

    private void loadData() {
        try {
            List<EligibilityCriteria> list = dao.getAllEligibilityCriteria();
            data = FXCollections.observableArrayList(list);
            eligibilityTable.setItems(data);
        } catch (EligibilityCriteriaException e) {
            showError("Failed to load eligibility criteria: " + e.getMessage());
        }
    }
    public void handleClose() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) eligibilityTable.getScene().getWindow();
        stage.close();
    }


    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Data Load Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    
}
