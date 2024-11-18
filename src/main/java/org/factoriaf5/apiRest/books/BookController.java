package org.factoriaf5.apiRest.books;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BooksRepository booksRepository;

    public BookController() {
        this.booksRepository = new InMemoryBookRepository();
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return this.booksRepository.findAll();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalbook = booksRepository.findByIsbn(isbn);

        if (optionalbook.isPresent()) {
            return new ResponseEntity<>(optionalbook.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}