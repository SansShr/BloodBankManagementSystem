package dao;

import model.BloodStock;
import db.DBConnection;
import exception.BloodStockException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodStockDAO {

    // Add a new blood stock to the database
    public void addBloodStock(BloodStock bloodStock) throws BloodStockException {
        String query = "INSERT INTO BloodStock (StockID, BloodBankID, BloodType, Quantity, ExpirationDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bloodStock.getId());  // StockID
            stmt.setString(2, bloodStock.getBloodBankId());  // BloodBankID
            stmt.setString(3, bloodStock.getBloodType());  // BloodType
            stmt.setInt(4, bloodStock.getQuantity());  // Quantity
            stmt.setDate(5, new java.sql.Date(bloodStock.getExpirationDate().getTime()));  // ExpirationDate

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodStockException("Failed to add blood stock.");
            }

        } catch (SQLException e) {
            throw new BloodStockException("Error adding blood stock: " + e.getMessage(), e);
        }
    }

    // Update an existing blood stock
    public void updateBloodStock(BloodStock bloodStock) throws BloodStockException {
        String updateQuery = "UPDATE BloodStock SET BloodBankID = ?, BloodType = ?, Quantity = ?, ExpirationDate = ? WHERE StockID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, bloodStock.getBloodBankId());  // BloodBankID
            stmt.setString(2, bloodStock.getBloodType());  // BloodType
            stmt.setInt(3, bloodStock.getQuantity());  // Quantity
            stmt.setDate(4, new java.sql.Date(bloodStock.getExpirationDate().getTime()));  // ExpirationDate
            stmt.setString(5, bloodStock.getId());  // StockID

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodStockException("Failed to update blood stock.");
            }

            // Optional: Log or print to verify the update was successful
            System.out.println("Updated " + rowsAffected + " rows for StockID: " + bloodStock.getId());

        } catch (SQLException e) {
            throw new BloodStockException("Error updating blood stock: " + e.getMessage(), e);
        }
    }

    // Delete a blood stock by StockID
    public void deleteBloodStock(String stockID) throws BloodStockException {
        String deleteQuery = "DELETE FROM BloodStock WHERE StockID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, stockID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodStockException("Failed to delete blood stock.");
            }

        } catch (SQLException e) {
            throw new BloodStockException("Error deleting blood stock: " + e.getMessage(), e);
        }
    }

    // Retrieve a blood stock by StockID
    public BloodStock getBloodStockById(String stockID) throws BloodStockException {
        String query = "SELECT * FROM BloodStock WHERE StockID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, stockID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BloodStock(
                        rs.getString("StockID"),
                        rs.getString("BloodBankID"),
                        rs.getString("BloodType"),
                        rs.getInt("Quantity"),
                        rs.getDate("ExpirationDate")
                );
            } else {
                throw new BloodStockException("Blood stock not found.");
            }

        } catch (SQLException e) {
            throw new BloodStockException("Error fetching blood stock: " + e.getMessage(), e);
        }
    }

    // Retrieve all blood stocks
    public List<BloodStock> getAllBloodStocks() throws BloodStockException {
        List<BloodStock> bloodStockList = new ArrayList<>();
        String query = "SELECT * FROM BloodStock";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BloodStock bloodStock = new BloodStock(
                        rs.getString("StockID"),
                        rs.getString("BloodBankID"),
                        rs.getString("BloodType"),
                        rs.getInt("Quantity"),
                        rs.getDate("ExpirationDate")
                );
                bloodStockList.add(bloodStock);
            }

        } catch (SQLException e) {
            throw new BloodStockException("Error retrieving blood stocks: " + e.getMessage(), e);
        }

        return bloodStockList;
    }
}