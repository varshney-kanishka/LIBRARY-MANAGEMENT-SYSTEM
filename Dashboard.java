import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {
    JLabel totalLabel, issuedLabel, availableLabel;
    Library lib;

    JButton addBtn, viewBtn, issueBtn, returnBtn, searchBtn, exitBtn;

    public Dashboard(Library lib) {
        this.lib = lib;

        setTitle("Library Dashboard");
    setSize(400, 500);
    setLayout(new GridLayout(7, 1, 10, 10));
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    
    JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));

    totalLabel = new JLabel();
    issuedLabel = new JLabel();
    availableLabel = new JLabel();

    statsPanel.add(totalLabel);
    statsPanel.add(issuedLabel);
    statsPanel.add(availableLabel);

    add(statsPanel);  


        addBtn = new JButton("Add Book");
        viewBtn = new JButton("View Books");
        issueBtn = new JButton("Issue Book");
        returnBtn = new JButton("Return Book");
        searchBtn = new JButton("Search Book");
        exitBtn = new JButton("Exit");

        add(addBtn);
        add(viewBtn);
        add(issueBtn);
        add(returnBtn);
        add(searchBtn);
        add(exitBtn);

        addBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        issueBtn.addActionListener(this);
        returnBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        updateStats();

        setVisible(true);
    }

    private void updateStats() {
        int total = lib.getTotalBooks();
        int issued = lib.getIssuedBooks();
        int available = total - issued;

        totalLabel.setText("Total: " + total);
        issuedLabel.setText("Issued: " + issued);
        availableLabel.setText("Available: " + available);
    }

    public Dashboard() {
        this(new Library());
    }

    public void actionPerformed(ActionEvent e) {

        try {

            if (e.getSource() == addBtn) {
                String idStr = JOptionPane.showInputDialog(this, "Enter ID:");
                String title = JOptionPane.showInputDialog(this, "Enter Title:");

                if (idStr == null || title == null || idStr.isEmpty() || title.isEmpty()) {
                    JOptionPane.showMessageDialog(this, " Fields cannot be empty!");
                    return;
                }

                boolean added = lib.addBook(Integer.parseInt(idStr), title);

                if (added)
                    JOptionPane.showMessageDialog(this, " Book Added!");
                else
                    JOptionPane.showMessageDialog(this, " Book ID already exists!");
            }

            else if (e.getSource() == viewBtn) {
                new ViewBooksUI();
            }

            else if (e.getSource() == issueBtn) {
                String id = JOptionPane.showInputDialog(this, "Enter ID:");

                if (id == null || id.isEmpty())
                    return;

                boolean issued = lib.issueBook(Integer.parseInt(id));

                if (issued)
                    JOptionPane.showMessageDialog(this, " Book Issued!");
                else
                    JOptionPane.showMessageDialog(this, " Book not found or already issued!");
            }

            else if (e.getSource() == returnBtn) {
                String id = JOptionPane.showInputDialog(this, "Enter ID:");

                if (id == null || id.isEmpty())
                    return;

                boolean returned = lib.returnBook(Integer.parseInt(id));

                if (returned)
                    JOptionPane.showMessageDialog(this, " Book Returned!");
                else
                    JOptionPane.showMessageDialog(this, " Book not found or not issued!");
            }

            else if (e.getSource() == searchBtn) {
                String id = JOptionPane.showInputDialog(this, "Enter ID:");

                if (id == null || id.isEmpty())
                    return;

                String result = lib.searchById(Integer.parseInt(id));
                JOptionPane.showMessageDialog(this, result);
            }

            else if (e.getSource() == exitBtn) {
                System.exit(0);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, " Enter valid number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, " Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard(new Library()));
    }
}