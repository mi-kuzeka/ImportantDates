package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.domain.Event;

public class UpdateEvent {
    private final EventRepository eventRepository;

    public UpdateEvent(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Update existing event in the database
     *
     * @param event {@link Event} object
     */
    public void invoke(Event event) {
        eventRepository.updateEvent(event);
    }
}
