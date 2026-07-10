import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Book {
int id;
String name;
boolean isIssued;
int issuedTo;
LocalDate issueDate;


Book(int id, String name) {
    this.id = id;
    this.name = name;
    this.isIssued = false;
}


}

class User {
int id;
String name;

User(int id, String name) {
    this.id = id;
    this.name = name;
}


}

public class LibraryManagementSystem {
static HashMap<Integer, Book> books = new HashMap<>();
static HashMap<Integer, User> users = new HashMap<>();
static Scanner sc = new Scanner(System.in);

public static void main(String[] args) {

    int choice=0;

    do {
        System.out.println("\n--- Smart Library System ---");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Add User");
        System.out.println("4. Issue Book");
        System.out.println("5. Return Book");
        System.out.println("6. Search Book");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input! Enter a number.");
            sc.next();
            continue;
        }

        choice = sc.nextInt();

        switch (choice) {
            case 1 -> addBook();
            case 2 -> viewBooks();
            case 3 -> addUser();
            case 4 -> issueBook();
            case 5 -> returnBook();
            case 6 -> searchBook();
            case 7 -> System.out.println("Exiting...");
            default -> System.out.println("Invalid choice!");
        }
    } while (choice != 7);
}

// Add Book
static void addBook() {
    System.out.print("Enter Book ID: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid ID!");
        sc.next();
        return;
    }

    int id = sc.nextInt();
    sc.nextLine();

    if (books.containsKey(id)) {
        System.out.println("Book ID already exists!");
        return;
    }

    System.out.print("Enter Book Name: ");
    String name = sc.nextLine();

    books.put(id, new Book(id, name));
    System.out.println("Book added successfully!");
}

// View Books
static void viewBooks() {
    if (books.isEmpty()) {
        System.out.println("No books available.");
        return;
    }

    System.out.println("\n--- Book List ---");
    for (Book b : books.values()) {
        System.out.print("ID: " + b.id +
                ", Name: " + b.name +
                ", Issued: " + (b.isIssued ? "Yes" : "No"));

        if (b.isIssued) {
            User u = users.get(b.issuedTo);
            System.out.print(", Issued To: " + (u != null ? u.name : "Unknown"));
            System.out.print(", Issue Date: " + b.issueDate);
        }

        System.out.println();
    }
}

// Add User
static void addUser() {
    System.out.print("Enter User ID: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid ID!");
        sc.next();
        return;
    }

    int id = sc.nextInt();
    sc.nextLine();

    if (users.containsKey(id)) {
        System.out.println("User already exists!");
        return;
    }

    System.out.print("Enter User Name: ");
    String name = sc.nextLine();

    users.put(id, new User(id, name));
    System.out.println("User added successfully!");
}

// Issue Book
static void issueBook() {
    System.out.print("Enter Book ID: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid input!");
        sc.next();
        return;
    }
    int bookId = sc.nextInt();

    System.out.print("Enter User ID: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid input!");
        sc.next();
        return;
    }
    int userId = sc.nextInt();

    if (!books.containsKey(bookId)) {
        System.out.println("Book not found!");
        return;
    }

    if (!users.containsKey(userId)) {
        System.out.println("User not found!");
        return;
    }

    Book b = books.get(bookId);

    if (b.isIssued) {
        System.out.println("Book already issued!");
        return;
    }

    b.isIssued = true;
    b.issuedTo = userId;
    b.issueDate = LocalDate.now();

    System.out.println("Book issued successfully!");
}

// Return Book
static void returnBook() {
    System.out.print("Enter Book ID: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid input!");
        sc.next();
        return;
    }

    int bookId = sc.nextInt();

    if (!books.containsKey(bookId)) {
        System.out.println("Book not found!");
        return;
    }

    Book b = books.get(bookId);

    if (!b.isIssued) {
        System.out.println("Book is not issued!");
        return;
    }

    long days = ChronoUnit.DAYS.between(b.issueDate, LocalDate.now());

    if (days > 7) {
        long fine = (days - 7) * 2;
        System.out.println("Late return! Fine = ₹" + fine);
    }

    b.isIssued = false;
    b.issuedTo = 0;
    b.issueDate = null;

    System.out.println("Book returned successfully!");
}

// Search Book
static void searchBook() {
    System.out.print("Enter Book ID: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid input!");
        sc.next();
        return;
    }

    int id = sc.nextInt();

    if (books.containsKey(id)) {
        Book b = books.get(id);
        System.out.println("Found: " + b.name +
                " | Issued: " + (b.isIssued ? "Yes" : "No"));
    } else {
        System.out.println("Book not found!");
    }
}
}
