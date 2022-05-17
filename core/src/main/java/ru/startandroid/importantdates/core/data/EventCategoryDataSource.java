package ru.startandroid.importantdates.core.data;

import java.util.List;

import ru.startandroid.importantdates.core.domain.EventCategory;

public interface EventCategoryDataSource {
    /**
     * Add new event category into the database
     *
     * @param eventCategory {@link EventCategory} object
     */
    public void add(EventCategory eventCategory);

    /**
     * Get all event categories from the database
     *
     * @return list of {@link EventCategory}s
     */
    public List<EventCategory> readAll();

    /**
     * Get the event category by ID
     *
     * @return {@link EventCategory} object
     */
    public EventCategory readById(long id);

    /**
     * Get the event category by its name
     *
     * @return {@link EventCategory} object
     */
    public EventCategory readByName(String name);

    /**
     * Update existing event category in the database
     *
     * @param eventCategory {@link EventCategory} object
     */
    public void update(EventCategory eventCategory);

    /**
     * Remove the event category by ID
     *
     * @param id ID of the event category
     */
    public void removeById(long id);
}
