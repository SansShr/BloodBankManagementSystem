package dao;

import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Method to handle user login
    public boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String role = rs.getString("role");
            System.out.println("Login successful. Logged in as: " + role);
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // Method to handle user signup
    public boolean signup(String username, String password, String role) throws SQLException {
        String checkSql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, username);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            System.out.println("Username already exists.");
            return false;
        }

        String insertSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertSql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, role);
        ps.executeUpdate();

        System.out.println("Signup successful.");
        return true;
    }
}
