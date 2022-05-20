package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.domain.Category;

public class GetCategoryByName {
    private final CategoryRepository categoryRepository;

    public GetCategoryByName(CategoryRepository repository) {
        categoryRepository = repository;
    }

    /**
     * Get the event category by its name
     *
     * @return {@link Category} object
     */
    public Category invoke(String name) {
        return categoryRepository.getCategoryByName(name);
    }
}
