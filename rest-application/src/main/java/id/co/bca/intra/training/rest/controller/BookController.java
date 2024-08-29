package id.co.bca.intra.training.rest.controller;

import id.co.bca.intra.training.rest.dto.bookDTO.InsertBookRequestDTO;
import id.co.bca.intra.training.rest.dto.bookDTO.UpdateBookRequestDTO;
import id.co.bca.intra.training.rest.entity.Book;
import id.co.bca.intra.training.rest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    private ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id: " + id + " not found");
        }

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Book> insertNewBook(@RequestBody InsertBookRequestDTO request) {
        if (request.getPublishYear() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Book book = new Book(request.getId(), request.getTitle(), request.getAuthor(), request.getPublishYear());

        Book result = bookService.saveIfNotExists(book);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<Book> updateBook(@RequestBody UpdateBookRequestDTO request) {
        Book book = bookService.getById(request.getId());
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity<>(bookService.save(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        bookService.remove(book);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
