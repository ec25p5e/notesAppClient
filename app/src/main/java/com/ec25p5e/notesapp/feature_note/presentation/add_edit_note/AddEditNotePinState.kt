package com.ec25p5e.notesapp.feature_note.presentation.add_edit_note

import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditNoteError

data class AddEditNotePinState(
    val isPinError: Boolean = false,
    val isNoteUnlocked: Boolean = false
)
