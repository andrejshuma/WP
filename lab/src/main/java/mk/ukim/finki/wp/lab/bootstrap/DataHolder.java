package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Book>books=null;
    public static List<BookReservation>reservations=null;

    public static List<Author>authors=null;

    @PostConstruct
    public void init(){

        authors=new ArrayList<>();
        authors.add(new Author("George","Orwell","British","1903-06-25"));
        authors.add(new Author("Aldous","Huxley","British","1894-07-26"));
        authors.add(new Author("Stephen","King","American","1947-09-21"));

        books = new ArrayList<>();
        books.add(new Book("Dune", "Science Fiction", 4.6,authors.get(0)));
        books.add(new Book("Brave New World", "Dystopian", 4.5,authors.get(1)));
        books.add(new Book("The Shining", "Horror", 4.7,authors.get(2)));
        books.add(new Book("Moby Dick", "Adventure / Classic", 4.3, authors.get(0)));
        books.add(new Book("War and Peace", "Historical Fiction", 4.8, authors.get(1)));
        books.add(new Book("The Road", "Post-Apocalyptic", 4.4, authors.get(2)));
        books.add(new Book("Jane Eyre", "Romance / Classic", 4.6, authors.get(0)));
        books.add(new Book("The Name of the Wind", "Fantasy", 4.8, authors.get(1)));
        books.add(new Book("Gone Girl", "Mystery / Thriller", 4.5, authors.get(2)));
        books.add(new Book("The Kite Runner", "Drama", 4.9, authors.get(0)));


        reservations=new ArrayList<>();
    }

}
