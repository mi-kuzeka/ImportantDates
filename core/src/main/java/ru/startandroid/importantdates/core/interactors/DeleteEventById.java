package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventRepository;

public class DeleteEventById {
    private final EventRepository eventRepository;

    public DeleteEventById(EventRepository repository) {
        this.eventRepository = repository;
    }

    /**
     * Remove the event by ID
     *
     * @param id ID of the event
     */
    public void invoke(long id) {
        eventRepository.deleteEventById(id);
    }
}
