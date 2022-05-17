package ru.startandroid.importantdates.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EventDateUnitTest {
    @Test
    public void givenEventDate0428_whenGetYearIs29_thenIsFalse() {
        EventDate eventDate = new EventDate(4, 28);
        assertNotEquals(29, eventDate.getYear());
    }

    @Test
    public void givenEventDate0428_whenGetYearIsMinus1_thenIsTrue() {
        EventDate eventDate = new EventDate(4, 28);
        assertEquals(-1, eventDate.getYear());
    }

    @Test
    public void givenEventDate05171992_whenGetDayIs17_thenIsTrue() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        assertEquals(17, eventDate.getDay());
    }

    @Test
    public void givenEventDate05171992_whenGetMonthIs5_thenIsTrue() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        assertEquals(5, eventDate.getMonth());
    }

    @Test
    public void givenEventDate05171992_whenGetYearIs1992_thenIsTrue() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        assertEquals(1992, eventDate.getYear());
    }

    @Test
    public void givenEventDate05171992_whenGetDayIs05_thenIsFalse() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        assertNotEquals(5, eventDate.getDay());
    }

    @Test
    public void givenEventDate05171992_whenGetMonthIs17_thenIsFalse() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        assertNotEquals(17, eventDate.getMonth());
    }

    @Test
    public void givenEventDate05171992_whenGetYearIsMinus1_thenIsFalse() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        assertNotEquals(-1, eventDate.getYear());
    }

    @Test
    public void givenEventDate05171992_whenFormattedDateIs05171992_thenIsTrue() {
        EventDate eventDate = new EventDate(5, 17, 1992);
        String datePattern = "MM/dd/yyyy";
        assertEquals("05/17/1992", eventDate.getFormattedDate(datePattern));
    }

    @Test
    public void givenEventDate0517_whenFormattedDateIs0517_thenIsTrue() {
        EventDate eventDate = new EventDate(5, 17);
        String datePattern = "MM/dd";
        assertEquals("05/17", eventDate.getFormattedDate(datePattern));
    }
}
