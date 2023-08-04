package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.domain.models.AddEditNoteResult
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError

class AddNote(
    private val repository: NoteRepository
) {

    operator fun invoke(note: Note): AddEditNoteResult {
        val titleError = (
                if(note.title.isBlank()) AddEditNoteError.FieldEmpty else null)
        val contentError = if(note.content.isBlank()) AddEditNoteError.FieldEmpty else null

        if(titleError != null || contentError != null)
            return AddEditNoteResult(titleError, contentError)

        repository.insertNote(note)
        return AddEditNoteResult(
            result = true
        )
    }
}