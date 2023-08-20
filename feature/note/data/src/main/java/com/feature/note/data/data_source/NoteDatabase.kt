package com.feature.note.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.feature.note.domain.model.Note
import com.feature.note.domain.model.Category
import com.feature.note.data.local.ArrayListConverter
import com.feature.note.data.local.ListPairConverter

@Database(
    entities = [Note::class, Category::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    ArrayListConverter::class,
    ListPairConverter::class,
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}