package dao;

import model.BloodRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestDAO {

    private Connection connection;

    // Constructor to initialize the connection
    public BloodRequestDAO(Connection connection) {
        this.connection = connection;
    }

    // Insert a new BloodRequest
    public void insertBloodRequest(BloodRequest bloodRequest) throws SQLException {
        String sql = "INSERT INTO BloodRequests (RequestID, HospitalID, BloodType, Quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bloodRequest.getId());  // RequestID
            stmt.setString(2, bloodRequest.getHospitalID());
            stmt.setString(3, bloodRequest.getBloodType());
            stmt.setInt(4, bloodRequest.getQuantity());
            stmt.executeUpdate();
        }
    }

    // Update an existing BloodRequest
    public void updateBloodRequest(BloodRequest bloodRequest) throws SQLException {
        String sql = "UPDATE BloodRequests SET HospitalID = ?, BloodType = ?, Quantity = ? WHERE RequestID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bloodRequest.getHospitalID());
            stmt.setString(2, bloodRequest.getBloodType());
            stmt.setInt(3, bloodRequest.getQuantity());
            stmt.setString(4, bloodRequest.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a BloodRequest by RequestID
    public void deleteBloodRequest(String requestID) throws SQLException {
        String sql = "DELETE FROM BloodRequests WHERE RequestID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, requestID);
            stmt.executeUpdate();
        }
    }

    // Get a BloodRequest by RequestID
    public BloodRequest getBloodRequestById(String requestID) throws SQLException {
        String sql = "SELECT * FROM BloodRequests WHERE RequestID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, requestID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new BloodRequest(
                        rs.getString("RequestID"),
                        rs.getString("HospitalID"),
                        rs.getString("BloodType"),
                        rs.getInt("Quantity")
                    );
                }
            }
        }
        return null;
    }

    // Get all BloodRequests
    public List<BloodRequest> getAllBloodRequests() throws SQLException {
        List<BloodRequest> bloodRequests = new ArrayList<>();
        String sql = "SELECT * FROM BloodRequests";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bloodRequests.add(new BloodRequest(
                    rs.getString("RequestID"),
                    rs.getString("HospitalID"),
                    rs.getString("BloodType"),
                    rs.getInt("Quantity")
                ));
            }
        }
        return bloodRequests;
    }
}
