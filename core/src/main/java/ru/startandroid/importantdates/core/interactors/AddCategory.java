package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.CategoryRepository;
import ru.startandroid.importantdates.core.domain.Category;

public class AddCategory {
    private final CategoryRepository categoryRepository;

    public AddCategory(CategoryRepository repository) {
        categoryRepository = repository;
    }

    /**
     * Add new event category into the database
     *
     * @param category {@link Category} object
     */
    public void invoke(Category category) {
        categoryRepository.addCategory(category);
    }
}
