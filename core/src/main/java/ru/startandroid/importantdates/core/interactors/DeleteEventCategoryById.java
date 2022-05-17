package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventCategoryRepository;

public class DeleteEventCategoryById {
    private final EventCategoryRepository eventCategoryRepository;

    public DeleteEventCategoryById(EventCategoryRepository repository) {
        eventCategoryRepository = repository;
    }

    /**
     * Remove the event category by ID
     *
     * @param id ID of the event category
     */
    public void invoke(long id) {
        eventCategoryRepository.deleteEventCategoryById(id);
    }
}
