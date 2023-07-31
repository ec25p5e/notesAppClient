package com.ec25p5e.notesapp.feature_note.domain.use_case.note

import android.content.res.Resources
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.feature_note.domain.exceptions.InvalidNoteException
import com.ec25p5e.notesapp.feature_note.domain.model.Note
import com.ec25p5e.notesapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank())
            throw InvalidNoteException(Resources.getSystem().getString(R.string.error_note_title_empty))

        if(note.content.isBlank())
            throw InvalidNoteException(Resources.getSystem().getString(R.string.error_note_content_empty))

        repository.insertNote(note)
    }
}