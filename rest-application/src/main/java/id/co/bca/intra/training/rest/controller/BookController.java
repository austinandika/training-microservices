package id.co.bca.intra.training.rest.controller;

import id.co.bca.intra.training.rest.dto.bookDTO.InsertBookRequestDTO;
import id.co.bca.intra.training.rest.dto.bookDTO.UpdateBookRequestDTO;
import id.co.bca.intra.training.rest.entity.Book;
import id.co.bca.intra.training.rest.responseEntity.CustomResponseEntity;
import id.co.bca.intra.training.rest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    private ResponseEntity<Object> getAllBooks() {
        return CustomResponseEntity.generateResponse(HttpStatus.OK, true, "", bookService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            return CustomResponseEntity.generateResponse(HttpStatus.BAD_REQUEST, false, "Book with id: " + id + " not found", null);
        }

        return CustomResponseEntity.generateResponse(HttpStatus.OK, true, "", book);
    }

    @PostMapping
    private ResponseEntity<Object> insertNewBook(@RequestBody InsertBookRequestDTO request) {
        if (request.getPublishYear() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Book book = new Book(request.getId(), request.getTitle(), request.getAuthor(), request.getPublishYear());

        Book result = bookService.saveIfNotExists(book);
        if (result == null) {
            return CustomResponseEntity.generateResponse(HttpStatus.BAD_REQUEST, false, "Book with id: " + request.getId() + " already exists", null);
        }

        return CustomResponseEntity.generateResponse(HttpStatus.CREATED, true, "", result);
    }

    @PutMapping
    private ResponseEntity<Object> updateBook(@RequestBody UpdateBookRequestDTO request) {
        Book book = bookService.getById(request.getId());
        if (book == null) {
            return CustomResponseEntity.generateResponse(HttpStatus.BAD_REQUEST, false, "Book with id: " + request.getId() + " not found", null);
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

        return CustomResponseEntity.generateResponse(HttpStatus.OK, true, "", bookService.save(book));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        bookService.remove(book);

        return CustomResponseEntity.generateResponse(HttpStatus.OK, true, "", null);
    }
}
