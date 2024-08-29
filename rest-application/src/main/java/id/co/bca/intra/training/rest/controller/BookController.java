package id.co.bca.intra.training.rest.controller;

import id.co.bca.intra.training.rest.dto.bookDTO.InsertBookRequestDTO;
import id.co.bca.intra.training.rest.dto.bookDTO.UpdateBookRequestDTO;
import id.co.bca.intra.training.rest.entity.Book;
import id.co.bca.intra.training.rest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Book getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id: " + id + " not found");
        }

        return book;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Book insertNewBook(@RequestBody InsertBookRequestDTO request) {
        if (request.getPublishYear() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publish year should contain positive integer");
        }

        Book book = new Book(request.getId(), request.getTitle(), request.getAuthor(), request.getPublishYear());

        Book result = bookService.saveIfNotExists(book);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with id: " + book.getId() + " already exists");
        }

        return result;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    private Book updateBook(@RequestBody UpdateBookRequestDTO request) {
        Book book = bookService.getById(request.getId());
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id: " + request.getId() + " not found");
        }

        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }

        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }

        if (request.getPublishYear() != null) {
            book.setPublishYear(request.getPublishYear());
        }

        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id: " + id + " not found");
        }

        bookService.remove(book);
    }
}
