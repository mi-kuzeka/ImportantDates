package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.domain.Category;

public class GetCategoryById {
    private final CategoryRepository categoryRepository;

    public GetCategoryById(CategoryRepository repository) {
        categoryRepository = repository;
    }

    /**
     * Get the event category by ID
     *
     * @return {@link Category} object
     */
    public Category invoke(int id) {
        return categoryRepository.getCategoryById(id);
    }
}
