import java.sql.*;

public class DBConnection {

    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";
    static final String PASS = "1234";

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");


        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void initializeDatabase() throws Exception {
        // Connect to MySQL without specifying database
        String rootUrl = "jdbc:mysql://localhost:3306";
        Connection con = DriverManager.getConnection(rootUrl, USER, PASS);
        Statement stmt = con.createStatement();

        // Create database if not exists
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS library_db");

        // Use the database
        stmt.executeUpdate("USE library_db");

        // Create books table
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "isIssued BOOLEAN DEFAULT FALSE, " +
                "issueDate DATE, " +
                "dueDate DATE)";
        stmt.executeUpdate(createBooksTable);

        // Create users table
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(50) PRIMARY KEY, " +
                "password VARCHAR(50) NOT NULL)";
        stmt.executeUpdate(createUsersTable);

        // Insert default admin user if not exists
        String insertUser = "INSERT IGNORE INTO users (username, password) VALUES ('admin', 'admin')";
        stmt.executeUpdate(insertUser);

        stmt.close();
        con.close();
    }
}