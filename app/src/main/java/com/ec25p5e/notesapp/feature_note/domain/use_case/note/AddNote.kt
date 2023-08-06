package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.data.local.encryption.CryptoManager
import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.domain.models.AddEditNoteResult
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError
import java.io.File
import java.io.FileOutputStream

class AddNote(
    private val repository: NoteRepository,
    // private val cryptoManager: CryptoManager,
) {

    operator fun invoke(note: Note): AddEditNoteResult {
        val titleError = (
                if(note.title.isBlank()) AddEditNoteError.FieldEmpty else null)
        val contentError = if(note.content.isBlank()) AddEditNoteError.FieldEmpty else null

        if(titleError != null || contentError != null)
            return AddEditNoteResult(titleError, contentError)

        /* val file = File("secret.txt")
        if(!file.exists()) {
            file.createNewFile()
        }
        val fos = FileOutputStream(file)

        val noteEncrypted = note.copy(
            title = cryptoManager.encrypt(
                bytes = note.title.encodeToByteArray(),
                outputStream = fos
            ).decodeToString(),
            content = cryptoManager.encrypt(
                bytes = note.content.encodeToByteArray(),
                outputStream = fos
            ).decodeToString()
        ) */


        repository.insertNote(note)
        return AddEditNoteResult(
            result = true
        )
    }
}