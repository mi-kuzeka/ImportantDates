package ru.startandroid.importantdates.framework;

import ru.startandroid.importantdates.core.interactors.*;

public final class Interactors {
    private final AddCategory addCategory;
    private final AddEvent addEvent;
    private final DeleteCategory deleteCategory;
    private final DeleteEvent deleteEvent;
    private final GetCategories getCategories;
    private final GetCategoryById getCategoryById;
    private final GetCategoryByName getCategoryByName;
    private final GetEventById getEventById;
    private final GetEvents getEvents;
    private final GetEventsByMonth getEventsByMonth;
    private final UpdateCategory updateCategory;
    private final UpdateEvent updateEvent;

    public Interactors(AddCategory addCategory,
                       AddEvent addEvent,
                       DeleteCategory deleteCategory,
                       DeleteEvent deleteEvent,
                       GetCategories getCategories,
                       GetCategoryById getCategoryById,
                       GetCategoryByName getCategoryByName,
                       GetEventById getEventById,
                       GetEvents getEvents,
                       GetEventsByMonth getEventsByMonth,
                       UpdateCategory updateCategory,
                       UpdateEvent updateEvent) {
        this.addCategory = addCategory;
        this.addEvent = addEvent;
        this.deleteCategory = deleteCategory;
        this.deleteEvent = deleteEvent;
        this.getCategories = getCategories;
        this.getCategoryById = getCategoryById;
        this.getCategoryByName = getCategoryByName;
        this.getEventById = getEventById;
        this.getEvents = getEvents;
        this.getEventsByMonth = getEventsByMonth;
        this.updateCategory = updateCategory;
        this.updateEvent = updateEvent;
    }

    public AddCategory getAddCategory() {
        return addCategory;
    }

    public AddEvent getAddEvent() {
        return addEvent;
    }

    public DeleteCategory getDeleteCategory() {
        return deleteCategory;
    }

    public DeleteEvent getDeleteEvent() {
        return deleteEvent;
    }

    public GetCategories getGetCategories() {
        return getCategories;
    }

    public GetCategoryById getGetCategoryById() {
        return getCategoryById;
    }

    public GetCategoryByName getGetCategoryByName() {
        return getCategoryByName;
    }

    public GetEventById getGetEventById() {
        return getEventById;
    }

    public GetEvents getGetEvents() {
        return getEvents;
    }

    public GetEventsByMonth getGetEventsByMonth() {
        return getEventsByMonth;
    }

    public UpdateCategory getUpdateCategory() {
        return updateCategory;
    }

    public UpdateEvent getUpdateEvent() {
        return updateEvent;
    }
}
