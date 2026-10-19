public class Book {
    private int id;
    private String title;
    private String author;
    private boolean issued;
    private String issuedTo;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
        this.issuedTo = "";
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return issued; }
    public String getIssuedTo() { return issuedTo; }

    public void issue(String memberName) {
        issued = true;
        issuedTo = memberName;
    }

    public void giveBack() {
        issued = false;
        issuedTo = "";
    }

    // used when saving to file
    public String toCsv() {
        return id + "," + title + "," + author + "," + issued + "," + issuedTo;
    }

    @Override
    public String toString() {
        String status = issued ? "Issued to " + issuedTo : "Available";
        return String.format("%-4d %-28s %-20s %s", id, title, author, status);
    }
}
