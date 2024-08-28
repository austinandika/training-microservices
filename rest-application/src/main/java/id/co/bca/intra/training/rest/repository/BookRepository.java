package id.co.bca.intra.training.rest.repository;

import id.co.bca.intra.training.rest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { }
