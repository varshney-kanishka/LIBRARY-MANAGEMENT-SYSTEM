
import javax.swing.*;
import java.util.ArrayList;

public class ViewBooksUI {

    public ViewBooksUI() {
        Library lib = new Library();
        ArrayList<Book> bookList = lib.getAllBooks();

        String[] columns = { "ID", "Title", "Issued" };
        String[][] data = new String[bookList.size()][3];

        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            data[i][0] = String.valueOf(book.id);
            data[i][1] = book.title;
            data[i][2] = String.valueOf(book.isIssued());
        }

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("All Books");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
