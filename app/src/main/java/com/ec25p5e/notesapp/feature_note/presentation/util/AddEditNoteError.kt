package com.ec25p5e.notesapp.feature_note.presentation.util

sealed class AddEditNoteError: Error() {
    data object FieldEmpty : AddEditNoteError()
    data object InputTooShort: AddEditNoteError()
}
