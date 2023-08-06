package com.ec25p5e.notesapp.feature_todo.presentation.add_edit_task

import com.ec25p5e.notesapp.feature_settings.domain.models.Unlock

data class AddEditTaskState(
    val isSaving: Boolean = false,
    val isSharing: Boolean,
    val isAutoSaveEnabled: Boolean,
)
