package ru.startandroid.importantdates.core.interactors;

import java.util.List;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.domain.Category;

public class GetCategories {
    private final CategoryRepository categoryRepository;

    public GetCategories(CategoryRepository repository) {
        categoryRepository = repository;
    }

    /**
     * Get all event categories from the database
     *
     * @return list of {@link Category}s
     */
    public List<Category> invoke() {
        return categoryRepository.getAllCategories();
    }
}
