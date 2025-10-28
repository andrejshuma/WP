package mk.ukim.finki.wp2025.service.impl;

import mk.ukim.finki.wp2025.model.Category;
import mk.ukim.finki.wp2025.repository.CategoryRepository;
import mk.ukim.finki.wp2025.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category create(String name, String description) {
        // Business rule: name and description cannot be null or empty
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = new Category(name, description);

        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(String name, String description) {
        // Business rule: name and description cannot be null or empty
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = new Category(name, description);

        return this.categoryRepository.save(category);
    }

    @Override
    public void delete(String name) {
        this.categoryRepository.delete(name);
    }

    @Override
    public List<Category> searchCategories(String text) {
        return this.categoryRepository.search(text);
    }
}
