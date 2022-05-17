package ru.startandroid.importantdates.core.interactors;

import java.util.List;

import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.domain.Event;

public class GetEventsByMonth {
    private final EventRepository eventRepository;

    public GetEventsByMonth(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Get events from the database by month
     *
     * @param month month number
     * @return list of {@link Event}s for this month
     */
    public List<Event> invoke(int month) {
        return eventRepository.getEventsByMonth(month);
    }
}
