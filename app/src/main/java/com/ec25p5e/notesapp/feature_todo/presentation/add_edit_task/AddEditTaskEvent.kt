package com.ec25p5e.notesapp.feature_todo.presentation.add_edit_task

import androidx.compose.ui.focus.FocusState
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent

sealed class AddEditTaskEvent {
    data class EnteredTitle(val value: String): AddEditTaskEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTaskEvent()
    data class EnteredContent(val value: String): AddEditTaskEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditTaskEvent()
    data class ChangeColor(val color: Int): AddEditTaskEvent()

    data object SaveTask: AddEditTaskEvent()
}
