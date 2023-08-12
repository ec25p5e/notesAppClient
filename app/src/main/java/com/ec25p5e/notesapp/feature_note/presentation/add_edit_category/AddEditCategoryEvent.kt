package com.ec25p5e.notesapp.feature_note.presentation.add_edit_category

import androidx.compose.ui.focus.FocusState
import com.ec25p5e.notesapp.feature_note.presentation.add_edit_note.AddEditNoteEvent

sealed class AddEditCategoryEvent {

    data class EnteredName(val value: String): AddEditCategoryEvent()

    data class ChangeNameFocus(val focusState: FocusState): AddEditCategoryEvent()
    data class ChangeColor(val color: Int): AddEditCategoryEvent()
    data object SaveCategory: AddEditCategoryEvent()
    data object IsSaveCategory: AddEditCategoryEvent()
}