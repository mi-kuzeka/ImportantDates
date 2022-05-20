package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.domain.Category;

public class DeleteCategory {
    private final CategoryRepository categoryRepository;

    public DeleteCategory(CategoryRepository repository) {
        categoryRepository = repository;
    }

    /**
     * Remove the event category
     *
     * @param category {@link Category} object
     */
    public void invoke(Category category) {
        categoryRepository.deleteCategory(category);
    }
}
