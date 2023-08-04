package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import kotlinx.coroutines.Job

data class AddEditNoteState(
    val isSaving: Boolean = false,
    val isSharing: Boolean,
    val isAutoSaveEnabled: Boolean,
    val isArchived: Boolean = false,
)
