package com.ec25p5e.notesapp.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategories(): Flow<List<Category>>

    @Upsert
    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulkCategories(categories: List<Category>?)

    @Query("DELETE FROM category")
    fun clearAll()
}