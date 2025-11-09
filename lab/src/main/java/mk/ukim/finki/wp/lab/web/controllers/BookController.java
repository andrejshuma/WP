package mk.ukim.finki.wp.lab.web.controllers;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.AuthorService;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }
    @GetMapping("/books")
    public String getBooksPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        List<Book> books = bookService.listAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        try {
            Book book = bookService.findById(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.findAll());
            return "book-form";
        } catch (IllegalArgumentException e) {
            return "redirect:/books?error=BookNotFound";
        }
    }

    @PostMapping("/books/save")
    public String saveBook(@RequestParam(required = false) Long id,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam double averageRating,
                           @RequestParam Long authorId, Model model) {
        Author author = authorService.findAll().stream()
                .filter(a -> a.getId().equals(authorId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        Book book;
        if (id != null) {
            // Update existing book
            book = bookService.findById(id);
            book.setTitle(title);
            book.setGenre(genre);
            book.setAverageRating(averageRating);
            book.setAuthor(author);
        } else {
            // Add new book
            book = new Book(title, genre, averageRating, author);
            bookService.save(book); // Ensure the new book is saved
        }
        return "redirect:/books";
    }
    @GetMapping("/books/book-form/{id}")
    public String getEditBookForm(@PathVariable Long id, Model model) {
        try {
            Book book = bookService.findById(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.findAll());
            return "book-form";
        } catch (IllegalArgumentException e) {
            return "redirect:/books?error=BookNotFound";
        }
    }

    @GetMapping("/books/book-form")
    public String getAddBookPage(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

}
