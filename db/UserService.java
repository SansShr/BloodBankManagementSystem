package db;

import java.sql.Connection;
import java.sql.SQLException;

import dao.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService(Connection conn) {
        this.userDAO = new UserDAO(conn);  // Initialize the UserDAO with DB connection
    }

    // Updated to return a User object instead of boolean
    public User login(String username, String password) throws SQLException {
        return userDAO.login(username, password);  // Delegate to UserDAO for login
    }

    public boolean signup(String username, String password, String role) throws SQLException {
        return userDAO.signup(username, password, role);  // Delegate to UserDAO for signup
    }
}
