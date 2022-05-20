package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventRepository;
import ru.startandroid.importantdates.core.domain.Event;

public class DeleteEvent {
    private final EventRepository eventRepository;

    public DeleteEvent(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Remove the event
     *
     * @param event {@link Event} object
     */
    public void invoke(Event event) {
        eventRepository.deleteEvent(event);
    }
}
