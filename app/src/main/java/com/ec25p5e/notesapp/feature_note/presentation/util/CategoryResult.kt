package com.ec25p5e.notesapp.feature_note.presentation.util

class CategoryResult(
    val titleError: AddEditNoteError? = null,
    val result: Boolean = false
) {

    fun isCorrect(): Boolean {
        if(titleError == null)
            return true

        return false
    }
}