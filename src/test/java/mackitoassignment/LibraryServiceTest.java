package mackitoassignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepositoryMock;

    @InjectMocks
    private LibraryService libraryService;

    public LibraryServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testBorrowBook_Success() {
        // Arrange: Mock a book that is available
        Book book = new Book(1L, "The Great Gatsby", true);
        when(bookRepositoryMock.findById(1L)).thenReturn(book);

        // Act: Borrow the book
        String result = libraryService.borrowBook(1L);

        // Assert: Verify the book's availability is updated and the correct message is returned
        assertEquals("Book borrowed successfully.", result);
        verify(bookRepositoryMock).save(book);
        assertFalse(book.isAvailable());
    }

    @Test
    public void testBorrowBook_BookAlreadyBorrowed() {
        // Arrange: Mock a book that is already borrowed
        Book book = new Book(1L, "1984", false);
        when(bookRepositoryMock.findById(1L)).thenReturn(book);

        // Act: Try to borrow the already borrowed book
        String result = libraryService.borrowBook(1L);

        // Assert: Verify the correct message is returned
        assertEquals("Book is already borrowed.", result);
        verify(bookRepositoryMock, never()).save(book); // Ensure save is not called
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        // Arrange: Return null when the book is not found
        when(bookRepositoryMock.findById(1L)).thenReturn(null);

        // Act: Try to borrow a non-existing book
        String result = libraryService.borrowBook(1L);

        // Assert: Verify the correct message is returned
        assertEquals("Book not found.", result);
        verify(bookRepositoryMock, never()).save(any(Book.class)); // Ensure save is not called
    }

    @Test
    public void testReturnBook_Success() {
        // Arrange: Mock a book that is borrowed
        Book book = new Book(1L, "The Catcher in the Rye", false);
        when(bookRepositoryMock.findById(1L)).thenReturn(book);

        // Act: Return the borrowed book
        String result = libraryService.returnBook(1L);

        // Assert: Verify the book's availability is updated and the correct message is returned
        assertEquals("Book returned successfully.", result);
        verify(bookRepositoryMock).save(book);
        assertTrue(book.isAvailable());
    }

    @Test
    public void testReturnBook_BookAlreadyAvailable() {
        // Arrange: Mock a book that is already available
        Book book = new Book(1L, "Moby Dick", true);
        when(bookRepositoryMock.findById(1L)).thenReturn(book);

        // Act: Try to return an already available book
        String result = libraryService.returnBook(1L);

        // Assert: Verify the correct message is returned
        assertEquals("Book is already available.", result);
        verify(bookRepositoryMock, never()).save(book); // Ensure save is not called
    }

    @Test
    public void testReturnBook_BookNotFound() {
        // Arrange: Return null when the book is not found
        when(bookRepositoryMock.findById(1L)).thenReturn(null);

        // Act: Try to return a non-existing book
        String result = libraryService.returnBook(1L);

        // Assert: Verify the correct message is returned
        assertEquals("Book not found.", result);
        verify(bookRepositoryMock, never()).save(any(Book.class)); // Ensure save is not called
    }
}
