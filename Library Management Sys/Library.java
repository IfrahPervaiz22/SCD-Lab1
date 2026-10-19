import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private int nextId = 1;
    private final String fileName = "books.csv";

    public Library() {
        loadFromFile();
    }

    public void addBook(String title, String author) {
        books.add(new Book(nextId++, title, author));
        saveToFile();
    }

    public boolean removeBook(int id) {
        Book b = findById(id);
        if (b == null || b.isIssued()) return false;
        books.remove(b);
        saveToFile();
        return true;
    }

    public Book findById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    public List<Book> search(String keyword) {
        List<Book> result = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword)
                    || b.getAuthor().toLowerCase().contains(keyword)) {
                result.add(b);
            }
        }
        return result;
    }

    // returns a message so Main can just print it
    public String issueBook(int id, String memberName) {
        Book b = findById(id);
        if (b == null) return "No book found with that ID.";
        if (b.isIssued()) return "Sorry, this book is already issued to " + b.getIssuedTo() + ".";
        b.issue(memberName);
        saveToFile();
        return "Book issued to " + memberName + ".";
    }

    public String returnBook(int id) {
        Book b = findById(id);
        if (b == null) return "No book found with that ID.";
        if (!b.isIssued()) return "This book was not issued.";
        b.giveBack();
        saveToFile();
        return "Book returned successfully.";
    }

    public List<Book> getAllBooks() {
        return books;
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Book b : books) {
                pw.println(b.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File f = new File(fileName);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", 5);
                if (p.length < 5) continue;
                Book b = new Book(Integer.parseInt(p[0]), p[1], p[2]);
                if (Boolean.parseBoolean(p[3])) b.issue(p[4]);
                books.add(b);
                nextId = Math.max(nextId, b.getId() + 1);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not read saved data: " + e.getMessage());
        }
    }
}
