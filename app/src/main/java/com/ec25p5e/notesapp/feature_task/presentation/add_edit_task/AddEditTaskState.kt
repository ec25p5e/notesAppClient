package com.ec25p5e.notesapp.feature_task.presentation.add_edit_task

data class AddEditTaskState(
    val isSaving: Boolean = false,
    val isSharing: Boolean,
    val isAutoSaveEnabled: Boolean,
)
