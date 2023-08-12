package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteResult
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError

class AddNote(
    private val repository: NoteRepository,
) {

    operator fun invoke(note: Note): AddEditNoteResult {
        val titleError =
            if(note.title.isBlank()) {
                AddEditNoteError.FieldEmpty
            } else if(note.title.length < Constants.MIN_NOTE_TITLE_LENGTH) {
                AddEditNoteError.InputTooShort
            } else {
                null
            }

        val contentError = if(note.content.isBlank()) AddEditNoteError.FieldEmpty else null

        if(titleError != null || contentError != null)
            return AddEditNoteResult(titleError, contentError)

        repository.insertNote(note)
        return AddEditNoteResult(
            result = true
        )
    }
}