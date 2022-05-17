package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventCategoryRepository;
import ru.startandroid.importantdates.core.domain.EventCategory;

public class UpdateEventCategory {
    private final EventCategoryRepository eventCategoryRepository;

    public UpdateEventCategory(EventCategoryRepository repository) {
        eventCategoryRepository = repository;
    }

    /**
     * Update existing event category in the database
     *
     * @param eventCategory {@link EventCategory} object
     */
    public void invoke(EventCategory eventCategory) {
        eventCategoryRepository.updateEventCategory(eventCategory);
    }
}
