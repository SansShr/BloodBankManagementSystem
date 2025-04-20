package Controller;

import db.DBConnection;
import model.User;
import exception.UserNotFoundException;

import java.sql.*;

public class LoginController {

    public static User login(String username, String password) throws Exception {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String role = rs.getString("role");
            return new User(id, username, role);
        } else {
            throw new UserNotFoundException("Invalid username or password.");
        }
    }
}
