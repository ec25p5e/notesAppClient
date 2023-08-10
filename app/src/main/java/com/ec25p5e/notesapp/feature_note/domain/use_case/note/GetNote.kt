package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import com.ec25p5e.notesapp.core.data.local.encryption.AESEncryptor
import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository,
) {

    suspend operator fun invoke(id: Int): Note? {
        val note = repository.getNoteById(id)
        return note /* note?.copy(
            title = AESEncryptor.decrypt(note.title)!!
        ) */
    }
}