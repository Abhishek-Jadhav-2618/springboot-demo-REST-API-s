package PracticeAssignmentDC1.demo.ServiceTest;

import PracticeAssignmentDC1.demo.Controller.BookController;
import PracticeAssignmentDC1.demo.Model.Book;
import PracticeAssignmentDC1.demo.Repository.BookRepository;
import PracticeAssignmentDC1.demo.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class BookServiceTest {
    @Mock
    private  BookController bookController;

    @InjectMocks
    private  BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_get_all_books(){
        Book book1 = new Book();
        book1.setId(1L);
        book1.setBookName("Ramayana");
        book1.setAuthor("Abhishek");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setBookName("Ramayana");
        book2.setAuthor("Abhishek");

        List<Book> bookList = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> mockBooks = bookService.getAllBooks();
        assertNotNull(bookList);

        assertEquals(2, bookList.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void test_get_book_by_id(){
        Book book1 = new Book();
        book1.setId(1L);
        book1.setBookName("Ramayana");
        book1.setAuthor("Abhishek");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book1));
        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ramayana", result.get().getBookName());
        assertEquals("Abhishek", result.get().getAuthor());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    public void test_create_book(){
        Book book1 = new Book();
        book1.setId(1L);
        book1.setBookName("Ramayana");
        book1.setAuthor("Abhishek");

        when(bookRepository.save(book1)).thenReturn(book1);

        Book bookCreated = bookService.createBook(book1);
        assertNotNull(bookCreated);
        assertEquals(book1.getId(), bookCreated.getId());
        assertEquals(book1.getBookName(), bookCreated.getBookName());
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    public void test_update_book(){
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setAuthor("Abhishek");

        Book updateBook = new Book();
        updateBook.setId(id);
        updateBook.setAuthor("Ankita");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(updateBook);
        Book result = bookService.updateBook(id, updateBook);

        assertEquals(updateBook.getAuthor(), result.getAuthor());
        assertEquals(updateBook.getId(), result.getId());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    public void test_create_delete_book(){
        Book book1 = new Book();
        book1.setId(1L);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

}
