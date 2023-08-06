package com.ec25p5e.notesapp.feature_task.presentation.util

sealed class AddEditTaskError: Error() {
    data object FieldEmpty : AddEditTaskError()
    data object InputTooShort: AddEditTaskError()
}