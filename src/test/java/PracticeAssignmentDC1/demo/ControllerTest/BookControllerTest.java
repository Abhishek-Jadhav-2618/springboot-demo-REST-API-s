package PracticeAssignmentDC1.demo.ControllerTest;
import PracticeAssignmentDC1.demo.Controller.BookController;
import PracticeAssignmentDC1.demo.Model.Book;
import PracticeAssignmentDC1.demo.Repository.BookRepository;
import PracticeAssignmentDC1.demo.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookControllerTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_get_all(){
        Book b1 = new Book();
        b1.setId(1L);
        b1.setAuthor("Abhishek");
        b1.setBookName("Ramayan");

        Book b2 = new Book();
        b2.setId(2L);
        b2.setBookName("Mahabharat");
        b2.setAuthor("Nitin");

        List<Book> books = new ArrayList<>();
        books.add(b1);
        books.add(b2);

        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> result = bookController.getAllBooks();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(books, result.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void test_get_book_by_id(){
        Long id = 1L;
        Book b1 = new Book();
        b1.setId(id);
        b1.setBookName("Ramayana");
        b1.setAuthor("Abhishek");

        when(bookService.getBookById(id)).thenReturn(Optional.of(b1));

        ResponseEntity<Book> result = bookController.getBookById(id);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(b1, result.getBody());
        verify(bookService, times(1)).getBookById(id);
    }

    @Test
    public void test_get_book_by_id_not_found(){
        Long id = 1L;

        when(bookService.getBookById(id)).thenReturn(Optional.empty());

        ResponseEntity<Book> result = bookController.getBookById(id);
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(bookService, times(1)).getBookById(id);
    }

    @Test
    public void test_create_book(){
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setBookName("Ramayana");
        book.setAuthor("Abhishek");

        when(bookService.createBook(book)).thenReturn(book);
        ResponseEntity<Book> result = bookController.createBook(book);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(book, result.getBody());
        verify(bookService, times(1)).createBook(book);
    }

    @Test
    public void test_update_book(){
        Long id =2L;
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setBookName("Ramayan");
        existingBook.setAuthor("Abhi");

        Book updatedBook = new Book();
        updatedBook.setId(id);
        updatedBook.setBookName("Mahabharath");
        updatedBook.setAuthor("Sujan");

        when(bookService.updateBook(id,existingBook)).thenReturn(updatedBook);
        ResponseEntity<Book> result = bookController.updateBook(id, existingBook);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedBook, result.getBody());
        verify(bookService, times(1)).updateBook(id, existingBook);
    }

    @Test
    public void test_delete_book(){
        Long id =1L;
        Book book = new Book();
        book.setId(id);
        book.setAuthor("Abhishek");
        book.setBookName("Ramayan");

        doNothing().when(bookService).deleteBook(id);
        ResponseEntity<Void> result = bookController.deleteBookById(id);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(bookService, times(1)).deleteBook(id);
    }
}
