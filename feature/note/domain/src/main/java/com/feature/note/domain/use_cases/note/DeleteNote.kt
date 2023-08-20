package com.feature.note.domain.use_cases.note

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {

    operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}