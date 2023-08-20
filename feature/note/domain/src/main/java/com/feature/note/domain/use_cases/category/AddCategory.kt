package com.feature.note.domain.use_cases.category

import com.ec25p5e.notesapp.core.util.Constants
import com.ec25p5e.notesapp.feature_note.domain.models.Category
import com.ec25p5e.notesapp.feature_note.presentation.util.CategoryResult
import com.ec25p5e.notesapp.feature_note.domain.repository.CategoryRepository
import com.ec25p5e.notesapp.feature_note.presentation.util.AddEditCategoryError

class AddCategory(
    private val repository: CategoryRepository,
) {

    operator fun invoke(category: Category): CategoryResult {
        val titleError = if(category.name.isBlank()) {
            AddEditCategoryError.FieldEmpty
        } else if(category.name.length < Constants.MIN_CATEGORY_TITLE_LENGTH) {
            AddEditCategoryError.InputTooShort
        } else {
            null
        }

        if(titleError != null)
            return CategoryResult(titleError)

        repository.insertCategory(category)
        return CategoryResult(
            result = true
        )
    }
}