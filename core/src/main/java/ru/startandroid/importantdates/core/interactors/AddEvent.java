package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.domain.Event;

public class AddEvent {
    private final EventRepository eventRepository;

    public AddEvent(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Add new event into the database
     *
     * @param event {@link Event} object
     */
    public void invoke(Event event) {
        eventRepository.addEvent(event);
    }
}
