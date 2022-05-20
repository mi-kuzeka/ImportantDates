package ru.startandroid.importantdates.core.data;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Category;

public interface CategoryDataSource {
    /**
     * Add new event category into the database
     *
     * @param category {@link Category} object
     */
    public void add(Category category);

    /**
     * Get all event categories from the database
     *
     * @return list of {@link Category}s
     */
    public List<Category> readAll();

    /**
     * Get the event category by ID
     *
     * @return {@link Category} object
     */
    public Category readById(int id);

    /**
     * Get the event category by its name
     *
     * @return {@link Category} object
     */
    public Category readByName(String name);

    /**
     * Update existing event category in the database
     *
     * @param category {@link Category} object
     */
    public void update(Category category);

    /**
     * Remove the event category
     *
     * @param category {@link Category} object
     */
    public void remove(Category category);
}
