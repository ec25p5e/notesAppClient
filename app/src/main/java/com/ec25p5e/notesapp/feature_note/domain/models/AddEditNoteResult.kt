package com.ec25p5e.notesapp.feature_note.domain.models

import com.ec25p5e.notesapp.core.util.SimpleResource
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError

data class AddEditNoteResult(
    val titleError: AddEditNoteError? = null,
    val contentError: AddEditNoteError? = null,
    val result: SimpleResource? = null
) {

    fun isCorrect(): Boolean {
        if(titleError == null && contentError == null)
            return true

        return false
    }
}
