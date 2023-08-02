package com.ec25p5e.notesapp.feature_note.presentation.util

sealed class AddEditNoteError: Error() {
    object FieldEmpty : AddEditNoteError()
    object InputTooShort: AddEditNoteError()
}
