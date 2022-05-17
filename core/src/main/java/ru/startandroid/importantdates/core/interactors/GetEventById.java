package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.domain.Event;

public class GetEventById {
    private final EventRepository eventRepository;

    public GetEventById(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Get the event by ID
     *
     * @return {@link Event} object
     */
    public Event invoke(long id) {
        return eventRepository.getEventById(id);
    }
}
