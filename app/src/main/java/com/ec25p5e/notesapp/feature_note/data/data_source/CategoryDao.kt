package com.ec25p5e.notesapp.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM category WHERE id = :categoryId")
    fun getCategoryById(categoryId: Int): Category

    @Upsert
    fun insertCategory(category: Category)

    @Upsert
    fun insertBulkCategories(categories: List<Category>?)

    @Delete
    fun deleteCategory(category: Category)

    @Query("DELETE FROM category")
    fun clearAll()
}