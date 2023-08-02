package com.ec25p5e.notesapp.feature_note.domain.repository

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotesForArchive(): Flow<List<Note>>

    suspend fun getAllNotes(fetchFromRemote: Boolean): Resource<Flow<List<Note>>>

    suspend fun pushNotes(): SimpleResource

    fun getNotesByCategory(categoryId: Int): Flow<List<Note>>

    fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note): SimpleResource

    suspend fun deleteNote(note: Note): SimpleResource

    fun archiveNote(id: Int)

    fun dearchiveNote(id: Int)
}