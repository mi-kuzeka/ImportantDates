package ru.startandroid.importantdates.core.data;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Category;

public class CategoryRepository {
    private final CategoryDataSource categoryDataSource;

    public CategoryRepository(CategoryDataSource dataSource) {
        this.categoryDataSource = dataSource;
    }

    /**
     * Add new event category into the database
     *
     * @param category {@link Category} object
     */
    public void addCategory(Category category) {
        categoryDataSource.add(category);
    }

    /**
     * Get all event categories from the database
     *
     * @return list of {@link Category}s
     */
    public List<Category> getAllCategories() {
        return categoryDataSource.readAll();
    }

    /**
     * Get the event category by ID
     *
     * @return {@link Category} object
     */
    public Category getCategoryById(int id) {
        return categoryDataSource.readById(id);
    }

    /**
     * Get the event category by its name
     *
     * @return {@link Category} object
     */
    public Category getCategoryByName(String name) {
        return categoryDataSource.readByName(name);
    }

    /**
     * Update existing event category in the database
     *
     * @param category {@link Category} object
     */
    public void updateCategory(Category category) {
        categoryDataSource.update(category);
    }

    /**
     * Remove the event category
     *
     * @param category {@link Category} object
     */
    public void deleteCategory(Category category) {
        categoryDataSource.remove(category);
    }
}
