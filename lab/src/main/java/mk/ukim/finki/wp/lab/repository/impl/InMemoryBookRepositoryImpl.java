package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryBookRepositoryImpl implements BookRepository {

    @Override
    public List<Book> findAll() {
        return DataHolder.books;
    }

    @Override
    public List<Book> searchBooks(String text, Double rating) {
        return DataHolder.books.stream().filter(b->b.getTitle().contains(text)&&b.getAverageRating()>=rating).toList();
    }

    @Override
    public void deleteById(Long id) {
        DataHolder.books.removeIf(b->b.getId().equals(id));
    }

    @Override
    public void save(Book book) {
        DataHolder.books.removeIf(b->b.getId().equals(book.getId()));
        DataHolder.books.add(book);
    }
}
