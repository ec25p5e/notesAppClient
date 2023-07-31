package com.ec25p5e.notesapp.feature_note.data.repository

import com.ec25p5e.notesapp.feature_note.data.data_source.NoteDao
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getNotesForArchive(): Flow<List<Note>> {
        return dao.getNotesForArchive()
    }

    override fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun archiveNote(id: Int) {
        dao.archiveNote(id)
    }

    override fun dearchiveNote(id: Int) {
        dao.dearchiveNote(id)
    }
}