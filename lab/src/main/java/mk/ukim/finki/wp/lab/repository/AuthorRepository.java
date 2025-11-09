package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Author;

import java.util.List;

public interface AuthorRepository {
    public List<Author> findAll();
}
