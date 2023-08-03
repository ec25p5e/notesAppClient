package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class DearchiveNote(
    private val repository: NoteRepository
) {

    operator fun invoke(id: Int) {
        repository.dearchiveNote(id)
    }
}