package com.ec25p5e.notesapp.feature_todo.presentation.util

sealed class AddEditTaskError: Error() {
    data object FieldEmpty : AddEditTaskError()
    data object InputTooShort: AddEditTaskError()
}