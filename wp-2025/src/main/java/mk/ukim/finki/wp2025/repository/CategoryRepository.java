package mk.ukim.finki.wp2025.repository;

import mk.ukim.finki.wp2025.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findByName(String name);

    List<Category> search(String text);

    void delete(String name);
}
