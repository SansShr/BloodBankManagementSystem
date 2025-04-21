package dao;

import model.EligibilityCriteria;
import db.DBConnection;
import exception.EligibilityCriteriaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EligibilityCriteriaDAO {

    // Retrieve all eligibility criteria
    public List<EligibilityCriteria> getAllEligibilityCriteria() throws EligibilityCriteriaException {
        List<EligibilityCriteria> criteriaList = new ArrayList<>();
        String query = "SELECT * FROM EligibilityCriteria";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EligibilityCriteria criteria = new EligibilityCriteria(
                        rs.getString("BloodType"),
                        rs.getString("Criteria")
                );
                criteriaList.add(criteria);
            }

        } catch (SQLException e) {
            throw new EligibilityCriteriaException("Error retrieving eligibility criteria: " + e.getMessage(), e);
        }

        return criteriaList;
    }
}
