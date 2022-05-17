package ru.startandroid.importantdates.core.interactors;

import java.util.List;

import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.domain.Event;

public class GetEvents {
    private final EventRepository eventRepository;

    public GetEvents(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Get all events from the database
     *
     * @return list of {@link Event}s
     */
    public List<Event> invoke() {
        return eventRepository.getEvents();
    }
}
