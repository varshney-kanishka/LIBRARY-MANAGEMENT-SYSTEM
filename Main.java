public class Main {
    public static void main(String[] args) {

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