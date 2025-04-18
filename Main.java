import java.sql.Connection;
import java.sql.SQLException;
import dao.BloodRequestDAO;
import db.DBConnection;
import model.BloodRequest;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Create a new BloodRequest object
    	BloodRequest bloodRequest = new BloodRequest("REQ001", "H1", "A+", 5);

        Class.forName("com.mysql.cj.jdbc.Driver");  // Explicitly load MySQL JDBC driver


        // Get a valid connection from DBConnection
        Connection connection = DBConnection.getConnection();

        // Create an instance of BloodRequestDAO with the valid connection
        BloodRequestDAO bloodRequestDAO = new BloodRequestDAO(connection);

        // Call the insert method to add the blood request to the database
        bloodRequestDAO.insertBloodRequest(bloodRequest);

        // Close the connection after usage
        connection.close();
    }
}
