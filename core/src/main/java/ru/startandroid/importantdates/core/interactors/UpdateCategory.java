package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.domain.Category;

public class UpdateCategory {
    private final CategoryRepository categoryRepository;

    public UpdateCategory(CategoryRepository repository) {
        categoryRepository = repository;
    }

    /**
     * Update existing event category in the database
     *
     * @param category {@link Category} object
     */
    public void invoke(Category category) {
        categoryRepository.updateCategory(category);
    }
}
