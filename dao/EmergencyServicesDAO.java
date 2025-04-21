package dao;

import model.EmergencyService;
import db.DBConnection;
import exception.EmergencyServiceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergencyServicesDAO {

    // Add a new emergency service to the database
    public void addEmergencyService(EmergencyService emergencyService) throws EmergencyServiceException {
        String query = "INSERT INTO EmergencyServices (ServiceID, RequestID, ServiceDetails) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emergencyService.getServiceID());
            stmt.setString(2, emergencyService.getRequestID());
            stmt.setString(3, emergencyService.getServiceDetails());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmergencyServiceException("Failed to add emergency service.");
            }

        } catch (SQLException e) {
            throw new EmergencyServiceException("Error adding emergency service: " + e.getMessage(), e);
        }
    }

    // Update an existing emergency service
    public void updateEmergencyService(EmergencyService emergencyService) throws EmergencyServiceException {
        String updateQuery = "UPDATE EmergencyServices SET ServiceDetails = ? WHERE ServiceID = ? AND RequestID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, emergencyService.getServiceDetails());
            stmt.setString(2, emergencyService.getServiceID());
            stmt.setString(3, emergencyService.getRequestID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmergencyServiceException("Failed to update emergency service.");
            }

            // Optional: Log or print to verify the update was successful
            System.out.println("Updated " + rowsAffected + " rows for ServiceID: " + emergencyService.getServiceID());

        } catch (SQLException e) {
            throw new EmergencyServiceException("Error updating emergency service: " + e.getMessage(), e);
        }
    }

    // Delete an emergency service by ServiceID and RequestID
    public void deleteEmergencyService(String serviceID, String requestID) throws EmergencyServiceException {
        String deleteQuery = "DELETE FROM EmergencyServices WHERE ServiceID = ? AND RequestID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, serviceID);
            stmt.setString(2, requestID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmergencyServiceException("Failed to delete emergency service.");
            }

        } catch (SQLException e) {
            throw new EmergencyServiceException("Error deleting emergency service: " + e.getMessage(), e);
        }
    }

    // Retrieve all emergency services
    public List<EmergencyService> getAllEmergencyServices() throws EmergencyServiceException {
        List<EmergencyService> emergencyServiceList = new ArrayList<>();
        String query = "SELECT * FROM EmergencyServices";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                EmergencyService emergencyService = new EmergencyService(
                        rs.getString("ServiceID"),
                        rs.getString("RequestID"),
                        rs.getString("ServiceDetails")
                );
                emergencyServiceList.add(emergencyService);
            }

        } catch (SQLException e) {
            throw new EmergencyServiceException("Error retrieving emergency services: " + e.getMessage(), e);
        }

        return emergencyServiceList;
    }

}
