package ru.startandroid.importantdates.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EventAgeHelperUnitTest {
    @Test
    public void givenEventYearMinus1AndCurrentYear2022_whenGetAgeIs29_thenIsFalse() {
        assertNotEquals(29, EventAgeHelper.getAge(-1, 2022));
    }

    @Test
    public void givenEventYear1992AndCurrentYear2022_whenGetAgeIs30_thenIsTrue() {
        assertEquals(30, EventAgeHelper.getAge(1992, 2022));
    }

    @Test
    public void givenEventYear1992AndCurrentYear2022_whenGetAgeIs32_thenIsFalse() {
        assertNotEquals(32, EventAgeHelper.getAge(1992, 2022));
    }

    @Test
    public void givenEventYear1992AndCurrentYear2027_whenGetAgeIs35_thenIsTrue() {
        assertEquals(35, EventAgeHelper.getAge(1992, 2027));
    }
}
