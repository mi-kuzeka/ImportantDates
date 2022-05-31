package ru.startandroid.importantdates.framework.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addCategory(CategoryEntity category);

    @Query("SELECT * FROM categories")
    public List<CategoryEntity> getAllCategories();

    @Query("SELECT * FROM categories WHERE id = :id")
    public CategoryEntity getCategoryById(int id);

    @Query("SELECT DISTINCT id, name FROM categories WHERE name = :name COLLATE NOCASE")
    public CategoryEntity getCategoryByName(String name);

    @Update
    public void updateCategory(CategoryEntity category);

    @Delete
    public void deleteCategory(CategoryEntity category);
}
