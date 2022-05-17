package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventCategoryRepository;
import ru.startandroid.importantdates.core.domain.EventCategory;

public class GetEventCategoryById {
    private final EventCategoryRepository eventCategoryRepository;

    public GetEventCategoryById(EventCategoryRepository repository) {
        eventCategoryRepository = repository;
    }

    /**
     * Get the event category by ID
     *
     * @return {@link EventCategory} object
     */
    public EventCategory invoke(long id) {
        return eventCategoryRepository.getEventCategoryById(id);
    }
}
