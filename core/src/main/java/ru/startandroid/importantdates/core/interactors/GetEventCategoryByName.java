package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventCategoryRepository;
import ru.startandroid.importantdates.core.domain.EventCategory;

public class GetEventCategoryByName {
    private final EventCategoryRepository eventCategoryRepository;

    public GetEventCategoryByName(EventCategoryRepository repository) {
        eventCategoryRepository = repository;
    }

    /**
     * Get the event category by its name
     *
     * @return {@link EventCategory} object
     */
    public EventCategory invoke(String name) {
        return eventCategoryRepository.getEventCategoryByName(name);
    }
}
