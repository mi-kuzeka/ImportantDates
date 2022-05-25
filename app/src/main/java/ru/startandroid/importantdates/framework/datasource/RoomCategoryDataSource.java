package ru.startandroid.importantdates.framework.datasource;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.importantdates.core.data.CategoryDataSource;
import ru.startandroid.importantdates.core.domain.Category;
import ru.startandroid.importantdates.framework.db.CategoryDao;
import ru.startandroid.importantdates.framework.db.CategoryEntity;
import ru.startandroid.importantdates.framework.db.ImportantDatesDatabase;

public class RoomCategoryDataSource implements CategoryDataSource {
    private final CategoryDao categoryDao;

    public RoomCategoryDataSource(Context context) {
        categoryDao = ImportantDatesDatabase.getInstance(context).categoryDao();
    }

    @Override
    public void add(Category category) {
        categoryDao.addCategory(getCategoryEntity(category));
    }

    @Override
    public List<Category> readAll() {
        List<CategoryEntity> categoryEntities = categoryDao.getAllCategories();
        List<Category> categories = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryEntities)
            categories.add(getCategory(categoryEntity));

        return categories;
    }

    @Override
    public Category readById(int id) {
        CategoryEntity categoryEntity = categoryDao.getCategoryById(id);
        return getCategory(categoryEntity);
    }

    @Override
    public Category readByName(String name) {
        CategoryEntity categoryEntity = categoryDao.getCategoryByName(name);
        return getCategory(categoryEntity);
    }

    @Override
    public void update(Category category) {
        categoryDao.updateCategory(getCategoryEntity(category));
    }

    @Override
    public void remove(Category category) {
        categoryDao.deleteCategory(getCategoryEntity(category));
    }

    /**
     * Cast {@link CategoryEntity} object to {@link Category} object
     *
     * @param categoryEntity {@link CategoryEntity} object
     * @return {@link Category} object
     */
    private Category getCategory(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.id, categoryEntity.name);
    }

    /**
     * Cast {@link Category} object to {@link CategoryEntity} object
     *
     * @param category {@link Category} object
     * @return {@link CategoryEntity} object
     */
    private CategoryEntity getCategoryEntity(Category category) {
        return new CategoryEntity(category.getId(), category.getName());
    }
}
