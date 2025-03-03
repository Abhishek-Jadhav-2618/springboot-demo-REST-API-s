package PracticeAssignmentDC1.demo.Repository;

import PracticeAssignmentDC1.demo.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
