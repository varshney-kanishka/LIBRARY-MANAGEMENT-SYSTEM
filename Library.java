import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Library {

    // ➕ ADD BOOK
    public boolean addBook(int id, String title) {
        try (Connection con = DBConnection.getConnection()) {

            String check = "SELECT * FROM books WHERE id=?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();

            if (rs.next())
                return false;

            String query = "INSERT INTO books VALUES (?, ?, false, NULL, NULL)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, title);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 📤 ISSUE BOOK
    public boolean issueBook(int id) {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM books WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && !rs.getBoolean("isIssued")) {

                LocalDate today = LocalDate.now();
                LocalDate due = today.plusDays(7);

                String update = "UPDATE books SET isIssued=true, issueDate=?, dueDate=? WHERE id=?";
                PreparedStatement ps2 = con.prepareStatement(update);

                ps2.setDate(1, Date.valueOf(today));
                ps2.setDate(2, Date.valueOf(due));
                ps2.setInt(3, id);

                ps2.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 📥 RETURN BOOK + FINE
    public boolean returnBook(int id) {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM books WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getBoolean("isIssued")) {

                Date dueDate = rs.getDate("dueDate");
                LocalDate today = LocalDate.now();

                if (dueDate != null && today.isAfter(dueDate.toLocalDate())) {
                    long daysLate = java.time.temporal.ChronoUnit.DAYS
                            .between(dueDate.toLocalDate(), today);

                    long fine = daysLate * 5;

                    javax.swing.JOptionPane.showMessageDialog(null,
                            "Late by " + daysLate + " days\nFine = ₹" + fine);
                }

                String update = "UPDATE books SET isIssued=false, issueDate=NULL, dueDate=NULL WHERE id=?";
                PreparedStatement ps2 = con.prepareStatement(update);

                ps2.setInt(1, id);
                ps2.executeUpdate();

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 📊 STATS
    public int getTotalBooks() {
        try (Connection con = DBConnection.getConnection()) {

            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM books");
            if (rs.next())
                return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getIssuedBooks() {
        try (Connection con = DBConnection.getConnection()) {

            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT COUNT(*) FROM books WHERE isIssued=true");

            if (rs.next())
                return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 📖 GET ALL BOOKS
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM books");

            while (rs.next()) {
                Book b = new Book(rs.getInt("id"), rs.getString("title"));

                if (rs.getBoolean("isIssued")) {
                    b.setIssued(true);
                }

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔍 SEARCH BY ID
    public String searchById(int id) {
        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM books WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id") + " | " + rs.getString("title") + " | Issued: " + rs.getBoolean("isIssued");
            } else {
                return "Book not found.";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}