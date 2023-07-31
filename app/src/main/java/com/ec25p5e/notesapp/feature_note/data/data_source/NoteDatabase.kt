package com.ec25p5e.notesapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ec25p5e.notesapp.feature_note.domain.model.Category
import com.ec25p5e.notesapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class, Category::class],
    version = 2
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao
    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}