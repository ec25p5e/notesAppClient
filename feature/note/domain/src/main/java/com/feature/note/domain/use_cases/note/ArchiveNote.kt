package com.feature.note.domain.use_cases.note

import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class ArchiveNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int) {
        repository.archiveNote(id)
    }
}