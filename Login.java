import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {

    private static final Scanner sc = new Scanner(System.in);

    public static boolean loginUser() {

        System.out.println("\n===== LOGIN SYSTEM =====");

        System.out.print("Username: ");
        String user = sc.nextLine().trim();

        System.out.print("Password: ");
        String pass = sc.nextLine().trim();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login Successful!");
                return true;
            } else {
                System.out.println("Invalid Credentials!");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
