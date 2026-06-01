package ui;

import model.Book;
import service.BookService;

import java.util.List;
import java.util.Scanner;

public class BookUi {
    private Scanner sc;
    private BookService bookService;

    public BookUi(Scanner sc, BookService bookService) {
        this.sc = sc;
        this.bookService = bookService;
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n\n\n\n\n\n\n\n=== 📚 Book Manage ===");
            System.out.println("1. Add new book");
            System.out.println("2. Update book information");
            System.out.println("3. View book list");
            System.out.println("4. Remove a book");
            System.out.println("5. find a book by key word");
            System.out.println("0. Back to main Menu");
            System.out.print("enter an option: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1: {
                        handleAddBook();
                        break;
                    }

                    case 2: {
                        handleFixBook();
                        break;
                    }

                    case 3: {
                        handleViewBook();
                        break;
                    }

                    case 4: {
                        handleDelete();
                        break;
                    }

                    case 5: {
                        handleSearchBook();
                        break;
                    }

                    case 0: {
                        isRunning = false;
                        break;
                    }

                    default:
                        System.out.println("only choose from 0-5");
                        System.out.print("Press ENTER to continue...");
                        sc.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("must enter number from 0-5");
            }
        }
    }

    private void handleAddBook() {
        System.out.println("\n\n\n\n\n\n\n\n--- ADD BOOK ---");
        try {
            System.out.print("Book ID: ");
            String bookId = sc.nextLine();
            System.out.print("Title: ");
            String title = sc.nextLine();
            System.out.print("Author: ");
            String author = sc.nextLine();
            System.out.print("Genre: ");
            String genre = sc.nextLine();
            System.out.print("Publication Year: ");
            int publication = Integer.parseInt(sc.nextLine());
            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());
            System.out.println("[1] Save [2] Cancel");
            System.out.print("Select: ");
            while (true) {
                String confirm = sc.nextLine();
                if (confirm.equals("1")) {
                    Book book = new Book(bookId, title, author, genre, publication, quantity);
                    bookService.addBook(book);
                    System.out.println("✅ Book added successfully.");
                    break;
                } else if (confirm.equals("2")) {
                    System.out.println("Action cancelled.");
                    break;
                } else {
                    System.out.println("invalid choice");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("must be number there");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handleFixBook() {
        System.out.println("\n\n\n\n\n\n\n\n--- UPDATE BOOK ---");
        try {
            System.out.print("Enter Book ID: ");
            String bookId = sc.nextLine();
            Book book = bookService.findBookById(bookId);
            if(book==null){
                System.out.println("this is wrong id");
            }

            else{
                System.out.println("Current Information:");
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Genre: " + book.getGenre());
                System.out.println("Publication Year: " + book.getPublicationYear());
                System.out.println("Quantity: " + book.getQuantity());

                System.out.print("\nEnter new Title (leave blank to skip): ");
                String title = sc.nextLine();
                String finalTitle = title.isEmpty() ? book.getTitle() : title;

                System.out.print("Enter new Author (leave blank to skip): ");
                String author = sc.nextLine();
                String finalAuthor = author.isEmpty() ? book.getAuthor() : author;

                System.out.print("Enter new Genre (leave blank to skip): ");
                String genre = sc.nextLine();
                String finalGenre = genre.isEmpty() ? book.getGenre() : genre;

                System.out.print("Enter new Publication Year (leave blank to skip): ");
                String publication = sc.nextLine();
                int finalPublication = publication.isEmpty() ? book.getPublicationYear() : Integer.parseInt(publication);

                System.out.print("Enter new Quantity (leave blank to skip): ");
                String quantity = sc.nextLine();
                int finalQuantity = quantity.isEmpty() ? book.getQuantity() : Integer.parseInt(quantity);

                System.out.println("\nDo you want to save this edit");
                System.out.println("[1] Save [2] Cancel");
                while (true) {
                    System.out.println();
                    String confirm = sc.nextLine();
                    if (confirm.equals("1")) {
                        bookService.fixBookInf(bookId, finalTitle, finalAuthor, finalGenre, finalPublication, finalQuantity);
                        System.out.println("✅ Book updated successfully.");
                        break;
                    } else if (confirm.equals("2")) {
                        System.out.println("Action cancelled.");
                        break;
                    } else {
                        System.out.println("invalid choice");
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("quantity and publication year must be number");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }


    private void handleViewBook() {
        List<Book> bookList = bookService.displayBook();
        if (bookList.isEmpty()) {
            System.out.println("there's no book on the list");
        } else {
            System.out.println("\n--- BOOK LIST ---");
            System.out.printf("%-10s | %-20s | %-20s | %-15s | %-6s | %-5s%n", "ID", "Title", "Author", "Genre", "Year", "Qty");
            System.out.println("-----------------------------------------------------------------------------------------");
            for (Book book : bookList) {
                System.out.printf("%-10s | %-20s | %-20s | %-15s | %-6d | %-5d%n\n\n",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublicationYear(), book.getQuantity());
            }
        }
        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }


    private void handleDelete() {
        System.out.println("\n\n\n\n\n\n\n\n--- REMOVE BOOK ---");
        try {
            System.out.print("Enter Book ID to remove: ");
            String bookId = sc.nextLine();
            Book book = bookService.findBookById(bookId);
            if (book == null) {
                System.out.println("wrong id");
            } else {
                System.out.println("Title " + book.getTitle());
                System.out.println("Author " + book.getAuthor());
                System.out.println("\n⚠️ WARNING: Are you sure you want to delete this book?");
                System.out.println("[1] Confirm Remove [2] Cancel");

                while (true) {
                    System.out.print("Enter your choice: ");
                    String confirm = sc.nextLine();
                    if (confirm.equals("1")) {
                        bookService.removeBook(bookId);
                        System.out.println("✅ Book removed successfully.");
                        break;
                    } else if (confirm.equals("2")) {
                        System.out.println("🚫 Action cancelled.");
                        break;
                    } else {
                        System.out.println("❌ Invalid choice! Please select 1 or 2.");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }


    private void handleSearchBook() {
        System.out.println("\n\n\n\n\n\n\n\n\n--- SEARCH BOOKS ---");
        try {
            System.out.print("Enter keyword (Title, Author, or Genre): ");
            String keyword = sc.nextLine();
            List<Book> searchResult = bookService.searchBook(keyword);
            if (searchResult == null || searchResult.isEmpty()) {
                System.out.println("don't have any book with that key word");
            } else {
                System.out.println("\n--- SEARCH RESULTS ---");
                System.out.printf("%-10s | %-20s | %-20s | %-15s | %-6s | %-5s%n", "ID", "Title", "Author", "Genre", "Year", "Qty");
                System.out.println("-----------------------------------------------------------------------------------------");
                for (Book book : searchResult) {
                    System.out.printf("%-10s | %-20s | %-20s | %-15s | %-6d | %-5d%n\n\n",
                            book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getPublicationYear(), book.getQuantity());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nPress ENTER to return...");
        sc.nextLine();
    }
}
