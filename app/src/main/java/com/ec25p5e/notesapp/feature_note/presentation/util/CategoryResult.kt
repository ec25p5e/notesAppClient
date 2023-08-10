package com.ec25p5e.notesapp.feature_note.presentation.util

import com.ec25p5e.notesapp.feature_auth.presentation.util.AuthError

class CategoryResult(
    val titleError: AuthError? = null,
    val result: Boolean = false
) {

    fun isCorrect(): Boolean {
        if(titleError == null)
            return true

        return false
    }
}