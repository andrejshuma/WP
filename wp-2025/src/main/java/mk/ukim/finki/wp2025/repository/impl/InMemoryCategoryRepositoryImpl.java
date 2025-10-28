package mk.ukim.finki.wp2025.repository.impl;

import mk.ukim.finki.wp2025.bootstrap.DataHolder;
import mk.ukim.finki.wp2025.model.Category;
import mk.ukim.finki.wp2025.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository for handling the in-memory storage of categories
@Repository
public class InMemoryCategoryRepositoryImpl implements CategoryRepository {
    @Override
    public Category save(Category category) {
        // Remove any existing category with the same name before adding (prevents duplicates)
        // Note: We're using names as identifiers for simplicity
        // In later lectures you'll learn why unique IDs are the better approach!
        DataHolder.categories.removeIf(c -> c.getName().equals(category.getName()));

        DataHolder.categories.add(category);

        return category;
    }

    @Override
    public List<Category> findAll() {
        return DataHolder.categories;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return DataHolder.categories.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Category> search(String text) {
        return DataHolder.categories.stream()
                .filter(c -> c.getName().contains(text) ||
                        c.getDescription().contains(text))
                .toList();
    }

    @Override
    public void delete(String name) {
        DataHolder.categories.removeIf(c -> c.getName().equals(name));
    }
}

