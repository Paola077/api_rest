package org.factoriaf5.apiRest.books;

import org.springframework.http.HttpStatus;
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
        return booksRepository.findAll();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalbook = booksRepository.findByIsbn(isbn);

        if (optionalbook.isPresent()) {
            return new ResponseEntity<>(optionalbook.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Optional<Book> optionalBook =  booksRepository.findByIsbn(book.getIsbn());

        if (optionalBook.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        booksRepository.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBookByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalBook = booksRepository.findByIsbn(isbn);

        if (!optionalBook.isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        booksRepository.deleteByIsbn(isbn);
        new ResponseEntity<>(HttpStatus.OK);
    }

   @PutMapping("/{isbn}")
    public ResponseEntity<Book> updatebook(@PathVariable String isbn, @RequestBody Book book) {
        Optional<Book> optionalBook = booksRepository.findByIsbn(isbn);

        if (optionalBook.isPresent()) {

            Book bookExist = optionalBook.get();

            bookExist.setTitle(book.getTitle());
            bookExist.setAuthor(book.getAuthor());

            booksRepository.save(bookExist);
            return new ResponseEntity<>(HttpStatus.OK);
        }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
}
