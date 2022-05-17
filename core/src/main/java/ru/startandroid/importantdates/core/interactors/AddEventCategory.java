package ru.startandroid.importantdates.core.interactors;

import ru.startandroid.importantdates.core.data.EventCategoryRepository;
import ru.startandroid.importantdates.core.domain.EventCategory;

public class AddEventCategory {
    private final EventCategoryRepository eventCategoryRepository;

    public AddEventCategory(EventCategoryRepository repository) {
        eventCategoryRepository = repository;
    }

    /**
     * Add new event category into the database
     *
     * @param eventCategory {@link EventCategory} object
     */
    public void invoke(EventCategory eventCategory) {
        eventCategoryRepository.addEventCategory(eventCategory);
    }
}
