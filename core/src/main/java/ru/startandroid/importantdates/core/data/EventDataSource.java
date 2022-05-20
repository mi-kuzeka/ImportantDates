package ru.startandroid.importantdates.core.data;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Event;

public interface EventDataSource {
    /**
     * Add new event into the database
     *
     * @param event {@link Event} object
     */
    public void add(Event event);

    /**
     * Get all events from the database
     *
     * @return list of {@link Event}s
     */
    public List<Event> readAll();

    /**
     * Get events from the database by month
     *
     * @param month month number
     * @return list of {@link Event}s for this month
     */
    public List<Event> readByMonth(int month);

    /**
     * Get the event by ID
     *
     * @return {@link Event} object
     */
    public Event readById(int id);

    /**
     * Update existing event in the database
     *
     * @param event {@link Event} object
     */
    public void update(Event event);

    /**
     * Remove the event
     *
     * @param event {@link Event} object
     */
    public void remove(Event event);
}
