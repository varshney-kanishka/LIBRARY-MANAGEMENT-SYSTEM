import java.sql.*;

public class DBConnection {

    static final String URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";
    static final String PASS = "1234";

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");


        return DriverManager.getConnection(URL, USER, PASS);
    }
}