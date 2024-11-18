package org.factoriaf5.apiRest.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BooksRepository {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);
}
