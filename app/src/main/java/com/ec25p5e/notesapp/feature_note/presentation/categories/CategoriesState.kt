package com.ec25p5e.notesapp.feature_note.presentation.categories

import com.ec25p5e.notesapp.feature_note.domain.model.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val isCreationVisible: Boolean = false
)