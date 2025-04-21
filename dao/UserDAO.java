package dao;

import model.User;

import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Method to handle user login with plain text password comparison
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        
        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                System.out.println("Login successful for: " + username + ", Role: " + role);
                return new User(id, username, role); // return full User object
            } else {
                System.out.println("Login failed: Invalid credentials");
                return null;  // Return null if login fails
            }
        } catch (SQLException e) {
            System.err.println("Error occurred during login: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception to propagate it
        }
    }

    // Method to handle user signup (stores plain text password)
    public boolean signup(String username, String password, String role) throws SQLException {
        // Check if username already exists
        String checkSql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, username);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            System.out.println("Username already exists.");
            return false;  // User already exists
        }

        // Insert new user into the database with plain text password
        String insertSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertSql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, role);
        ps.executeUpdate();

        System.out.println("Signup successful.");
        return true;  // User successfully created
    }
}
