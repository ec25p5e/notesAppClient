package com.ec25p5e.notesapp.feature_note.presentation.categories

import com.ec25p5e.notesapp.feature_note.domain.models.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val isDeleting: Boolean = false,
    val categoryToDelete: Category? = null
)