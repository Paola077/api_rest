package org.factoriaf5.apiRest.books;

import java.util.*;

public class InMemoryBookRepository implements BooksRepository{
    private final static List<Book> booksDB = new ArrayList<>();

    public InMemoryBookRepository() {
        booksDB.add(new Book("A123", "title 1", "Author 1"));
        booksDB.add(new Book("A124", "title 2", "Author 2"));
    }

    @Override
    public List<Book> findAll() {
        return Collections.unmodifiableList(booksDB);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {

        for (Book book : booksDB) {
            if(book.getIsbn().equals(isbn)) {
                return Optional.of(book);
            }
        }

        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        findByIsbn(book.getIsbn()).ifPresentOrElse(
                existBook -> {
                    existBook.setTitle(book.getTitle());
                    existBook.setAuthor(book.getAuthor());
                }, () -> {
                    booksDB.add(book);
                }
        );
    }

    @Override
    public void deleteByIsbn(String isbn) {
        booksDB.removeIf(book -> book.getIsbn().equals(isbn));
    }
}
