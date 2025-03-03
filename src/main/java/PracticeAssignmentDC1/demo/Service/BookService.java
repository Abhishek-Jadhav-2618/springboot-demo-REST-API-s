package PracticeAssignmentDC1.demo.Service;
import PracticeAssignmentDC1.demo.Model.Book;
import PracticeAssignmentDC1.demo.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private  BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }
    public Book createBook(Book book){
        return bookRepository.save(book);
    }
    public Book updateBook(Long id, Book bookDetails){
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException(("Book Not found")));
        existingBook.setId(bookDetails.getId());
        return bookRepository.save(existingBook);


    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
