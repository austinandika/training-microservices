package id.co.bca.intra.training.rest.service;

import id.co.bca.intra.training.rest.entity.Book;
import id.co.bca.intra.training.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void remove(Book book) {
        bookRepository.delete(book);
    }

    public void remove(Long id) {
        bookRepository.deleteById(id);
    }
}
