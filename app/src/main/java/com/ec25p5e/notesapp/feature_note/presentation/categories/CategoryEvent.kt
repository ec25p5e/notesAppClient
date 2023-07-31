package com.ec25p5e.notesapp.feature_note.presentation.categories

import androidx.compose.ui.focus.FocusState
import com.ec25p5e.notesapp.feature_note.domain.model.Category

sealed class CategoryEvent {
    data class EnteredTitle(val value: String): CategoryEvent()
    data class ChangeTitleFocus(val focusState: FocusState): CategoryEvent()
    data class ChangeColor(val color: Int): CategoryEvent()

    data class DeleteCategory(val category: Category): CategoryEvent()

    object RestoreCategory: CategoryEvent()
}