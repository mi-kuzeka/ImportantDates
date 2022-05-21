package ru.startandroid.importantdates.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EventUnitTest {
    @Test
    public void givenEventDate04281993_whenGetDayIs28_thenIsTrue() {
        EventDate eventDate = new EventDate(4, 28, 1993);
        Category category = new Category(1, "Birthday");
        Event event = new Event(2, "Henry", eventDate, category, "");
        assertEquals(28, event.getDay());
    }

    @Test
    public void givenEventDate04281993_whenGetDayIs4_thenIsFalse() {
        EventDate eventDate = new EventDate(4, 28, 1993);
        Category category = new Category(1, "Birthday");
        Event event = new Event(3, "Frodo", eventDate, category, "");
        assertNotEquals(4, event.getDay());
    }

    @Test
    public void givenEventNameSam_whenGetNameIsSam_thenIsTrue() {
        EventDate eventDate = new EventDate(4, 28);
        Category category = new Category(1, "Birthday");
        Event event = new Event(4, "Sam", eventDate, category, "");
        assertEquals("Sam", event.getName());
    }

    @Test
    public void givenEventNameJane_whenGetNameIsEmpty_thenIsFalse() {
        EventDate eventDate = new EventDate(4, 28);
        Category category = new Category(1, "Birthday");
        Event event = new Event(5, "Jane", eventDate, category, "");
        assertNotEquals("", event.getName());
    }

    @Test
    public void givenEventNotesMyHusband_whenGetNotesIsMyHusband_thenIsTrue() {
        EventDate eventDate = new EventDate(1, 1);
        Category category = new Category(1, "Birthday");
        Event event = new Event(6, "Kate", eventDate, category, "My husband");
        assertEquals("My husband", event.getNotes());
    }

    @Test
    public void givenEventNotesMyWife_whenGetNotesIsEmpty_thenIsFalse() {
        EventDate eventDate = new EventDate(1, 1);
        Category category = new Category(1, "Birthday");
        Event event = new Event(7, "Jack", eventDate, category, "My wife");
        assertNotEquals("", event.getNotes());
    }

    @Test
    public void givenEventId8_whenGetIdIs8_thenIsTrue() {
        EventDate eventDate = new EventDate(1, 1);
        Category category = new Category(1, "Birthday");
        Event event = new Event(8, "Leo", eventDate, category, "");
        assertEquals(8, event.getId());
    }

    @Test
    public void givenEventId9_whenGetIdIs8_thenIsFalse() {
        EventDate eventDate = new EventDate(1, 1);
        Category category = new Category(1, "Birthday");
        Event event = new Event(9, "Nancy", eventDate, category, "");
        assertNotEquals(8, event.getId());
    }
}
