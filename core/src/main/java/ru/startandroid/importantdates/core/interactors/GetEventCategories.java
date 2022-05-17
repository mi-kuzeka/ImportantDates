package ru.startandroid.importantdates.core.interactors;

import java.util.List;

import ru.startandroid.importantdates.core.data.EventCategoryRepository;
import ru.startandroid.importantdates.core.domain.EventCategory;

public class GetEventCategories {
    private final EventCategoryRepository eventCategoryRepository;

    public GetEventCategories(EventCategoryRepository repository) {
        eventCategoryRepository = repository;
    }

    /**
     * Get all event categories from the database
     *
     * @return list of {@link EventCategory}s
     */
    public List<EventCategory> invoke() {
        return eventCategoryRepository.getAllEventCategories();
    }
}
