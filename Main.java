public class Main {
    public static void main(String[] args) {

        try {
            DBConnection.initializeDatabase();
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
            return;
        }

        boolean isLoggedIn = Login.loginUser();

        if (!isLoggedIn) {
            System.out.println("Exiting program...");
            return;
        }

        System.out.println("Opening Dashboard...");

        Library lib = new Library();
        new Dashboard(lib);
    }
}