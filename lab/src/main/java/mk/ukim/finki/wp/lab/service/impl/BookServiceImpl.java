package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String text, Double rating) {
        if(text==null||text.isEmpty()||rating==null){
            throw new IllegalArgumentException();
        }
        return bookRepository.searchBooks(text,rating);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findAll().stream().filter(b->b.getId().equals(id)).findFirst().orElseThrow(()->new IllegalArgumentException("Book with given id not found"));
    }

    @Override
    public List<Author> listAllAuthors() {
        return (List<Author>) bookRepository.findAll().stream().map(Book::getAuthor).toList();
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }
}
