package ru.startandroid.importantdates.core.domain;

public class EventCategory {
    private final int id;
    private final String name;

    /**
     * Construct object that shows which category the Event belong to
     *
     * @param id   category ID
     * @param name category name
     */
    public EventCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get event category ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get name of the event category
     */
    public String getName() {
        return this.name;
    }
}
