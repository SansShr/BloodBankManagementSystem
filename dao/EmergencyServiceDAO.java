package dao;

import model.EmergencyService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergencyServiceDAO {

    private Connection connection;

    // Constructor to initialize the connection
    public EmergencyServiceDAO(Connection connection) {
        this.connection = connection;
    }

    // Insert a new EmergencyService
    public void insertEmergencyService(EmergencyService emergencyService) throws SQLException {
        String sql = "INSERT INTO emergencyservices (ServiceID, RequestID, ServiceDetails) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, emergencyService.getServiceId());
            stmt.setString(2, emergencyService.getRequestId());
            stmt.setString(3, emergencyService.getServiceDetails());
            stmt.executeUpdate();
        }
    }

    // Update an existing EmergencyService
    public void updateEmergencyService(EmergencyService emergencyService) throws SQLException {
        String sql = "UPDATE emergencyservices SET ServiceDetails = ? WHERE ServiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, emergencyService.getServiceDetails());
            stmt.setString(2, emergencyService.getServiceId());
            stmt.executeUpdate();
        }
    }

    // Delete an EmergencyService by ServiceID
    public void deleteEmergencyService(String serviceId) throws SQLException {
        String sql = "DELETE FROM emergencyservices WHERE ServiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, serviceId);
            stmt.executeUpdate();
        }
    }

    // Get an EmergencyService by ServiceID
    public EmergencyService getEmergencyServiceById(String serviceId) throws SQLException {
        String sql = "SELECT * FROM emergencyservices WHERE ServiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, serviceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new EmergencyService(
                        rs.getString("ServiceID"),
                        rs.getString("RequestID"),
                        rs.getString("ServiceDetails")
                    );
                }
            }
        }
        return null;
    }

    // Get all EmergencyServices
    public List<EmergencyService> getAllEmergencyServices() throws SQLException {
        List<EmergencyService> emergencyServices = new ArrayList<>();
        String sql = "SELECT * FROM emergencyservices";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                emergencyServices.add(new EmergencyService(
                    rs.getString("ServiceID"),
                    rs.getString("RequestID"),
                    rs.getString("ServiceDetails")
                ));
            }
        }
        return emergencyServices;
    }
}
