package ru.startandroid.importantdates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Assert;
import org.junit.Test;

import ru.startandroid.importantdates.presentation.EventAgeHelper;

public class EventAgeHelperUnitTest {
    @Test
    public void givenEventYearMinus1AndCurrentYear2022_whenGetAgeIs29_thenIsFalse() {
        Assert.assertNotEquals(29, EventAgeHelper.getAge(-1, 2022));
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
