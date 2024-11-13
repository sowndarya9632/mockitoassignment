package mackitoassignment;

//Book class to represent book information
 class Book {
 private Long id;
 private String title;
 private boolean available;

 public Book(Long id, String title, boolean available) {
     this.id = id;
     this.title = title;
     this.available = available;
 }

 public Long getId() { return id; }
 public String getTitle() { return title; }
 public boolean isAvailable() { return available; }
 public void setAvailable(boolean available) { this.available = available; }
}

//BookRepository interface
 interface BookRepository {
 Book findById(Long bookId);
 void save(Book book);
}

//LibraryService class to handle borrowing and returning books
public class LibraryService {
 private final BookRepository bookRepository;

 public LibraryService(BookRepository bookRepository) {
     this.bookRepository = bookRepository;
 }

 // Method to borrow a book
 public String borrowBook(Long bookId) {
     Book book = bookRepository.findById(bookId);
     if (book == null) {
         return "Book not found.";
     }
     if (!book.isAvailable()) {
         return "Book is already borrowed.";
     }
     book.setAvailable(false); // Mark the book as borrowed
     bookRepository.save(book); // Save the updated book status
     return "Book borrowed successfully.";
 }

 // Method to return a book
 public String returnBook(Long bookId) {
     Book book = bookRepository.findById(bookId);
     if (book == null) {
         return "Book not found.";
     }
     if (book.isAvailable()) {
         return "Book is already available.";
     }
     book.setAvailable(true); // Mark the book as available again
     bookRepository.save(book); // Save the updated book status
     return "Book returned successfully.";
 }
}
