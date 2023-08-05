package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import com.ec25p5e.notesapp.feature_settings.domain.models.Unlock
import com.ec25p5e.notesapp.feature_settings.domain.models.UnlockMethod

data class AddEditNoteState(
    val isSaving: Boolean = false,
    val isCategoryModalOpen: Boolean = false,
    val isSharing: Boolean,
    val isAutoSaveEnabled: Boolean,
    val isArchived: Boolean = false,
    val lockedMode: Unlock = Unlock(),
)
