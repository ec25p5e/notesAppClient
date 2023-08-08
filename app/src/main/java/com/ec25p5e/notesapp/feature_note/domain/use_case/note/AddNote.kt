package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.feature_note.domain.models.AddEditNoteResult
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError

class AddNote(
    private val repository: NoteRepository,
) {

    operator fun invoke(note: Note): AddEditNoteResult {
        val titleError = (if(note.title.isBlank()) AddEditNoteError.FieldEmpty else null)
        val contentError = if(note.content.isBlank()) AddEditNoteError.FieldEmpty else null

        if(titleError != null || contentError != null)
            return AddEditNoteResult(titleError, contentError)

        val encryptedNote = note.copy(
            title = AESEncryptor.encrypt(note.title)!!,
            content = AESEncryptor.encrypt(note.content)!!
        )

        repository.insertNote(encryptedNote)
        return AddEditNoteResult(
            result = true
        )
    }
}