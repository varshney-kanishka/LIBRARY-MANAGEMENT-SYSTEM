import java.time.LocalDate;

public class Book {

    int id;
    String title;
    boolean isIssued;
    LocalDate issueDate;
    LocalDate dueDate;

    public Book(int id, String title, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.isIssued = isIssued;
        if (isIssued) {
            this.issueDate = LocalDate.now();
            this.dueDate = issueDate.plusDays(7);
        } else {
            this.issueDate = null;
            this.dueDate = null;
        }
    }

    public String getDetails() {
        return "ID: " + id +
                " | Title: " + title +
                " | Issued: " + isIssued +
                " | Issue Date: " + (issueDate == null ? "N/A" : issueDate) +
                " | Due Date: " + (dueDate == null ? "N/A" : dueDate);
    }

    public Book(int id, String title) {
        this(id, title, false);
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        this.isIssued = issued;

        if (issued) {
            this.issueDate = LocalDate.now();
            this.dueDate = issueDate.plusDays(7);
        } else {
            this.issueDate = null;
            this.dueDate = null;
        }
    }

    public void display() {
        System.out.println("ID: " + id + " | Title: " + title + " | Issued: " + isIssued +
                " | Date: " + (issueDate == null ? "N/A" : issueDate));
    }
}
