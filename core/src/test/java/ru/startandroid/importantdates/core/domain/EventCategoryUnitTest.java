package ru.startandroid.importantdates.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EventCategoryUnitTest {
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
}
