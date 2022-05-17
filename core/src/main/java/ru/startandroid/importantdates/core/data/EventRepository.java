package ru.startandroid.importantdates.core.data;

import java.util.List;

import ru.startandroid.importantdates.core.domain.Event;

public class EventRepository {
    private final EventDataSource eventDataSource;

    public EventRepository(EventDataSource dataSource) {
        this.eventDataSource = dataSource;
    }

    /**
     * Add new event into the database
     *
     * @param event {@link Event} object
     */
    public void addEvent(Event event) {
        eventDataSource.add(event);
    }

    /**
     * Get all events from the database
     *
     * @return list of {@link Event}s
     */
    public List<Event> getEvents() {
        return eventDataSource.readAll();
    }

    /**
     * Get events from the database by month
     *
     * @param month month number
     * @return list of {@link Event}s for this month
     */
    public List<Event> getEventsByMonth(int month) {
        return eventDataSource.readByMonth(month);
    }

    /**
     * Get the event by ID
     *
     * @return {@link Event} object
     */
    public Event getEventById(long id) {
        return eventDataSource.readById(id);
    }

    /**
     * Update existing event in the database
     *
     * @param event {@link Event} object
     */
    public void updateEvent(Event event) {
        eventDataSource.update(event);
    }

    /**
     * Remove the event by ID
     *
     * @param id ID of the event
     */
    public void deleteEventById(long id) {
        eventDataSource.removeById(id);
    }
}
