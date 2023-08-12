package com.ec25p5e.notesapp.feature_note.presentation.categories

import androidx.compose.ui.focus.FocusState
import com.ec25p5e.notesapp.feature_note.domain.models.Category

sealed class CategoryEvent {
    data class EnteredTitle(val value: String): CategoryEvent()
    data class ChangeTitleFocus(val focusState: FocusState): CategoryEvent()

    data class ChangeCategorySelected(val id: Int): CategoryEvent()
    data class SetToDelete(val category: Category): CategoryEvent()

    data object RestoreCategory: CategoryEvent()
    data object SaveCategory: CategoryEvent()
    data object IsCreateCategory: CategoryEvent()
    data object ToggleCategoryDelete: CategoryEvent()
    data object DeleteCategory: CategoryEvent()
}