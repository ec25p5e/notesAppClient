package com.ec25p5e.notesapp.feature_note.domain.repository

import com.ec25p5e.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun getNotesForArchive(): Flow<List<Note>>

    fun getNoteById(id: Int): Note?

    fun insertNote(note: Note)

    fun deleteNote(note: Note)

    fun archiveNote(id: Int)

    fun dearchiveNote(id: Int)
}