//package Controller;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import model.User;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class DashboardController {
//
//    @FXML
//    private VBox buttonContainer; // container where buttons will be dynamically added
//
//    @FXML
//    private Label welcomeLabel;
//
//    private User user;
//
//    // Admin Routes
//    private static final Map<String, String> adminRoutes = new LinkedHashMap<>() {{
//        put("Blood Banks", "/views/bloodbank.fxml");
//        put("Blood Stock", "/views/bloodstock.fxml");
//        put("Donors", "/views/donors.fxml");
//        put("Donations", "/views/donations.fxml");
//        put("Patients", "/views/patients.fxml");
//        put("Emergency Services", "/views/emergencyservices.fxml");
//        put("Eligibility Criteria", "/views/eligibilitycriteria.fxml");
//        put("Blood Requests", "/views/bloodrequests.fxml");
//        put("Hospitals", "/views/hospitals.fxml");
//    }};
//
//    // User Routes
//    private static final Map<String, String> userRoutes = new LinkedHashMap<>() {{
//        put("Blood Request", "/views/bloodrequests.fxml");
//        put("Check Eligibility", "/views/eligibilitycriteria.fxml");
//        put("Become a Donor", "/views/donors.fxml");
//        put("Emergency Request", "/views/emergencyservices.fxml");
//    }};
//
//    // Initialize with user info
//    public void init(User user) {
//        this.user = user;
//        System.out.println("Initializing Dashboard for: " + user.getUsername());
//        welcomeLabel.setText("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
//
//        // Log role here to verify what role was assigned to the user
//        System.out.println("User Role: " + user.getRole());  // Debug log
//
//        // Determine which route to use based on the role
//        Map<String, String> routes = "admin".equalsIgnoreCase(user.getRole()) 
//            ? adminRoutes 
//            : userRoutes;
//
//        // Add buttons dynamically for routes based on the role
//        for (Map.Entry<String, String> entry : routes.entrySet()) {
//            Button button = new Button(entry.getKey());
//            button.setPrefWidth(300);
//            button.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
//            button.setOnAction(e -> loadPage(entry.getValue()));
//            buttonContainer.getChildren().add(button); // Add the button to the container
//            System.out.println("Added button: " + entry.getKey());
//        }
//    }
//
//
//    // Load specific FXML page in the same window
//    private void loadPage(String fxmlPath) {
//        try {
//            System.out.println("Loading page: " + fxmlPath);  // Debug log for page loading
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            AnchorPane pane = loader.load();
//            Stage stage = (Stage) buttonContainer.getScene().getWindow();
//            stage.setScene(new Scene(pane)); // Switch to the new scene
//            System.out.println("Page loaded successfully.");  // Debug log when page is loaded
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Failed to Load Page");
//            alert.setContentText("Could not load: " + fxmlPath);
//            alert.showAndWait();
//            System.out.println("Error loading page: " + fxmlPath);
//            e.printStackTrace();
//        }
//    }
//}
