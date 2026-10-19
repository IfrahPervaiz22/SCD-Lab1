import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Library library = new Library();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: addBook(); break;
                case 2: viewBooks(library.getAllBooks()); break;
                case 3: searchBooks(); break;
                case 4: issueBook(); break;
                case 5: returnBook(); break;
                case 6: removeBook(); break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    static void showMenu() {
        System.out.println("\n===== LIBRARY MANAGER =====");
        System.out.println("1. Add book");
        System.out.println("2. View all books");
        System.out.println("3. Search book");
        System.out.println("4. Issue book");
        System.out.println("5. Return book");
        System.out.println("6. Remove book");
        System.out.println("0. Exit");
    }

    static void addBook() {
        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Author: ");
        String author = sc.nextLine().trim();
        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Title and author cannot be empty.");
            return;
        }
        library.addBook(title, author);
        System.out.println("Book added.");
    }

    static void viewBooks(List<Book> list) {
        if (list.isEmpty()) {
            System.out.println("No books to show.");
            return;
        }
        System.out.printf("%-4s %-28s %-20s %s%n", "ID", "Title", "Author", "Status");
        System.out.println("-".repeat(70));
        for (Book b : list) System.out.println(b);
    }

    static void searchBooks() {
        System.out.print("Search by title or author: ");
        viewBooks(library.search(sc.nextLine().trim()));
    }

    static void issueBook() {
        int id = readInt("Book ID: ");
        System.out.print("Member name: ");
        String name = sc.nextLine().trim();
        System.out.println(library.issueBook(id, name));
    }

    static void returnBook() {
        int id = readInt("Book ID: ");
        System.out.println(library.returnBook(id));
    }

    static void removeBook() {
        int id = readInt("Book ID to remove: ");
        if (library.removeBook(id)) System.out.println("Book removed.");
        else System.out.println("Cannot remove (book not found or currently issued).");
    }

    // keeps asking until the user types a proper number
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
