package ru.startandroid.importantdates.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CategoryUnitTest {
    @Test
    public void givenCategoryId1NameBirthday_whenGetNameIsBirthday_thenIsTrue() {
        String categoryName = "Birthday";
        Category category = new Category(1, categoryName);
        assertEquals(categoryName, category.getName());
    }

    @Test
    public void givenCategoryId1NameBirthday_whenGetIdIs2_thenIsFalse() {
        Category category = new Category(1, "Birthday");
        assertNotEquals(2, category.getId());
    }
}
