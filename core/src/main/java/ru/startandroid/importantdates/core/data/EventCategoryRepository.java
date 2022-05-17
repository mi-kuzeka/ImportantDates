package ru.startandroid.importantdates.core.data;

import java.util.List;

import ru.startandroid.importantdates.core.domain.EventCategory;

public class EventCategoryRepository {
    private final EventCategoryDataSource eventCategoryDataSource;

    public EventCategoryRepository(EventCategoryDataSource dataSource) {
        this.eventCategoryDataSource = dataSource;
    }

    /**
     * Add new event category into the database
     *
     * @param eventCategory {@link EventCategory} object
     */
    public void addEventCategory(EventCategory eventCategory) {
        eventCategoryDataSource.add(eventCategory);
    }

    /**
     * Get all event categories from the database
     *
     * @return list of {@link EventCategory}s
     */
    public List<EventCategory> getAllEventCategories() {
        return eventCategoryDataSource.readAll();
    }

    /**
     * Get the event category by ID
     *
     * @return {@link EventCategory} object
     */
    public EventCategory getEventCategoryById(long id) {
        return eventCategoryDataSource.readById(id);
    }

    /**
     * Get the event category by its name
     *
     * @return {@link EventCategory} object
     */
    public EventCategory getEventCategoryByName(String name) {
        return eventCategoryDataSource.readByName(name);
    }

    /**
     * Update existing event category in the database
     *
     * @param eventCategory {@link EventCategory} object
     */
    public void updateEventCategory(EventCategory eventCategory) {
        eventCategoryDataSource.update(eventCategory);
    }

    /**
     * Remove the event category by ID
     *
     * @param id ID of the event category
     */
    public void deleteEventCategoryById(long id) {
        eventCategoryDataSource.removeById(id);
    }
}
