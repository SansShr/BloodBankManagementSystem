package db;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bloodmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "Swarali_31";

    public static Connection getConnection() throws SQLException {
        System.out.println("Trying to connect to the database...");
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        if (connection != null) {
            System.out.println("Connection established successfully!");
        }
        return connection;
    }
}

