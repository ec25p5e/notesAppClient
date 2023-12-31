package com.ec25p5e.notesapp.feature_note.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ec25p5e.notesapp.core.data.local.converters.ArrayListConverter
import com.ec25p5e.notesapp.core.data.local.converters.ListPairConverter
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.domain.models.Note

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