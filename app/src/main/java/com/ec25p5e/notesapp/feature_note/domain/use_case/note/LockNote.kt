package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class LockNote(
    private val repository: NoteRepository
) {

    operator fun invoke(noteId: Int) {
        repository.lockNote(noteId)
    }
}