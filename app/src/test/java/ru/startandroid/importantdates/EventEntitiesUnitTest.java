package ru.startandroid.importantdates;

import org.junit.Test;

import static org.junit.Assert.*;

import ru.startandroid.importantdates.entity.Event;
import ru.startandroid.importantdates.entity.EventCategory;
import ru.startandroid.importantdates.entity.EventDate;

/**
 * Event entities local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EventEntitiesUnitTest {
    @Test
    public void givenEventDate04281993_whenGetAgeIs29_thenIsTrue() {
        EventDate eventDate = new EventDate(4, 28, 1993);
        assertEquals(29, eventDate.getAge(2022));
    }

    @Test
    public void givenEventDate0428_whenGetAgeIs29_thenIsFalse() {
        EventDate eventDate = new EventDate(4, 28);
        assertNotEquals(29, eventDate.getAge(2022));
    }

    @Test
    public void givenCategoryId1NameBirthday_whenGetNameIsBirthday_thenIsTrue() {
        String categoryName = "Birthday";
        EventCategory eventCategory = new EventCategory(1, categoryName);
        assertEquals(categoryName, eventCategory.getName());
    }

    @Test
    public void givenCategoryId1NameBirthday_whenGetIdIs2_thenIsFalse() {
        EventCategory eventCategory = new EventCategory(1, "Birthday");
        assertNotEquals(2, eventCategory.getId());
    }

    @Test
    public void givenEventDate05171992_whenGetAgeIs30_thenIsTrue() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        EventCategory eventCategory = new EventCategory(1, "Birthday");
        Event event = new Event(1,"Jack", eventDate, eventCategory, "");
        assertEquals(30, event.getAge(2022));
    }
}