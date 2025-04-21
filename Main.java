
import java.sql.Connection;
import java.sql.SQLException;

import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Get the database connection
            Connection connection = DBConnection.getConnection();
            if (connection != null) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Database connection failed!");
            }

            // Load the login screen FXML
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));

            // Create the scene
            Scene scene = new Scene(root);

            // Link the CSS file
            scene.getStylesheets().add(getClass().getResource("/views/style.css").toExternalForm());
          

            // Set the stage
            primaryStage.setTitle("Blood Bank Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Optional
            primaryStage.show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Database connection failed.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Error: Null pointer encountered. Please check the FXML loading.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}




