package com.ec25p5e.notesapp.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE isArchived = 0")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE isArchived = 1")
    fun getNotesForArchive(): Flow<List<Note>>

    @Query("DELETE FROM note")
    fun clearAll()

    @Query("SELECT * FROM note WHERE isArchived = 0")
    fun getLocalNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE categoryId = :categoryId and isArchived = 0")
    fun getNotesByCategory(categoryId: Int): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE categoryId = :categoryId")
    fun getNotesByCategoryList(categoryId: Int): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): Note?

    @Query("UPDATE note SET isArchived = 1 WHERE id = :id")
    fun archiveNote(id: Int)

    @Query("UPDATE note SET isArchived = 0 WHERE id = :id")
    fun dearchiveNote(id: Int)

    @Query("SELECT isCopied FROM note where id = :id")
    fun getNumberOfCopy(id: Int): Int

    @Query("UPDATE note SET isLocked = 1 WHERE id = :id")
    fun lockNote(id: Int)

    @Query("UPDATE note SET isLocked = 0 WHERE id = :id")
    fun unLockNote(id: Int)

    @Query("SELECT COUNT(id) FROM note WHERE categoryId = :categoryId")
    fun getCountNoteForCategory(categoryId: Int): Int

    @Upsert
    fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulkNote(notes: List<Note>?)

    @Delete
    fun deleteNote(note: Note)
}